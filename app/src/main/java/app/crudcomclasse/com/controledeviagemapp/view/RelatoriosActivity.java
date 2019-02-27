package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.ViagemController;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class RelatoriosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorios);
        List<Viagem> viagemList = new ViagemController(this).pesquisarViagens();
        ExpandableListView expandableListView = findViewById(R.id.expandable);
        expandableListView.setAdapter(new ExpandListAdapter(this, (ArrayList<Viagem>) viagemList));

    }
}
