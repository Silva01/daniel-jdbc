package br.com.daniel.jdbc.conexao;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.daniel.jdbc.exception.NaoConectadoDbException;

/**
 * @author Daniel
 * @version 1.0.0
 * 
 * Classe faz a conexão com o banco de dados, ao realizar a conexão
 * retorna essa conexão pronta para que seja utilizada em por outras
 * classes de persistência
 *
 */
public class Conexao {

	private String host;
	private String userDb;
	private String passDb;
	
	
	public Conexao(String host, String userDb, String passDb) {		
		this.host = host;
		this.userDb = userDb;
		this.passDb = passDb;
	}
	

	/**
	 * 
	 * @return Conexao
	 * 
	 * Inicia a conexão com o banco de dados Mysql
	 * 
	 * para essa conexão, utiliza-se os parametros informados
	 * pelo usuário que são <b>Host do banco de dados</b>,  
	 * <b>usuário do banco de dados</b> e <b>Senha do banco de dados</b>
	 */
	public Connection conectarMysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(host, userDb, passDb);
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Connection conectarPostgrees(){
		return null;
	}
	
	public Connection conectarOracle(){
		return null;
	}
	
	public Connection conectarSqlServer(){
		return null;
	}
}
