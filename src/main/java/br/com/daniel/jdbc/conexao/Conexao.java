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
 * classes de persistência.
 * 
 * Esta classe precisa que os parametros sejam informados 
 *
 */
public class Conexao {

	private String host;
	private String userDb;
	private String passDb;
	
	

	/**
	 * Contrutor no qual inicializa as principais variaveis que contém informações de 
	 * conexão com o banco de dados
	 * 
	 * @param host
	 * @param userDb
	 * @param passDb
	 */
	public Conexao(final String host, final String userDb, final String passDb) {		
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
	 * @throws NaoConectadoDbException 
	 */
	public Connection conectarMysql() throws NaoConectadoDbException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://"+ host, userDb, passDb);
		} catch (NaoConectadoDbException e) {
			throw new NaoConectadoDbException(e, "Erro ao conectar na base de dados");			
		} catch (Exception e) {
			throw new NaoConectadoDbException((NaoConectadoDbException) e, "Erro ao conectar na base de dados");
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
