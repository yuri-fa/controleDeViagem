package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;

public class MotoristaOnLongClick implements View.OnLongClickListener {

    Context context;
    String id;


    @Override
    public boolean onLongClick(View v) {
        context = v.getContext();
        id = v.getTag().toString();
        final CharSequence[] opcoes = {"Editar","Deletar"};

        new AlertDialog.
                Builder(context).
                    setTitle("Opçoes para motorista")
                        .setItems(opcoes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                if(item == 0){
                                    editarMotorista(Integer.parseInt(id));
//                                    Toast.makeText(context,"opcao editar funcionando, valor da tag" + id,Toast.LENGTH_SHORT).show();
                                }else if(item == 1){
                                    deletarMotorista(Integer.parseInt(id));
                                    Toast.makeText(context,"opcao deletar funcionando, valor da tag" + id,Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).show();
        return false;
    }

    public void editarMotorista( final int id){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.form_motorista,null,false);

        Motorista motorista = new MotoristaController(context).pegarPorId(id);

        final EditText nomeMotorista = (EditText) view.findViewById(R.id.nomeCompleto);
        nomeMotorista.setText(motorista.getMotNomeCompleto());
        final EditText nomeDeGuerra = (EditText) view.findViewById(R.id.nomeDeguerra);
        nomeDeGuerra.setText(motorista.getMotNomeGuerra());
        final EditText cpf = (EditText) view.findViewById(R.id.cpf);
        cpf.setText(motorista.getMotCpf());
        new AlertDialog.Builder(context)
                .setView(view)
                .setTitle("Editando")
                .setPositiveButton("Atuaizar dados", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Motorista motorista = new Motorista();
                        motorista.setMotNumSequencial(id);
                        motorista.setMotNomeCompleto(nomeMotorista.getText().toString());
                        motorista.setMotNomeGuerra(nomeDeGuerra.getText().toString());
                        motorista.setMotCpf(cpf.getText().toString());

                        boolean isUpdate = new MotoristaController(context).update(motorista);

                        if(isUpdate){
                            Toast.makeText(context,"Foi sal pivete",Toast.LENGTH_LONG).show();
                            ((MotoristaActivity) context).pesquisarTodos();
                        }else{
                            Toast.makeText(context,"Foi sal pedo",Toast.LENGTH_LONG).show();
                        }

                        dialog.cancel();
                    }
                }).show();
    }

    public void deletarMotorista(int id){
        boolean isDelete = new MotoristaController(context).deletarMotorista(id);

        if(isDelete){
            Toast.makeText(context,"Foi sal",Toast.LENGTH_LONG).show();
            ((MotoristaActivity)context).pesquisarTodos();
        }else{
            Toast.makeText(context,"Foi pedo",Toast.LENGTH_LONG).show();
        }
    }
}
