package com.challenges.cadastrotarefas;

import org.springframework.boot.SpringApplication;

public class TestCadastroTarefasApplication {

    public static void main(String[] args) {
        SpringApplication.from(CadastroTarefasApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
