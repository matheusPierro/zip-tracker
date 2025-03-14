# API de Monitoramento de Consulta de CEP

## 📌 Sobre o Projeto
Esta aplicação permite a busca de endereços a partir de um CEP, consumindo uma API externa mockada via Wiremock. Todas as consultas são registradas em um banco de dados, incluindo o horário da requisição e os dados retornados.

## 🏗️ Arquitetura da Solução

A arquitetura segue os princípios SOLID e está dividida em:
- **Controller**: Exposição dos endpoints REST.
- **Service**: Lógica de negócio para busca de CEP e armazenamento de logs.
- **Client**: Consumo de endpoint externo para busca de CEP mockado.
- **Wiremock**: Simulação da API externa de CEP.
- **Model**: Camada de criação de Entidades para o Banco de Dados.
- **Repository**: Persistência dos logs de consulta.
- **Banco de Dados**: Armazena logs das consultas.

![Diagrama da Solução](Diagrama de Solução.jpg)

## 🚀 Tecnologias Utilizadas
- **Java 21**
- **Spring Boot**
- **Wiremock**
- **MongoDB**
- **Docker e Docker Compose**
- **AWS CloudWatch**

## 📦 Como Executar o Projeto

### ✅ Pré-requisitos
- Docker instalado
- Java 21 e Gradle instalados

### 🔧 Passo a Passo
1. Clone o repositório:
   ```bash
   git clone https://github.com/matheusPierro/zip-tracker.git
   cd zip-tracker
   ```
2. Suba os containers do banco de dados e mock da API:
   ```bash
   docker-compose up -d
   ```
3. Execute a aplicação via Maven:
   ```bash
   ./gradlew bootRun
   ```
4. Acesse os endpoints da API:
   ```bash
   curl http://localhost:8080/cep/01001-000
   ```
5. Validação dos Logs no Banco de Dados:
   ```bash
   docker exec -it mongodb mongosh
   
   use cepdb
   
   db.cep_logs.find().pretty()
   ```
## 📝 Endpoints Disponíveis

| Método | URL                  | Descrição               |
|--------|----------------------|-------------------------|
| GET    | `/cep/{cep}`         | Retorna os dados do CEP |

## 📊 Estrutura do Banco de Dados

Os logs são armazenados com os seguintes campos:
- **id**: Identificador único
- **cep**: CEP consultado
- **dados**: Resposta da API externa
- **dataConsulta**: Data e hora da consulta

## 📢 Apresentação
- Explicação do desenho da solução.
- Demonstração do código e execução da aplicação.

## 📎 Repositório
[GitHub - Link para o projeto](https://github.com/matheusPierro/zip-tracker)

---
✉️ Qualquer dúvida, entre em contato!

- **Email** - matpierro570@gmail.com
- **Whatsapp** - (11)961065956

