# CADASTRO_TAREFAS_BACK

### Pré-Requisitos
* Java 21
* Angular CLI
* Docker

### Tecnologias
* Angular 17
* Java 21
* SpringBoot 4.1.0
* MySQL 9.7
* Docker
* JUnit
* Mockito

### Executar o projeto
O usuário e senha para logar na aplicação é dbamain;

Para rodar o projeto localmente utilize:
```
./gradlew bootRun
```

Para construir as imagens docker dos projetos rode na raiz deste projeto:
```
docker compose up --build
```

Para rodar os testes automatizados execute:
```
./gradlew test
```

### Escolhas de arquitetura
* Escolhi utilizar o MySQL pois é o banco de dados que tenho mais experiência em relação ao PostgreeSQL/MariaDB;
* Escolhi a arquitetura MVC, pois se trata de um projeto simples sem nescessidade de utilizar padrão hexagonal por não haver integração entre micro serviços;
* Escolhi o java 21 pois é a versão LTS mais atual com menos chances de haver erros devido a mudanças no compilador;