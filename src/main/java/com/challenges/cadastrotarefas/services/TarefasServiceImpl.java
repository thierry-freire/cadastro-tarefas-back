package com.challenges.cadastrotarefas.services;

import com.challenges.cadastrotarefas.dtos.TarefasDTO;
import com.challenges.cadastrotarefas.enums.StatusEnum;
import com.challenges.cadastrotarefas.exceptions.ResourceNotFoundException;
import com.challenges.cadastrotarefas.model.Tarefas;
import com.challenges.cadastrotarefas.repository.TarefasQueries;
import com.challenges.cadastrotarefas.repository.TarefasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TarefasServiceImpl implements TarefasService {
    private final TarefasRepository tarefasRepository;
    private final TarefasQueries tarefasQueries;

    public TarefasServiceImpl(TarefasRepository tarefasRepository, TarefasQueries tarefasQueries) {
        this.tarefasRepository = tarefasRepository;
        this.tarefasQueries = tarefasQueries;
    }

    @Override
    public Page<TarefasDTO> list(Pageable pageable, StatusEnum status, String responsavel) {

        return tarefasQueries.list(pageable, status, responsavel).map(TarefasDTO::new);
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
