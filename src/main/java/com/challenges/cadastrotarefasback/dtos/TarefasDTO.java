package com.challenges.cadastrotarefasback.dtos;

import com.challenges.cadastrotarefasback.model.Tarefas;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefasDTO {
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatório")
    @Size(max = 1000)
    private String descricao;

    @NotBlank
    @Size(max = 1)
    private String status;

    @NotNull(message = "Data é obrigatório")
    private Date dataCriacao;

    @Future(message = "A data deve ser futura")
    private Date dataConclusao;

    @NotBlank(message = "Local é obrigatório")
    @Size(max = 100)
    private String responsavel;

    public TarefasDTO(Tarefas tarefa) {
        this.id = tarefa.getId();
        this.titulo = tarefa.getTitulo();
        this.descricao = tarefa.getDescricao();
        this.dataCriacao = tarefa.getDataCriacao();
        this.dataConclusao = tarefa.getDataConclusao();
        this.responsavel = tarefa.getResponsavel();
    }
}
