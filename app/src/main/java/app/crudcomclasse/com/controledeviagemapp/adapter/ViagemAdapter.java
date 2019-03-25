package app.crudcomclasse.com.controledeviagemapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class ViagemAdapter extends RecyclerView.Adapter<ViagemAdapter.ViewHolder> {

    private List<Viagem> viagemList;
    private Context context;
    private RecyclerView recyclerView;

    public ViagemAdapter(List<Viagem> viagemList, Context context, RecyclerView recyclerView) {
        this.viagemList = viagemList;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView codigo;
        public TextView data;
        public TextView motorista;
        public TextView veiculo;
        public LinearLayout placas;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
//            this.codigo = (TextView) layout.findViewById(R.id.viagem_codigo);
//            this.data = (TextView) layout.findViewById(R.id.viagem_data);
//            this.motorista = (TextView) layout.findViewById(R.id.viagem_motorista);
//            this.veiculo = (TextView) layout.findViewById(R.id.viagem_veiculo);
//            this.placas = (LinearLayout) layout.findViewById(R.id.placas);
        }

    }

    public void add(Integer position,Viagem viagem){
        this.viagemList.add(position,viagem);
        notifyItemInserted(position);
    }

    public void removo(Integer position){
        this.viagemList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View layout = layoutInflater.inflate(R.layout.card_view_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Viagem viagem = viagemList.get(position);
    }

    @Override
    public int getItemCount() {
        return viagemList.size();
    }
}
