# Semana Spring React - SDS 4.0

Projeto do evento Semana Spring React da [DevSuperior](https://devsuperior.com.br/).

O projeto consiste em um dashboard de vendas utilizando o React no Frontend, Java e SpringBoot no backend e deploy em nuvem, através das plataformas [Netlify](https://www.netlify.com/) e [Heroku](https://id.heroku.com/login).

O projeto do backend utiliza a arquitetura em camadas.

# Stacks utilizadas na semana
- [React](https://pt-br.reactjs.org/)
- [NodeJS](https://nodejs.org/en/) (versão 14.16.1)
- [yarn](https://yarnpkg.com/)
- [Java](https://www.java.com/pt-BR/) versão 11
- [Maven](https://maven.apache.org/) (versão 3.6.3)
- [SpringBoot](https://spring.io/projects/spring-boot) 2.5.4
- [H2 Database](https://www.h2database.com/html/main.html)
- [Postgres](https://www.postgresql.org/) (versão 12)

# Stack adicionada para desenvolvimento com containers
- [Docker](https://www.docker.com/)
- [docker-compose](https://docs.docker.com/compose/)

# Rodando o projeto

## Sem containers

Para executar o projeto sem containers é necessário ter instalado localmente as stacks utilizadas na semana. Também é necessário que o banco de dados esteja criado, juntamente com a as tabelas e o seed.

### Iniciando o backend:

Na pasta do projeto do backend execute o seguinte comando:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=test 
```

### Iniciando o frontend:

Na pasta do projeto do frontend execute o seguinte comando:

```shell
yarn start
```

## Incializando pelos containers:

Para executar o projeto em containers é necessário ter o Docker e o docker-compose instalado.

Na pasta raiz do projeto, execute o seguinte comando:

```shell
docker-compose up -d
```

## Realizando o seed do banco do de dados

Para executar o projeto localmente é necessário criar o banco de dados, criar as tabelas e executar o seed. Para isso execute os comandos em no arquivo init.sql em .docker/postgres

O script init.sql tem a função de realizar o seed no banco de dados Postgres ao criar a instancia do container. Caso o banco de dados não esteja criado, execute o script init.sql no seu SGBD.

## TODOS

- Backend
    - [] Implementar testes no backend para a arquitetura em camadas
    - [] Alterar a arquitetura do backend para arquitetura Ports & Adapters (hexagonal)
    - [] Implementar os testes para o novo padrão arquitetural
    - [] Corrigir container para container de desenvolvimento (hoje o Dockerfile) gera um container de produção e não é possível que, ao atualizar o projeto, as alterações reflitam em tempo de execução **dica: estudar o devcontainer java gerado pela extensão remote container do VSCode**)
    - [] Implementar segurança

- Frontend:
    - [] Incluir o arquivo .env para armazenar as variáveis de ambiente
    - [] Isolar componentes das libs. Por exemplo, o componente BarChart não deve chamar diretamente a biblioteca do axios. Isso pode ser feito através da abstração das libs para um outro módulo
