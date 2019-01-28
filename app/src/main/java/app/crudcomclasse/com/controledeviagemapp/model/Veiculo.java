package app.crudcomclasse.com.controledeviagemapp.model;

public class Veiculo {
    private Integer veiNumSequencial;
    private String placa;
    private Motorista motorista;

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

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
}
