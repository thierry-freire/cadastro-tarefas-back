package com.challenges.cadastrotarefas.repository;

import com.challenges.cadastrotarefas.enums.StatusEnum;
import com.challenges.cadastrotarefas.model.Tarefas;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=update")
@Testcontainers
@DisabledInAotMode
class TarefasRepositoryIntegrationTest {

    @Container
    static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.4");

    @Autowired
    private TarefasRepository tarefasRepository;

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @Test
    @Transactional
    void findAllByDeleted_deveRetornarApenasEventosPendentes() {
        tarefasRepository.save(evento("Evento em andamento", "Maria", StatusEnum.EM_ANDAMENTO));
        tarefasRepository.save(evento("Evento pendente", "Joao", StatusEnum.PENDENTE));

        Page<Tarefas> eventosAtivos = tarefasRepository.findAllByStatus(StatusEnum.PENDENTE, PageRequest.of(0, 10));

        assertThat(eventosAtivos).hasSize(1);
        assertThat(eventosAtivos.getContent().getFirst().getTitulo()).isEqualTo("Evento pendente");
        assertThat(eventosAtivos.getContent().getFirst().getStatus()).isEqualTo(StatusEnum.PENDENTE);
    }

    private Tarefas evento(String titulo, String responsavel, StatusEnum statusEnum) {
        Date agora = new Date();
        return new Tarefas(null, titulo, "Descricao", StatusEnum.PENDENTE,
                agora, null, responsavel);
    }
}
