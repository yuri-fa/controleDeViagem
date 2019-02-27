package app.crudcomclasse.com.controledeviagemapp.model;

import java.util.List;

public class Relatorio {
    private String motorista;
    private String veiculo;
    private String pesoTotal;
    private List<String> placas;

    public Relatorio() {
    }

    public Relatorio(String motorista, String veiculo, String pesoTotal) {
        this.motorista = motorista;
        this.veiculo = veiculo;
        this.pesoTotal = pesoTotal;
    }

    public String dadosRelatorio(int quantidadeDeviagens){
        String linha = "";
        linha = motorista+","+veiculo;
        for(String placa : placas){
            linha += ","+ placa;
        }
        if (quantidadeDeviagens > placas.size()){
            for (int i = 0;i < quantidadeDeviagens - placas.size();i++){
                linha += ",0";
            }
        }
        linha += ","+ pesoTotal;
        return linha;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(String pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public List<String> getPlacas() {
        return placas;
    }

    public void setPlacas(List<String> placas) {
        this.placas = placas;
    }



}
