package app.crudcomclasse.com.controledeviagemapp.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

public class VeiculoOnLongClick implements View.OnLongClickListener {

    Context context;
    String id;

    @Override
    public boolean onLongClick(View v) {
       context = v.getContext();
       id = v.getTag().toString();
       CharSequence [] opcoes = {"Editar","Deletar"};
       new AlertDialog.
               Builder(context)
               .setTitle("Opçoes para veiculo")
               .setItems(opcoes, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int opcao) {
                       if (opcao == 0){
                           editarVeiculo(Integer.parseInt(id));
                       }else{
                           excluirVeiculo(Integer.parseInt(id));
                       }
                   }
               }).show();
       return false;
    }

    public void editarVeiculo(int id){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.form_veiculo,null,false);
        final Veiculo veiculo = new VeiculoController(context).pesquisarPorId(id);
        final List<Motorista> motoristaList = new MotoristaController(context).pegarTodos();
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
        final TextView placa = (TextView) view.findViewById(R.id.form_placa);
        placa.setText(veiculo.getPlaca());

        new AlertDialog
                .Builder(context)
                .setView(view)
                .setTitle("Editando")
                .setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Motorista motorista = null;
                        Integer id = opcoes.getCheckedRadioButtonId();
                        Long numSequencial = Long.parseLong(id.toString());
                        for (Motorista motoristaTemp : motoristaList){
                            if (motoristaTemp.getMotNumSequencial().equals(numSequencial)){
                                motorista = motoristaTemp;
                                break;
                            }
                        }
                        veiculo.setPlaca(placa.getText().toString());
                        if (motorista != null){
                            veiculo.setMotorista(motorista);
                        }
                        boolean isUpdate = new VeiculoController(context).atualizarVeiculo(veiculo);

                        if (isUpdate){
                            Toast.makeText(context,"Veiculo editado com sucesso",Toast.LENGTH_LONG).show();
                            ((VeiculoActivity)context).pesquisarTodos();
                        }else{
                            Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    public void excluirVeiculo(int id){
        boolean isDelete = new VeiculoController(context).deletarVeiculo(id);
        if (isDelete){
            ((VeiculoActivity)context).pesquisarTodos();
            Toast.makeText(context,"Veiculo excluido com sucesso",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_LONG).show();
        }
    }
}
