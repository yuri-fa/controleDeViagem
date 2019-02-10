package app.crudcomclasse.com.controledeviagemapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.RelatorioController;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class RelatoriosActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorios);

        List<Viagem> viagemList = new RelatorioController(this).pesquisarViagens();
        LinearLayout listRelatorios = this.findViewById(R.id.list_relatorios);
        for (Viagem viagem : viagemList){
            TextView txtViagem = new TextView(this);
            txtViagem.setText("Codigo");
            txtViagem.setPadding(05,0,0,0);
            txtViagem.setTextSize(25);

            TextView txtViagemValor = new TextView(this);
            txtViagemValor.setText(viagem.getNumSequencial().toString());
            txtViagemValor.setTextSize(30);
            txtViagemValor.setPadding(20,0,0,0);

            TextView txtViagemData = new TextView(this);
            txtViagemData.setText("Data Cadastro");
            txtViagemData.setTextSize(25);
            txtViagemData.setPadding(05,0,0,0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("DD/mm/yyyy HH:mm:ss");
            TextView txtViagemDataValor = new TextView(this);
            txtViagemDataValor.setText(dateFormat.format(viagem.getDthrViagem()));
            txtViagemDataValor.setTextSize(30);
            txtViagemDataValor.setPadding(20,0,0,0);

            TextView txtMotorista = new TextView(this);
            txtViagemDataValor.setText("Motorista");
            txtViagemDataValor.setTextSize(25);
            txtViagemDataValor.setPadding(05,0,0,0);

            TextView txtMotoristaValor = new TextView(this);
            txtMotoristaValor.setText(viagem.getMotorista().getMotNomeCompleto());
            txtMotoristaValor.setTextSize(30);
            txtMotoristaValor.setPadding(20,0,0,0);

            TextView txtVeiculo = new TextView(this);
            txtVeiculo.setText("Veiculo");
            txtVeiculo.setTextSize(25);
            txtVeiculo.setPadding(05,0,0,0);

            TextView txtVeiculoValor = new TextView(this);
            txtVeiculoValor.setText(viagem.getVeiculo().getPlaca());
            txtVeiculoValor.setTextSize(30);
            txtVeiculoValor.setPadding(20,0,0,0);


            LinearLayout linearLayoutDadosViagem = new LinearLayout(this);
            linearLayoutDadosViagem.setOrientation(LinearLayout.VERTICAL);
            linearLayoutDadosViagem.addView(txtViagem);
            linearLayoutDadosViagem.addView(txtViagemValor);
            linearLayoutDadosViagem.addView(txtViagemData);
            linearLayoutDadosViagem.addView(txtViagemDataValor);
            linearLayoutDadosViagem.addView(txtMotorista);
            linearLayoutDadosViagem.addView(txtMotoristaValor);
            linearLayoutDadosViagem.addView(txtVeiculo);
            linearLayoutDadosViagem.addView(txtVeiculoValor);

            LinearLayout linearLayoutDadosPlaca = new LinearLayout(this);
            linearLayoutDadosPlaca.setOrientation(LinearLayout.VERTICAL);
            for (Placa placa : viagem.getConjuntoDePlacas()){
                TextView txtSerial = new TextView(this);
                txtSerial.setText("Serial");
                txtSerial.setTextSize(25);
                txtSerial.setPadding(05,0,0,0);

                TextView txtSerialValor = new TextView(this);
                txtSerialValor.setText(placa.getSerial());
                txtSerialValor.setTextSize(30);
                txtSerialValor.setPadding(20,0,0,0);

                TextView txtPeso = new TextView(this);
                txtPeso.setText("Peso");
                txtPeso.setTextSize(25);
                txtPeso.setPadding(05,0,0,0);

                TextView txtPesoValor = new TextView(this);
                txtPesoValor.setText(placa.getPeso().toString());
                txtPesoValor.setTextSize(30);
                txtPesoValor.setPadding(20,0,0,0);


                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(txtSerial);
                linearLayout.addView(txtSerialValor);
                linearLayout.addView(txtPeso);
                linearLayout.addView(txtPesoValor);

                CardView cardPlaca = new CardView(this);
                cardPlaca.setCardElevation(10);
                cardPlaca.addView(linearLayout);
                cardPlaca.isPaddingRelative();
                cardPlaca.setPadding(0,10,0,10);
                linearLayoutDadosPlaca.addView(cardPlaca);
            }

            linearLayoutDadosViagem.addView(linearLayoutDadosPlaca);
            CardView card = new CardView(this);
            card.setCardElevation(10);
            card.addView(linearLayoutDadosViagem);
            card.isPaddingRelative();
            card.setOnLongClickListener(new VeiculoOnLongClick());
            card.setTag(viagem.getNumSequencial());
            card.setPadding(0,10,0,10);
            card.setCardBackgroundColor(15*33);
            listRelatorios.addView(card);
        }
    }
}
