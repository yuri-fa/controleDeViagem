package app.crudcomclasse.com.controledeviagemapp.model;

import java.math.BigDecimal;

public class Placa {

    private String serial;
    private BigDecimal peso;
    private Integer viagem;

    public Placa() {
    }

    public Integer getViagem() {
        return viagem;
    }

    public void setViagem(Integer viagem) {
        this.viagem = viagem;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
