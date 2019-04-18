package app.crudcomclasse.com.controledeviagemapp.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.crudcomclasse.com.controledeviagemapp.util.ControleViagemUtil;
import app.crudcomclasse.com.controledeviagemapp.util.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Relatorio;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class RelatorioController extends DataBaseAdapter {
    private Context context;
    public RelatorioController(Context context) {
        super(context);
        this.context = context;
    }

    public List<Viagem> pesquisarViagens() {
        List<Viagem> viagemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM viagem" +
                " LEFT JOIN veiculo on viveiculo = veinumsequencial" +
                " LEFT JOIN motorista on vimotorista = motnumsequencial";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Viagem viagem = new Viagem();
                Veiculo veiculo = new Veiculo();
                Motorista motorista = new Motorista();
                viagem.setMotorista(motorista);
                viagem.setVeiculo(veiculo);
                viagem.setConjuntoDePlacas(new ArrayList<Placa>());
                Integer viNumSequencial = Integer.parseInt(cursor.getString(cursor.getColumnIndex("vinumsequencial")));
                Long data = Long.parseLong(cursor.getString(cursor.getColumnIndex("vidthrinicio")));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(data);
                Date date = calendar.getTime();
                String veiPlaca = cursor.getString(cursor.getColumnIndex("veiplaca"));
                String nomeMotorista = cursor.getString(cursor.getColumnIndex("motnomecompleto"));
                viagem.setNumSequencial(viNumSequencial);
                viagem.setDthrViagem(date);
                veiculo.setPlaca(veiPlaca);
                motorista.setMotNomeCompleto(nomeMotorista);

                String queryPlaca = "SELECT * FROM placa where plaviagem = " + viagem.getNumSequencial();
                Cursor cursorPlaca = db.rawQuery(queryPlaca, null);
                if (cursorPlaca.moveToFirst()) {
                    do {
                        Placa placa = new Placa();
                        String pesoTemp = cursorPlaca.getString(cursorPlaca.getColumnIndex("plapeso"));
                        BigDecimal peso = new BigDecimal(pesoTemp);
                        String serial = cursorPlaca.getString(cursorPlaca.getColumnIndex("plaserial"));
                        Integer plaNumSequencial = Integer.parseInt(cursorPlaca.getString(cursorPlaca.getColumnIndex("planumsequencial")));
                        placa.setPlaNumSequencial(plaNumSequencial);
                        placa.setPeso(peso);
                        placa.setSerial(serial);
                        viagem.getConjuntoDePlacas().add(placa);
                    } while (cursor.moveToNext());
                }
                viagemList.add(viagem);
            } while (cursor.moveToNext());
        }
        return viagemList;
    }

    public boolean exportarRelatorio(Date dataInicio,Date dataFinal){
        Map<String,Relatorio> listMap = new HashMap<>();
        FileWriter myFile = null;
        PrintWriter myOutWriter = null;
        String cabecalho = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String TimeStampDB = sdf.format(new Date());
        SQLiteDatabase sampleDB = this.getReadableDatabase();
        int maiorQuantidadeDeViagens = 0;

        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Sem permissao para armazenagem", Toast.LENGTH_SHORT).show();
            return false;
        }
        File diretorio = new File(Environment.getExternalStorageDirectory()+"/relatorios/");
        try {
            if (!diretorio.mkdir()){
                diretorio.mkdirs();
            }
            String dataAtual = ControleViagemUtil.formatarDataParaString(new Date());
            File teste = new File(diretorio, dataAtual+".csv");
            teste.setExecutable(true);
            teste.setReadable(true);
            teste.setWritable(true);

            myFile = new FileWriter(teste);
            myOutWriter = new PrintWriter(myFile);
            cabecalho = "MOTORISTA,VEICULO";
            long dataViagemInicio = dataInicio.getTime();
            long dataViagemFim = dataFinal.getTime();
            String query = "select distinct motnumsequencial,motnomecompleto,veinumsequencial,veiplaca," +
            " sum(plapeso) as peso_total from viagem" +
            " left outer join motorista on vimotorista = motnumsequencial"+
            " left outer join veiculo on viveiculo = veinumsequencial" +
            " left outer join placa on vinumsequencial = plaviagem"+
            " where vidthrinicio >= "+dataViagemInicio+
            " and vidthrinicio <= "+ dataViagemFim +
            " group by vimotorista,viveiculo";

            Cursor cursor = sampleDB.rawQuery(query,null);
            if (cursor.moveToFirst()) {
                do {
                    String motorista = cursor.getString(cursor.getColumnIndex("motnomecompleto"));
                    String motoristaId = cursor.getString(cursor.getColumnIndex("motnumsequencial"));
                    String veiculo = cursor.getString(cursor.getColumnIndex("veiplaca"));
                    String veiculoId = cursor.getString(cursor.getColumnIndex("veinumsequencial"));
                    String pesoTotal = cursor.getString(cursor.getColumnIndex("peso_total"));
                    Relatorio relatorio = new Relatorio(motorista, veiculo, pesoTotal);
                    relatorio.setPlacas(new ArrayList<String>());
                    String queryPlaca = "select sum(plapeso) as peso_viagem from motorista"+
                    " left outer join viagem on motnumsequencial = vimotorista"+
                    " left outer join veiculo on viveiculo = veinumsequencial"+
                    " left outer join placa on vinumsequencial = plaviagem"+
                    " where motnumsequencial = "+ motoristaId +
                    " and veinumsequencial = "+ veiculoId +
                    " and vidthrinicio >= "+ dataViagemInicio+
                    " and vidthrinicio <= "+ dataViagemFim +
                    " group by vinumsequencial,viveiculo";

                    Cursor cursorPlaca = sampleDB.rawQuery(queryPlaca, null);
                    if (cursorPlaca.moveToFirst()) {
                        do {
                            relatorio.getPlacas().add(cursorPlaca.getString(cursorPlaca.getColumnIndex("peso_viagem")));
                        } while (cursorPlaca.moveToNext());
                    }
                    listMap.put(relatorio.getMotorista(), relatorio);
                    if(maiorQuantidadeDeViagens < relatorio.getPlacas().size()){
                        maiorQuantidadeDeViagens = relatorio.getPlacas().size();
                    }
                }while (cursor.moveToNext());
                cursor.close();
            }
        } catch (IOException se) {
            se.printStackTrace();
            return false;
        }
        for (int i=1;i <= maiorQuantidadeDeViagens;i++){
            cabecalho += ",peso_viagem";
        }
        cabecalho += ",peso_total";
        myOutWriter.append(cabecalho);
        myOutWriter.append("\n");
        for (Relatorio relatorio : listMap.values()){
            myOutWriter.append(relatorio.dadosRelatorio(maiorQuantidadeDeViagens));
            myOutWriter.append("\n");
        }
        try {
            myOutWriter.close();
            myFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            sampleDB.close();
        }
        return true;
    }

    private String dataConvertida(String data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(data));
        return dateFormat.format(calendar.getTime());
    }
}
