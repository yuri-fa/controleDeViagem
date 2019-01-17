package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.databaseadapter.DataBaseAdapter;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

public class VeiculoController extends DataBaseAdapter {

    public VeiculoController(Context context) {
        super(context);
    }

    public boolean inserirVeiculo(Veiculo veiculo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("veiplaca",veiculo.getPlaca());
        values.put("veitipo",veiculo.getTipo());
        boolean isInsert = db.insert("veiculo",null,values) > 0;
        db.close();
        return isInsert;
    }

    public List<Veiculo> pesquisarTodos() {
        List<Veiculo> veiculoList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from veiculo";

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                Veiculo veiculo = new Veiculo();
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("veinumsequencial")));
                String placa = cursor.getString(cursor.getColumnIndex("veiplaca"));
                String tipo = cursor.getString(cursor.getColumnIndex("veitipo"));

                veiculo.setVeiNumSequencial(id);
                veiculo.setPlaca(placa);
                veiculo.setTipo(tipo);
                veiculoList.add(veiculo);
            }while(cursor.moveToNext());
        }
        return veiculoList;
    }

    public Veiculo pesquisarPorId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Veiculo veiculo = new Veiculo();
        String query = "SELECT * FROM veiculo WHERE veinumsequencial ="+ id;
        Cursor cursor = db.rawQuery(query,null);

        if  (cursor.moveToFirst()){
            veiculo.setVeiNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("veinumsequencial"))));
            veiculo.setPlaca(cursor.getString(cursor.getColumnIndex("veiplaca")));
            veiculo.setTipo(cursor.getString(cursor.getColumnIndex("veitipo")));
        }
        db.close();
        return veiculo;
    }

    public boolean atualizarVeiculo(Veiculo veiculo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("veinumsequencial",veiculo.getVeiNumSequencial());
        values.put("veiplaca",veiculo.getPlaca());
        values.put("veitipo",veiculo.getTipo());
        String [] whereArgs = {veiculo.getVeiNumSequencial().toString()};
        String where = "veinumsequencial = ?";

        boolean isUpdate = db.update("veiculo",values,where,whereArgs) > 0;
        db.close();
        return isUpdate;
    }

    public boolean deletarVeiculo(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete veiculo where veinumsequencial =" + id;

        boolean isDelete = db.delete("veiculo","veinumsequencial =" + id,null) > 0;
        db.close();
        return isDelete;
    }
}
