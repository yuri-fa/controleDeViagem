package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.VeiculoController;
import app.crudcomclasse.com.controledeviagemapp.model.Veiculo;

public class VeiculoOnClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view =  layoutInflater.inflate(R.layout.form_veiculo,null,false);

        final EditText placa = view.findViewById(R.id.form_placa);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Inserindo Veiculo");
        builder.setView(view);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(placa.getText().toString());
                boolean inserido = new VeiculoController(context).inserirVeiculo(veiculo);
                if (inserido){
                    Toast.makeText(context,"Inserido com sucesso",Toast.LENGTH_SHORT);
                    ((VeiculoActivity) context).pesquisarTodos();
                }else{
                    Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_SHORT);
                }
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
