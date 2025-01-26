[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/yasmindias/loan-simulator/blob/master/README.md)

# Simulador de Empréstimo #
Esse projeto roda uma simulação de empréstimo e retorna as condições de pagamento, baseadas no valor solicitado, taxa de juros e prazo de pagamento.

## Rodar locamente
### Pré-requisitos
- [Java 17](https://jdk.java.net/archive/) **(OpenJDK)**
- [Gradle](https://gradle.org/install/)

### Rodar o projeto
Rode o comando ```./gradlew bootRun``` pra iniciar o projeto.
As requisições devem ser feitas na url ``http://localhost:8080``, seguindo os exemplos disponíveis no [Swagger](http://localhost:8080/swagger-ui/index.html)

### Rodar os testes
Para rodar os testes use o comando ```./gradlew test```.
