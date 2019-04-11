package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.ViagemController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class ViagemActivity extends AppCompatActivity {

    private static Viagem viagem = new Viagem();
    private Spinner motSpinner,veiSpinner;
    private RecyclerView recyclerView;
    private PlacaAdapter placaAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem);
        recyclerView = (RecyclerView) findViewById(R.id.reciclerViewViagemPlacas);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ImageButton novoProduto = (ImageButton) findViewById(R.id.novoProduto);
        novoProduto.setOnClickListener(new ViagemOnClickPlacas());
        carregarMotoristas();
        carregarVeiculos();
        try{
            Integer idViagem = getIntent().getIntExtra("IDVIAGEM",0);
            if (idViagem != 0){
                carregarDadosParaEdicao(idViagem);
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
            Toast.makeText(this,"Falha tente novamente",Toast.LENGTH_LONG).show();
        }
    }

    public void carregarMotoristas(){
        ArrayList<Motorista> motoristas = new ViagemController(this).buscarMotoristas();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,motoristas);
        motSpinner = (Spinner) findViewById(R.id.mot_spinner);
        motSpinner.setAdapter(arrayAdapter);
        motSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Motorista motorista = Motorista.class.cast(view.getTag(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void carregarVeiculos(){
        ArrayList<Veiculo> veiculos = new ViagemController(this).buscarVeiculos();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,veiculos);
        veiSpinner = (Spinner) findViewById(R.id.vei_spinner);
        veiSpinner.setAdapter(arrayAdapter);
        veiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Veiculo veiculo = Veiculo.class.cast(view.getTag(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void receberPlaca(Placa placa,boolean inserindo){
        if (viagem.getConjuntoDePlacas() == null){
            viagem.setConjuntoDePlacas(new ArrayList<Placa>());
        }
        viagem.getConjuntoDePlacas().add(placa);
        if (inserindo){
            placaAdapter = new PlacaAdapter(recyclerView,viagem.getConjuntoDePlacas(),this);
            recyclerView.setAdapter(placaAdapter);
        }
    }

    public void carregarDadosParaEdicao(Integer idViagem){
        viagem = new ViagemController(this).buscarPorId(idViagem);
        atualizarLista();
    }

    public void removerPlaca(int position) {
        viagem.getConjuntoDePlacas().remove(position);
        atualizarLista();
    }

    public void finalizarViagem(View view){
        boolean valido = true;
        viagem.setVeiculo((Veiculo) veiSpinner.getSelectedItem());
        viagem.setMotorista((Motorista) motSpinner.getSelectedItem());
        if (viagem.getMotorista() == null){
            valido = false;
            Toast.makeText(view.getContext(),"Informe o Motorista",Toast.LENGTH_LONG).show();
        }
        if (viagem.getVeiculo() == null){
            valido = false;
            Toast.makeText(view.getContext(),"Informe o veiculo",Toast.LENGTH_LONG).show();
        }
        if (viagem.getConjuntoDePlacas() == null || viagem.getConjuntoDePlacas().size() == 0){
            valido = false;
            Toast.makeText(view.getContext(),"Informe um produto",Toast.LENGTH_LONG).show();
        }
        if (valido){
            viagem.setDthrViagem(new Date());
            boolean isInsert = new ViagemController(view.getContext()).inserirViagem(viagem);
            if (isInsert){
                Toast.makeText(view.getContext(),"Viagem cadastrada com sucesso",Toast.LENGTH_LONG).show();
                placaAdapter = null;
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                viagem = new Viagem();
            }else{
                Toast.makeText(view.getContext(),"Falha tente novamente",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void atualizarLista(){
        placaAdapter = new PlacaAdapter(recyclerView,viagem.getConjuntoDePlacas(),this);
        recyclerView.setAdapter(placaAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (placaAdapter != null)
            placaAdapter.notifyDataSetChanged();
    }

    public static Viagem getViagem(){
        return viagem;
    }

    public void setViagem(Viagem viagemTemp){
        viagem = viagemTemp;
    }
}
