package com.challenges.cadastrotarefasback.services;

import com.challenges.cadastrotarefasback.dtos.TarefasDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TarefasService {
    Page<TarefasDTO> list(Pageable pageable);

    TarefasDTO getOne(Long id);

    TarefasDTO update(Long id, TarefasDTO updateInfo);

    void save(TarefasDTO evento);

    void delete(Long id);
}
