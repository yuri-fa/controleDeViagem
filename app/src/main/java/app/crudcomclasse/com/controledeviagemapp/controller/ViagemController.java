package app.crudcomclasse.com.controledeviagemapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.util.ControleViagemUtil;
import app.crudcomclasse.com.controledeviagemapp.util.DataBaseAdapter;
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
                        placa.setViagem(viagem.getNumSequencial());
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

    public Viagem buscarPorId(Integer idViagem) {
        SQLiteDatabase database = this.getReadableDatabase();
        Viagem viagem = new Viagem();
        Veiculo veiculo = new Veiculo();
        Motorista motorista = new Motorista();
        viagem.setConjuntoDePlacas(new ArrayList<Placa>());
        String query = "select * from viagem " +
                " left outer join veiculo on viveiculo = veinumsequencial" +
                " left outer join motorista on vimotorista = motnumsequencial" +
                " where vinumsequencial = "+ idViagem;
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
                viagem.setNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("vinumsequencial"))));
                viagem.setDthrViagem(ControleViagemUtil.formatarStringParaData(cursor.getString(cursor.getColumnIndex("vidthrinicio"))));
                veiculo.setVeiNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("veinumsequencial"))));
                veiculo.setPlaca(cursor.getString(cursor.getColumnIndex("veiplaca")));
                motorista.setMotNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("motnumsequencial"))));
                motorista.setMotNomeCompleto(cursor.getString(cursor.getColumnIndex("motnomeguerra")));
            }while(cursor.moveToNext());
        }
        viagem.setMotorista(motorista);
        viagem.setVeiculo(veiculo);
        String queryDePlaca = "SELECT * FROM placa where plaviagem = "+viagem.getNumSequencial();
        Cursor cursorPlaca = database.rawQuery(queryDePlaca, null);
        if (cursorPlaca.moveToFirst()){
            do{
                Placa placa = new Placa();
                placa.setPlaNumSequencial(Integer.parseInt(cursorPlaca.getString(cursorPlaca.getColumnIndex("planumsequencial"))));
                placa.setSerial(cursorPlaca.getString(cursorPlaca.getColumnIndex("plaserial")));
                placa.setPeso(new BigDecimal(cursorPlaca.getString(cursorPlaca.getColumnIndex("plapeso"))));
                viagem.getConjuntoDePlacas().add(placa);
            }while (cursorPlaca.moveToNext());
        }
        return viagem;
    }

    public boolean excluirViagem(Integer numSequencial) {
        return true;
    }

    public ArrayList<Motorista> buscarMotoristas(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Motorista> motoristaList = new ArrayList<>();
        String query = "select * from motorista";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
                Motorista motorista = new Motorista();
                motorista.setMotNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("motnumsequencial"))));
                motorista.setMotNomeCompleto(cursor.getString(cursor.getColumnIndex("motnomecompleto")));
                motorista.setMotNomeGuerra(cursor.getString(cursor.getColumnIndex("motnomeguerra")));
                motorista.setMotCpf(cursor.getString(cursor.getColumnIndex("motcpf")));
                motoristaList.add( motorista);
            }while(cursor.moveToNext());
        }
        return motoristaList;
    }

    public ArrayList<Veiculo> buscarVeiculos(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Veiculo> veiculoList = new ArrayList<>();
        String query = "select * from veiculo";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
                Veiculo veiculo = new Veiculo();
                veiculo.setVeiNumSequencial(Integer.parseInt(cursor.getString(cursor.getColumnIndex("veinumsequencial"))));
                veiculo.setPlaca(cursor.getString(cursor.getColumnIndex("veiplaca")));
                veiculoList.add(veiculo);
            }while(cursor.moveToNext());
        }
        return veiculoList;
    }
}
