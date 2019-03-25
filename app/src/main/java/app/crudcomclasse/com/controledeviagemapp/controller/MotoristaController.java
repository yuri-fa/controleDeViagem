package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.util.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;

public class MotoristaController extends DataBaseAdapter {

    public MotoristaController(Context context) {
        super(context);
    }

    public Boolean inserirMotorista(Motorista motorista){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("motnomecompleto",motorista.getMotNomeCompleto());
        values.put("motnomeguerra",motorista.getMotNomeGuerra());
        values.put("motcpf",motorista.getMotCpf());
        boolean isInsert = db.insert("motorista",null,values) > 0;
        db.close();
        return isInsert;
    }

    public List<Motorista> pegarTodos(){
        List<Motorista> motoristas = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from motorista";

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){

            do{

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("motnumsequencial")));
                String nomeCompleto = cursor.getString(cursor.getColumnIndex("motnomecompleto"));
                String nomeDeGuerra = cursor.getString(cursor.getColumnIndex("motnomeguerra"));
                String cpf = cursor.getString(cursor.getColumnIndex("motcpf"));

                Motorista motorista = new Motorista();
                motorista.setMotNumSequencial(id);
                motorista.setMotNomeCompleto(nomeCompleto);
                motorista.setMotNomeGuerra(nomeDeGuerra);
                motorista.setMotCpf(cpf);

                motoristas.add(motorista);

            }while(cursor.moveToNext());
        }
        db.close();
        return motoristas;

    }


    public Motorista pegarPorId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Motorista motorista = new Motorista();
        String query = "select * from motorista where motnumsequencial = " + id;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            motorista.setMotNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("motnumsequencial"))));
            motorista.setMotNomeCompleto(cursor.getString(cursor.getColumnIndex("motnomecompleto")));
            motorista.setMotNomeGuerra(cursor.getString(cursor.getColumnIndex("motnomeguerra")));
            motorista.setMotCpf(cursor.getString(cursor.getColumnIndex("motcpf")));
        }else{
            return null;
        }
        db.close();
        return motorista;
    }

    public boolean update(Motorista motorista){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("motnomecompleto",motorista.getMotNomeCompleto());
        values.put("motnomeguerra",motorista.getMotNomeGuerra());
        String where = "motnumsequencial = ?";
        String [] whereArgs = {motorista.getMotNumSequencial().toString()};
        boolean isUpdate = db.update("motorista",values,where,whereArgs) > 0;
        db.close();
        return isUpdate;
    }

    public boolean deletarMotorista(int id) {
        SQLiteDatabase db = getWritableDatabase();
        boolean isDelete = db.delete("motorista","motnumsequencial = "+id,null) > 0;
        db.close();
        return isDelete;
    }
}
