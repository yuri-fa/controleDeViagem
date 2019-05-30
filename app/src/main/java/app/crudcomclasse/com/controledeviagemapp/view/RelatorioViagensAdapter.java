package app.crudcomclasse.com.controledeviagemapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.ViagemController;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;
import app.crudcomclasse.com.controledeviagemapp.util.ControleViagemUtil;

public class RelatorioViagensAdapter extends RecyclerView.Adapter<RelatorioViagensAdapter.ViewHolder>{

    private RecyclerView recyclerView;
    private List<Viagem> viagemList;
    private Context context;

    public RelatorioViagensAdapter(RecyclerView recyclerView, List<Viagem> viagemList, Context context) {
        this.recyclerView = recyclerView;
        this.viagemList = viagemList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View layout;
        TextView codigo;
        TextView data;
        TextView motorista;
        TextView veiculo;
        LinearLayout conjuntoPlacas;
        ImageButton editarViagem;
        ImageButton excluirViagem;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            codigo = (TextView) layout.findViewById(R.id.txt_viagem_codigo);
            data = (TextView) layout.findViewById(R.id.txt_viagem_data);
            motorista = (TextView) layout.findViewById(R.id.txt_viagem_motorista);
            veiculo = (TextView) layout.findViewById(R.id.txt_viagem_veiculo);
            conjuntoPlacas = (LinearLayout) layout.findViewById(R.id.conjunto_placas);
            editarViagem = (ImageButton) layout.findViewById(R.id.editar_viagem);
            excluirViagem = (ImageButton) layout.findViewById(R.id.excluir_viagem);
        }
    }

    @NonNull
    @Override
    public RelatorioViagensAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.relatorio_dados_da_viagem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RelatorioViagensAdapter.ViewHolder holder, int position) {
        final Viagem viagem = viagemList.get(position);
        final List<Placa> placaList = viagem.getConjuntoDePlacas();
        holder.codigo.setText("Codigo : "+ viagem.getNumSequencial());
        holder.data.setText("Data : "+ ControleViagemUtil.formatarDataParaString(viagem.getDthrViagem()));
        holder.veiculo.setText("Veiculo : " + viagem.getVeiculo().getPlaca());
        holder.motorista.setText("Motorista : " + viagem.getMotorista().getMotNomeCompleto());
        holder.conjuntoPlacas.removeAllViews();

        for (Placa placa : placaList){
            TextView textSerial = new TextView(context);
            textSerial.setPadding(10,0,0,0);
            textSerial.setText("Serial : " + placa.getSerial());
            textSerial.setTextSize(20);

            TextView textPeso = new TextView(context);
            textPeso.setPadding(10,0,0,0);
            textPeso.setText("Peso : "+ placa.getPeso().toString());
            textPeso.setTextSize(20);

            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(textSerial);
            linearLayout.addView(textPeso);

            CardView card = new CardView(context);
            card.setCardElevation(3);
            card.setRadius(4);
            card.setPadding(5,10,5,10);
            card.setCardBackgroundColor(0000);
            card.addView(linearLayout);
            holder.conjuntoPlacas.addView(card);
        }

        holder.editarViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViagemActivity.class);
                intent.putExtra("IDVIAGEM", viagem.getNumSequencial());
                context.startActivity(intent);
            }
        });
        holder.excluirViagem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Deseja excluir a viagem?");
                builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        boolean excluido = new ViagemController(context).excluirViagem(viagem.getNumSequencial());
                        if (excluido){
                            Toast.makeText(context,"Viagem excluida com sucesso",Toast.LENGTH_SHORT).show();
                            ((RelatoriosActivity)context).pesquisarViagens(v);
                        }else{
                            Toast.makeText(context,"Falha tente novamente",Toast.LENGTH_SHORT).show();
                        }
                    }});
                builder.setNeutralButton("Cancelar",new DialogInterface.OnClickListener(){
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
        return viagemList.size();
    }
}
