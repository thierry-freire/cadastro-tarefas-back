package com.challenges.cadastrotarefas.controller;

import com.challenges.cadastrotarefas.dtos.ErrorDTO;
import com.challenges.cadastrotarefas.dtos.TarefasDTO;
import com.challenges.cadastrotarefas.enums.StatusEnum;
import com.challenges.cadastrotarefas.services.TarefasService;
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

    @Operation(summary = "Listar as tarefas de forma paginada")
    @GetMapping("/tasks")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Page<TarefasDTO>> list (@Valid @ParameterObject Pageable pageable, @RequestParam(required = false) StatusEnum status, @RequestParam(required = false) String responsavel){
        return ResponseEntity.ok(tarefasService.list(pageable, status, responsavel));
    }

    @Operation(summary = "Pesquisar uma tarefa pelo seu id")
    @GetMapping("/tasks/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> getOne (@Valid @PathVariable Long id) {
        return ResponseEntity.ok(tarefasService.getOne(id));
    }

    @Operation(summary = "Atualizar uma Tarefa")
    @PutMapping("/tasks/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> update (@Valid @PathVariable Long id, @Valid @RequestBody TarefasDTO updateInfo) {
        return ResponseEntity.ok(tarefasService.update(id, updateInfo));
    }

    @Operation(summary = "Cadastrar uma nova Tarefa")
    @PostMapping("/tasks")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarefa cadastrada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> save (@Valid @RequestBody TarefasDTO Tarefa) {
        tarefasService.save(Tarefa);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Deletar uma Tarefa")
    @DeleteMapping("/tasks/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Tarefa deletada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<TarefasDTO> delete (@Valid @PathVariable Long id) {
        tarefasService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
