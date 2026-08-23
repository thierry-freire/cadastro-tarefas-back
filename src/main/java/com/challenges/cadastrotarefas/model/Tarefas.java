package com.challenges.cadastrotarefas.model;

import com.challenges.cadastrotarefas.dtos.TarefasDTO;
import com.challenges.cadastrotarefas.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarefa")
public class Tarefas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @Column(name = "data_conclusao")
    private Date dataConclusao;

    @Column(name = "responsavel", nullable = false)
    private String responsavel;



    public Tarefas(TarefasDTO tarefaDTO) {
        this.id = tarefaDTO.getId();
        this.titulo = tarefaDTO.getTitulo();
        this.descricao = tarefaDTO.getDescricao();
        this.status = tarefaDTO.getStatus();
        this.dataCriacao = tarefaDTO.getDataCriacao();
        this.dataConclusao = Date.from(Instant.now());
        this.responsavel = tarefaDTO.getResponsavel();
    }
}
