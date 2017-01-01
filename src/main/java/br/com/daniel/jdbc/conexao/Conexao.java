package br.com.daniel.jdbc.conexao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import br.com.daniel.jdbc.exception.NaoConectadoDbException;
import br.com.daniel.jdbc.exception.NaoEncontradoPropertiesException;

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
	
	
	public Conexao() throws NaoConectadoDbException {
		try {
			this.host = this.dados("db.host");
			this.userDb = this.dados("db.user");
			this.passDb = this.dados("db.pass");
		} catch (Exception e) {
			throw new NaoConectadoDbException((NaoConectadoDbException) e);
		}
		
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
	
	/**
	 * @param campo do arquivo
	 * @return dados do campo que se encontra no arquivo .properties
	 * 
	 * Ler o arquivo de propriedades e retorna os dados dos campos de acordo
	 * com o campo passado por parametro
	 * @throws NaoEncontradoPropertiesException 
	 * @throws Exception 
	 */
	private String dados(final String campo) throws NaoEncontradoPropertiesException {
		try (FileInputStream file = new FileInputStream("propriedades.properties")){			
			Properties prop = new Properties();			
			prop.load(file);
			return prop.getProperty(campo);			
		} catch (Exception e) {
			throw new NaoEncontradoPropertiesException((NaoEncontradoPropertiesException) e, "Erro ao Ler arquivos de Propriedades");
		}
	}
}
