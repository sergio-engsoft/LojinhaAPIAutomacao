# Lojinha API Automação de Testes 

Este repositório contém uma automação de testes para a API da Lojinha, um sistema fictício utilizado para demonstrar habilidades em testes de APIs. O projeto foi desenvolvido com o objetivo de demonstrar a proficiência no uso de frameworks de teste,
automação de API e integração com ferramentas de CI/CD.

A automação inclui testes de funcionalidades essenciais da API, como autenticação, criação, edição, remoção e validação de dados de usuários e produtos, garantindo que todos os pontos críticos do sistema sejam testados de maneira eficaz.

## Tecnologias Utilizadas

- **Java 23 JDK**: A versão mais recente do Java foi utilizada para garantir que o projeto esteja alinhado com as últimas melhorias e práticas recomendadas.
- **JUnit 5**: Framework de teste para facilitar a escrita e execução de testes automatizados.
- **RestAssured**: Biblioteca para simplificar os testes de APIs RESTful.
- **Maven**: Ferramenta de automação de construção para gerenciar dependências e construir o projeto.
- **GitHub Actions**: Para a automação de pipelines CI/CD, garantindo a integração contínua e a execução de testes sempre que o código é alterado.

## Objetivo

O objetivo principal deste projeto é demonstrar domínio sobre a automação de testes de APIs RESTful, cobrindo cenários de teste para autenticação, criação de usuários, produtos, e suas respectivas validações, como a verificação de campos obrigatórios e a gestão de erros.

Este repositório também apresenta integração com o GitHub Actions para que todos os testes sejam executados automaticamente sempre que houver uma modificação no código, facilitando a validação contínua da qualidade do código.

## Funcionalidades

- **Testes de Autenticação**: Verifica se a autenticação de usuários está funcionando corretamente.
- **Testes de Criação, Edição e Remoção de Usuários**: Garante que o cadastro, a edição e a remoção de usuários aconteçam de forma robusta.
- **Testes de Produto**: Realiza testes para garantir a criação, busca, edição e deleção de produtos na API.
- **Validações de Campos Obrigatórios**: Testa se os campos obrigatórios são devidamente validados pela API.
- **Erros e Mensagens de Resposta**: Verifica as mensagens de erro retornadas pela API em cenários de falha.

## Como Rodar

### Pré-requisitos

- **Java 23 JDK**: O projeto foi desenvolvido utilizando o JDK 23. Você pode baixá-lo e instalá-lo no [site oficial do JDK](https://jdk.java.net/23).
- **Maven**: O Maven é utilizado para gerenciar dependências e rodar os testes. Se você ainda não tem o Maven instalado, consulte a [documentação do Maven](https://maven.apache.org/install.html) para instruções de instalação.

### Passos para Executar os Testes Localmente

1. Clone este repositório:
   ```bash
   git clone https://github.com/sergio-engsoft/LojinhaAPIAutomacao.git
Navegue até a pasta do projeto:

bash
Copiar
Editar
cd LojinhaAPIAutomacao
Execute os testes utilizando o Maven:

bash
Copiar
Editar
mvn clean test
CI/CD com GitHub Actions
Este repositório está configurado para executar os testes automaticamente sempre que houver uma atualização no código. O fluxo de CI/CD foi configurado no arquivo .github/workflows/ci.yml, utilizando o GitHub Actions.

## Links Úteis

- **Swagger Editor da API Lojinha:** [Swagger Editor](https://editor.swagger.io/)
- **Base URL da API Lojinha:** `165.227.93.41/lojinha`
- **Repositório no GitHub:** [Lojinha API Automação](https://github.com/sergio-engsoft/LojinhaAPIAutomacao)
- **Perfil do LinkedIn:** [Sérgio dos Santos Soares](https://linkedin.com/in/sergio-dos-santos-soares)

Licença
Este projeto está licenciado sob a MIT License - veja o arquivo LICENSE para mais detalhes.
