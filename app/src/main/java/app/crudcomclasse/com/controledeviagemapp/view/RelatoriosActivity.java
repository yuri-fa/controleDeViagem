package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

class RelatoriosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorios);
        List<Viagem> viagemList = new RelatorioController(this).pesquisarViagens();
    }
}
