package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;

public class ViagemOnClickPlacas implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.form_viagem_placas,null,false);
        final EditText txtSerial = view.findViewById(R.id.txt_serial);
        final EditText txtPeso = view.findViewById(R.id.txt_peso);

        new AlertDialog
                .Builder(context)
                .setView(view)
                .setTitle("Inserindo Placas")
                .setPositiveButton("Adicionar Placa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Placa placa = new Placa();
                        placa.setSerial(txtSerial.getText().toString());
                        BigDecimal peso = new BigDecimal(txtPeso.getText().toString());
                        placa.setPeso(peso);
                        ((ViagemActivity)context).receberPlaca(placa);
                    }
                }).show();
    }
}
