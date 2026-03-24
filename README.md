# Lojinha API Automação

[![CI](https://github.com/sergio-engsoft/LojinhaAPIAutomacao/actions/workflows/ci.yml/badge.svg)](https://github.com/sergio-engsoft/LojinhaAPIAutomacao/actions/workflows/ci.yml)
[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.10-green)](https://junit.org/junit5/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.4-orange)](https://rest-assured.io/)

Testes de API para a Lojinha — um e-commerce de exemplo. Cobre os módulos de Usuário, Produto e Componente com cenários de sucesso, campos obrigatórios, limites de valor, controle de acesso e validação de contrato (JSON Schema).

## Stack

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| JUnit 5 | 5.10.2 |
| RestAssured | 5.4.0 |
| JSON Schema Validator | 5.4.0 |
| Allure | 2.27.0 |
| Owner | 1.0.12 |

## Estrutura

```
src/
├── main/java/pojo/              # Modelos de payload da API
└── test/
    ├── java/
    │   ├── base/BaseTest.java   # Configuração central do RestAssured
    │   ├── config/              # Gerenciamento de propriedades (Owner)
    │   ├── datafactory/         # Fábricas de dados de teste
    │   └── modulos/             # Testes por domínio
    └── resources/
        ├── config.properties
        └── schemas/             # JSON Schemas para validação de contrato
```

## Como executar

```bash
git clone https://github.com/sergio-engsoft/LojinhaAPIAutomacao.git
cd LojinhaAPIAutomacao
mvn clean test
```

```bash
# Só testes críticos (~30s)
mvn test -Dgroups=smoke

# Suíte completa
mvn test -Dgroups=regression

# Relatório Allure
mvn allure:serve
```

## Configuração

As propriedades ficam em `src/test/resources/config.properties`. A precedência é: variável de sistema > variável de ambiente > arquivo.

```bash
# Exemplo: apontar para outro ambiente
mvn test -Dapi.base.uri=http://outro-ambiente.com
```

Credenciais nunca ficam no código. No CI, configure os secrets `API_ADMIN_LOGIN` e `API_ADMIN_PASSWORD` em **Settings > Secrets and variables > Actions**.

## CI/CD

GitHub Actions executa os testes a cada push e PR para `main`, publica o relatório Allure no GitHub Pages e mantém histórico de tendências entre execuções.

Para ativar o Pages: **Settings > Pages > Deploy from branch `gh-pages`**.

---

Sergio Soares · [LinkedIn](https://linkedin.com/in/sergio-dos-santos-soares) · sergiodossantossoares@hotmail.com