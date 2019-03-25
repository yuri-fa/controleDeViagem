package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.adapter.MotoristaAdapter;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;

public class MotoristaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MotoristaAdapter motoristaAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

//        salvando os status da instancia
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motorista);
        recyclerView = (RecyclerView) findViewById(R.id.reciclerViewMotorista);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ImageButton imageButton = (ImageButton) findViewById(R.id.novoMotorista);
        imageButton.setOnClickListener(new MotoristaOnClick());
        pesquisarTodos();

    }

    public void pesquisarTodos(){
        motoristaAdapter = new MotoristaAdapter(new MotoristaController(this).pegarTodos(),this,recyclerView);
        recyclerView.setAdapter(motoristaAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        motoristaAdapter.notifyDataSetChanged();
    }
}
