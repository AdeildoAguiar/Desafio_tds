
---

# Desafio_tds

O Desafio_tds é um projeto que visa criar um serviço de encurtamento de URLs. Ele permite que os usuários cadastrem URLs longas e obtenham uma versão curta, facilitando o compartilhamento e a memorização de links.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Swagger
- Lombok

## Configuração

### Banco de Dados MySQL

O Desafio_tds utiliza um banco de dados MySQL para armazenar as URLs originais e encurtadas. Antes de executar o projeto, certifique-se de configurar o MySQL e criar um banco de dados com o nome "tds". Você pode configurar as credenciais do banco de dados no arquivo `application.properties` localizado em `src/main/resources`.

Exemplo de configuração do `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tds
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuração do dialeto do Hibernate para MySQL 8
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Executando o Projeto

Para executar o projeto, siga estas etapas:

1. Clone o repositório para o seu ambiente local:

   ```sh
   git clone https://github.com/AdeildoAguiar/Desafio_tds.git
   ```

2. Navegue até o diretório do projeto:

   ```sh
   cd Desafio_tds
   ```

3. Execute o projeto usando o Maven:

   ```sh
   mvn spring-boot:run
   ```

4. O projeto será iniciado e estará disponível em `http://localhost:8080`.

### Endpoints da API

O projeto oferece os seguintes endpoints para interagir com o serviço de encurtamento de URLs:

- `POST /api/v1/urls`: Cadastra uma URL e retorna a versão encurtada.
- `GET /{shortUrl}`: Redireciona para a URL original correspondente à versão encurtada.
- `GET /api/v1/stats/{shortUrl}`: Obtém estatísticas de acesso para uma URL encurtada.

Para visualizar e interagir com os endpoints da API, você pode acessar a documentação Swagger disponível em `http://localhost:8080/swagger-ui.html`.



---
