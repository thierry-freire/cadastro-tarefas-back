package com.challenges.cadastrotarefasback.repository;

import com.challenges.cadastrotarefasback.model.Tarefas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefasRepository extends JpaRepository<Tarefas, Long> {
    Page<Tarefas> findAllByStatus(String status, Pageable pageable);
}
