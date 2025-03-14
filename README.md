# API de Consulta de CEP

## 📌 Sobre o Projeto
Esta aplicação permite a busca de endereços a partir de um CEP, consumindo uma API externa mockada via Wiremock/Mockoon. Todas as consultas são registradas em um banco de dados, incluindo o horário da requisição e os dados retornados.

## 🏗️ Arquitetura da Solução

A arquitetura segue os princípios SOLID e está dividida em:
- **Controller**: Exposição dos endpoints REST.
- **Service**: Lógica de negócio para busca de CEP e armazenamento de logs.
- **Repository**: Persistência dos logs de consulta.
- **Wiremock/Mockoon**: Simulação da API externa de CEP.
- **Banco de Dados**: Armazena logs das consultas.

![Diagrama da Solução](caminho/para/diagrama.png)

## 🚀 Tecnologias Utilizadas
- **Java 11+**
- **Spring Boot**
- **Wiremock/Mockoon**
- **MongoDB/PostgreSQL**
- **Docker e Docker Compose** (Diferencial)
- **AWS** (Opcional)

## 📦 Como Executar o Projeto

### ✅ Pré-requisitos
- Docker instalado (caso utilize `docker-compose`)
- Java 11+ e Maven instalados

### 🔧 Passo a Passo
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```
2. Suba os containers do banco de dados e mock da API:
   ```bash
   docker-compose up -d
   ```
3. Execute a aplicação via Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Acesse os endpoints da API:
   ```bash
   curl -X GET "http://localhost:8080/api/cep/{cep}" -H "Content-Type: application/json"
   ```

## 📝 Endpoints Disponíveis

| Método | URL                     | Descrição |
|--------|--------------------------|-------------|
| GET    | `/api/cep/{cep}`         | Retorna os dados do CEP |
| GET    | `/api/logs`              | Retorna o histórico de consultas |

## 📊 Estrutura do Banco de Dados

Os logs são armazenados com os seguintes campos:
- **id**: Identificador único
- **cep**: CEP consultado
- **dados**: Resposta da API externa
- **dataConsulta**: Data e hora da consulta

## 📌 Testes e Validação
- Testes unitários com JUnit e Mockito.
- Testes de integração com Spring Boot Test.

## 📢 Apresentação
- Explicação do desenho da solução.
- Demonstração do código e execução da aplicação.

## 📎 Repositório
[GitHub - Link para o projeto](https://github.com/seu-usuario/seu-repositorio)

---
✉️ Qualquer dúvida, entre em contato!

