package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.adapter.MotoristaAdapter;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;

public class MotoristaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MotoristaAdapter motoristaAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

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
