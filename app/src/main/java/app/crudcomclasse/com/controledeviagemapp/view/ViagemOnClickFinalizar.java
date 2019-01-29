package app.crudcomclasse.com.controledeviagemapp.view;

import android.view.View;
import android.widget.Toast;

import java.util.Date;

import app.crudcomclasse.com.controledeviagemapp.model.Viagem;

public class ViagemOnClickFinalizar implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        boolean valido = true;
        Viagem viagem = ViagemActivity.getViagem();
        if (viagem.getVeiculo() == null){
            valido = false;
            Toast.makeText(v.getContext(),"Informe o veiculo",Toast.LENGTH_LONG).show();
        }
        if (viagem.getConjuntoDePlacas() == null || viagem.getConjuntoDePlacas().size() == 0){
            valido = false;
            Toast.makeText(v.getContext(),"Informe um produto",Toast.LENGTH_LONG).show();
        }
        if (valido){
            viagem.setDthrViagem(new Date());
            boolean isInsert = new ViagemController(v.getContext()).inserirViagem(viagem);
            if (isInsert){
                Toast.makeText(v.getContext(),"Viagem cadastrada com sucesso",Toast.LENGTH_LONG).show();
                ((ViagemActivity) v.getContext()).limparDados();
            }else{
                Toast.makeText(v.getContext(),"Falha tente novamente",Toast.LENGTH_LONG).show();
            }
        }
    }
}
