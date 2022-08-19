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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;

import br.com.superloja.domain.ItensCompra;
import br.com.superloja.exception.BadResourceException;
import br.com.superloja.exception.ResourceAlreadyExistsException;
import br.com.superloja.exception.ResourceNotFoundException;
import br.com.superloja.service.ItensCompraService;


@RestController
@RequestMapping("/api")
public class ItensCompraController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int ROW_PER_PAGE = 5;

    @Autowired
    private ItensCompraService itensCompraService;

    @Operation(summary = "Busca itens da compra", description = "Buscar todas os itens das compras", tags = {"itens_compra"})
    @GetMapping(value = "/itensCompra", consumes =
            MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ItensCompra>> findAll(
            @Parameter(description = "Paginação", example = "{\"page\":0,\"size\":1}", allowEmptyValue = true)
            Pageable pageable)	{
        return ResponseEntity.ok(itensCompraService.findAll(pageable));
    }

    @Operation(summary = "Busca ID", description = "Buscar itens Compra por ID", tags = {"itens_compra"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(schema = @Schema(implementation = ItensCompra.class))),
            @ApiResponse(responseCode = "404", description = "Itens da compra não encontrados")
    })
    @GetMapping(value = "/itensCompra/{id}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItensCompra> findById(@PathVariable long id) {
        try {
            ItensCompra itensCompra = itensCompraService.findById(id);
            return ResponseEntity.ok(itensCompra);
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }

    }

    @Operation(summary = "Adicionar itens da compra", description = "Adicionar novos itens da compra informados no banco de dados", tags = {"itens_compra"})
    @PostMapping(value = "/itensCompra")
    public ResponseEntity<ItensCompra> addItensCompra(@RequestBody ItensCompra itensCompra) throws URISyntaxException {
        try {
            ItensCompra itensCompraNovo = itensCompraService.save(itensCompra);
            return ResponseEntity.created(new URI("/api/itensCompra/" + itensCompraNovo.getId()))
                    .body(itensCompraNovo);
        } catch (ResourceAlreadyExistsException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Alterar itens compra", description = "Alterar valores do itens compra com id selecionado", tags = {"itens_compra"})
    @PutMapping(value = "/itensCompra/{id}")
    public ResponseEntity<ItensCompra> updateItensCompra(@Valid @RequestBody ItensCompra itensCompra,
                                             @PathVariable long id) {
        try {
            itensCompra.setId(id);
            itensCompraService.update(itensCompra);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @Operation(summary = "Deletar itens compra", description = "Deletar itens compra com o ID informado", tags = {"itens_compra"})
    @DeleteMapping(path = "/itensCompra/{id}")
    public ResponseEntity<Void> deleteItensCompraById(@PathVariable long id) {
        try {
            itensCompraService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @Operation(summary = "Busca itens de uma compra específica", description = "Buscar os itens de uma compra específica", tags = {"itens_compra"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso",
                    content = @Content(schema = @Schema(implementation = ItensCompra.class))),
            @ApiResponse(responseCode = "404", description = "Itens da compra específica não encontrados")
    })
    @GetMapping(value = "/itensCompra/compra/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ItensCompra>> findByIdCompra(@PathVariable long id, Pageable pageable) {

        try {
            Page<ItensCompra> itensCompra = itensCompraService.findByIdCompra(id, pageable);
            return ResponseEntity.ok(itensCompra);
        } catch (ResourceNotFoundException ex) {
            logger.error(ex.getMessage());

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}