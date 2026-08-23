package com.challenges.cadastrotarefas.services;

import com.challenges.cadastrotarefas.dtos.TarefasDTO;
import com.challenges.cadastrotarefas.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TarefasService {
    Page<TarefasDTO> list(Pageable pageable, StatusEnum status, String responsavel);

    TarefasDTO getOne(Long id);

    TarefasDTO update(Long id, TarefasDTO updateInfo);

    void save(TarefasDTO evento);

    void delete(Long id);
}
