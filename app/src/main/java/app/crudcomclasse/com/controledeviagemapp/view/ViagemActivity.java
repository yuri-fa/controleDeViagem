package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class ViagemActivity extends AppCompatActivity {

    private Viagem viagem = new Viagem();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem);

        Button btnVeiculo = (Button) this.findViewById(R.id.btn_addVeiculo);
        btnVeiculo.setOnClickListener(new ViagemOnClickVeiculo());
        Button btnPlaca = (Button) this.findViewById(R.id.btn_addproduto);
        btnPlaca.setOnClickListener(new ViagemOnClickPlacas());
    }

    public void receberVeiculo(Veiculo veiculo){
        TextView txtVeiculo = (TextView) this.findViewById(R.id.txt_veiculo);
        this.viagem.setVeiculo(veiculo);
        txtVeiculo.setText(veiculo.getPlaca());
        txtVeiculo.setVisibility(View.VISIBLE);
    }

    public void receberPlaca(Placa placa){
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

}
