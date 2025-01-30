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
### Pré-requisitos
- [Docker](https://www.docker.com/products/docker-desktop/)

Para executar os testes de carga usamos a ferramenta [K6](https://grafana.com/docs/k6/latest/), rodando dentro de um container Docker.
O teste está configurado para gerar 10000 simulações diferentes, caso deseje alterar esse número deve ser alterado a constante *NUMBER_OF_SIMULATIONS* no arquivo `load_test.js`

Use os seguintes comandos para buildar e rodar os testes:

```
docker build . -t dock-k6
docker run --add-host=host.docker.internal:host-gateway --rm dock-k6 run load_test.js
```

## Rodar em um Container Docker
### Pré-requisitos
- [Docker](https://www.docker.com/products/docker-desktop/)


## Exemplos de Requisição

### Sucesso

**Requisição**

``POST /simulation``
```json
[
    {
        "totalValue": 66189,
        "birthDate": "1930-01-27",
        "paymentTerm": 40
    },
    {
        "totalValue": 13000,
        "birthDate": "1978-01-27",
        "paymentTerm": 38
    }
]
```

**Resposta**
```json
[
    {
        "totalAmount": 900178.00,
        "monthlyPayment": 22504.45,
        "totalInterestPaid": 833989.00
    },
    {
        "totalAmount": 84196.22,
        "monthlyPayment": 2215.69,
        "totalInterestPaid": 71196.22
    }
]
```

### Falha
**Requisição**

``POST /simulation``
```json
[
    {
        "totalValue": 66189,
        "birthDate": "2018-01-27",
        "paymentTerm": 40
    },
    {
        "totalValue": 90,
        "birthDate": "1978-01-27",
        "paymentTerm": 38
    },
    {
        "totalValue": 150,
        "birthDate": "1995-01-27",
        "paymentTerm": 1
    }
]
```

**Resposta**

```json
{
	"status": 400,
	"error": "Bad Request",
	"message": "Error on validation",
	"errors": [
		"simulate.request[1].totalValue: deve ser maior que ou igual à 100",
		"simulate.request[2].paymentTerm: deve ser maior que ou igual à 2",
		"simulate.request[0].birthDate: o cliente deve ser maior de idade"
	],
	"timestamp": "2025-01-30T10:59:25.478886-03:00"
}
```

## Documentação
- [Swagger](http://localhost:3000/swagger-ui/index.html)
-  
