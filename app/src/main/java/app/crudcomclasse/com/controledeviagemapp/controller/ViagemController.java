package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.databaseadapter.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

class ViagemController extends DataBaseAdapter {
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
}
