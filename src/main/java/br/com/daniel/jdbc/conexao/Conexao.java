package br.com.daniel.jdbc.conexao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

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
	
	
	public Conexao() {		
		this.host = this.dados("db.host");
		this.userDb = this.dados("db.user");
		this.passDb = this.dados("db.pass");
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
	
	/**
	 * @param campo do arquivo
	 * @return dados do campo que se encontra no arquivo .properties
	 * 
	 * Ler o arquivo de propriedades e retorna os dados dos campos de acordo
	 * com o campo passado por parametro
	 */
	private String dados(final String campo){
		try (FileInputStream file = new FileInputStream("propriedades.properties")){			
			Properties prop = new Properties();			
			prop.load(file);
			return prop.getProperty(campo);			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
