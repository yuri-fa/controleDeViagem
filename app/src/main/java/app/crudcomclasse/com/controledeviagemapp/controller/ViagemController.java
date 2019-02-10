package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.databaseadapter.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class ViagemController extends DataBaseAdapter {
    public ViagemController(Context context) {
        super(context);
    }

    public boolean inserirViagem(Viagem viagem){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean valido = true;
        ContentValues contentValues = new ContentValues();
        contentValues.put("viveiculo",viagem.getVeiculo().getVeiNumSequencial());
        contentValues.put("vimotorista",viagem.getVeiculo().getMotorista().getMotNumSequencial());
        contentValues.put("vidthrinicio",viagem.getDthrViagem().getTime());

        Long idViagem = db.insert("viagem",null,contentValues);
        if (idViagem != null){
            db.close();
                boolean isInsertPlaca = inserirPlaca(viagem.getConjuntoDePlacas(),idViagem);
                if (!isInsertPlaca){
                    db = this.getWritableDatabase();
                    valido = false;
                    String queryDelete = "delete viagem where vinumsequencial =" + viagem.getNumSequencial();
                    db.execSQL(queryDelete);
                    db.close();
                }
        }
        return valido;
    }

    public boolean inserirPlaca(List<Placa> placas,Long idViagem) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean valido = true;
        for (int i = 0;i< placas.size();i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("plaviagem",idViagem.intValue());
            contentValues.put("plaserial",placas.get(i).getSerial());
            contentValues.put("plapeso",placas.get(i).getPeso().toString());
            boolean isInsert = db.insert("placa",null,contentValues) > 0;
            if (!isInsert){
                db.close();
                valido = false;
                break;
            }
        }
        if (!valido){
            try {
                String query = "delete placa where plaviagem =" + placas.get(0).getViagem();
                db.execSQL(query);
            }catch(Exception e){
            }
        }
        db.close();
        return valido;
    }

    public ArrayList<Viagem> pesquisarViagens(){
        ArrayList<Viagem> viagemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM viagem " +
                        "LEFT OUTER JOIN motorista on vimotorista = motnumsequencial " +
                        "LEFT OUTER JOIN veiculo on viveiculo = veinumsequencial ";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                Viagem viagem = new Viagem();
                viagem.setConjuntoDePlacas(new ArrayList<Placa>());
                Motorista motorista = new Motorista();
                Veiculo veiculo = new Veiculo();
                viagem.setNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("vinumsequencial"))));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(cursor.getString(cursor.getColumnIndex("vidthrinicio"))));
                viagem.setDthrViagem(calendar.getTime());
                motorista.setMotNomeCompleto(cursor.getString(cursor.getColumnIndex("motnomecompleto")));
                veiculo.setPlaca(cursor.getString(cursor.getColumnIndex("veiplaca")));
                String queryDePlaca = "SELECT * FROM placa where plaviagem = "+viagem.getNumSequencial();
                Cursor cursorPlaca = db.rawQuery(queryDePlaca, null);
                if (cursorPlaca.moveToFirst()){
                    do{
                        Placa placa = new Placa();
                        placa.setPlaNumSequencial(Integer.parseInt(cursorPlaca.getString(cursorPlaca.getColumnIndex("planumsequencial"))));
                        placa.setSerial(cursorPlaca.getString(cursorPlaca.getColumnIndex("plaserial")));
                        placa.setPeso(new BigDecimal(cursorPlaca.getString(cursorPlaca.getColumnIndex("plapeso"))));

                        viagem.getConjuntoDePlacas().add(placa);
                    }while (cursorPlaca.moveToNext());
                }
                viagem.setMotorista(motorista);
                viagem.setVeiculo(veiculo);
                viagemList.add(viagem);
            }while(cursor.moveToNext());
        }
        db.close();
        return viagemList;
    }

}
