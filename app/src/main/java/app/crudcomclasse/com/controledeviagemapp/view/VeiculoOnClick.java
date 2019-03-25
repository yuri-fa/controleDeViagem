package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

public class VeiculoOnClick implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        final Veiculo veiculo = new Veiculo();
        final Context context = v.getContext();
        final List<Motorista> motoristaList = new MotoristaController(context).pegarTodos();
        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.form_veiculo,null,false);
        final EditText placa = (EditText) view.findViewById(R.id.form_placa);
        final RadioGroup opcoes = (RadioGroup) view.findViewById(R.id.form_group_motoristas);
        final List<RadioButton> radioList = new ArrayList<>();

        for (Motorista motorista : motoristaList){
            RadioButton radioButton = new RadioButton(context);
            radioButton.setId(Integer.parseInt(motorista.getMotNumSequencial().toString()));
            radioButton.setText(motorista.getMotNomeGuerra());
            radioButton.setTextSize(25);
            opcoes.addView(radioButton);
            radioList.add (radioButton);
        }

        new AlertDialog
                .Builder(context)
                .setView(view)
                .setTitle("Inserindo")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Motorista motorista = null;
                Integer id = opcoes.getCheckedRadioButtonId();
                for (Motorista motoristaTemp : motoristaList){
                    if (motoristaTemp.getMotNumSequencial().equals(id)){
                        motorista = motoristaTemp;
                        break;
                    }
                }
                veiculo.setPlaca(placa.getText().toString());
                if (motorista != null){
                    veiculo.setMotorista(motorista);
                }
                boolean isInsert = new VeiculoController(context).inserirVeiculo(veiculo);

                if (isInsert){
                    Toast.makeText(context,"Veiculo salvo com sucesso",Toast.LENGTH_LONG).show();
                    ((VeiculoActivity) context).pesquisarTodos();
                }else{
                    Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }).show();

    }
}
