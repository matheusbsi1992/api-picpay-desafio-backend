package br.com.picpay.service.modelos;

import br.com.picpay.dto.PessoaDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static br.com.picpay.datafactory.PessoaData.pessoaComum;
import static br.com.picpay.datafactory.PessoaData.pessoaLojista;
import static br.com.picpay.util.Util.PATH;
import static br.com.picpay.util.Util.URL;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;


@DisplayName("Testes de API Rest Módulo de Pessoa")
public class PessoaComumLojistaTest {

    private int randomInt = (int) (Math.random() * 10);

    @BeforeEach
    public void inicializarCasosDeTeste() {
        basePath = PATH;
        baseURI = URL;
    }

    @Test
    @DisplayName("Insercao de Pessoa Comum")
    public void inserirPessoaComum() {

        given()
                .contentType(ContentType.JSON)
                .body(pessoaComum(randomInt))
                .when()
                .post("inserirPessoa")
                .then()
                .assertThat()
                .body(equalTo("Pessoa adicionada com sucesso"))
                .statusCode(201);
    }

    @Test
    @DisplayName("Insercao de Pessoa Lojista")
    public void inserirPessoaLojista() {

        given()
                .contentType(ContentType.JSON)
                .body(pessoaLojista(randomInt))
                .when()
                .post("inserirPessoa")
                .then()
                .assertThat()
                .body(equalTo("Pessoa adicionada com sucesso"))
                .statusCode(201);
    }

    @Test
    @DisplayName("Depositar Dinheiro Conta de Pessoa Comum")
    //-- Dados inseridos primeiro para pessoa comum
    public void inserirDepositoPessoaComum() {

        List<PessoaDTO> pessoaList = given()
                .contentType(ContentType.JSON)
                .when()
                .get("listarTodosDadosUsuarios")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", PessoaDTO.class)
                .stream()
                .filter(pessoaDTO -> pessoaDTO.getCpfPessoaDTO() != null && pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum() != null).toList();
        //-- Depositar so para uma devida pessoa. Com o break ou so pela devida pesquisa.
        //List<PessoaDTO> pessoaDTO = pessoaList.stream().filter(p -> p.getCpfPessoaDTO().getCpfPessoaComum() != null).toList();
        for (PessoaDTO pessoaDTON : pessoaList) {

            String pessoaComumCPF01 = pessoaDTON.getCpfPessoaDTO().getCpfPessoaComum();

            double valorDeposito = (randomInt * 1000);

            given()
                    .contentType(ContentType.JSON)
                    .when()
                    //depositarDinheiroPessoaComum/{pessoacpfemissor}/{valordedeposito}
                    .post("depositarDinheiroPessoaComum/" + pessoaComumCPF01 + "/" + valorDeposito)
                    .then()
                    .assertThat()
                    .body(equalTo("Dinheiro adicionado com sucesso"))
                    .statusCode(201);

            break;

        }
    }

    @Test
    @DisplayName("Listar de Pessoas")
    public void listarPessoaComumLojista() {

        List<PessoaDTO> listarPessoas = given()
                .contentType(ContentType.JSON)
                .when()
                .get("listarTodosDadosUsuarios")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("");

        System.out.println(listarPessoas.stream().toList());
    }

    @Test
    @DisplayName("Inserir Transferencia Pessoa Comum")
    // -- Listar todos as pessoas: Comum e Lojista, em seguida identifica a primeira pessoa comum, e a ultima pessoa Comum. Logo apos transfere dinheiro para a ultima pessoa Comum
    public void transeferirDinheiroPessoaComum() {

        List<PessoaDTO> listarPessoas = given()
                .contentType(ContentType.JSON)
                .when()
                .get("listarTodosDadosUsuarios")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", PessoaDTO.class)
                .stream()
                //-- Filtrar pessoas com o CPF
                .filter(pessoaDTO -> pessoaDTO.getCpfPessoaDTO() != null
                        && pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum() != null
                        && pessoaDTO.getCnpjPessoaDTO() == null || pessoaDTO.getCnpjPessoaDTO().getCnpjPessoaLojista() == null).toList();

        String pessoaComumCPF01 = "", pessoaComumCPF02 = "";

        double valorTransferencia = 1500.0;

        //-- Informar a primeira pessoa
        pessoaComumCPF01 = listarPessoas.getFirst().getCpfPessoaDTO().getCpfPessoaComum();
        //-- Informar a segunda pessoa
        pessoaComumCPF02 = listarPessoas.getLast().getCpfPessoaDTO().getCpfPessoaComum();

        given()
                .contentType(ContentType.JSON)
                .when()
                //---transferenciaPessoaComum/{pessoacpfemissor}/{pessoacpfreceptor}/{valortransferencia}
                .post("transferenciaPessoaComum?pessoacpfemissor=" + pessoaComumCPF01 + "&pessoacpfreceptor=" + pessoaComumCPF02 + "&valortransferencia=" + valorTransferencia)
                .then()
                .assertThat()
                .statusCode(201)
                .body("emissor", equalTo(pessoaComumCPF01))
                .body("receptor", equalTo(pessoaComumCPF02))
                .body("valorTransferencia", equalTo((float) valorTransferencia));
    }

    @Test
    @DisplayName("Inserir Transferencia Pessoa Lojista")
    // -- Listar todos as pessoas: Comum e Lojista, em seguida identifica a primeira pessoa comum, e a primeira pessoa Lojista. Logo apos transfere dinheiro para a primeira pessoa Lojista
    public void transeferirDinheiroPessoaComumParaLojista() {

        List<PessoaDTO> listarPessoas = given()
                .contentType(ContentType.JSON)
                .when()
                .get("listarTodosDadosUsuarios")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", PessoaDTO.class)
                .stream()
                //-- Filtrar pessoas com o CPF e com o CNPJ
                .toList();
        String pessoaComumCPF = "", pessoaLojista = "";

        double valorTransferencia = 1500.0;

        //-- Informar a primeira pessoa -- Comum 1º
        pessoaComumCPF = listarPessoas
                .stream()
                .filter(pessoaDTO -> pessoaDTO.getCpfPessoaDTO() != null && pessoaDTO.getCpfPessoaDTO().getCpfPessoaComum() != null)
                .findFirst()
                .get()
                .getCpfPessoaDTO()
                .getCpfPessoaComum();
        //-- Informar a segunda pessoa -- Lojista 1º
        pessoaLojista = listarPessoas
                .stream()
                .filter(pessoaDTO -> pessoaDTO.getCnpjPessoaDTO() != null && pessoaDTO.getCnpjPessoaDTO().getCnpjPessoaLojista() != null)
                .findFirst()
                .get()
                .getCnpjPessoaDTO()
                .getCnpjPessoaLojista();

        //String pessoaLojistaEncoded = URLEncoder.encode(pessoaLojista, StandardCharsets.UTF_8);

        //@PostMapping("transferenciaParaPessoaLojista/{pessoacpfemissor}/{pessoacnpj}/{valortransferencia}")
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("transferenciaParaPessoaLojista?pessoacpfemissor=" + pessoaComumCPF + "&pessoacnpj=" + pessoaLojista + "&valortransferencia=" + valorTransferencia)
                .then()
                .assertThat()
                .statusCode(201)
                .body("emissor", equalTo(pessoaComumCPF))
                .body("receptor", equalTo(pessoaLojista))
                .body("valorTransferencia", equalTo((float) valorTransferencia));
    }

}