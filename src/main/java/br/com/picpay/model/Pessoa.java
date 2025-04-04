package br.com.picpay.model;

import br.com.picpay.datafactory.TipoPessoa;

public abstract class Pessoa {

    private String nomeCompletoPessoa;
    private String emailPessoa;
    private String senhaPessoa;
    private TipoPessoa tipoPessoa;

    public Pessoa(String nomeCompletoPessoa, String emailPessoa, String senhaPessoa, TipoPessoa tipoPessoa) {
        this.nomeCompletoPessoa = nomeCompletoPessoa;
        this.emailPessoa = emailPessoa;
        this.senhaPessoa = senhaPessoa;
        this.tipoPessoa = tipoPessoa;
    }

    public Pessoa() {
    }

    public String getNomeCompletoPessoa() {
        return nomeCompletoPessoa;
    }

    public void setNomeCompletoPessoa(String nomeCompletoPessoa) {
        this.nomeCompletoPessoa = nomeCompletoPessoa;
    }

    public String getEmailPessoa() {
        return emailPessoa;
    }

    public void setEmailPessoa(String emailPessoa) {
        this.emailPessoa = emailPessoa;
    }

    public String getSenhaPessoa() {
        return senhaPessoa;
    }

    public void setSenhaPessoa(String senhaPessoa) {
        this.senhaPessoa = senhaPessoa;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa pessoa)) return false;

        return nomeCompletoPessoa.equals(pessoa.nomeCompletoPessoa) && emailPessoa.equals(pessoa.emailPessoa) && senhaPessoa.equals(pessoa.senhaPessoa) && tipoPessoa.equals(pessoa.tipoPessoa);
    }

    @Override
    public int hashCode() {
        int result = nomeCompletoPessoa.hashCode();
        result = 31 * result + emailPessoa.hashCode();
        result = 31 * result + senhaPessoa.hashCode();
        result = 31 * result + tipoPessoa.hashCode();
        return result;
    }

}
