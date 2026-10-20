# FPS Arena 🎮

API REST de uma plataforma de partidas competitivas de FPS, desenvolvida com Java e Spring Boot.

O FPS Arena possui sistema de usuários, autenticação com Spring Security e JWT, carteira virtual, partidas, lobby, chat e campeonatos.

O projeto foi desenvolvido como projeto de estudo e portfólio, com foco em desenvolvimento back-end, regras de negócio, segurança, banco de dados, testes automatizados, Docker e CI com GitHub Actions.

---

## 📌 Sobre o projeto

O FPS Arena simula uma plataforma onde jogadores podem criar e participar de partidas competitivas.

A aplicação possui:

- Cadastro de usuários
- Login
- Autenticação com JWT
- Spring Security
- Controle de acesso por `USER` e `ADMIN`
- Criptografia de senha com BCrypt
- Carteira virtual
- Depósitos
- Saques
- Extrato de transações
- Criação de partidas
- Partidas públicas
- Partidas privadas
- Partidas com senha
- Times CT e T
- Sistema de jogador pronto
- Lobby
- Transferência de liderança
- Chat dentro da partida
- Campeonatos
- Inscrição em campeonatos
- Cancelamento de inscrição
- Cobrança da inscrição
- Devolução da inscrição em caso de cancelamento
- Premiação
- Swagger / OpenAPI
- Testes automatizados
- Docker
- Docker Compose
- GitHub Actions

---

## 🛠️ Tecnologias utilizadas

### Back-end

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- BCrypt
- Maven

### Banco de dados

- MySQL

### Testes

- JUnit
- Mockito
- MockMvc

### Documentação

- Swagger
- OpenAPI

### DevOps

- Docker
- Docker Compose
- GitHub Actions
- Git
- GitHub

### Front-end

- HTML
- CSS
- JavaScript
- Fetch API

---

# 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada em camadas:

```text
┌─────────────────────────────┐
│         Front-end           │
│        HTML / CSS / JS      │
└──────────────┬──────────────┘
               │
               │ HTTP / JSON
               ▼
┌─────────────────────────────┐
│         Controller          │
│   Recebe as requisições     │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│          Service            │
│      Regras de negócio      │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│         Repository          │
│       Acesso aos dados      │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│            MySQL            │
│          fps_arena          │
└─────────────────────────────┘
```

Fluxo principal:

```text
Front-end
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

---

# 🔐 Segurança

A aplicação utiliza **Spring Security** e **JWT (JSON Web Token)** para autenticação.

Após o login, a API retorna um token JWT.

As requisições protegidas utilizam:

```http
Authorization: Bearer SEU_TOKEN
```

O filtro JWT identifica o usuário autenticado através do token.

Dessa forma, operações que dependem do usuário autenticado não precisam receber manualmente o `usuarioId`.

A aplicação possui dois níveis de acesso:

```text
USER
ADMIN
```

Operações administrativas possuem proteção específica através da role `ADMIN`.

---

# 👤 Usuários

O sistema possui cadastro e autenticação de usuários.

As senhas são armazenadas utilizando **BCrypt**.

O cadastro cria usuários com a role padrão:

```text
USER
```

Usuários administradores podem possuir a role:

```text
ADMIN
```

### Cadastro

```http
POST /usuarios
```

### Login

```http
POST /login
```

O login retorna o JWT utilizado nas demais requisições protegidas.

---

# 💰 Carteira

Cada usuário possui uma carteira virtual.

É possível:

- Consultar saldo
- Depositar dinheiro
- Sacar dinheiro
- Consultar extrato
- Utilizar saldo para inscrição em campeonatos

As operações da carteira utilizam o usuário autenticado pelo JWT.

### Principais endpoints

```http
GET /carteira
POST /carteira/depositar
POST /carteira/sacar
GET /carteira/transacoes
```

O sistema impede saques superiores ao saldo disponível.

Também não permite depósitos com valor igual ou inferior a zero.

---

# 🎮 Partidas

O sistema permite criar e participar de partidas competitivas.

Os modos disponíveis são:

```text
5v5
2v2
1v1
```

O tamanho dos times é definido de acordo com o modo escolhido.

### Regras implementadas

- Jogador não pode entrar duas vezes na mesma partida.
- Partida finalizada não aceita novos jogadores.
- Partidas podem ser públicas ou privadas.
- Partidas podem utilizar senha.
- Partidas privadas não aparecem na listagem pública.
- Jogadores são distribuídos entre os times CT e T.
- O jogador pode marcar seu estado como pronto.
- Apenas o líder pode iniciar a partida.
- É necessário possuir jogadores nos dois times para iniciar.
- Caso o líder saia, a liderança é transferida.
- Quando o último jogador sai, a partida é finalizada.

---

# 💬 Lobby e Chat

Cada partida possui um lobby com informações dos jogadores e dos times.

O lobby permite acompanhar:

- Jogadores conectados
- Time CT
- Time T
- Líder da partida
- Estado de pronto dos jogadores

O sistema também possui chat por partida.

### Chat

```http
GET /partidas/{id}/mensagens
POST /partidas/{id}/mensagens
```

Somente jogadores presentes na partida podem utilizar o chat.

As mensagens possuem limite de caracteres.

---

# 🏆 Campeonatos

O FPS Arena possui sistema de campeonatos.

Cada campeonato possui:

- Nome
- Modo
- Tipo
- Valor de inscrição
- Premiação
- Número de vagas
- Número de inscritos
- Status

### Modos

```text
5v5
2v2
1v1
```

### Status

O campeonato pode possuir diferentes estados de acordo com seu ciclo de vida, incluindo:

```text
ABERTO
```

O campeonato começa aberto para inscrições e possui controle de vagas.

---

# 📝 Inscrição em campeonatos

O jogador pode realizar sua inscrição enquanto o campeonato estiver aberto.

O fluxo é:

```text
Jogador
   ↓
