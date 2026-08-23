package com.challenges.cadastrotarefasback.repository;

import com.challenges.cadastrotarefasback.model.Tarefas;
import com.challenges.cadastrotarefasback.repository.TarefasRepository;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        tarefasRepository.save(evento("Evento ativo", "Maria"));
        tarefasRepository.save(evento("Evento excluido", "Maria"));

        Page<Tarefas> eventosAtivos = tarefasRepository.findAllByStatus("P", PageRequest.of(0, 10));

        assertThat(eventosAtivos).hasSize(1);
        assertThat(eventosAtivos.getContent().getFirst().getTitulo()).isEqualTo("Evento pendente");
        assertThat(eventosAtivos.getContent().getFirst().getStatus()).isEqualTo("P");
    }

    private Tarefas evento(String titulo, String responsavel) {
        Date agora = new Date();
        return new Tarefas(null, titulo, "Descricao","P",
                agora, null, responsavel);
    }
}
