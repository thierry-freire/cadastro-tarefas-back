package com.challenges.cadastrotarefasback.model;

import com.challenges.cadastrotarefasback.dtos.TarefasDTO;
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
@Table(name = "eventos")
public class Tarefas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long id;

    @Column(name = "titulo_evento")
    private String titulo;

    @Column(name = "descricao_evento")
    private String descricao;

    @Column(name = "status")
    private String status;

    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Column(name = "data_conclusao")
    private Date dataConclusao;

    @Column(name = "responsavel")
    private String responsavel;



    public Tarefas(TarefasDTO eventoDTO) {
        this.id = eventoDTO.getId();
        this.titulo = eventoDTO.getTitulo();
        this.descricao = eventoDTO.getDescricao();
        this.status = eventoDTO.getStatus();
        this.dataCriacao = eventoDTO.getDataCriacao();
        this.dataConclusao = Date.from(Instant.now());
        this.responsavel = eventoDTO.getResponsavel();
    }
}