Solicita inscrição
   ↓
Verificação de saldo
   ↓
Cobrança da inscrição
   ↓
Registro da inscrição
   ↓
Inscritos +1
```

O sistema impede:

- Inscrição duplicada
- Inscrição em campeonato lotado
- Inscrição sem saldo suficiente
- Inscrição em campeonato que não aceita mais jogadores

Também existe cancelamento de inscrição.

Quando permitido, o valor da inscrição é devolvido para a carteira do jogador.

---

# 💵 Premiação

O campeonato possui valor de premiação definido durante sua criação.

Ao finalizar o campeonato, o sistema realiza o pagamento da premiação de acordo com as regras implementadas.

Essa parte está integrada ao sistema de carteira e às transações financeiras do usuário.

---

# 📡 Principais endpoints

## Usuários

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/usuarios` | Cadastra um usuário |
| GET | `/usuarios/eu` | Consulta o usuário autenticado |

## Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/login` | Realiza login e retorna o JWT |

## Carteira

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/carteira` | Consulta a carteira |
| POST | `/carteira/depositar` | Realiza depósito |
| POST | `/carteira/sacar` | Realiza saque |
| GET | `/carteira/transacoes` | Consulta o extrato |

## Partidas

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/partidas` | Lista partidas |
| GET | `/partidas/{id}` | Busca uma partida |
| POST | `/partidas` | Cria uma partida |
| POST | `/partidas/{id}/entrar` | Entra em uma partida |
| DELETE | `/partidas/{id}/jogadores/eu` | Sai da partida |
| POST | `/partidas/{id}/pronto` | Marca o jogador como pronto |
| POST | `/partidas/{id}/iniciar` | Inicia a partida |

## Chat

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/partidas/{id}/mensagens` | Lista mensagens |
| POST | `/partidas/{id}/mensagens` | Envia mensagem |

## Campeonatos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/campeonatos` | Lista campeonatos |
| GET | `/campeonatos/{id}` | Busca campeonato |
| POST | `/campeonatos` | Cria campeonato |
| POST | `/campeonatos/{id}/inscricoes` | Realiza inscrição |
| DELETE | `/campeonatos/{id}/inscricoes/eu` | Cancela inscrição |
| POST | `/campeonatos/{id}/finalizar` | Finaliza campeonato |
| DELETE | `/campeonatos/{id}` | Remove campeonato |

Operações administrativas exigem autenticação com role `ADMIN`.

---

# 📖 Swagger / OpenAPI

A API possui documentação interativa utilizando Swagger / OpenAPI.

Com a aplicação executando localmente:

```text
http://localhost:8080/swagger-ui/index.html
```

O Swagger permite visualizar os endpoints e realizar testes diretamente pela interface.

Para utilizar endpoints protegidos:

1. Realize o login.
2. Copie o JWT retornado.
3. Clique em **Authorize**.
4. Informe o token.
5. Execute as requisições protegidas.

---

# 🧪 Testes automatizados

O projeto possui testes automatizados utilizando:

- JUnit
- Mockito
- MockMvc
- Spring Security Test

Os testes verificam regras de negócio e comportamento dos endpoints.

Entre os cenários testados estão:

- Saldo insuficiente para saque
- Depósito inválido
- Entrada em partida lotada
- Jogador duplicado na partida
- Saída do líder
- Transferência de liderança
- Autenticação
- Autorização por role
- Operações de campeonato

Para executar os testes:

```powershell
.\mvnw.cmd test
```

---

# 🐳 Docker

