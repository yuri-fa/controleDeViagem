package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;

public class MotoristaActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        salvando os status da instancia
        super.onCreate(savedInstanceState);
//        setando o contexto da activity para motorista
        setContentView(R.layout.motorista);

        pesquisarTodos();

    }

//    public void onClickAcaoLimparDados(View view){
//
//        EditText nomeCompleto = findViewById(R.id.nomeCompleto);
//        EditText nomeDeGuerra = findViewById(R.id.nomeDeguerra);
//        EditText cpf = findViewById(R.id.cpf);
//
//        nomeCompleto.setText("");
//        nomeDeGuerra.setText("");
//        cpf.setText("");
//    }

//    public void onClickAcaoSalvar(View view){
//        Motorista motorista = new Motorista();
//        EditText nomeCompleto = findViewById(R.id.nomeCompleto);
//        EditText nomeDeGuerra = findViewById(R.id.nomeDeguerra);
//        EditText cpf = findViewById(R.id.cpf);
//
//        motorista.setMotNomeCompleto(nomeCompleto.getText().toString());
//        motorista.setMotNomeGuerra(nomeDeGuerra.getText().toString());
//        String teste = cpf.getText().toString();
//        Long inteiro = Long.parseLong(teste);
//        motorista.setMotCpf(inteiro);
//        String msg;
//        onClickAcaoLimparDados(view);
//        Boolean isInsert = new MotoristaController(this).inserirMotorista(motorista);
//
//        if(isInsert){
//            msg = "Motorista: "+ nomeCompleto.getText().toString() +",\n CPF: " + cpf.getText().toString();
//            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//        }else{
//            msg = "Falha tente novamente";
//            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void pesquisarTodos(){

        LinearLayout lista = (LinearLayout) findViewById(R.id.listaMotoristas);
//        String teste1 = "Nome:\n    xandao\n Nome de Guerra: \n     xandy\n Cpf: \n     0974589749";


        TextView ovos = new TextView(this);
//        ovos.setPadding(0,10,10,0);
        ovos.setTextSize(30);
        ovos.setText("Nome:");

        TextView ovos2 = new TextView(this);
//        ovos2.setPadding(0,10,10,0);
        ovos2.setText("Toin da gia");
        ovos2.setTextSize(50);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(ovos);
        linearLayout.addView(ovos2);

        CardView card = new CardView(this);
        card.addView(linearLayout);
//        card.addView(ovos2);

        lista.addView(card);
//        lista.addView(ovos2);

    }
}
