package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;

public class MotoristaOnClick implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.form_motorista,null,false);

        final EditText nomeMotorista = (EditText) view.findViewById(R.id.form_nome_Completo);
        final EditText nomeGuerra = (EditText) view.findViewById(R.id.form_nome_guerra);

        new AlertDialog
                .Builder(context)
                .setView(view)
                .setTitle("Inserindo")
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Motorista motorista = new Motorista();
                        motorista.setMotNomeCompleto(nomeMotorista.getText().toString());
                        motorista.setMotNomeGuerra(nomeGuerra.getText().toString());

                        boolean isInsert = new MotoristaController(context).inserirMotorista(motorista);

                        if (isInsert){
                            Toast.makeText(context,"Motorista salvo com sucesso",Toast.LENGTH_LONG).show();
                            ((MotoristaActivity) context).pesquisarTodos();
                        }else{
                            Toast.makeText(context,"Falha, tente novamente",Toast.LENGTH_LONG).show();
                        }
                        dialog.cancel();
                    }
                }).show();
    }
}
