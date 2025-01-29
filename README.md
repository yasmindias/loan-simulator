[![en-us](https://img.shields.io/badge/lang-en--us-red.svg)](https://github.com/yasmindias/loan-simulator/blob/master/README.en.md)

# Simulador de Empréstimo #
Esse projeto roda uma simulação de empréstimo e retorna as condições de pagamento, baseadas no valor solicitado, taxa de juros e prazo de pagamento.

## Rodar locamente
### Pré-requisitos
- [Java 17](https://jdk.java.net/archive/) **(OpenJDK)**
- [Gradle](https://gradle.org/install/)

### Rodar o projeto
Rode o comando ```./gradlew bootRun``` pra iniciar o projeto.

As requisições devem ser feitas na url ``http://localhost:8080/``, seguindo os exemplos disponíveis no [Swagger](http://localhost:8080/swagger-ui/index.html)

### Rodar os testes
#### Testes Unitários
Para rodar os testes use o comando ```./gradlew clean test```.

#### Testes de Integração
Para rodar os testes de integração use o comando ```./gradlew clean integrationTest```.

#### Testes de Performance


## Rodar em um Container Docker
### Pré-requisitos
- [Docker](https://www.docker.com/products/docker-desktop/)



## Documentação
- [Swagger](http://localhost:3000/swagger-ui/index.html)
- 
