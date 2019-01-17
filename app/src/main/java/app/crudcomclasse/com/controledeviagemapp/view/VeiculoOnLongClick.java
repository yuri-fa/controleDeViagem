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
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
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

        final TextView placa = (TextView) view.findViewById(R.id.placa);
        placa.setText(veiculo.getPlaca());
        RadioButton button = (RadioButton) view.findViewById(R.id.cavalo);
        RadioButton button1 = (RadioButton) view.findViewById(R.id.carreta);
        RadioButton button2 = (RadioButton) view.findViewById(R.id.dolly);

        if (button.getText().toString().equals(veiculo.getTipo())){
            button.setChecked(true);
        }else if (button1.getText().toString().equals(veiculo.getTipo())){
            button1.setChecked(true);
        }else{
            button2.setChecked(true);
        }
        new AlertDialog
                .Builder(context)
                .setView(view)
                .setTitle("Editando")
                .setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        veiculo.setPlaca(placa.getText().toString());
                        RadioGroup opcoes = (RadioGroup) view.findViewById(R.id.tipoVeiculo);
                        RadioButton radioButton;
                        String tipoVeiculo = null;

                        switch (opcoes.getCheckedRadioButtonId()){
                            case R.id.cavalo:
                                radioButton = view.findViewById(R.id.cavalo);
                                tipoVeiculo = radioButton.getText().toString();
                                break;
                            case R.id.carreta:
                                radioButton = view.findViewById(R.id.carreta);
                                tipoVeiculo = radioButton.getText().toString();
                                break;
                            case R.id.dolly:
                                radioButton = view.findViewById(R.id.dolly);
                                tipoVeiculo = radioButton.getText().toString();
                                break;
                        }
                        veiculo.setTipo(tipoVeiculo);

                        boolean isUpdate = new VeiculoController(context).atualizarVeiculo(veiculo);

                        if (isUpdate){
                            Toast.makeText(context,"Foi Sal!!!",Toast.LENGTH_LONG).show();
                            ((VeiculoActivity)context).pesquisarTodos();
                        }else{
                            Toast.makeText(context,"Foi Pedo!!!",Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    }
                }).show();
    }

    public void excluirVeiculo(int id){
        boolean isDelete = new VeiculoController(context).deletarVeiculo(id);
        if (isDelete){
            ((VeiculoActivity)context).pesquisarTodos();
            Toast.makeText(context,"Foi Sal!!!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Foi Pedo!!!",Toast.LENGTH_LONG).show();
        }
    }
}
