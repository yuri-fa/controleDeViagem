package app.crudcomclasse.com.controledeviagemapp.model;

import java.math.BigDecimal;

public class Placa {

    private String serial;
    private BigDecimal peso;

    public Placa() {
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
