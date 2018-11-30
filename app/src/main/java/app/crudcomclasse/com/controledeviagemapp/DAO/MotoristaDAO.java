package app.crudcomclasse.com.controledeviagemapp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MotoristaDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ControleDeViagem.DB";
    protected static final int DATABASE_VERSION = 1;


    public MotoristaDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE motorista"
                + "(motnumsequencial INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"motnomecompleto TEXT,"
                +"motnomeguerra TEXT,"
                +"motcpf BIGINT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS motorista";
        db.execSQL(query);
        onCreate(db);
    }
}
