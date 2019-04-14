package app.crudcomclasse.com.controledeviagemapp.view;

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

import java.math.BigDecimal;
import java.util.List;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.controller.ViagemController;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;

public class PlacaAdapter extends RecyclerView.Adapter<PlacaAdapter.ViewHolder> {

    private RecyclerView recyclerView;
    private List<Placa> placaList;
    private Context context;

    public PlacaAdapter(RecyclerView recyclerView, List<Placa> placaList, Context context) {
        this.recyclerView = recyclerView;
        this.placaList = placaList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View layout;
        private TextView serial;
        private TextView peso;
 
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            serial = layout.findViewById(R.id.placa_serial);
            peso = layout.findViewById(R.id.placa_peso);
        }
    }

    public void add(Integer position, Placa placa){
        placaList.add(position,placa);
        notifyItemInserted(position);
    }

    public void remove(Integer position){
        placaList.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public PlacaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.placa_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacaAdapter.ViewHolder holder, final int position) {
        final Placa placa = placaList.get(position);
        holder.serial.setText(placa.getSerial());
        holder.peso.setText(placa.getPeso().toString());
        holder.layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Opçoes");
                builder.setNeutralButton("Editar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View view = layoutInflater.inflate(R.layout.form_viagem_placas,null,false);
                        final EditText serial = view.findViewById(R.id.txt_serial);
                        final EditText peso = view.findViewById(R.id.txt_peso);
                        peso.setText(placa.getPeso().toString());
                        serial.setText(placa.getSerial());

                        new AlertDialog.Builder(context)
                                .setTitle("Editando")
                                .setView(view)
                                .setPositiveButton("Salvar", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean valido = true;
                                        if (serial.getText().toString() == null || serial.getText().toString().equals("")){
                                            System.out.println(serial.getText().toString());
                                            String teste = serial.getText().toString();
                                            valido = false;
                                            Toast.makeText(context, "Informe o Serial", Toast.LENGTH_SHORT).show();
                                        }
                                        if (peso.getText().toString() == null || peso.getText().toString().equals("")){
                                            valido = false;
                                            Toast.makeText(context, "Informe o peso", Toast.LENGTH_SHORT).show();
                                        }
                                        if (valido){
                                            placaList.get(position).setPeso(new BigDecimal(peso.getText().toString()));
                                            placaList.get(position).setSerial(serial.getText().toString());
                                            notifyItemChanged(position);
                                        }
                                    }
                                }).show();
                    }
                });
                builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (placa.getPlaNumSequencial() != null){
//                            boolean excluido = new ViagemController(context).excluirPlaca(placa.getPlaNumSequencial());
//                        }
                        ((ViagemActivity)context).removerPlaca(position);
                    }
                } );
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return placaList.size();
    }

    public List<Placa> getPlacaList() {
        return placaList;
    }
}
