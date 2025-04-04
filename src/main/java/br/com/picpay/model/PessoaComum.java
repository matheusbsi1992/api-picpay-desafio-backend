package br.com.picpay.model;

import br.com.picpay.datafactory.TipoPessoa;

public class PessoaComum extends Pessoa{

    private String cpfPessoaComum;

    public PessoaComum(String nomeCompletoPessoa, String emailPessoa, String senhaPessoa, TipoPessoa tipoPessoa, String cpfPessoaComum) {
        super(nomeCompletoPessoa, emailPessoa, senhaPessoa, tipoPessoa);
        this.cpfPessoaComum = cpfPessoaComum;
    }

    public PessoaComum(String cpfPessoaComum) {
        this.cpfPessoaComum = cpfPessoaComum;
    }

    public PessoaComum(){

    }

    public String getCpfPessoaComum() {
        return cpfPessoaComum;
    }

    public void setCpfPessoaComum(String cpfPessoaComum) {
        this.cpfPessoaComum = cpfPessoaComum;
    }

}
