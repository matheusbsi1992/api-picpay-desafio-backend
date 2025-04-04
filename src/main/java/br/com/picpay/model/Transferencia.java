package br.com.picpay.model;


public class Transferencia {

    private double valorDeTransferencia;

    private double saldoDeTransferencia;

    private double saqueTransferencia;

    private PessoaComum pessoaComumEmissor;

    private PessoaComum pessoaComumReceptor;

    private PessoaLojista pessoaLojista;

    public Transferencia(PessoaComum pessoaComumEmissor, PessoaLojista pessoaLojista, double valorDeTransferencia) {
        this.valorDeTransferencia = valorDeTransferencia;
        this.pessoaComumEmissor = pessoaComumEmissor;
        this.pessoaLojista = pessoaLojista;
    }

    public Transferencia(PessoaComum pessoaComumEmissor, PessoaComum pessoaComumReceptor, double valorDeTransferencia) {
        this.valorDeTransferencia = valorDeTransferencia;
        this.pessoaComumEmissor = pessoaComumEmissor;
        this.pessoaComumReceptor = pessoaComumReceptor;
    }

    public Transferencia() {
    }

    public double getValorDeTransferencia() {
        return valorDeTransferencia;
    }

    public void setValorDeTransferencia(double valorDeTransferencia) {
        this.valorDeTransferencia = valorDeTransferencia;
    }

    public double getSaldoDeTransferencia() {
        return saldoDeTransferencia;
    }

    public void setSaldoDeTransferencia(double saldoDeTransferencia) {
        this.saldoDeTransferencia = saldoDeTransferencia;
    }

    public double getSaqueTransferencia() {
        return saqueTransferencia;
    }

    public void setSaqueTransferencia(double saqueTransferencia) {
        this.saqueTransferencia = saqueTransferencia;
    }

    public PessoaComum getPessoaComumEmissor() {
        return pessoaComumEmissor;
    }

    public void setPessoaComumEmissor(PessoaComum pessoaComumEmissor) {
        this.pessoaComumEmissor = pessoaComumEmissor;
    }

    public PessoaComum getPessoaComumReceptor() {
        return pessoaComumReceptor;
    }

    public void setPessoaComumReceptor(PessoaComum pessoaComumReceptor) {
        this.pessoaComumReceptor = pessoaComumReceptor;
    }

    public PessoaLojista getPessoaLojista() {
        return pessoaLojista;
    }

    public void setPessoaLojista(PessoaLojista pessoaLojista) {
        this.pessoaLojista = pessoaLojista;
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "valorDeTransferencia=" + valorDeTransferencia +
                ", saldoDeTransferencia=" + saldoDeTransferencia +
                ", saqueTransferencia=" + saqueTransferencia +
                ", pessoaComumEmissor=" + pessoaComumEmissor +
                ", pessoaComumReceptor=" + pessoaComumReceptor +
                ", pessoaLojista=" + pessoaLojista +
                '}';
    }
}