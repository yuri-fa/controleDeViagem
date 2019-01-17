package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

class ViagemOnClickVeiculo implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        final List<Veiculo> veiculos = new VeiculoController(v.getContext()).pesquisarTodos();
        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.form_viagem,null,false);

        final RadioGroup groupVeiculo = (RadioGroup) view.findViewById(R.id.group_veiculos);
        final List<RadioButton> radioList = new ArrayList<>();
        for (Veiculo veiculo : veiculos){
            RadioButton radio = new RadioButton(v.getContext());
            radio.setId(Integer.parseInt(veiculo.getVeiNumSequencial().toString()));
            radio.setText(veiculo.getPlaca());
            radio.setTextSize(25);
            groupVeiculo.addView(radio);
            radioList.add(radio);
        }


        new AlertDialog
                .Builder(context)
                .setView(view)
                .setTitle("Inserindo Veiculo")
                .setPositiveButton("Adicionar Veiculo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       int radioId = groupVeiculo.getCheckedRadioButtonId();
                        Veiculo veiculo = new Veiculo();
                        forOne :for(final RadioButton temp : radioList){
                           if (temp.getId() == radioId){
                                for (Veiculo vei :veiculos){
                                    if (vei.getPlaca().equals(temp.getText().toString())){
                                        veiculo = vei;
                                        break forOne;
                                    }
                                }
                            }
                       }
                       ((ViagemActivity)context).receberVeiculo(veiculo);
                        dialog.dismiss();
                    }
                }).show();



    }
}
