package com.challenges.cadastrotarefas.auth;

import com.challenges.cadastrotarefas.config.SecurityConfig;
import com.challenges.cadastrotarefas.controller.TarefasController;
import com.challenges.cadastrotarefas.dtos.TarefasDTO;
import com.challenges.cadastrotarefas.enums.StatusEnum;
import com.challenges.cadastrotarefas.services.TarefasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class, TarefasController.class})
@Import({SecurityConfig.class, JwtTokenService.class})
@TestPropertySource(properties = {
        "security.jwt.secret=uma-chave-de-teste-com-mais-de-trinta-e-dois-bytes",
        "security.jwt.expiration=PT1H"
})
class SecurityApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarefasService tarefasService;

    @Test
    void apiDeveRecusarRequisicaoSemToken() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginDeveGerarTokenQueAutorizaApi() throws Exception {
        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"dbamain\",\"password\":\"dbamain\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        Matcher tokenMatcher = Pattern.compile("\\\"accessToken\\\":\\\"([^\\\"]+)\\\"").matcher(loginResponse);
        if (!tokenMatcher.find()) {
            throw new AssertionError("A resposta de login não contém accessToken.");
        }
        String token = tokenMatcher.group(1);
        when(tarefasService.list(any(), any(), any())).thenReturn(new PageImpl<>(List.of(
                new TarefasDTO(1L, "Tarefa", "Descrição", StatusEnum.PENDENTE, new Date(), null, "Maria"))));

        mockMvc.perform(get("/api/tasks").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }
}
