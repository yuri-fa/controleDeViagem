package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.crudcomclasse.com.controledeviagemapp.R;
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
                                    editarMotorista(12);
//                                    Toast.makeText(context,"opcao editar funcionando, valor da tag" + id,Toast.LENGTH_SHORT).show();
                                }else if(item == 1){
                                    Toast.makeText(context,"opcao deletar funcionando, valor da tag" + id,Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).show();
        return false;
    }

    public void editarMotorista(int id){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.form_motorista,null,false);

        final EditText nomeMotorista = new EditText(view.getContext());
        nomeMotorista.setText("meus ovos");
        final EditText nomeDeGuerra = (EditText) view.findViewById(R.id.nomeDeguerra);
        nomeDeGuerra.setText("fkjkndjkfbjsdbfj");
        final EditText cpf = (EditText) view.findViewById(R.id.cpf);

        new AlertDialog.Builder(context)
                .setView(view)
                .setTitle("Editando")
                .setPositiveButton("Atuaizar dados", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Motorista motorista = new Motorista();

                        motorista.setMotNomeCompleto(nomeMotorista.getText().toString());
                        motorista.setMotNomeGuerra(nomeDeGuerra.getText().toString());
                        motorista.setMotCpf(Long.parseLong(cpf.getText().toString()));




                        dialog.cancel();
                    }
                }).show();



    }
}
