package com.challenges.cadastrotarefasback;

import org.springframework.boot.SpringApplication;

public class TestCadastroTarefasBackApplication {

    public static void main(String[] args) {
        SpringApplication.from(CadastroTarefasBackApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
