package br.com.picpay.datafactory;

import br.com.picpay.dto.PessoaDTO;
import br.com.picpay.model.PessoaComum;
import br.com.picpay.model.PessoaLojista;

public class PessoaData {


    public static PessoaDTO pessoaComum(int valor) {

        PessoaDTO pessoaDTO = new PessoaDTO();

        PessoaComum pessoaComum = new PessoaComum();

        pessoaComum.setCpfPessoaComum("123.456.789-1" + valor);

        pessoaDTO.setEmailPessoaDTO("fuica@gmail.com");
        pessoaDTO.setNomeCompletoPessoaDTO("Garrincha");
        pessoaDTO.setSenhaPessoaDTO("123456");
        pessoaDTO.setTipoPessoaDTO(TipoPessoa.valorExistenteTipoPessoa("C"));
        pessoaDTO.setCpfPessoaDTO(pessoaComum);

        return pessoaDTO;
    }

    public static PessoaDTO pessoaLojista(int valor) {

        PessoaDTO pessoaDTO = new PessoaDTO();

        PessoaLojista pessoaLojista = new PessoaLojista();

        pessoaLojista.setCnpjPessoaLojista("48.063.173/0001-8" + valor);

        pessoaDTO.setEmailPessoaDTO("maria.luiza@gmail.com");
        pessoaDTO.setNomeCompletoPessoaDTO("Maria Luiza");
        pessoaDTO.setSenhaPessoaDTO("123456");
        pessoaDTO.setTipoPessoaDTO(TipoPessoa.valorExistenteTipoPessoa("L"));
        pessoaDTO.setCnpjPessoaDTO(pessoaLojista);

        return pessoaDTO;
    }
}
