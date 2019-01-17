package app.crudcomclasse.com.controledeviagemapp.model;

import java.util.Date;
import java.util.List;

public class Viagem {
    private int numSequencial;
    private Motorista motorista;
    private List<Placa> conjuntoDePlacas;
    private Veiculo veiculo;
    private Date dthrViagem;


    public int getNumSequencial() {
        return numSequencial;
    }

    public void setNumSequencial(int numSequencial) {
        this.numSequencial = numSequencial;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public List<Placa> getConjuntoDePlacas() {
        return conjuntoDePlacas;
    }

    public void setConjuntoDePlacas(List<Placa> conjuntoDePlacas) {
        this.conjuntoDePlacas = conjuntoDePlacas;
    }

    public Date getDthrViagem() {
        return dthrViagem;
    }

    public void setDthrViagem(Date dthrViagem) {
        this.dthrViagem = dthrViagem;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
