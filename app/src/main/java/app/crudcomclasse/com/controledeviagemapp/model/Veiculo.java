package app.crudcomclasse.com.controledeviagemapp.model;

public class Veiculo {
    private Integer veiNumSequencial;
    private String placa;
    private String tipo;



    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getVeiNumSequencial() {
        return veiNumSequencial;
    }

    public void setVeiNumSequencial(Integer veiNumSequencial) {
        this.veiNumSequencial = veiNumSequencial;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
