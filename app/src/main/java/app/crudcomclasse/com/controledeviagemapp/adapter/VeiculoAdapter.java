package app.crudcomclasse.com.controledeviagemapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import app.crudcomclasse.com.controledeviagemapp.view.VeiculoActivity;

public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoAdapter.ViewHolder> {

    private RecyclerView recyclerView;
    private List<Veiculo> veiculoList;
    private Context context;

    public VeiculoAdapter(RecyclerView recyclerView, List<Veiculo> veiculoList, Context context) {
        this.recyclerView = recyclerView;
        this.veiculoList = veiculoList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private View layout;
        private TextView placaVeiculo;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            placaVeiculo = (TextView) layout.findViewById(R.id.placa_veiculo);
        }
    }

    public void add(Integer position,Veiculo veiculo){
        veiculoList.add(position,veiculo);
        notifyItemInserted(position);
    }

    public void remove(Integer position){
        veiculoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_veiculo,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Veiculo veiculo = veiculoList.get(position);
        holder.placaVeiculo.setText(veiculo.getPlaca());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Atualizar ou Deletar");
               builder.setPositiveButton("Atualizar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View view = layoutInflater.inflate(R.layout.form_veiculo,null,false);
                        final TextView placa = (TextView) view.findViewById(R.id.form_placa);
                        placa.setText(veiculo.getPlaca());

                        new android.support.v7.app.AlertDialog
                                .Builder(context)
                                .setView(view)
                                .setTitle("Editando")
                                .setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Veiculo veiculoUpdate = new Veiculo();
                                        veiculoUpdate.setVeiNumSequencial(veiculo.getVeiNumSequencial());
                                        veiculoUpdate.setPlaca(placa.getText().toString());
                                        boolean isUpdate = new VeiculoController(context).atualizarVeiculo(veiculoUpdate);

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
                });
               builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       boolean isDelete = new VeiculoController(context).deletarVeiculo(veiculo.getVeiNumSequencial());
                       if (isDelete){
                           ((VeiculoActivity)context).pesquisarTodos();
                           Toast.makeText(context,"Veiculo excluido com sucesso",Toast.LENGTH_LONG).show();
                       }else{
                           Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_LONG).show();
                       }
                   }
               });
               builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                   }
               });
               builder.create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return veiculoList.size();
    }
}
