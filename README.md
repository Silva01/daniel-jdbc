# JDBC-DANIEL
[![Build Status](https://travis-ci.org/Silva01/daniel-jdbc.svg?branch=master)](https://travis-ci.org/Silva01/daniel-jdbc)

Esta API tem como principal Objetivo simplificar uma consulta feita a um banco de dados, um dos principais objetivo é facilitar o a obtenção dos dados na base de dados e o atribuir a um objeto que representa a tabela em questão.

daniel-jdbc suporta os seguintes bancos de dados:
 - [x] MySQL
 - [ ] PostgreSQL
 - [ ] Oracle
 - [ ] SQL Server

Para utilizar basta baixar o arquivo [.jar](https://github.com/Silva01/daniel-jdbc/releases/tag/v1.0.5) e o adicionar ao Build Path do projeto, desta forma o projeto poderá carregar as classes responsável por executar as funcionalidades de transação de banco de dados.

## Exemplos de Select: 

```java
Dao dao = new Dao();

ResultSet result = dao
					.sid()
					.dql(new Conexao("propriedades.properties").conectarMysql())
					.select("SELECT idade, nome FROM teste.teste")
					.execute();
```

Para realizar um simples Select é preciso apenas criar uma instância da classe *Dao*, em um select o objeto retornado será sempre um objeto ResultSet.

A classe conexão possui 2 contrutores onde é possivel passar os parametros de banco de dados necessários para a conexão.

```java
// parametros passados host = "localhost:3306", userDb = "teste", passDB = "12345"
Conexao con = new Conexao(host, userDb, passDB);
```

No Exemplo acima a classe Conexão recebe 3 parâmetros no qual são utilizados para conexão com o banco de dados, desta forma o Select Acima teria a seguinte instrução.

```java
String host = "localhost:3306";
String userDb = "teste";
String passDb = "12345";

Dao dao = new Dao();

ResultSet result = dao
				  .sid()
				  .dql(new Conexao(host, userDB, passDb).conectarMysql())
				  .select("SELECT idade, nome FROM teste.teste")
				  .execute();
``` 

Outra forma de passar parametros de banco de dados é por meio de um arquivo de propriedades. Desta forma basta apenas passar a URI do arquivo .properties.

```java
Conexao con = new Conexao("/user/teste/banco.properties");
```

## Exemplos de Transações DML

As instruções DML por sua maioria retornam um objeto do tipo Boolean informando `true` para caso a transação tenha ocorrido de forma segura e correta, para caso de erros, a retornado `false` 

### Instrução Insert:

```java
Boolean result = dao
				.sid()
				.dml(new Conexao("propriedades.properties").conectarMysql())
 				.insert("INSERT INTO `teste`.`teste` (`nome`, `idade`) VALUES (?, ?);", "Teste", 10)
 				.execute();
```

Como podemos ver, as instruções DML seguem o mesmo padrão das instruções DQL.

### Instrução UPDATE
São transações que realizam a alteração de algum dado na base de dados.

```java
Boolean response = dao
				.sid()
				.dml(new Conexao("propriedades.properties").conectarMysql())
				.update("UPDATE `teste`.`teste` SET `nome`= ?, `idade`= ? WHERE `id`= ? ","Teste2", 22, id)
				.execute();
```

### Instrução de Delete
São transações que exclui um dado da base de dados.

```java
Boolean response = dao
				.sid()
				.dml(new Conexao("propriedades.properties").conectarMysql())
				.delete("DELETE FROM `teste`.`teste` WHERE nome = ? AND idade = ?", "Teste2", 22)
				.execute();
```
