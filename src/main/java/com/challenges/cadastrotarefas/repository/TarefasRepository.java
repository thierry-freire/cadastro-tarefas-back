package com.challenges.cadastrotarefas.repository;

import com.challenges.cadastrotarefas.enums.StatusEnum;
import com.challenges.cadastrotarefas.model.Tarefas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefasRepository extends JpaRepository<Tarefas, Long> {
    Page<Tarefas> findAllByStatus(StatusEnum status, Pageable pageable);
}
