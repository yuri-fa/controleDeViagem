package app.crudcomclasse.com.controledeviagemapp.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViagemHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static Context context;
    private static MyClickListener myClickListener;
    private List<Viagem> viagemList;

    public static class  ViagemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView viagemCodigo;
        TextView viagemCodigoValor;
        TextView viagemData;
        TextView viagemDataValor;
        TextView viagemMotorista;
        TextView viagemMotoristaNome;
        TextView viagemVeiculo;
        TextView viagemVeiculoPlaca;
        LinearLayout viagemPlacas;

        public ViagemHolder(View itemView) {
            super(itemView);
            viagemCodigo = itemView.findViewById(R.id.txt_viagem_codigo);
            viagemCodigoValor = itemView.findViewById(R.id.txt_viagem_codigo_valor);
            viagemData = itemView.findViewById(R.id.txt_viagem_data);
            viagemDataValor = itemView.findViewById(R.id.txt_viagem_data_valor);
            viagemMotorista = itemView.findViewById(R.id.txt_viagem_motorista);
            viagemMotoristaNome = itemView.findViewById(R.id.txt_viagem_motorista_nome);
            viagemVeiculo = itemView.findViewById(R.id.txt_viagem_veiculo);
            viagemVeiculoPlaca = itemView.findViewById(R.id.txt_viagem_veiculo_placa);
//            viagemPlacas = itemView.findViewById(R.id.viagem_placas);
            Log.i(LOG_TAG,"Adicionando OnClick");
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(),v);
            context = v.getContext();
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListenerTemp){
        myClickListener = myClickListenerTemp;
    }

    public MyRecyclerViewAdapter(List<Viagem> viagemList) {
        this.viagemList = viagemList;
    }

    @NonNull
    @Override
    public ViagemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row,parent,false);
        ViagemHolder viagemHolder = new ViagemHolder(view);
        return viagemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViagemHolder holder, int position) {
        Viagem viagemTemp = viagemList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        holder.viagemCodigo.setText("Codigo");
        holder.viagemCodigoValor.setText(viagemTemp.getNumSequencial().toString());
        holder.viagemData.setText("Data da Viagem");
        holder.viagemDataValor.setText(dateFormat.format(viagemTemp.getDthrViagem()));
        holder.viagemMotorista.setText("Motorista");
        holder.viagemMotoristaNome.setText(viagemTemp.getMotorista().getMotNomeCompleto());
        holder.viagemVeiculo.setText("Veiculo");
        holder.viagemVeiculoPlaca.setText(viagemTemp.getVeiculo().getPlaca());
    }

    public void addItem(Viagem viagem,int index){
        viagemList.add(index,viagem);
        notifyItemInserted(index);
    }

    public void removeItemClick(int index){
        viagemList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return viagemList.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
