package br.com.picpay.service;

import br.com.picpay.datafactory.TipoPessoa;
import br.com.picpay.dto.PessoaDTO;
import br.com.picpay.model.PessoaComum;
import br.com.picpay.model.PessoaLojista;
import br.com.picpay.model.Transferencia;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.picpay.dto.PessoaDTO.novaPessoaComum;
import static br.com.picpay.dto.PessoaDTO.novaPessoaLojista;

@Service
public class PessoaTransferencia {

    public List<PessoaDTO> objectListPessoa = new ArrayList<>();

    public List<PessoaDTO> inserirPessoa(PessoaDTO pessoaDTO) {

        if (pessoaDTO.getTipoPessoaDTO().getIdentificador().equals(TipoPessoa.PESSOACOMUM.getIdentificador())) {

            PessoaComum pessoaComum = new PessoaComum(pessoaDTO.getNomeCompletoPessoaDTO(),
                    pessoaDTO.getEmailPessoaDTO(),
                    pessoaDTO.getSenhaPessoaDTO(),
                    pessoaDTO.getTipoPessoaDTO(),
                    pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum());

            var pessoaNovaComumDTO = novaPessoaComum(pessoaComum);

            if (existeCPF(pessoaNovaComumDTO.getCpfPessoaDTO().getCpfPessoaComum())) {
                throw new RuntimeException("CPF JÁ EXISTE !!!");
            }
            Transferencia transferencia = new Transferencia();
            pessoaNovaComumDTO.setTransferenciaDTO(transferencia);
            objectListPessoa.add(pessoaNovaComumDTO);

        } else {
            PessoaLojista pessoaLojista = new PessoaLojista(pessoaDTO.getNomeCompletoPessoaDTO(),
                    pessoaDTO.getEmailPessoaDTO(),
                    pessoaDTO.getSenhaPessoaDTO(),
                    pessoaDTO.getTipoPessoaDTO(),
                    pessoaDTO.getCnpjPessoaDTO().getCnpjPessoaLojista());

            var pessoaNovaLojistaDTO = novaPessoaLojista(pessoaLojista);

            if (existeCNPJ(pessoaNovaLojistaDTO.getCnpjPessoaDTO().getCnpjPessoaLojista())) {
                throw new RuntimeException("CNPJ JÁ EXISTE !!!");
            }
            Transferencia transferencia = new Transferencia();
            pessoaNovaLojistaDTO.setTransferenciaDTO(transferencia);
            objectListPessoa.add(pessoaNovaLojistaDTO);
        }

        return objectListPessoa;
    }

    public List<PessoaDTO> retornarTodasPessoas() {
        return this.objectListPessoa
                .stream()
                .toList();
    }

    private Boolean existeCPF(String pessoaComumCPF) {

        return objectListPessoa.stream()
                .anyMatch(p -> Optional.ofNullable(p.getCpfPessoaDTO())
                        .map(cpf -> cpf.getCpfPessoaComum())
                        .orElse("")
                        .equals(pessoaComumCPF));
    }

    private Boolean existeCNPJ(String pessoaLojistaCNPJ) {

        return objectListPessoa.stream()
                .anyMatch(p -> Optional.ofNullable(p.getCnpjPessoaDTO())
                        .map(cnpj -> cnpj.getCnpjPessoaLojista())
                        .orElse("")
                        .equals(pessoaLojistaCNPJ));
    }

    private PessoaDTO existeContaComumUsuario(String pessoaComumCPF) {

        return objectListPessoa
                .stream()
                .filter(pessoaDTO -> pessoaDTO != null
                        && pessoaDTO.getCpfPessoaDTO() != null
                        && pessoaComumCPF.equals(pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum())
                ).findAny().get();

    }

    private PessoaDTO existeContaLojistaUsuario(String pessoaLojistaCNPJ) {
        return objectListPessoa.
                stream()
                .filter(pessoaDTO -> pessoaDTO != null
                        && pessoaDTO.getCnpjPessoaDTO() != null
                        && pessoaLojistaCNPJ.equals(pessoaDTO.getCnpjPessoaDTO().getCnpjPessoaLojista())
                ).findAny().get();
    }

