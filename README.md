# 🚀 E-Commerce Automation Framework (Web & API)

[![Allure Report](https://img.shields.io/badge/Allure%20Report-Live%20Dashboard-brightgreen?style=for-the-badge&logo=allure)](https://david-qa-code.github.io/automacao-selenium-web-api/)
[![Java 17](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Selenium 4](https://img.shields.io/badge/Selenium-4-green?style=for-the-badge&logo=selenium)](https://www.selenium.dev/)
[![REST Assured](https://img.shields.io/badge/REST%20Assured-5.4-blue?style=for-the-badge)](https://rest-assured.io/)

> 📊 **Dashboard em tempo real:** [Clique aqui para ver o Relatório Allure de Execução](https://david-almeida-silva.github.io/automation-exercise-e2e-api/)

---

## 📌 Sobre o Projeto
Este repositório consiste em um framework robusto de automação de testes cobrindo camadas **Front-End (UI)** e **Back-End (API)** para o e-commerce [Automation Exercise](https://automationexercise.com/). 

O objetivo do projeto é demonstrar a implementação de boas práticas de arquitetura de testes, resiliência de locators, independência de massa de dados e integração contínua (CI/CD) com publicação de relatórios na nuvem.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Web Automation:** Selenium WebDriver 4
* **API Automation:** REST Assured 5
* **Test Runner:** JUnit 5 (Jupiter)
* **Gerenciador de Dependências:** Apache Maven
* **Relatórios Visuais:** Allure Framework (Allure JUnit 5 / Allure Maven)
* **CI/CD & Hosting:** GitHub Actions & GitHub Pages

---

## 🏗️ Arquitetura e Decisões de Design

* **Page Object Model (POM):** Separação estrita entre a camada de mapeamento/interação com páginas (`pages/`) e a camada de validação dos cenários (`tests/`).
* **Resiliência e Seletores Fortes:** Priorização do uso de seletores `data-qa` fornecidos pela aplicação, garantindo testes insensíveis a mudanças puramente estéticas de layout.
* **Execução Headless & Cloud-Ready:** Configuração do `ChromeOptions` utilizando argumentos como `--headless=new`, `--no-sandbox`, `--disable-dev-shm-usage` e resolução fixa `1920x1080` para garantir execução estável em contêineres Linux sem interface gráfica (GitHub Actions).
* **Massa de Dados Dinâmica:** Utilização de timestamps (`System.currentTimeMillis()`) na geração de e-mails para garantir autonomia de cada execução e evitar conflitos de duplicidade de dados.
* **Tratamento de Contrato de API (Resilient API Testing):** Manipulação e higienização de respostas de API que retornam conteúdo HTML encapsulando o JSON por meio de expressões regulares (`Regex`) e extração via `JsonPath`.
* **Teardown (Limpeza de Dados):** Execução do fluxo de exclusão de conta (`DeleteAccountPage`) ao final do fluxo E2E para evitar acúmulo de dados órfãos na base da aplicação.

---

## 🧪 Cenários de Teste Cobertos (5 Test Cases)

### 🌐 Testes de Interface Web (UI)
1. **`deveAcessarTelaDeLoginComSucesso`**: Navegação inicial e validação de rota da página de login.
2. **`deveExibirErroAoLogarComCredenciaisInvalidas`**: Submissão de credenciais incorretas e asserção da mensagem de erro tratada.
3. **`deveRealizarCheckoutCompletoComSucesso` (E2E Master)**: Fluxo completo desde a criação de usuário dinâmico, adição de produto ao carrinho, preenchimento de endereço e cartão de crédito, confirmação do pedido e teardown (exclusão do usuário).

### ⚙️ Testes de API (REST Assured)
4. **`deveRetornarListaDeProdutosComSucesso`**: Requisição `GET /productsList`, higienização do HTML retornado, validação do `responseCode: 200` e garantia de lista não vazia.
5. **`devePesquisarProdutoPorNomeComSucesso`**: Requisição `POST /searchProduct` enviando parâmetros via formulário (`formParam`), extração do JSON e asserção nos itens retornados.

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
* Java JDK 17 instalado
* Apache Maven instalado e configurado na variável de ambiente `PATH`
* Google Chrome instalado

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/david-almeida-silva/automation-exercise-e2e-api
   ```

2. **Acesse a pasta do projeto:**
   ```bash
   cd automacao-selenium-web-api
   ```

3. **Execute a suíte completa de testes:**
   ```bash
   mvn clean test
   ```

4. **Gerar e visualizar o relatório visual Allure localmente:**
   ```bash
   mvn allure:serve
   ```

---

## ☁️ Esteira de Integração Contínua (CI/CD)

O projeto conta com um workflow automatizado no **GitHub Actions** (`.github/workflows/ci-cd.yml`):

1. **Trigger:** A cada `push` ou `pull_request` nas branches `main` ou `master`.
2. **Ambiente:** Provisionamento de uma máquina virtual `ubuntu-latest` com JDK 17.
3. **Execução:** Execução dos testes de UI e API em modo *headless*.
4. **Build do Relatório:** Compilação dos resultados via `mvn allure:report`.
5. **Publishing:** Deploy automático do HTML compilado para a branch `gh-pages`, atualizando o **GitHub Pages** em tempo real.