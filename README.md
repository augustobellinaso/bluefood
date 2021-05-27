# BlueFood - Aplicativo para pedir comida

## Descrição do Projeto

Aplicativo Java Web para fazer o pedido de comida nos restaurantes cadastrados. Projeto desenvolvido durante a <a href="https://www.softblue.com.br/site/page/id/FMD_3_Vendas">Formação Master Developer </a> da <a href="https://www.softblue.com.br/">Softblue</a>.

Deploy da aplicação no Heroku: https://bluefood-abellinaso.herokuapp.com/login

---

<!--ts-->
   * [Executando o Projeto](#executando-o-projeto)
   * [Como utilizar](#como-utilizar)
   * [Features](#features)
   * [Tecnologias](#tecnologias)
<!--te-->

---

## Executando o Projeto

### Pré Requisitos

- Ter instalado alguma IDE ([Eclipse](https://www.eclipse.org/), [IntelliJ](https://www.jetbrains.com/pt-br/idea/) ou [Spring Tools Suite](https://spring.io/tools))
  - Observações:
    -  Se utilizar o Eclipse, deve-se ter instalado o plugin Spring Tools 4;
    -  Se utilizar o IntelliJ, é necessário ser a versão Ultimate para ter o suporte ao Java Web
- Caso queira utilizar a aplicação com a funcionalidade de fazer a validação/processamento do pagamento, é necessário ter instalado e estar executando localmente a aplicação [Sbpay](https://github.com/augustobellinaso/sbpay).
- Ter instalado e configurado o banco de dados.

### Fazendo download do código e inserindo na IDE

- Fazer o download do código fonte no formato `.zip` e extrair o mesmo para a pasta de destino.

#### Abrindo o projeto com IntelliJ

- Ir no menu `File > Open` e selecionar a pasta do arquivo descompactado.
- A IDE irá identificar que o projeto possui o gerenciador de dependências `Gradle` e irá solicitar a importação das dependências, basta colocar para importar. Caso aparecer a mensagem `Trust Gradle Project`, clique no botão `Trust Project` para que seja possível editar o projeto.
- Após fazer isso basta esperar alguns momentos até que todas as dependendências sejam carregadas e indexadas ao projeto.
  - Pode ser necessário informar a JDK que a IDE deve usar para executar o projeto. Como o projeto foi desenvolvido com Java na versão 11, selecione qualquer JDK que tenha suporte à essa versão.

#### Abrindo o projeto com Eclipse e/ou Spring Tools Suite
- Ir no menu `File > Import > Gradle > Existing Gradle Project`, apertar `Next` e fazer o mesmo na tela de boas vindas do `Gradle`. Na tela seguinte, no campo `Project root directory`, clique em `Browse` e selecione a pasta descompactada do projeto e então clique em `Finish`.
- Aguarde o Eclipse inserir o projeto e as suas dependências.


--- 

## Como Utilizar

- Para utilizar a aplicação localmente, algumas modificações precisam ser feitas.
- Adicionar no arquivo `application.properties` o seguinte comando: `spring.profiles.active=dev`.

### Banco de dados

- A aplicação utiliza no ambiente de desenvolvimento o banco de dados MySQL, assim, para poder utilizar a aplicação corretamente é necessário configurar na máquina local o banco de dados e informar os dados do mesmo no arquivo `application-dev.properties`:
```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect
spring.datasource.url=jdbc:mysql://localhost:3306/bluefooddb?useSSL=false&useTimezone=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=bluefood
spring.datasource.password=nhac
```
trocando as informações acima pelas definidas em seu ambiente.
- Também é necessário fazer uma modificação no arquivo `build.gradle`, deixando comentada a dependência do banco de dados PostgreSQL (usado em produção) e descomentando a dependência do MySQL. Deve ficar assim:
```
    runtimeOnly 'mysql:mysql-connector-java'
    //runtimeOnly 'org.postgresql:postgresql'
```
  - Após fazer essa alteração é necessário dar um `Refresh Gradle` no projeto.
  
  
### Imagens

- De modo que as imagens funcionem corretamente no seu dispositivo, é necessário fazer uma alteração no arquivo `application-dev.properties`:
  - Nas variáveis definidas abaixo:
  ```
  bluefood.files.logotipo=COLOQUE-AQUI-O-CAMINHO-DA-PASTA/bluefood/src/main/resources/static/bluefoodfiles/logotipos
  bluefood.files.comida=COLOQUE-AQUI-O-CAMINHO-DA-PASTA/bluefood/src/main/resources/static/bluefoodfiles/comidas
  bluefood.files.categoria=COLOQUE-AQUI-O-CAMINHO-DA-PASTA/bluefood/src/main/resources/static/bluefoodfiles/categorias
  ```
  é necessário informar o caminho do local onde a pasta do projeto foi colocado, trocando o trecho `COLOQUE-AQUI-O-CAMINHO-DA-PASTA` pelo caminho.
  
### Executando

Após as alterações acima terem sido feitas, é possível executar o aplicativo.
- Para utilizar o projeto basta executar o mesmo na IDE e depois acessar o navegador através da URL `http://localhost:8080`;
  - No Eclipse/Spring Tools Suite, clique com o botão direito em cima da pasta do projeto no `Package Manager`, vá até `Run As` e selecione `Spring Boot Application`.
  - No IntelliJ, execute a aplicação clicando na setinha verde que aparece ao lado da declaração da classe dentro do arquivo `BluefoodApplication`.  
    
---

## Features

- [x] Cadastro de clientes;
- [x] Cadastro de restaurantes;
- [x] Cadastro dos itens vendidos por cada restaurante;
- [x] Carrinho de compras com o pedido de cada usuário;
- [x] Validação dos campos nas telas de cadastro;
- [x] Upload de imagens;
- [x] Possibilidade de remover itens do pedido;
- [x] Remoção de itens cadastrados por restaurante;
- [x] Alteração de dados dos cadastros;
- [x] Validação do pagamento utilizando um WebService externo;
- [x] Alteração do status de um pedido por parte do restaurante;
- [x] Cálculo da taxa de entrega baseado no CEP de cada cliente;
- [x] Relatório de pedidos e de itens vendidos para cada restaurante cadastrado;
- [x] Validação de segurança no login;
- [x] Testes Unitários com JUnit e Mockito.

---

## Tecnologias

- HTML5
- CSS
- JavaScript
- [Thymeleaf](https://www.thymeleaf.org/)
- [Lombok](https://projectlombok.org/)
- [Spring](https://spring.io/)
  - Spring DevTools
  - Spring Web
  - Spring Web Services
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- [PostgreSQL](https://www.postgresql.org/)
  - para ambiente de produção
- [MySQL](https://www.mysql.com/)
  - para ambiente de desenvolvimento 
- [H2 Database](https://www.h2database.com/html/main.html)
  - para execução dos testes.
  
  