    public PessoaDTO identificarDepositar(String cpfPessoa, String cnpjLojista, double valorDeposito) {

        PessoaDTO obPessoaDTO = null;
        if (cnpjLojista == null) {
            obPessoaDTO = objectListPessoa
                    .stream()
                    .filter(pessoaDTO -> pessoaDTO != null
                            && pessoaDTO.getCpfPessoaDTO() != null
                            && cpfPessoa.equals(pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("O " + cpfPessoa + " não foi encontrado"));

            //for (PessoaDTO objetoPessoa : objectListPessoa) {--informar que valor de deposito deve ser maior do que zero
            // if (existeCPF(cpfPessoa)) {
            if (!obPessoaDTO.toString().isEmpty()) {
                // obPessoaDTO.getTransferenciaDTO().setValorDeTransferencia(valorDeposito);
                valorDeposito = /*obPessoaDTO.getTransferenciaDTO().getValorDeTransferencia()*/ valorDeposito + obPessoaDTO.getTransferenciaDTO().getSaldoDeTransferencia();
                obPessoaDTO.getTransferenciaDTO().setSaldoDeTransferencia(valorDeposito);
            } else {
                throw new RuntimeException("Não existe Usuário (Comum) para o Depósito em Dinheiro !!!");
            }
        } else {
            obPessoaDTO = objectListPessoa
                    .stream()
                    .filter(pessoaDTO -> pessoaDTO != null
                            && pessoaDTO.getCnpjPessoaDTO() != null
                            && cnpjLojista.equals(pessoaDTO.getCnpjPessoaDTO().getCnpjPessoaLojista()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("O " + cnpjLojista + " não foi encontrado"));
            if (!obPessoaDTO.toString().isEmpty()) {
                //obPessoaDTO.getTransferenciaDTO().setValorDeTransferencia(valorDeposito);
                valorDeposito = /*obPessoaDTO.getTransferenciaDTO().getValorDeTransferencia()*/ valorDeposito + obPessoaDTO.getTransferenciaDTO().getSaldoDeTransferencia();
                obPessoaDTO.getTransferenciaDTO().setSaldoDeTransferencia(valorDeposito);
            } else {
                throw new RuntimeException("Não existe Usuário (Comum) para o Depósito em Dinheiro !!!");
            }
        }
        return obPessoaDTO;
    }

    public PessoaDTO identificarTransferencia(String cpfPessoa, double valorTransferencia) {
        PessoaDTO obPessoaDTO = objectListPessoa
                .stream()
                .filter(pessoaDTO -> pessoaDTO != null && pessoaDTO.getCpfPessoaDTO() != null && pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum().equals(cpfPessoa))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("O " + cpfPessoa + " não foi encontrado"));
        //for (PessoaDTO objetoPessoa : objectListPessoa) {-- informar que o valor de saldo deve ser maior do que o de transferencia
        if (!obPessoaDTO.toString().isEmpty()) {
            //obPessoaDTO.getTransferenciaDTO().setValorDeTransferencia(valorTransferencia);
            valorTransferencia = obPessoaDTO.getTransferenciaDTO().getSaldoDeTransferencia() - valorTransferencia;//obPessoaDTO.getTransferenciaDTO().getValorDeTransferencia();
            obPessoaDTO.getTransferenciaDTO().setSaldoDeTransferencia(valorTransferencia);
        } else {
            throw new RuntimeException("Não existe Usuário (Comum) para a Transferência em Dinheiro !!!");
        }
        return obPessoaDTO;
    }

    private Boolean existeSaldoNaContaUsuarioComum(PessoaDTO pessoaCPF, double valorTransferencia) {
        if (pessoaCPF.getTransferenciaDTO().getSaldoDeTransferencia() <= 0 || pessoaCPF.getTransferenciaDTO().getSaldoDeTransferencia() < valorTransferencia)
            throw new RuntimeException("Não Existe Saldo para a Conta Correspondente !!!");
        return true;
    }

    //-- processo nao sendo reconhecido pelo Framework-- Tentei com o @Transactional, mas o SpringBoot nao esta chamando a biblioteca ou ate mesmo nao esta sendo reconhecido
    public Transferencia transferenciaPessoaComum(String pessoaCPFcomumEmissor, String pessoaCPFcomumReceptor, double valorDeTransferencia) {

        if (!existeCPF(pessoaCPFcomumEmissor) || !existeCPF(pessoaCPFcomumReceptor)) {
            throw new RuntimeException("Não existe Usuário (Comum) para o Depósito em Dinheiro !!!");
        } else {
            if (contemServicoAutorizadorExterno()) {
                var pessoaComumEmissor = existeContaComumUsuario(pessoaCPFcomumEmissor);

                existeSaldoNaContaUsuarioComum(pessoaComumEmissor, valorDeTransferencia);

                var pessoaComumReceptor = existeContaComumUsuario(pessoaCPFcomumReceptor);

                if (pessoaComumEmissor.getCpfPessoaDTO().getCpfPessoaComum().equals(pessoaComumReceptor.getCpfPessoaDTO().getCpfPessoaComum())) {
                    throw new RuntimeException("CPF Iguais. O Processo Não Pode Ocorrer Para Está Mesma Situação. Estamos Atulizando os Paramêtros.");
                }

                pessoaComumEmissor = identificarTransferencia(pessoaCPFcomumEmissor, valorDeTransferencia);

                pessoaComumReceptor = identificarDepositar(pessoaCPFcomumReceptor, null, valorDeTransferencia);

                Transferencia transferencia = new Transferencia(pessoaComumEmissor.getCpfPessoaDTO(), pessoaComumReceptor.getCpfPessoaDTO(), valorDeTransferencia);

                contemServicoAutorizadorExternoSMS(transferencia);

                return transferencia;
            }

            return null;
        }
    }

    public Transferencia transferenciaPessoaLojista(String pessoaCPFcomumEmissor, String pessoaCNPJ, double valorDeTransferencia) {

        if (!existeCPF(pessoaCPFcomumEmissor)) {
            throw new RuntimeException("Não Existe Usuário (Comum) para a Transeferência em Dinheiro !!!");
        }
        if (!existeCNPJ(pessoaCNPJ)) {
            throw new RuntimeException("Não Existe CNPJ para a Transferência em Dinheiro !!!");
        }
        var pessoaComumEmissor = existeContaComumUsuario(pessoaCPFcomumEmissor);

        existeSaldoNaContaUsuarioComum(pessoaComumEmissor, valorDeTransferencia);

        var pessoaLojistaReceptor = existeContaLojistaUsuario(pessoaCNPJ);

        if (contemServicoAutorizadorExterno()) {
            pessoaComumEmissor = identificarTransferencia(pessoaCPFcomumEmissor, valorDeTransferencia);
        }

        var pessoaLojistaCNPJ = identificarDepositar(pessoaCPFcomumEmissor, pessoaLojistaReceptor.getCnpjPessoaDTO().getCnpjPessoaLojista(), valorDeTransferencia);

        Transferencia transferencia = new Transferencia(pessoaComumEmissor.getCpfPessoaDTO(), pessoaLojistaCNPJ.getCnpjPessoaDTO(), valorDeTransferencia);

        contemServicoAutorizadorExternoSMS(transferencia);

        return transferencia;

    }

    private boolean contemServicoAutorizadorExterno() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://util.devi.tools/api/v2/authorize";

            ResponseEntity<?> novo = restTemplate.getForEntity(url, String.class);

            return Objects.requireNonNull(novo.getBody()).toString().contains("success");
        } catch (HttpServerErrorException | HttpClientErrorException.Forbidden ex) {
            throw new RuntimeException(ex.getResponseBodyAsString() + " - " + ex.getStatusCode());
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException(ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
        }
    }

    private boolean contemServicoAutorizadorExternoSMS(Transferencia transferencia) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://util.devi.tools/api/v1/notify";

            HttpEntity<Transferencia> request = new HttpEntity<>(transferencia);
            ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);

            return response.getStatusCode().is2xxSuccessful();

        } catch (HttpServerErrorException.GatewayTimeout ex) {
            throw new RuntimeException("Erro 504 (Gateway Timeout): " + ex.getResponseBodyAsString());
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new RuntimeException("Erro 400 (Bad Request): " + ex.getResponseBodyAsString());
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Erro HTTP " + ex.getStatusCode() + ": " + ex.getResponseBodyAsString());
        } catch (Exception ex) {
            throw new RuntimeException("Erro inesperado ao chamar o serviço externo: " + ex.getMessage());
        }
    }


}