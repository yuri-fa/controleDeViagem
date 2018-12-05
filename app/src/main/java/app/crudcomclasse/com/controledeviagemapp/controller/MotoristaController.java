package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.DAO.MotoristaDAO;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;

public class MotoristaController extends MotoristaDAO {

    public MotoristaController(Context context) {
        super(context);
    }

    public Boolean inserirMotorista(Motorista motorista){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("motnomecompleto",motorista.getMotNomeCompleto());
        values.put("motnomeguerra",motorista.getMotNomeGuerra());
        values.put("motcpf",motorista.getMotCpf());

        return db.insert("motorista",null,values) > 0;
    }

    public List<Motorista> pegarTodos(){
        List<Motorista> motoristas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
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
                motorista.setMotCpf(Long.parseLong(cpf));

                motoristas.add(motorista);

            }while(cursor.moveToNext());
        }

        return motoristas;

    }




}
