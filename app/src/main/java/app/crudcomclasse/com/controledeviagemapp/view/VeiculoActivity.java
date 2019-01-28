package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

public class VeiculoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veiculo);

        Button novo = (Button) findViewById(R.id.inserirVeiculo);
        novo.setOnClickListener(new VeiculoOnClick());

        pesquisarTodos();
    }

    public void pesquisarTodos() {

        List<Veiculo> listaDeVeiculos = new VeiculoController(this).pesquisarTodos();
        LinearLayout listaVeiculos = (LinearLayout) findViewById(R.id.listaVeiculos);
        listaVeiculos.removeAllViews();

        for(Veiculo veiculo : listaDeVeiculos){
            TextView txt1 = new TextView(this);
            txt1.setPaddingRelative(30,0,0,0);
            txt1.setTextSize(20);
            txt1.setText("Placa");

            TextView txt2 = new TextView(this);
            txt2.setTextSize(30);
            txt2.setPaddingRelative(50,0,0,0);
            txt2.setText(veiculo.getPlaca());

            TextView txt3 = new TextView(this);
            TextView txt4 = new TextView(this);
            if (veiculo.getMotorista() != null){
                txt3.setPaddingRelative(30,0,0,0);
                txt3.setTextSize(20);
                txt3.setText("Motorista vinculado");

                txt4.setPaddingRelative(30,0,0,0);
                txt4.setTextSize(30);
                txt4.setText(veiculo.getMotorista().getMotNomeGuerra());
            }

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(txt1);
            linearLayout.addView(txt2);
            linearLayout.addView(txt3);
            linearLayout.addView(txt4);

            linearLayout.setPadding(0,10,0,0);
            linearLayout.setBackgroundColor(15*33);

            CardView card = new CardView(this);
            card.setCardElevation(10);
            card.addView(linearLayout);
            card.setOnLongClickListener(new VeiculoOnLongClick());
            card.setTag(veiculo.getVeiNumSequencial());
            card.setPadding(0,10,0,10);
            card.setCardBackgroundColor(15*33);
            listaVeiculos.addView(card);
        }

    }
}
