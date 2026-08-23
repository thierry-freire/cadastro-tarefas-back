package com.challenges.cadastrotarefasback.services;

import com.challenges.cadastrotarefasback.dtos.TarefasDTO;
import com.challenges.cadastrotarefasback.exceptions.ResourceNotFoundException;
import com.challenges.cadastrotarefasback.model.Tarefas;
import com.challenges.cadastrotarefasback.repository.TarefasRepository;
import com.challenges.cadastrotarefasback.services.TarefasServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefasServiceImplTest {

    @Mock
    private TarefasRepository tarefasRepository;

    @InjectMocks
    private TarefasServiceImpl eventosService;

    @Test
    void update_deveAtualizarEventoQuandoIdExistir() {
        Tarefas eventoExistente = evento(1L, "Titulo antigo", "Descricao antiga", "Maria");
        TarefasDTO atualizacao = new TarefasDTO(1L, "Titulo novo", "Descricao nova", "C", Date.from(Instant.now()), dataFutura(), "Joao");
        when(tarefasRepository.findById(1L)).thenReturn(Optional.of(eventoExistente));
        when(tarefasRepository.save(eventoExistente)).thenReturn(eventoExistente);

        TarefasDTO resultado = eventosService.update(1L, atualizacao);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getTitulo()).isEqualTo("Titulo novo");
        assertThat(resultado.getDescricao()).isEqualTo("Descricao nova");
        assertThat(resultado.getStatus()).isEqualTo("C");
        assertThat(resultado.getDataCriacao()).isEqualTo(atualizacao.getDataCriacao());
        assertThat(resultado.getDataConclusao()).isEqualTo(atualizacao.getDataConclusao());
        assertThat(resultado.getResponsavel()).isEqualTo("Joao");
        verify(tarefasRepository).save(eventoExistente);
    }

    @Test
    void delete_deveLancarExcecaoQuandoEventoNaoExistir() {
        when(tarefasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventosService.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(tarefasRepository, never()).save(org.mockito.ArgumentMatchers.any(Tarefas.class));
    }

    private Tarefas evento(Long id, String titulo, String descricao, String responsavel) {
        return new Tarefas(id, titulo, descricao, "P", Date.from(Instant.now()), null, responsavel);
    }

    private Date dataFutura() {
        return Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    }
}
