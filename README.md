# SchoolSystem
--------SOBRE O QUE É A APLICAÇÃO--------

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

--------OBJETIVO--------

Sempre que criamos uma API, temos de fazer várias configurações, até mesmo, configurações padrões, que sempre serão as mesmas
independente da API. Isso é uma coisa que cansa muito e acaba tomando muito tempo, para resolver esse problema, desenvolvi essa
"api esqueleto" para sempre utilizar esses padrões em uma nova api que eu esteja desenvolvendo, desse modo, economizando muito tempo.
 Obs: Essa API seria usada de base para outras API caso essa por sua vez seja uma aplicação que precise de autenticação e autorização. 

--------FUNCIONALIDADES--------

  - O Usuário se registra no sistema.
  - O Usuário se autentica  no sistema.
  - O Usuário pode entrar no seu perfil  (ver as informações de sua conta).
  - O Usuário pode mudar a sua senha.
  - O Administrador pode ver uma página de usuários cadastrados no sistema.
  - O Administrador pode ver qualquer usuário registrado no sistema em específico pelo id.

---------VERSIONAMENTO--------

Versão do Java : 17

Versão do Spring Boot : 2.7.7

--------FERRAMENTAS---------

Linguagem de Desenvolvimento :

       Java

Principal Framework :

      Spring Boot.

Dependências :

     Data JPA - > Utilizando o JPA para fazer a ponte entre o banco de dados e a aplicação -> ORM
     
     Validation -> Utilizado para proibir requisições com dados inválidos ou com um formato inválido.
     
     Web -> É uma aplicação WEB, Utilizado para receber requisições, devolver uma resposta ...

     H2 - > Utilizado esse banco em MEMÓRIA para fazer testes, ambiente de teste.
     
     PostgreSQL -> Utilizado esse BANCO no ambiente de desenvolvimento e produção.
     
     Test -> Foram feitos testes na aplicação com JUNIT ( Testes de integração e Unidade).
     
     JsonWebToken -> Foi utilizado a arquitetura REST, por isso fiz a a aplicação ser stateless e usar jwt.
     
     Security -> Utilizado para fazer a segurança do sistema - > Autorização e Autenticação.
     
     OpenAPI -> Utilizado para documentar a API através dos ENDPOINTS no swagger.
     
     LomBok -> Utilizado para evitar muitas linhas de código.

     JacksonDatatype - > Utilizado para transformar objetos em JSON.

Programas Utilizados :

    Heroku - Utilizado para deixar o projeto na nuvem.
    
    Postman - Utilizado para fazer as requisições (CONSUMIR) a aplicação.
    
    IntelliJ - IDE escolhida para desenvolver o projeto.
    
    pgAdmin4 - Plataforma utilizada para fazer a manipulação e a leitura dos dados de uma base de dados do banco PostgreSQL.
    
    Git e GitHub - Utilizados para commitar o projeto e subir o código para a nuvem(remoto).

    JWT.IO - Depurar o token e ver o seu formato.

Bancos de Dados :

    PostgreSQL - Usado em ambiente de desenvolvimento e produção.
    
    H2 - Usado em ambiente de teste.

------------------------- INFORMAÇÕES ADICIONAIS ---------------------

    Linhas de Código : 2168
    
    Classes / Arquivos implementados : 78

    Testes : 39

------------------------ LINKS -----------------------------

Explicação da API em vídeo no YouTube : 

Perfil no Linkedin : https://www.linkedin.com/in/victoralmada/