# Lojinha API Automação 🛒✅

Este projeto é um framework de automação de testes de API para a aplicação de e-commerce "Lojinha", desenvolvido em Java. O objetivo é garantir a qualidade e a estabilidade dos endpoints da API, cobrindo cenários de autenticação, gerenciamento de usuários, produtos e componentes.

## 🚀 Visão Geral do Projeto

O **`LojinhaAPIAutomacao`** foca na validação de uma API RESTful, simulando interações de um e-commerce. Ele demonstra a capacidade de construir testes de API robustos, escaláveis e de fácil manutenção, utilizando as melhores práticas da indústria.

Principais funcionalidades testadas:

*   **Autenticação:** Login e validação de token.

*   **Usuários:** Criação e remoção de usuários, incluindo validações de campos.

*   **Produtos:** Criação, busca, alteração e remoção de produtos, com validações de limites de valores e campos obrigatórios.

*   **Componentes:** Adição, busca, alteração e remoção de componentes de produtos, com validações de campos e associações.

## ✨ Cenários de Teste Automatizados

O projeto automatiza um total de 43 cenários de teste abrangentes, distribuídos entre os módulos da API:

*   **Módulo de Usuário:** 8 cenários de teste.

*   **Módulo de Produto:** 19 cenários de teste.

*   **Módulo de Componente:** 16 cenários de teste.

Esses testes cobrem uma vasta gama de casos, incluindo cenários de sucesso, validações de campos obrigatórios, limites de valores, autorização (com e sem token), e tratamento de dados inexistentes, garantindo uma cobertura abrangente das funcionalidades CRUD e de autenticação da API.

![Gif automação API](https://github.com/user-attachments/assets/f8ed4715-f869-4ce8-bc1c-31cd94e65e33)

## 🚀 Tecnologias Utilizadas

*   **Linguagem:** Java 23

*   **Framework de Testes:** JUnit 5 (versão 5.8.2)

*   **Biblioteca de Testes de API:** RestAssured (versão 5.2.0)

*   **Gerenciador de Dependências/Build:** Apache Maven

*   **Serialização/Deserialização JSON:** Jackson Databind (versão 2.15.2)

*   **Controle de Versão:** Git

*   **Integração Contínua (CI/CD):** GitHub Actions
  
## 📁 Estrutura do Projeto

```
LojinhaAPIAutomacao/
├──.github/
│   └── workflows/
│       └── ci.yml  # Workflow de CI/CD com GitHub Actions
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pojo/       # Classes POJO para modelagem de payloads JSON
│   │           ├── ComponentePojo.java
│   │           ├── ProdutoPojo.java
│   │           └── UsuarioPojo.java
│   └── test/
│       └── java/
│           ├── datafactory/ # Classes Data Factory para geração de massa de dados
│           │   ├── ComponenteDataFactory.java
│           │   ├── ProdutoDataFactory.java
│           │   └── UsuarioDataFactory.java
│           └── modulos/    # Pacotes de testes organizados por módulo da API
│               ├── componente/
│               │   └── ComponenteTest.java
│               ├── produto/
│               │   └── ProdutoTest.java
│               └── usuario/
│                   └── UsuarioTest.java
└── pom.xml             # Arquivo de configuração do Maven
```

### **Detalhes da Estrutura:**

*   **`src/main/java/pojo`**: Contém as classes POJO (`UsuarioPojo`, `ProdutoPojo`, `ComponentePojo`) que representam os contratos de dados JSON da API. Isso garante que os payloads de requisição e as respostas da API sejam fortemente tipados, facilitando a manipulação e validação dos dados. A modelagem inclui a capacidade de lidar com **objetos aninhados e listas** (ex: `List<ComponentePojo>` dentro de `ProdutoPojo`), refletindo a complexidade de APIs do mundo real.
*   **`src/test/java/datafactory`**: Classes Data Factory (`UsuarioDataFactory`, `ProdutoDataFactory`, `ComponenteDataFactory`) são responsáveis por centralizar e gerar massa de dados de teste. Essa abordagem promove a **reutilização de dados, melhora a legibilidade dos testes** e facilita a manutenção, permitindo a criação de dados padrão e customizados, inclusive para cenários complexos com dados aninhados.
*   **`src/test/java/modulos`**: Os testes são organizados em pacotes por módulo da API (`componente`, `produto`, `usuario`), o que reflete a estrutura da aplicação e otimiza a escalabilidade e a manutenção dos testes.

## 🌐 Integração Contínua (CI/CD)

Este projeto está configurado com **GitHub Actions** para Integração Contínua. Os testes são executados automaticamente a cada `push` e `pull request` para a branch `main`, garantindo feedback imediato sobre a qualidade da API e mantendo o pipeline sempre verde.

*   O workflow está definido em: `.github/workflows/ci.yml`

## Como Executar os Testes 🧪

### Pré-requisitos

- Java (JDK) 17 ou superior  
- Maven 3.9.6 instalado  
- Git instalado  

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/sergio-engsoft/LojinhaAPIAutomacao.git
```

2. Navegue até a pasta do projeto:
```bash
cd LojinhaAPIAutomacao
```

3. Execute os testes com Maven:
```bash
mvn test
```

## Integração Contínua (CI) com GitHub Actions ⚙️

O projeto conta com uma pipeline configurada no GitHub Actions que executa os testes automaticamente a cada push para o repositório. O status da execução é exibido diretamente no GitHub por meio de um badge de status no topo do repositório.

## Links Úteis 🔗

- [Repositório no GitHub](https://github.com/sergio-engsoft/LojinhaAPIAutomacao)
- [Meu LinkedIn](https://linkedin.com/in/sergio-dos-santos-soares)
- **E-mail:** sergiodossantossoares@hotmail.com
