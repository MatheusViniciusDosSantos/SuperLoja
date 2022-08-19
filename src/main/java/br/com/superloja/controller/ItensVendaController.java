package br.com.superloja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.ItensVenda;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.ItensVendaService;


@RestController
@RequestMapping("/api")
public class ItensVendaController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int ROW_PER_PAGE = 5;

    @Autowired
    private ItensVendaService itensVendaService;

    @Operation(summary = "Busca itens da Venda", description = "Buscar todas os itens das vendas", tags = {"itens_venda"})
    @GetMapping(value = "/itensVenda", consumes =
            MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ItensVenda>> findAll(
            @Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
            Pageable pageable)	{
        return ResponseEntity.ok(itensVendaService.findAll(pageable));
    }

    @Operation(summary = "Busca ID", description = "Buscar itens venda por ID", tags = {"itens_venda"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(schema = @Schema(implementation = ItensVenda.class))),
            @ApiResponse(responseCode = "404", description = "Itens da venda não encontrados")
    })
    @GetMapping(value = "/itensVenda/{id}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItensVenda> findById(@PathVariable long id) {
        try {
            ItensVenda itensVenda = itensVendaService.findById(id);
            return ResponseEntity.ok(itensVenda);
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }

    }

    @Operation(summary = "Adicionar itens da venda", description = "Adicionar novos itens da venda informados no banco de dados", tags = {"itens_venda"})
    @PostMapping(value = "/itensVenda")
    public ResponseEntity<ItensVenda> addItensVenda(@RequestBody ItensVenda itensVenda) throws URISyntaxException {
        try {
            ItensVenda itensVendaNovo = itensVendaService.save(itensVenda);
            return ResponseEntity.created(new URI("/api/itensVenda/" + itensVendaNovo.getId()))
                    .body(itensVendaNovo);
        } catch (ResourceAlreadyExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Alterar itens venda", description = "Alterar valores do itens venda com id selecionado", tags = {"itens_venda"})
    @PutMapping(value = "/itensVenda/{id}")
    public ResponseEntity<ItensVenda> updateItensVenda(@Valid @RequestBody ItensVenda itensVenda,
                                                         @PathVariable long id) {
        try {
            itensVenda.setId(id);
            itensVendaService.update(itensVenda);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @Operation(summary = "Deletar itens venda", description = "Deletar itens venda com o ID informado", tags = {"itens_venda"})
    @DeleteMapping(path = "/itensVenda/{id}")
    public ResponseEntity<Void> deleteItensVendaById(@PathVariable long id) {
        try {
            itensVendaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
    
    @GetMapping(value = "/itensVenda/venda/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ItensVenda>> findByIdCompra(@PathVariable long id, Pageable pageable) throws ResourceNotFoundException {    	
    	Page<ItensVenda> itensVenda = itensVendaService.findByIdVenda(id, pageable);
            return ResponseEntity.ok(itensVenda);
    }
    @Operation(summary = "Busca itens de uma venda específica", description = "Buscar os itens de uma venda específica", tags = {"itens_venda"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(schema = @Schema(implementation = ItensVenda.class))),
            @ApiResponse(responseCode = "404", description = "Itens da venda específica não encontrados")
    })
    @GetMapping(value = "/itensVenda/venda/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ItensVenda>> findByIdVenda(@PathVariable long id, Pageable pageable) {

        try {
            Page<ItensVenda> itensVenda = itensVendaService.findByIdVenda(id, pageable);
            return ResponseEntity.ok(itensVenda);
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}