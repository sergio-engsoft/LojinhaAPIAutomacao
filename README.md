# Lojinha API Automação 🛒✅

Este é um projeto de automação de testes de API desenvolvido com o objetivo de demonstrar dominância sobre automação de testes de API, utilizando ferramentas modernas e seguindo boas práticas de desenvolvimento. A API testada simula um ambiente de e-commerce (Lojinha), permitindo operações como criação de usuários, autenticação, cadastro de produtos e componentes.

O projeto foi baseado em um curso de automação de testes de API ministrado por Julio de Lima.

## Tecnologias Utilizadas 🚀

- **Java 23 (JDK)**  
- **JUnit 5**  
- **RestAssured**  
- **Maven 3.9.6**  
- **Git**  
- **GitHub Actions** (CI/CD)  
- **Swagger** (Documentação da API)
- **Postman**
## Estrutura do Projeto 📁

```
LojinhaAPIAutomacao
├── .github/workflows/ci.yml     # Pipeline de integração contínua
├── src/test/java                # Testes automatizados
│   ├── datafactory              # Geradores de dados para os testes
│   ├── domain                   # POJOs (modelos de dados)
│   └── tests                    # Casos de teste organizados por módulo
├── pom.xml                      # Gerenciador de dependências e build (Maven)
```

## Como Executar os Testes 🧪

### Pré-requisitos

- Java 23 instalado  
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

- [Editor Swagger](https://editor.swagger.io/)
- [Documentação da API da Lojinha](http://165.227.93.41/lojinha)
- [Repositório no GitHub](https://github.com/sergio-engsoft/LojinhaAPIAutomacao)
- [Meu LinkedIn](https://linkedin.com/in/sergio-dos-santos-soares)
