package app.crudcomclasse.com.controledeviagemapp.model;

public class Veiculo {

    private Integer veiNumSequencial;
    private String placa;

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

    @Override
    public String toString(){
        return placa;
    }
}
