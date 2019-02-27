package app.crudcomclasse.com.controledeviagemapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import app.crudcomclasse.com.controledeviagemapp.R;
import app.crudcomclasse.com.controledeviagemapp.model.Placa;
import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private final LayoutInflater layoutInflater;
    private ArrayList<Viagem> viagemList;

    public ExpandListAdapter(Context context, ArrayList<Viagem> viagens) {
        this.layoutInflater = LayoutInflater.from(context);
        this.viagemList = viagens;
    }

    @Override
    public int getGroupCount() {
        return viagemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return viagemList.get(groupPosition).getConjuntoDePlacas().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return viagemList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return viagemList.get(groupPosition).getConjuntoDePlacas().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View linha = convertView;
        ViewHolder viewHolder;

        if (linha == null){
            linha = layoutInflater.inflate(R.layout.relatorio_dados_da_viagem,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.addView(linha.findViewById(R.id.txt_viagem_codigo))
                    .addView(linha.findViewById(R.id.txt_viagem_data))
                    .addView(linha.findViewById(R.id.txt_viagem_motorista))
                    .addView(linha.findViewById(R.id.txt_viagem_veiculo));
            linha.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) linha.getTag();
        }
        Viagem viagemTemp = viagemList.get(groupPosition);
        TextView textViewCodigo = (TextView) viewHolder.getView(R.id.txt_viagem_codigo);
        TextView textViewData= (TextView) viewHolder.getView(R.id.txt_viagem_data);
        TextView textViewMotorista = (TextView) viewHolder.getView(R.id.txt_viagem_motorista);
        TextView textViewVeiculo = (TextView) viewHolder.getView(R.id.txt_viagem_veiculo);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        textViewCodigo.setText("Codigo : " + viagemTemp.getNumSequencial().toString());
        textViewData.setText("Data : " + dateFormat.format(viagemTemp.getDthrViagem()));
        textViewMotorista.setText("Motorista : " + viagemTemp.getMotorista().getMotNomeCompleto() );
        textViewVeiculo.setText("Veiculo : " + viagemTemp.getVeiculo().getPlaca());
        return linha;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View linha = convertView;
        ViewHolder viewHolder;

        if (linha == null){
            linha = layoutInflater.inflate(R.layout.relatorio_dados_placas,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.addView(linha.findViewById(R.id.txt_relatorio_serial))
                      .addView(linha.findViewById(R.id.txt_relatorio_peso));
            linha.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) linha.getTag();
        }
        Placa placaTemp = viagemList.get(groupPosition).getConjuntoDePlacas().get(childPosition);
        TextView textViewSerial = (TextView) viewHolder.getView(R.id.txt_relatorio_serial);
        TextView textViewPeso  = (TextView) viewHolder.getView(R.id.txt_relatorio_peso);

        textViewSerial.setText("Serial : " + placaTemp.getSerial());
        textViewPeso.setText("Peso : " + placaTemp.getPeso().toString());

        return linha;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolder {
        private HashMap<Integer, View> storedViews = new HashMap<Integer, View>();

        public ViewHolder addView(View view) {
            int id = view.getId();
            storedViews.put(id, view);
            return this;
        }

        public View getView(int id) {
            return storedViews.get(id);
        }
    }
}
