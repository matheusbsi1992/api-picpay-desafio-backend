package br.com.picpay.controller;

import br.com.picpay.dto.PessoaDTO;
import br.com.picpay.service.PessoaTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/picpay/v1/")
public class PessoaController {

    @Autowired
    private PessoaTransferencia pessoaTransferencia;

    @PostMapping("inserirPessoa")
    public ResponseEntity<?> inserirPessoa(@RequestBody PessoaDTO pessoaDTO) {
        if (!pessoaTransferencia.inserirPessoa(pessoaDTO).isEmpty())
            return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa adicionada com sucesso");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pessoa não adicionada");
    }

    @PostMapping("depositarDinheiroPessoaComum/{pessoacpfemissor}/{valordedeposito}")
    public ResponseEntity<?> depositoPessoaComum(@PathVariable("pessoacpfemissor") String pessoaCPFcomumEmissor,
                                                 @PathVariable("valordedeposito") double valorDeDeposito) {
        if (pessoaTransferencia.identificarDepositar(pessoaCPFcomumEmissor, null, valorDeDeposito) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Dinheiro adicionado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Dinheiro não adicionado");
    }

    @PostMapping("transferenciaPessoaComum")
    //@Transactional(rollbackFor = Exception.class) -- Transactional nao sendo identificado
    public ResponseEntity<?> transferenciaPessoaComum(@RequestParam("pessoacpfemissor") String pessoaCPFcomumEmissor,
                                                      @RequestParam("pessoacpfreceptor") String pessoaCPFcomumReceptor,
                                                      @RequestParam("valortransferencia") double valorDeTransferencia) {

        var transferencia = pessoaTransferencia.transferenciaPessoaComum(pessoaCPFcomumEmissor, pessoaCPFcomumReceptor, valorDeTransferencia);

        Map<String, Object> pessoaComum = new HashMap<>();
        pessoaComum.put("emissor", transferencia.getPessoaComumEmissor().getCpfPessoaComum());
        pessoaComum.put("receptor", transferencia.getPessoaComumReceptor().getCpfPessoaComum());
        pessoaComum.put("valorTransferencia", transferencia.getValorDeTransferencia());

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaComum);
    }

    //@Transactional(rollbackFor = Exception.class) -- Transactional nao sendo identificado
    @PostMapping("transferenciaParaPessoaLojista")
    public ResponseEntity<?> transferenciaPessoaLojista(@RequestParam("pessoacpfemissor") String pessoaCPFcomumEmissor,
                                                        @RequestParam("pessoacnpj") String pessoaCNPJ,
                                                        @RequestParam("valortransferencia") double valorDeTransferencia) {

        var transferencia = pessoaTransferencia.transferenciaPessoaLojista(pessoaCPFcomumEmissor, pessoaCNPJ/*URLDecoder.decode(pessoaCNPJ,StandardCharsets.UTF_8)*/, valorDeTransferencia);

        Map<String, Object> pessoaComumLojista = new HashMap<>();
        pessoaComumLojista.put("emissor", transferencia.getPessoaComumEmissor().getCpfPessoaComum());
        pessoaComumLojista.put("receptor", transferencia.getPessoaLojista().getCnpjPessoaLojista());
        pessoaComumLojista.put("valorTransferencia", transferencia.getValorDeTransferencia());

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaComumLojista);
    }


    @GetMapping("listarTodosDadosUsuarios")
    public ResponseEntity<?> listarTodosOsDadosDosUsuarios() {
        List<PessoaDTO> pessoaDTOList = pessoaTransferencia.retornarTodasPessoas();
        return ResponseEntity.ok(pessoaDTOList);
    }

    @GetMapping("listarTodos")
    public ResponseEntity<?> listarTodosUsuarios() {
        List<PessoaDTO> pessoaDTOList = pessoaTransferencia.retornarTodasPessoas();
        List<Map<String, Object>> respostaPessoaComumLojista = new ArrayList<>();


        for (PessoaDTO pessoaDTO : pessoaDTOList) {
            Map<String, Object> pessoaComumLojista = new HashMap<>();
            pessoaComumLojista.put("Nome Pessoa", pessoaDTO.getNomeCompletoPessoaDTO());
            pessoaComumLojista.put("Email Pessoa", pessoaDTO.getEmailPessoaDTO());
            if ((pessoaDTO.getCpfPessoaDTO() != null) && pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum() != null) {
                pessoaComumLojista.put("CPF Pessoa", pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum());
            } else {
                pessoaComumLojista.put("CNPJ Pessoa", (pessoaDTO.getCnpjPessoaDTO() != null) ? pessoaDTO.getCnpjPessoaDTO().getCnpjPessoaLojista() : null);
            }
            pessoaComumLojista.put("Tipo de Pessoa", pessoaDTO.getTipoPessoaDTO().getTipoPessoa());
            respostaPessoaComumLojista.add(pessoaComumLojista);
        }
        return ResponseEntity.ok(respostaPessoaComumLojista);
    }
}
