package com.challenges.cadastrotarefasback.services;

import com.challenges.cadastrotarefasback.dtos.TarefasDTO;
import com.challenges.cadastrotarefasback.exceptions.ResourceNotFoundException;
import com.challenges.cadastrotarefasback.model.Tarefas;
import com.challenges.cadastrotarefasback.repository.TarefasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TarefasServiceImpl implements TarefasService {
    private final TarefasRepository tarefasRepository;

    public TarefasServiceImpl(TarefasRepository tarefasRepository) {
        this.tarefasRepository = tarefasRepository;
    }

    @Override
    public Page<TarefasDTO> list(Pageable pageable) {
        return tarefasRepository.findAll(pageable).map(TarefasDTO::new);
    }

    @Override
    public TarefasDTO getOne(Long id) {
        return new TarefasDTO(tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado.")));
    }

    @Override
    public TarefasDTO update(Long id, TarefasDTO updateInfo) {
        Tarefas evento = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado."));
        evento.setTitulo(updateInfo.getTitulo());
        evento.setDescricao(updateInfo.getDescricao());
        evento.setStatus(updateInfo.getStatus());
        evento.setResponsavel(updateInfo.getResponsavel());
        evento.setDataConclusao(updateInfo.getDataConclusao());

        return new TarefasDTO(tarefasRepository.save(evento));
    }

    @Override
    public void save(TarefasDTO evento) {
        tarefasRepository.save(new Tarefas(evento));
    }

    @Override
    public void delete(Long id) {
        Tarefas evento = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado."));

        tarefasRepository.delete(evento);
    }
}
