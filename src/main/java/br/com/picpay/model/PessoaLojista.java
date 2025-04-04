package br.com.picpay.model;

import br.com.picpay.dto.PessoaDTO;
import br.com.picpay.datafactory.TipoPessoa;
import org.springframework.beans.BeanUtils;

public class PessoaLojista extends Pessoa {

    private String cnpjPessoaLojista;

    public PessoaLojista(String nomeCompletoPessoa, String emailPessoa, String senhaPessoa, TipoPessoa tipoPessoa, String cnpjPessoaLojista) {
        super(nomeCompletoPessoa, emailPessoa, senhaPessoa, tipoPessoa);
        this.cnpjPessoaLojista = cnpjPessoaLojista;
    }

    public PessoaLojista(String cnpjPessoaLojista) {
        this.cnpjPessoaLojista = cnpjPessoaLojista;
    }

    public PessoaLojista() {}

    public String getCnpjPessoaLojista() {
        return cnpjPessoaLojista;
    }

    public void setCnpjPessoaLojista(String cnpjPessoaLojista) {
        this.cnpjPessoaLojista = cnpjPessoaLojista;
    }
}
