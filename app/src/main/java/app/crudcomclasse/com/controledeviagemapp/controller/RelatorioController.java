package app.crudcomclasse.com.controledeviagemapp.controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.crudcomclasse.com.controledeviagemapp.databaseadapter.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Relatorio;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

import static android.content.Context.MODE_PRIVATE;

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

    public boolean exportTheDB(Date dataInicio,Date dataFinal){
        Map<String,Relatorio> listMap = new HashMap<>();
        FileWriter myFile = null;
        PrintWriter myOutWriter = null;
        String cabecalho = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String TimeStampDB = sdf.format(cal.getTime());
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
            String dataAtual = String.valueOf(new Date().getTime());
            File teste = new File(diretorio, dataAtual+".csv");
            teste.setExecutable(true);
            teste.setReadable(true);
            teste.setWritable(true);

            myFile = new FileWriter(teste);
            myOutWriter = new PrintWriter(myFile);
            cabecalho = "MOTORISTA,VEICULO";

            String query = "select distinct motnomecompleto,veiplaca,sum(placas.plapeso) as peso_total from viagem" +
                    " left outer join motorista as mot on vimotorista = mot.motnumsequencial" +
                    " left outer join veiculo as vei on viveiculo = veinumsequencial" +
                    " left outer join placa as placas on vinumsequencial = placas.plaviagem" +
                    " group by motnomecompleto";

            Cursor cursor = sampleDB.rawQuery(query,null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String motorista = cursor.getString(cursor.getColumnIndex("motnomecompleto"));
                        String veiculo = cursor.getString(cursor.getColumnIndex("veiplaca"));
                        String pesoTotal = cursor.getString(cursor.getColumnIndex("peso_total"));
                        Relatorio relatorio = new Relatorio(motorista, veiculo, pesoTotal);
                        relatorio.setPlacas(new ArrayList<String>());

                        String queryPlaca = "select mot.motnomecompleto,sum(placas.plapeso) as pesoViagem" +
                                " from motorista as mot" +
                                " left outer join viagem as vi" +
                                " on mot.motnumsequencial = vi.vimotorista" +
                                " left outer join veiculo as vei" +
                                " on vi.viveiculo = vei.veinumsequencial" +
                                " left outer join placa as placas" +
                                " on vinumsequencial = placas.plaviagem" +
                                " where mot.motnomecompleto = '"+ motorista + "'"+
                                " group by vi.vinumsequencial,vei.veiplaca";

                        Cursor cursorPlaca = sampleDB.rawQuery(queryPlaca, null);
                        if (cursorPlaca != null) {
                            if (cursorPlaca.moveToFirst()) {
                                do {
                                    relatorio.getPlacas().add(cursorPlaca.getString(cursorPlaca.getColumnIndex("pesoViagem")));
                                } while (cursorPlaca.moveToNext());
                            }
                        }
                        listMap.put(relatorio.getMotorista(), relatorio);
                        if(maiorQuantidadeDeViagens < relatorio.getPlacas().size()){
                            maiorQuantidadeDeViagens = relatorio.getPlacas().size();
                        }
                    }while (cursor.moveToNext());
                    cursor.close();
                }
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