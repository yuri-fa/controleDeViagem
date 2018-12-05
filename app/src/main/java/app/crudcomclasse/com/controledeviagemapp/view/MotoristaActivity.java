package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;

public class MotoristaActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        salvando os status da instancia
        super.onCreate(savedInstanceState);
//        setando o contexto da activity para motorista
        setContentView(R.layout.motorista);

        Button botaoInserir = (Button) findViewById(R.id.btnCriarContato);

        botaoInserir.setOnClickListener(new MotoristaOnClick());

        pesquisarTodos();

    }

    public void pesquisarTodos(){

        List<Motorista> listaMotorista = new ArrayList<>();

        listaMotorista = new MotoristaController(this).pegarTodos();

        LinearLayout lista = (LinearLayout) findViewById(R.id.listaMotoristas);

        for(Motorista motorista : listaMotorista){

            TextView txt1 = new TextView(this);
            txt1.setTextSize(30);
            txt1.setText("Nome completo:");

            TextView txt2 = new TextView(this);
            txt2.setText(motorista.getMotNomeCompleto());
            txt2.setTextSize(20);

            TextView txt3 = new TextView(this);
            txt3.setTextSize(30);
            txt3.setText("Nome de guerra:");

            TextView txt4 = new TextView(this);
            txt4.setText(motorista.getMotNomeGuerra());
            txt4.setTextSize(20);

            TextView txt5 = new TextView(this);
            txt5.setTextSize(30);
            txt5.setText("Cpf:");

            TextView txt6 = new TextView(this);
            txt6.setText(motorista.getMotCpf().toString());
            txt6.setTextSize(20);


            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(txt1);
            linearLayout.addView(txt2);
            linearLayout.addView(txt3);
            linearLayout.addView(txt4);
            linearLayout.addView(txt5);
            linearLayout.addView(txt6);
            linearLayout.

            linearLayout.setPadding(0,10,0,10);


            CardView card = new CardView(this);
            card.setElevation(100);
            card.setCardElevation(100);
            card.addView(linearLayout);
            card.setOnLongClickListener(new MotoristaOnLongClick());
            card.setTag(motorista.getMotNumSequencial());
            card.setPadding(0,10,0,10);
            lista.addView(card);
        }
    }
}
