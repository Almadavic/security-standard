# SecurityStandard

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Almadavic/security-standard/blob/main/LICENSE)

 # O QUE É A APLICAÇÃO :

Essa é uma aplicação para ser o esqueleto (base) de outras API's futuras que necessitam de autenticação JWT e autorização. Além desses
2 recursos, a API conta com outras diversas configurações padrões que podem ser aproveitadas em uma futura API, como:

- Configurações padrões de Segurança no geral.
- Entidades User e Role totalmente prontas.
- Configurações padrões do Swagger.
- Tratamento de Exceptions através de classes Handler e classes personalizadas.
- Diversos Design Patterns.
- Dados iniciais para população do banco.
- Controllers, Services e Repositories padrões pré configurados.
- DTO's de request e response.
- Regras de négocio.
- Exception personalizadas.
- Funcionalidades extras além de autenticação.
- Uma grande cobertura de testes.

# OBJETIVO :

Sempre que criamos uma API, temos de fazer várias configurações, até mesmo, configurações padrões, que sempre serão as mesmas
independente da API. Isso é uma coisa que cansa muito e acaba tomando muito tempo, para resolver esse problema, desenvolvi essa
"api esqueleto" para sempre utilizar esses padrões em uma nova api que eu esteja desenvolvendo, desse modo, economizando muito tempo.
 Obs: Essa API seria usada de base para outras API caso essa por sua vez seja uma aplicação que precise de autenticação e autorização. 

# FUNCIONALIDADES :

  - O Usuário se registra no sistema.
  - O Usuário se autentica  no sistema.
  - O Usuário pode entrar no seu perfil (ver as informações de sua conta).
  - O Usuário pode mudar a sua senha.
  - O Administrador pode ver uma página de usuários cadastrados no sistema.
  - O Administrador pode ver qualquer usuário registrado no sistema em específico pelo id.

# VERSIONAMENTO :

Versão do Java: 17

Versão do Spring Boot: 3.0.1

# FERRAMENTAS :

Back end:

      - Java
      - Spring Boot c/ Spring Security e JWT.
      - JPA / Hibernate
      - Maven

Dependências:

     Data JPA - > Utilizando o JPA para fazer a ponte entre o banco de dados e a aplicação -> ORM
     
     Validation -> Utilizado para proibir requisições com dados inválidos ou com um formato inválido.
     
     Web -> É uma aplicação WEB, Utilizado para receber requisições, devolver uma resposta ...

     H2 - > Utilizado esse banco em MEMÓRIA para fazer testes, ambiente de teste.
     
     PostgreSQL -> Utilizado esse BANCO no ambiente de desenvolvimento e produção.
     
     Test -> Foram feitos testes na aplicação com JUNIT ( Testes de integração e Unidade).
     
     Java-JWT -> A aplicação é stateless, por isso precisamos da biblioteca para gerar e verificar o token. 
     
     Security -> Utilizado para fazer a segurança do sistema - > Autorização e Autenticação.
     
     OpenAPI -> Utilizado para documentar a API (Swagger).
     
     LomBok -> Utilizado para evitar muitas linhas de código através de annotations.

     JacksonDatatype - > Utilizado para transformar objetos em JSON.

Programas Utilizados :
 
    Postman - Utilizado para fazer as requisições (CONSUMIR) a aplicação.
    
    IntelliJ - IDE escolhida para desenvolver o projeto.
    
    pgAdmin4 - Plataforma utilizada para fazer a manipulação e a leitura dos dados de uma base de dados do banco PostgreSQL.
    
    Git e GitHub - Utilizados para commitar o projeto e subir o código para a nuvem(remoto).

    JWT.IO - Depurar o token e ver o seu formato.

Bancos de Dados :

    PostgreSQL - Usado em ambiente de desenvolvimento e produção.
    
    H2 - Usado em ambiente de teste.


- Swagger :

<img src="https://user-images.githubusercontent.com/85299065/228671863-ea7b96f2-442e-44a8-a121-363e8eef5514.png" width="100%" height="100%">

- Schema :

<img src="https://user-images.githubusercontent.com/85299065/212201048-78f9aa0b-e700-4406-8a68-84b74f1b96fa.PNG" width="100%" height="100%">

# INFORMAÇÕES ADICIONAIS :

    Linhas de Código: 2217
    
    Classes / Arquivos implementados: 79

    Testes: 40

    Link index do Swagger: /security-api/swagger-ui/index.html

# LINKS ADICIONAIS : 

Explicação da API em vídeo no YouTube : https://youtu.be/58U1HHhoS2I

Perfil no Linkedin : https://www.linkedin.com/in/victoralmada/
