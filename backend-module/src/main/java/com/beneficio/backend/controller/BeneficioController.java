package com.beneficio.backend.controller;

import com.beneficio.backend.dto.TransferenciaRequest;
import com.beneficio.ejb.BeneficioEjbLocal;
import com.beneficio.ejb.entity.Beneficio;
import com.beneficio.ejb.entity.Transferencia;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável pelos endpoints relacionados aos benefícios.
 * Atua como interface entre o frontend e o EJB module.
 *
 * @author Rafael Lucio
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/beneficios")
@CrossOrigin(origins = "*")
public class BeneficioController {

    @Autowired
    private BeneficioEjbLocal beneficioEjb;

    /**
     * Lista todos os benefícios cadastrados no sistema.
     *
     * @return ResponseEntity contendo a lista de benefícios
     */
    @GetMapping
    public ResponseEntity<List<Beneficio>> listar() {
        List<Beneficio> beneficios = beneficioEjb.findAll();
        return ResponseEntity.ok(beneficios);
    }

    /**
     * Busca um benefício específico pelo seu identificador.
     *
     * @param id identificador único do benefício
     * @return ResponseEntity contendo o benefício ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> obterPorId(@PathVariable Long id) {
        Beneficio beneficio = beneficioEjb.findById(id);
        return beneficio != null ? ResponseEntity.ok(beneficio) : ResponseEntity.notFound().build();
    }

    /**
     * Cria um novo benefício no sistema.
     *
     * @param beneficio dados do benefício a ser criado
     * @return ResponseEntity contendo o benefício criado ou erro de validação
     */
    @PostMapping
    public ResponseEntity<Beneficio> criar(@Valid @RequestBody Beneficio beneficio) {
        Beneficio savedBeneficio = beneficioEjb.save(beneficio);
        return ResponseEntity.status(201).body(savedBeneficio);
    }

    /**
     * Atualiza um benefício existente.
     *
     * @param id identificador do benefício a ser atualizado
     * @param beneficio dados do benefício para atualização
     * @return ResponseEntity contendo o benefício atualizado ou 404 se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> atualizar(@PathVariable Long id, @Valid @RequestBody Beneficio beneficio) {
        Beneficio updatedBeneficio = beneficioEjb.update(id, beneficio);
        return ResponseEntity.ok(updatedBeneficio);
    }

    /**
     * Remove um benefício do sistema.
     *
     * @param id identificador do benefício a ser removido
     * @return ResponseEntity indicando sucesso ou erro
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        beneficioEjb.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista apenas os benefícios que estão ativos.
     *
     * @return ResponseEntity contendo a lista de benefícios ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<Beneficio>> obterAtivos() {
        List<Beneficio> beneficios = beneficioEjb.findAtivos();
        return ResponseEntity.ok(beneficios);
    }

    /**
     * Busca benefícios cujo nome contenha o texto especificado.
     *
     * @param nome texto a ser buscado no nome dos benefícios
     * @return ResponseEntity contendo a lista de benefícios encontrados
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Beneficio>> buscar(@RequestParam String nome) {
        List<Beneficio> beneficios = beneficioEjb.searchByNome(nome);
        return ResponseEntity.ok(beneficios);
    }

    /**
     * Realiza transferência de valor entre dois benefícios.
     *
     * @param request dados da transferência
     * @return ResponseEntity indicando sucesso ou erro
     */
    @PostMapping("/transferir")
    public ResponseEntity<String> transferir(@Valid @RequestBody TransferenciaRequest request) {
        beneficioEjb.transfer(request.getFromId(), request.getToId(), request.getAmount());
        return ResponseEntity.ok("Transferência realizada com sucesso");
    }

    /**
     * Lista o histórico de todas as transferências realizadas.
     *
     * @return ResponseEntity contendo a lista de transferências
     */
    @GetMapping("/transferencias/historico")
    public ResponseEntity<List<Transferencia>> obterHistoricoTransferencias() {
        List<Transferencia> transferencias = beneficioEjb.getHistoricoTransferencias();
        return ResponseEntity.ok(transferencias);
    }

    /**
     * Lista o histórico de transferências de um benefício específico.
     *
     * @param beneficioId identificador do benefício
     * @return ResponseEntity contendo a lista de transferências do benefício
     */
    @GetMapping("/{beneficioId}/transferencias")
    public ResponseEntity<List<Transferencia>> obterTransferenciasPorBeneficio(@PathVariable Long beneficioId) {
        List<Transferencia> transferencias = beneficioEjb.getTransferenciasByBeneficio(beneficioId);
        return ResponseEntity.ok(transferencias);
    }
}
