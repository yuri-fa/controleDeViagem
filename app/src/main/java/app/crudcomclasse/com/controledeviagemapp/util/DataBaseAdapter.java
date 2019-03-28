package app.crudcomclasse.com.controledeviagemapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ControleDeViagem.DB";
    protected static final int DATABASE_VERSION = 1;

    public DataBaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String motorista = "CREATE TABLE motorista"
                + "(motnumsequencial INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"motnomecompleto TEXT,"
                +"motnomeguerra TEXT,"
                +"motcpf TEXT)";
        String veiculo = "CREATE TABLE veiculo(" +
                "veinumsequencial INTEGER PRIMARY KEY AUTOINCREMENT," +
                "veiplaca TEXT)";
        String viagem = "CREATE TABLE viagem(" +
                "vinumsequencial INTEGER PRIMARY KEY AUTOINCREMENT," +
                "vidthrinicio NUMERIC NOT NULL," +
                "vimotorista INTEGER NOT NULL," +
                "viveiculo INTEGER NOT NULL," +
                "FOREIGN KEY(vimotorista) REFERENCES motorista(motnumsequencial),"+
                "FOREIGN KEY(viveiculo) REFERENCES veiculo(veinumsequencial))";
        String placa ="CREATE TABLE placa(" +
                "planumsequencial INTEGER PRIMARY KEY AUTOINCREMENT," +
                "plaserial TEXT NOT NULL," +
                "plapeso NUMERIC NOT NULL," +
                "plaviagem INTEGER NOT NULL," +
                "FOREIGN KEY(plaviagem) REFERENCES viagem(vinumsequencial))";

        db.execSQL(placa);
        db.execSQL(motorista);
        db.execSQL(veiculo);
        db.execSQL(viagem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String motorista = "DROP TABLE IF EXISTS motorista";
        String veiculo = "DROP TABLE IF EXISTS veiculo";
        db.execSQL(motorista);
        db.execSQL(veiculo);
        onCreate(db);
    }
}
