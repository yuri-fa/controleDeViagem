package app.crudcomclasse.com.controledeviagemapp.model;

public class Motorista {

    private Integer motNumSequencial;
    private String motNomeCompleto;
    private String motNomeGuerra;
    private Long motCpf;

    public Motorista(){

    }

    public Integer getMotNumSequencial() {
        return motNumSequencial;
    }

    public void setMotNumSequencial(Integer motNumSequencial) {
        this.motNumSequencial = motNumSequencial;
    }

    public String getMotNomeCompleto() {
        return motNomeCompleto;
    }

    public void setMotNomeCompleto(String motNomeCompleto) {
        this.motNomeCompleto = motNomeCompleto;
    }

    public String getMotNomeGuerra() {
        return motNomeGuerra;
    }

    public void setMotNomeGuerra(String motNomeGuerra) {
        this.motNomeGuerra = motNomeGuerra;
    }

    public Long getMotCpf() {
        return motCpf;
    }

    public void setMotCpf(Long motCpf) {
        this.motCpf = motCpf;
    }
}
