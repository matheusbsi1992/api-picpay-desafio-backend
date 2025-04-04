package br.com.picpay.dto;

import br.com.picpay.datafactory.TipoPessoa;
import br.com.picpay.model.PessoaComum;
import br.com.picpay.model.PessoaLojista;
import br.com.picpay.model.Transferencia;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PessoaDTO {

    @JsonProperty("nomeCompletoPessoa")
    private String nomeCompletoPessoaDTO;
    @JsonProperty("pessoaComum")
    private PessoaComum cpfPessoaDTO;
    @JsonProperty("pessoaLojista")
    private PessoaLojista cnpjPessoaDTO;
    @JsonProperty("emailPessoa")
    private String emailPessoaDTO;
    @JsonProperty("senhaPessoa")
    private String senhaPessoaDTO;
    @JsonProperty("tipoPessoa")
    private TipoPessoa tipoPessoaDTO;
    @JsonProperty("transferencia")
    private Transferencia transferenciaDTO;

    //-- os dados de Pessoa DTO esta me retornando como nulo
    public static PessoaDTO novaPessoaComum(PessoaComum pessoaComum) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNomeCompletoPessoaDTO(pessoaComum.getNomeCompletoPessoa());
        pessoaDTO.setEmailPessoaDTO(pessoaComum.getEmailPessoa());
        pessoaDTO.setSenhaPessoaDTO(pessoaComum.getSenhaPessoa());
        pessoaDTO.setTipoPessoaDTO(pessoaComum.getTipoPessoa());
        pessoaDTO.setCpfPessoaDTO(pessoaComum);
        return pessoaDTO;
    }

    public static PessoaDTO novaPessoaLojista(PessoaLojista pessoaLojista) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNomeCompletoPessoaDTO(pessoaLojista.getNomeCompletoPessoa());
        pessoaDTO.setEmailPessoaDTO(pessoaLojista.getEmailPessoa());
        pessoaDTO.setSenhaPessoaDTO(pessoaLojista.getSenhaPessoa());
        pessoaDTO.setTipoPessoaDTO(pessoaLojista.getTipoPessoa());
        pessoaDTO.setCnpjPessoaDTO(pessoaLojista);
        return pessoaDTO;
    }

    public PessoaDTO() {
    }

    public String getNomeCompletoPessoaDTO() {
        return nomeCompletoPessoaDTO;
    }

    public void setNomeCompletoPessoaDTO(String nomeCompletoPessoaDTO) {
        this.nomeCompletoPessoaDTO = nomeCompletoPessoaDTO;
    }

    public PessoaComum getCpfPessoaDTO() {
        return cpfPessoaDTO;
    }

    public void setCpfPessoaDTO(PessoaComum cpfPessoaDTO) {
        this.cpfPessoaDTO = cpfPessoaDTO;
    }

    public PessoaLojista getCnpjPessoaDTO() {
        return cnpjPessoaDTO;
    }

    public void setCnpjPessoaDTO(PessoaLojista cnpjPessoaDTO) {
        this.cnpjPessoaDTO = cnpjPessoaDTO;
    }

    public String getEmailPessoaDTO() {
        return emailPessoaDTO;
    }

    public void setEmailPessoaDTO(String emailPessoaDTO) {
        this.emailPessoaDTO = emailPessoaDTO;
    }

    public String getSenhaPessoaDTO() {
        return senhaPessoaDTO;
    }

    public void setSenhaPessoaDTO(String senhaPessoaDTO) {
        this.senhaPessoaDTO = senhaPessoaDTO;
    }

    public TipoPessoa getTipoPessoaDTO() {
        return tipoPessoaDTO;
    }

    public void setTipoPessoaDTO(TipoPessoa tipoPessoaDTO) {
        this.tipoPessoaDTO = tipoPessoaDTO;
    }

    public Transferencia getTransferenciaDTO() {
        return transferenciaDTO;
    }

    public void setTransferenciaDTO(Transferencia transferenciaDTO) {
        this.transferenciaDTO = transferenciaDTO;
    }
}
