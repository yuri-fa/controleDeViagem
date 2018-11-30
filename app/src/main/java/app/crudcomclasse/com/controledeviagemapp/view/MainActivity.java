package app.crudcomclasse.com.controledeviagemapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import app.crudcomclasse.com.controledeviagemapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void OnClickNavDadosMotorista(View view) {
        Intent intent = new Intent(this,MotoristaActivity.class);
        startActivity(intent);
    }
}