O projeto possui configuração Docker para facilitar a execução da aplicação e do banco de dados.

Arquivos principais:

```text
Dockerfile
docker-compose.yml
```

O banco utilizado é:

```text
fps_arena
```

O projeto também possui configuração específica para execução em ambiente Docker.

As informações sensíveis de conexão com o banco não devem ser armazenadas diretamente no repositório.

---

# ⚙️ Configuração

O projeto possui um arquivo de exemplo para configuração:

```text
application.properties.example
```

As configurações reais ficam no arquivo local:

```text
application.properties
```

Esse arquivo não deve ser enviado para o GitHub quando possuir informações sensíveis.

Também existe configuração específica para o ambiente Docker:

```text
application-docker.properties
```

---

# ▶️ Executando localmente

## Pré-requisitos

- Java 17
- Git
- MySQL
- Docker e Docker Compose, caso utilize o ambiente Docker

Clone o projeto:

```bash
git clone https://github.com/LeonardoAugusto1/fps-arena-api.git
```

Entre na pasta:

```bash
cd fps-arena-api
```

Configure o banco de dados `fps_arena` e as propriedades da aplicação.

Execute:

```powershell
.\mvnw.cmd spring-boot:run
```

A API será executada em:

```text
http://localhost:8080
```

---

# 🌐 Front-end

O FPS Arena possui um front-end desenvolvido com:

- HTML
- CSS
- JavaScript
- Fetch API
- Tailwind CSS
- Lucide

O front-end se comunica com a API através de requisições HTTP.

Entre as telas disponíveis estão:

- Login
- Home
- Carteira
- Campeonatos
- Partidas
- Criar partida
- Lobby
- Chat

O token JWT é armazenado no navegador e enviado nas requisições protegidas.

---

# 🔄 CI com GitHub Actions

O projeto utiliza GitHub Actions para integração contínua.

O workflow está localizado em:

```text
.github/workflows/ci.yml
```

O processo utiliza:

```text
Ubuntu
Java 17
Temurin
Maven
```

Fluxo:

```text
Push
 ↓
GitHub Actions
 ↓
Configuração do Java
 ↓
Execução dos testes
 ↓
Build da aplicação
```

---

# 📁 Estrutura principal

```text
fps-arena-api/
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/example/fpsarena/
│   │
│   └── test/
│       └── java/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── application.properties.example
└── README.md
```

---

# 📸 Screenshots

As imagens do projeto podem ser adicionadas nesta seção.

Exemplo:

```markdown
### Login

![Login](docs/images/login.png)

### Home

![Home](docs/images/home.png)

### Carteira

![Carteira](docs/images/carteira.png)

### Campeonatos

![Campeonatos](docs/images/campeonatos.png)

### Lobby

![Lobby](docs/images/lobby.png)

### Swagger

![Swagger](docs/images/swagger.png)

### GitHub Actions

![GitHub Actions](docs/images/github-actions.png)
```

---

# 🚧 Próximas etapas

Algumas funcionalidades e melhorias podem ser adicionadas futuramente:

- Melhorias adicionais no front-end
- Ranking
- Sistema de times
- Melhorias no lobby
- Melhorias de segurança
- Paginação de listagens
- Melhorias na experiência do usuário

---

# ☁️ Deploy

O projeto ainda não está publicado na internet.

A publicação será realizada posteriormente utilizando AWS.

O próximo objetivo do projeto é estudar os serviços necessários para realizar o deploy da aplicação, banco de dados e front-end.

Fluxo planejado:

```text
FPS Arena
   ↓
AWS
   ↓
Deploy da API
   ↓
Banco de dados
   ↓
Front-end
   ↓
Configuração de rede e CORS
   ↓
Aplicação disponível na internet
```

---

# 🎯 Objetivo

O FPS Arena foi desenvolvido para consolidar conhecimentos de desenvolvimento back-end utilizando Java e Spring Boot.

Durante o projeto foram trabalhados:

- APIs REST
- HTTP
- JSON
- DTOs
- Controllers
- Services
- Repositories
- JPA
- MySQL
- Regras de negócio
- Spring Security
- JWT
- BCrypt
- CORS
- Testes automatizados
- Docker
- Docker Compose
- Git
- GitHub
- GitHub Actions
- Swagger / OpenAPI
- Integração entre front-end e back-end

O próximo estágio do projeto será a infraestrutura e publicação utilizando AWS.

---

# 👨‍💻 Desenvolvedor

**Leonardo Augusto**

Projeto desenvolvido para estudo e construção de portfólio na área de desenvolvimento back-end.

GitHub:

https://github.com/LeonardoAugusto1

Repositório:

https://github.com/LeonardoAugusto1/fps-arena-api