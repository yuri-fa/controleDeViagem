package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.adapter.VeiculoAdapter;
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

public class VeiculoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VeiculoAdapter veiculoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veiculo);
        recyclerView = (RecyclerView) findViewById(R.id.reciclerViewVeiculo);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ImageButton imageButton = (ImageButton) findViewById(R.id.novoVeiculo);
        imageButton.setOnClickListener(new VeiculoOnClick());
        pesquisarTodos();
    }

    public void pesquisarTodos() {
        List<Veiculo> veiculos =  new VeiculoController(this).pesquisarTodos();
        veiculoAdapter = new VeiculoAdapter(recyclerView,veiculos, this);
        recyclerView.setAdapter(veiculoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        veiculoAdapter.notifyDataSetChanged();
    }
}
