package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

public class VeiculoOnClick implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        final Veiculo veiculo = new Veiculo();
        final Context context = v.getContext();

        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = layoutInflater.inflate(R.layout.form_veiculo,null,false);

        final EditText placa = (EditText) view.findViewById(R.id.placa);
        final RadioGroup opcoes = (RadioGroup) view.findViewById(R.id.tipoVeiculo);

        new AlertDialog
                .Builder(context)
                .setView(view)
                .setTitle("Inserindo")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tipoVeiculo = null;
                RadioButton radioButton;
                switch (opcoes.getCheckedRadioButtonId()){
                    case R.id.cavalo:
                        radioButton = view.findViewById(R.id.cavalo);
                        tipoVeiculo = radioButton.getText().toString();
                    break;case R.id.carreta:
                        radioButton = view.findViewById(R.id.carreta);
                        tipoVeiculo = radioButton.getText().toString();
                    break;case R.id.dolly:
                        radioButton = view.findViewById(R.id.dolly);
                        tipoVeiculo = radioButton.getText().toString();
                    break;
                }

                veiculo.setPlaca(placa.getText().toString());
                veiculo.setTipo(tipoVeiculo);
                boolean isInsert = new VeiculoController(context).inserirVeiculo(veiculo);

                if (isInsert){
                    Toast.makeText(context,"Foi sal",Toast.LENGTH_LONG).show();
                    ((VeiculoActivity) context).pesquisarTodos();
                }else{
                    Toast.makeText(context,"Foi Pedo",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }).show();

    }
}
