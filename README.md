# Conversor de Moedas

Este projeto é um simples conversor de moedas e aplicação de cotação de câmbio escrito em Java. Ele utiliza a ExchangeRate-API e a Fixer-API para buscar taxas de câmbio e realizar conversões de moedas.

## Funcionalidades

- Buscar taxas de câmbio para uma determinada moeda base.
- Converter um valor de uma moeda para outra.
- Listar moedas disponíveis.

## Pré-requisitos

- Java 11 ou superior
- Maven
- Chave da API ExchangeRate-API
- Chave da API Fixer-API

## Configuração

1. Clone o repositório:
    ```sh
    git clone https://github.com/sudoaptgetmach/currency-converter.git
    cd conversor-moedas
    ```

2. Configure suas chaves de API como variáveis de ambiente:
    ```sh
    export EXCHANGERATE_API_KEY=sua_chave_exchangerate_api
    export FIXER_API_KEY=sua_chave_fixer_api
    ```

3. Construa o projeto usando Maven:
    ```sh
    mvn clean install
    ```

## Executando a Aplicação

Para executar a aplicação, use o seguinte comando:
```sh
java -cp target/conversor-moedas-1.0-SNAPSHOT.jar org.mach.AppManager
```

## Uso

Ao executar a aplicação, você será solicitado a escolher entre duas opções:

1. Obter a cotação de uma moeda específica.

2. Converter um valor de uma moeda para outra (digite lista para ver as moedas disponíveis).  

Estrutura do Projeto

`src/main/java/org/mach/AppManager.java`: O ponto de entrada principal da aplicação.
`src/main/java/org/mach/controller/Conversion.java`: Lida com a lógica de conversão e cotação de câmbio.
`src/main/java/org/mach/http/ExchangeRateApiClient.java`: Faz requisições HTTP para a ExchangeRate-API.
`src/main/java/org/mach/http/FixerApiClient.java`: Faz requisições HTTP para a Fixer-API.
`src/main/java/org/mach/json/JsonCreator.java`: Lida com a criação e escrita de JSON.  
`src/main/java/org/mach/records/Record.java`: Representa um registro para conversão de moeda e cotação de câmbio.
