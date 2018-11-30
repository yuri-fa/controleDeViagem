package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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




}
