package com.challenges.cadastrotarefas.repository;

import com.challenges.cadastrotarefas.enums.StatusEnum;
import com.challenges.cadastrotarefas.model.Tarefas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class TarefasQueries {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TarefasQueries(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Page<Tarefas> list(Pageable pageable, StatusEnum status, String responsavel) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tarefa WHERE 1 = 1 ");
        String countSql = "SELECT COUNT(*) FROM tarefa";

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (status != null) {
            sql.append("AND status = :status ");
            params.addValue("status", status.name());
        }

        if (responsavel != null && !responsavel.isEmpty()) {
            sql.append("AND responsavel LIKE :responsavel ");
            params.addValue("responsavel", "%"+ responsavel + "%");
        }

        sql.append("ORDER BY data_criacao DESC LIMIT :limit OFFSET :offset");

        params.addValue("limit", pageable.getPageSize()).addValue("offset", (int) pageable.getOffset());

        List<Tarefas> tarefasList = jdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(Tarefas.class));
        Long total = jdbcTemplate.queryForObject(countSql, new MapSqlParameterSource(), Long.class);

        return new PageImpl<>(tarefasList, pageable, total != null ? total : 0);
    }

}
