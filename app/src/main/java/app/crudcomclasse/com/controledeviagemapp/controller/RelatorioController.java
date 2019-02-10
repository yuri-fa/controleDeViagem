package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.databaseadapter.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class RelatorioController extends DataBaseAdapter {
    public RelatorioController(Context context) {
        super(context);
    }

    public List<Viagem> pesquisarViagens(){
        List<Viagem> viagemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =" SELECT * FROM viagem" +
                " LEFT JOIN veiculo on viveiculo = veinumsequencial" +
                " LEFT JOIN motorista on vimotorista = motnumsequencial";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
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

                String queryPlaca = "SELECT * FROM placa where plaviagem = "+ viagem.getNumSequencial();
                Cursor cursorPlaca = db.rawQuery(queryPlaca,null);
                if (cursorPlaca.moveToFirst()){
                    do{
                        Placa placa = new Placa();
                        String pesoTemp = cursorPlaca.getString(cursorPlaca.getColumnIndex("plapeso"));
                        BigDecimal peso = new BigDecimal(pesoTemp);
                        String serial = cursorPlaca.getString(cursorPlaca.getColumnIndex("plaserial"));
                        Integer plaNumSequencial = Integer.parseInt(cursorPlaca.getString(cursorPlaca.getColumnIndex("planumsequencial")));
                        placa.setPlaNumSequencial(plaNumSequencial);
                        placa.setPeso(peso);
                        placa.setSerial(serial);
                        viagem.getConjuntoDePlacas().add(placa);
                    }while(cursor.moveToNext());
                }
                viagemList.add(viagem);
            }while(cursor.moveToNext());
        }
        return viagemList;
    }
}
