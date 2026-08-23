package com.challenges.cadastrotarefas.controller;

import com.challenges.cadastrotarefas.dtos.TarefasDTO;
import com.challenges.cadastrotarefas.enums.StatusEnum;
import com.challenges.cadastrotarefas.exceptions.ResourceNotFoundException;
import com.challenges.cadastrotarefas.services.TarefasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TarefasController.class)
@AutoConfigureMockMvc(addFilters = false)
class TarefasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarefasService tarefasService;

    @Test
    void list_deveRetornarTarefasPaginadas() throws Exception {
        TarefasDTO tarefa = tarefa(1L, "Tarefa pendente", StatusEnum.PENDENTE);
        when(tarefasService.list(PageRequest.of(0, 5), StatusEnum.PENDENTE, "Maria"))
                .thenReturn(new PageImpl<>(List.of(tarefa), PageRequest.of(0, 5), 1));

        mockMvc.perform(get("/api/tasks")
                        .param("page", "0")
                        .param("size", "5")
                        .param("status", "PENDENTE")
                        .param("responsavel", "Maria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].titulo").value("Tarefa pendente"))
                .andExpect(jsonPath("$.content[0].status").value("PENDENTE"));
    }

    @Test
    void getOne_deveRetornarTarefaQuandoIdExistir() throws Exception {
        when(tarefasService.getOne(1L)).thenReturn(tarefa(1L, "Tarefa pendente", StatusEnum.PENDENTE));

        mockMvc.perform(get("/api/tasks/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Tarefa pendente"));
    }

    @Test
    void getOne_deveRetornarNotFoundQuandoIdNaoExistir() throws Exception {
        when(tarefasService.getOne(99L)).thenThrow(new ResourceNotFoundException("Tarefa não encontrada."));

        mockMvc.perform(get("/api/tasks/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Tarefa não encontrada."));
    }

    @Test
    void save_deveRetornarCreated() throws Exception {
        doNothing().when(tarefasService).save(any(TarefasDTO.class));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTarefa("Nova tarefa", "PENDENTE")))
                .andExpect(status().isCreated());

        verify(tarefasService).save(any(TarefasDTO.class));
    }

    @Test
    void update_deveRetornarTarefaAtualizada() throws Exception {
        TarefasDTO atualizada = tarefa(1L, "Tarefa atualizada", StatusEnum.CONCLUIDA);
        when(tarefasService.update(eq(1L), any(TarefasDTO.class))).thenReturn(atualizada);

        mockMvc.perform(put("/api/tasks/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTarefa("Tarefa atualizada", "CONCLUIDA")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("CONCLUIDA"));

        verify(tarefasService).update(eq(1L), any(TarefasDTO.class));
    }

    @Test
    void delete_deveRetornarAccepted() throws Exception {
        doNothing().when(tarefasService).delete(1L);

        mockMvc.perform(delete("/api/tasks/{id}", 1))
                .andExpect(status().isAccepted());

        verify(tarefasService).delete(1L);
    }

    private TarefasDTO tarefa(Long id, String titulo, StatusEnum status) {
        return new TarefasDTO(id, titulo, "Descrição", status, new Date(), null, "Maria");
    }

    private String jsonTarefa(String titulo, String status) {
        return """
                {
                  "titulo": "%s",
                  "descricao": "Descrição",
                  "status": "%s",
                  "dataCriacao": "2026-08-23T12:00:00.000+00:00",
                  "dataConclusao": "2026-12-31T12:00:00.000+00:00",
                  "responsavel": "Maria"
                }
                """.formatted(titulo, status);
    }
}
