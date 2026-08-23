package com.challenges.cadastrotarefasback.controller;

import com.challenges.cadastrotarefasback.dtos.ErrorDTO;
import com.challenges.cadastrotarefasback.dtos.TarefasDTO;
import com.challenges.cadastrotarefasback.services.TarefasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TarefasController {
    private final TarefasService tarefasService;

    public TarefasController(TarefasService tarefasService) {
        this.tarefasService = tarefasService;
    }

    @Operation(summary = "Listar os eventos de forma paginada")
    @GetMapping("/events")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos encontrados"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Page<TarefasDTO>> list (@Valid @ParameterObject Pageable pageable){
        return ResponseEntity.ok(tarefasService.list(pageable));
    }

    @Operation(summary = "Pesquisar um evento pelo seu id")
    @GetMapping("/events/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> getOne (@Valid @PathVariable Long id) {
        return ResponseEntity.ok(tarefasService.getOne(id));
    }

    @Operation(summary = "Atualizar um evento")
    @PutMapping("/events/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento atualizado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> update (@Valid @PathVariable Long id, @Valid @RequestBody TarefasDTO updateInfo) {
        return ResponseEntity.ok(tarefasService.update(id, updateInfo));
    }

    @Operation(summary = "Cadastrar um novo evento")
    @PostMapping("/events")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento cadastrado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> save (@Valid @RequestBody TarefasDTO evento) {
        tarefasService.save(evento);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Deletar um evento")
    @DeleteMapping("/events/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Evento deletado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> delete (@Valid @PathVariable Long id) {
        tarefasService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
