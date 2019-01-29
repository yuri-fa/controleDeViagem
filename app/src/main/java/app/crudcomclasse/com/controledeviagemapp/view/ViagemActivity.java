package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class ViagemActivity extends AppCompatActivity {

    private static Viagem viagem = new Viagem();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem);

        Button btnVeiculo = (Button) this.findViewById(R.id.btn_addVeiculo);
        btnVeiculo.setOnClickListener(new ViagemOnClickVeiculo());
        Button btnPlaca = (Button) this.findViewById(R.id.btn_addproduto);
        btnPlaca.setOnClickListener(new ViagemOnClickPlacas());
        Button btnFinalizacao = (Button) this.findViewById(R.id.btn_finalizarViagem);
        btnFinalizacao.setOnClickListener(new ViagemOnClickFinalizar());

    }

    public void receberVeiculo(Veiculo veiculo){
        if (veiculo.getMotorista() != null){
            TextView txtVeiculo = (TextView) this.findViewById(R.id.txt_veiculo);
            this.viagem.setVeiculo(veiculo);
            txtVeiculo.setText(veiculo.getPlaca());
            txtVeiculo.setVisibility(View.VISIBLE);
            TextView txtMotorista = (TextView) this.findViewById(R.id.txt_motorista);
            this.viagem.setMotorista(veiculo.getMotorista());
            txtMotorista.setText(veiculo.getMotorista().getMotNomeGuerra());
            txtMotorista.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this,"Veiculo nao possui motorista vinculado",Toast.LENGTH_LONG).show();
        }
    }

    public void receberPlaca(Placa placa){
        if (viagem.getConjuntoDePlacas() == null){
            viagem.setConjuntoDePlacas(new ArrayList<Placa>());
        }
        viagem.getConjuntoDePlacas().add(placa);
        LinearLayout listPlaca = this.findViewById(R.id.list_placas);
        TextView textSerial = new TextView(this);
        textSerial.setText("Serial");
        textSerial.setTextSize(20);
        textSerial.setPaddingRelative(30,0,0,0);

        TextView textSerialInput = new TextView(this);
        textSerialInput.setTextSize(20);
        textSerialInput.setText(placa.getSerial());
        textSerialInput.setPaddingRelative(50,0,0,0);

        TextView textPeso = new TextView(this);
        textPeso.setText("Peso");
        textPeso.setTextSize(20);
        textPeso.setPaddingRelative(30,0,0,0);

        TextView textPesoInput = new TextView(this);
        textPesoInput.setTextSize(20);
        textPesoInput.setText(placa.getPeso().toString());
        textPesoInput.setPaddingRelative(50,0,0,0);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(textSerial);
        linearLayout.addView(textSerialInput);
        linearLayout.addView(textPeso);
        linearLayout.addView(textPesoInput);

        CardView card = new CardView(this);
        card.setCardElevation(10);
        card.addView(linearLayout);
//        card.setOnLongClickListener(new VeiculoOnLongClick());
        card.setPadding(0,10,0,10);
        card.setCardBackgroundColor(15*33);

        listPlaca.addView(card);
    }

    public void limparDados(){
        TextView txtVeiculo = (TextView) this.findViewById(R.id.txt_veiculo);
        txtVeiculo.setText("");
        TextView txtMotorista = (TextView) this.findViewById(R.id.txt_motorista);
        txtMotorista.setText("");
        LinearLayout listPlaca = this.findViewById(R.id.list_placas);
        listPlaca.removeAllViews();
        viagem = new Viagem();
    }

    public static Viagem getViagem(){
        return viagem;
    }

}
