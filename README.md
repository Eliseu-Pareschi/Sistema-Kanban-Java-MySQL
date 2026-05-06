# 📋 Sistema Kanban - Grupo 3

Sistema de gerenciamento de tarefas desenvolvido em **Java** com persistência em **MySQL**. O projeto permite a organização de fluxos de trabalho através de uma estrutura Kanban, separando tarefas por tipo (Bug ou Feature) e prioridade.

## 🚀 Funcionalidades
* **Gestão de Projetos:** Cadastro e controle de projetos ativos.
* **Quadro Kanban:** Organização de tarefas nos status `FAZER`, `FAZENDO` e `FEITO`.
* **Diferenciação de Tarefas:** 
  * **Bugs:** Controle de severidade e reprodutibilidade.
  * **Features:** Avaliação de valor de negócio e complexidade.
* **Histórico:** Registro de eventos e mudanças de status das tarefas.

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java (Swing/AWT para interface)
* **Banco de Dados:** MySQL 8.0
* **Arquitetura:** Padrão DAO (Data Access Object)

## 📂 Estrutura do Banco de Dados
O sistema utiliza três tabelas principais:
1. `projetos`: Armazena os nomes dos projetos.
2. `tarefas`: Contém os dados das atividades, incluindo ENUMs para status e prioridades.
3. `eventos_tarefa`: Log de alterações para auditoria.

## 🔧 Como Configurar
1. Clone o repositório.
2. Importe o arquivo `kanban_db.sql` no seu MySQL para gerar a estrutura e os dados de teste.
3. Certifique-se de adicionar o `mysql-connector-java` às bibliotecas do seu projeto no NetBeans.

## 👥 Integrantes
Nome	RGM
Giovani Ramires Cardoso dos Santos	46104828
Arthur Oliveira Souza	42941661
Arthur Ramires Quirino	46092323
Eliseu Carlos Lopes Pareschi 45876029
