package br.com.picpay.datafactory;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TipoPessoa {

    PESSOACOMUM("C", "COMUM"),
    PESSOALOJISTA("L", "LOJISTA");

    private String tipoPessoa;
    private String identificador;

    private TipoPessoa(String identificador, String tipodePessoa) {
        this.identificador = identificador;
        this.tipoPessoa = tipodePessoa;
    }

    public static TipoPessoa valorExistenteTipoPessoa(String identificador) {
        if (identificador.equals("C")) {
            return PESSOACOMUM;
        } else if (identificador.equals("L")) {
            return PESSOALOJISTA;
        } else return null;
    }


    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
