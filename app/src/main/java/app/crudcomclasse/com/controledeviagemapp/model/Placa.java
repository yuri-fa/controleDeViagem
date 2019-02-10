package app.crudcomclasse.com.controledeviagemapp.model;

import java.math.BigDecimal;

public class Placa {
    private Integer plaNumSequencial;
    private String serial;
    private BigDecimal peso;
    private Integer viagem;

    public Placa() {
    }

    public Integer getPlaNumSequencial() {
        return plaNumSequencial;
    }

    public void setPlaNumSequencial(Integer plaNumSequencial) {
        this.plaNumSequencial = plaNumSequencial;
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
