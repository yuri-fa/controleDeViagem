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

import app.crudcomclasse.com.controledeviagemapp.model.PdfExport;
import app.crudcomclasse.com.controledeviagemapp.util.ControleViagemUtil;
import app.crudcomclasse.com.controledeviagemapp.util.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Relatorio;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;
import app.crudcomclasse.com.controledeviagemapp.view.ExportacaoRelatorio;

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

    public String exportarRelatorio(Long dataInicio,Long dataFinal){
        List<Relatorio> relatorioList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "select distinct motnumsequencial,motnomecompleto,veinumsequencial,veiplaca," +
        " sum(plapeso) as peso_total from viagem" +
        " left outer join motorista on vimotorista = motnumsequencial"+
        " left outer join veiculo on viveiculo = veinumsequencial" +
        " left outer join placa on vinumsequencial = plaviagem"+
        " where vidthrinicio >= "+dataInicio+
        " and vidthrinicio <= "+ dataFinal +
        " group by vimotorista,viveiculo";

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                String motorista = cursor.getString(cursor.getColumnIndex("motnomecompleto"));
                String motoristaId = cursor.getString(cursor.getColumnIndex("motnumsequencial"));
                String veiculo = cursor.getString(cursor.getColumnIndex("veiplaca"));
                String veiculoId = cursor.getString(cursor.getColumnIndex("veinumsequencial"));
                String pesoTotal = cursor.getString(cursor.getColumnIndex("peso_total"));
                Relatorio relatorio = new Relatorio(motorista, veiculo, pesoTotal);
                relatorio.setPlacas(new ArrayList<String>());

                String queryPlaca = "select vinumsequencial,sum(plapeso) as peso_viagem from placa " +
                    "left outer join viagem on plaviagem = vinumsequencial " +
                    "where vimotorista =  " + motoristaId +" "+
                    "and viveiculo = 1 and " + veiculoId +" "+
                    "and (vidthrinicio >= "+ dataInicio +" and vidthrinicio <= "+ dataFinal +") " +
                    "group by vinumsequencial";


                Cursor cursorPlaca = sqLiteDatabase.rawQuery(queryPlaca, null);
                if (cursorPlaca.moveToFirst()) {
                    do {
                        relatorio.getPlacas().add(cursorPlaca.getString(cursorPlaca.getColumnIndex("peso_viagem")));
                        relatorioList.add(relatorio);
                    } while (cursorPlaca.moveToNext());
                }
            }while (cursor.moveToNext());
            cursor.close();
        }
        sqLiteDatabase.close();
        return new PdfExport().gerarRelatorio(relatorioList,dataInicio,context);
    }

    private String dataConvertida(String data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(data));
        return dateFormat.format(calendar.getTime());
    }
}
