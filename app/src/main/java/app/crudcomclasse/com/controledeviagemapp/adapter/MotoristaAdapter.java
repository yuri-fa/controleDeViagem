package app.crudcomclasse.com.controledeviagemapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.MotoristaController;
import app.crudcomclasse.com.controledeviagemapp.model.Motorista;
import app.crudcomclasse.com.controledeviagemapp.view.MotoristaActivity;

public class MotoristaAdapter extends RecyclerView.Adapter<MotoristaAdapter.ViewHolder> {

    private List<Motorista> motoristaList;
    private Context context;
    private RecyclerView recyclerView;

    public MotoristaAdapter(List<Motorista> motoristaList, Context context, RecyclerView recyclerView) {
        this.motoristaList = motoristaList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomeCompleto;
        public TextView nomeGuerra;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nomeCompleto = (TextView) layout.findViewById(R.id.nomeCompleto);
            nomeGuerra = (TextView) layout.findViewById(R.id.nomeDeguerra);
        }
    }

    public void add(Integer posicao,Motorista motorista){
        motoristaList.add(posicao,motorista);
        notifyItemInserted(posicao);
    }

    public void remove(Integer posicao){
        motoristaList.remove(posicao);
        notifyItemRemoved(posicao);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View layout = layoutInflater.inflate(R.layout.row_motorista,parent,false);
        ViewHolder viewHolder = new ViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Motorista motorista = motoristaList.get(position);
        holder.nomeCompleto.setText("Nome Completo : "+motorista.getMotNomeCompleto());
        holder.nomeGuerra.setText(" Nome de Guerra : "+motorista.getMotNomeGuerra());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Atualizar ou deletar");
                builder.setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View view = layoutInflater.inflate(R.layout.form_motorista,null,false);

                        final EditText nomeMotorista = (EditText) view.findViewById(R.id.form_nome_Completo);
                        nomeMotorista.setText(motorista.getMotNomeCompleto());
                        final EditText nomeDeGuerra = (EditText) view.findViewById(R.id.form_nome_guerra);
                        nomeDeGuerra.setText(motorista.getMotNomeGuerra());
                        new AlertDialog.Builder(context)
                                .setView(view)
                                .setTitle("Editando")
                                .setPositiveButton("Atuaizar dados", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Motorista motoristaUpdate = new Motorista();
                                        motoristaUpdate.setMotNumSequencial(motorista.getMotNumSequencial());
                                        motoristaUpdate.setMotNomeCompleto(nomeMotorista.getText().toString());
                                        motoristaUpdate.setMotNomeGuerra(nomeDeGuerra.getText().toString());

                                        boolean isUpdate = new MotoristaController(context).update(motoristaUpdate);

                                        if(isUpdate){
                                            Toast.makeText(context,"Motorista atualizado com sucesso",Toast.LENGTH_LONG).show();
                                            ((MotoristaActivity) context).pesquisarTodos();
                                        }else{
                                            Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_LONG).show();
                                        }

                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                });
                builder.setNeutralButton("Cancelar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDelete = new MotoristaController(context).deletarMotorista(motorista.getMotNumSequencial());
                        if(isDelete){
                            Toast.makeText(context,"Motorista deletado com sucesso",Toast.LENGTH_LONG).show();
                            ((MotoristaActivity)context).pesquisarTodos();
                        }else{
                            Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return motoristaList.size();
    }
}