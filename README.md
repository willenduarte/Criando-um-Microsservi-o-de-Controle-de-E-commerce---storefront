# Projeto Storefront (Microsserviço)

Este é o microsserviço responsável pela interface de usuário e gerenciamento de produtos/estoque através de chamadas síncronas e comunicação assíncrona com o Warehouse.

## 🚀 Pré-requisitos

Para executar este projeto, você precisará ter o seguinte instalado e configurado no seu ambiente local:

* **Java Development Kit (JDK):** Versão 21 ou superior.
* **Maven ou Gradle:** (Assumimos Gradle com base no Dockerfile).
* **Docker Desktop:** Para executar a aplicação e o broker de mensagens (RabbitMQ) em contêineres.

## ⚙️ Configuração do Ambiente Docker

Este projeto é orquestrado com o Docker Compose, que gerencia a aplicação Spring Boot e o broker de mensagens RabbitMQ.

### Estrutura de Arquivos Importantes:

* **`.env`:** Contém credenciais sensíveis, como a URL de conexão com o RabbitMQ externo (se aplicável).
* **`Dockerfile`:** Define como a imagem do **Storefront** é construída (usando Gradle para compilar o JAR).
* **`docker-compose.yml`:** Define e orquestra os serviços (`storefrontapp`, `warehouseapp`, `rabbitmq`).

### ⚠️ Configuração do RabbitMQ

O projeto está configurado para usar um **RabbitMQ local** orquestrado pelo Docker Compose, conforme o seu setup:

* **Imagem:** `rabbitmq:3-management`
* **Credenciais:** `admin`/`admin`
* **Portas:** 5672 (Broker) e 15672 (Management UI).

Se a intenção for usar o **CloudAMQP**, é necessário remover o serviço `rabbitmq` do `docker-compose.yml` e garantir que o `.env` injete a variável `CLOUDAMQP_URL` nos serviços `warehouseapp` e `storefrontapp`.

## 🛠️ Como Rodar o Projeto

1.  **Verificar a Rede:** Garanta que a rede Docker necessária esteja criada (deve ser feita apenas uma vez):
    ```bash
    docker network create ecommerce-net
    ```

2.  **Construir e Iniciar os Serviços:** Navegue até a raiz do projeto (onde está o `docker-compose.yml`) e execute:
    ```bash
    docker compose up --build
    ```

3.  **Acessar Aplicação:** Após a inicialização bem-sucedida, o Storefront estará acessível via:
    * **HTTP:** `http://localhost:8081/storefront` (Esta é a porta mapeada).

### 🧩 Comunicação entre Microsserviços

| Comunicação | Tipo | Host/URL (Dentro do Docker) | Porta Exposta (Localmente) |
| :--- | :--- | :--- | :--- |
| **Storefront $\leftrightarrow$ Warehouse (Síncrona/HTTP)** | Chamada Direta | `http://warehouseapp:8080/warehouse` | 8080 |
| **Storefront $\leftrightarrow$ RabbitMQ (Assíncrona)** | Fila de Mensagens | Conexão via host `rabbitmq` | 5672 (Broker) / 15672 (Management UI) |
