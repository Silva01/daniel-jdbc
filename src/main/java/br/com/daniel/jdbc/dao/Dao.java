package br.com.daniel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.daniel.jdbc.conexao.Conexao;
import br.com.daniel.jdbc.exception.ExceptionDatabase;
import br.com.daniel.jdbc.exception.NaoConectadoDbException;
import br.com.daniel.jdbc.util.Utilidades;

/**
 * @author Daniel Silva
 * @version 1.0.1
 * 
 * Dao básico no qual realiza as principais operações de transação 
 * no banco de dados.
 * 
 * 
 * Exemplo Select:
 * 
 * 	ResultSet result = dao.sid()
 *				.dql(new Conexao("propriedades.properties").conectarMysql())
 *				.select("SELECT idade, nome FROM teste.teste")
 *				.execute();
 *
 *
 * Exemplo Insert:
 * 
 * 	Boolean result = dao.sid().dml(new Conexao("propriedades.properties").conectarMysql())
 * 				.insert("INSERT INTO `teste`.`teste` (`nome`, `idade`) VALUES (?, ?);", "Teste", 10)
 * 				.execute();
 * 
 * 
 * Exemplo Update:
 * 
 * 	Boolean response = dao.sid().dml(new Conexao("propriedades.properties").conectarMysql())
 *				.update("UPDATE `teste`.`teste` SET `nome`= ?, `idade`= ? WHERE `id`= ? ","Teste2", 22, id)
 *				.execute();
 *
 * Exemplo Delete:
 *  Boolean response = dao.sid().dml(new Conexao("propriedades.properties").conectarMysql())
 *				.delete("DELETE FROM `teste`.`teste` WHERE nome = ? AND idade = ?", "Teste2", 22)
 *				.execute();
 */
public class Dao {	

	
	public Crud sid(){
		return new Crud();
	}
	
	
	/**
	 * @author Daniel
	 * @version 1.0.0
	 * 
	 * Possui as funcionalidades básicas de CRUD
	 *
	 */
	public class Crud {
		
		/**
		 * @param conexão
		 * @return
		 * 
		 * Executa funções básicas de DQL
		 */
		public Dql dql(final Connection conn){
			return new Dql(conn);
		}
		
		/**
		 * @param Conexão
		 * @return
		 * 
		 * Executa funções básicas de DML
		 */
		public Dml dml(final Connection conn){
			return new Dml(conn);
		}
		
		/**
		 * @author Daniel
		 * @version 1.0.0
		 * 
		 * Possui as funções básicas de DQL
		 */
		public class Dql {
			
			private Connection conn;
			private PreparedStatement stm;
			
			public Dql(final Connection conn) {
				this.conn = conn;
			}
			
			/**
			 * @param Instrução de banco de dados
			 * @param Parametros de consulta
			 * @return Consulta do banco de dados
			 * @throws NaoConectadoDbException 
			 * @throws ExceptionDatabase 
			 * @throws Exception 
			 * 
			 * Realiza uma consulta ao banco de dados utilizando parametros de filtros 
			 * 
			 */
			public Dql select(final String query, final Object...params) throws NaoConectadoDbException, ExceptionDatabase {			
				try {
					this.stm = conn.prepareStatement(query);
					
					if (params != null && params.length > 0) {						
						this.stm = Utilidades.preencherCampos(this.stm, params);										
					}			
					
					return this;
					 
				} catch (NaoConectadoDbException e) {
					throw new NaoConectadoDbException(e);					
				} catch (Exception e) {
					throw new ExceptionDatabase(e);					
				} 
			}
			
			/**
			 * @return Resultado do Select
			 * 
			 * Executa instrução de SQL de Select
			 * @throws ExceptionDatabase 
			 */
			public ResultSet execute() throws ExceptionDatabase {
				try {
					return this.stm.executeQuery();			
				} catch (Exception e) {
					throw new ExceptionDatabase(e);					
				}
			}			
		}
		
		
		/**
		 * @author Daniel
		 * @version 1.0.0
		 * 
		 * Possui as funções básicas de DML
		 *
		 */
		public class Dml{
			
			private Connection conn;
			private PreparedStatement stm;
			
			public Dml(final Connection conn) {
				this.conn = conn;
			}
			
			/**
			 * @param Instrução de banco de dados
			 * @param parametros
			 * @return True para inserido ou false para não inserido
			 * @throws ExceptionDatabase 
			 * @throws SQLException
			 * 
			 * Realiza um insert a base de dados
			 */
			public Dml insert(final String query, final Object...params) throws ExceptionDatabase {			
				
				prepararStatement(query);
				preencherStatement(params);
				
				return this;	
								 
			}
			
			/**
			 * @param Instrução de banco de dados
			 * @param parametros
			 * @return True para atualizado ou false para não atualizado
			 * @throws ExceptionDatabase 
			 * @throws SQLException
			 * 
			 * Realiza uma transação de update na base de dados
			 */
			public Dml update(final String query, final Object...params) throws ExceptionDatabase {					
				prepararStatement(query);
				preencherStatement(params);
				
				return this;				
			}
			
			/**
			 * @param Instrução de banco de dados
			 * @param parametros
			 * @return True para excluido ou false para não excluido
			 * @throws SQLException
			 * 
			 * Realiza a exclusão de um dados na base de dados
			 * @throws ExceptionDatabase 
			 */
			public Dml delete(final String query, final Object...params) throws ExceptionDatabase{		
				
				prepararStatement(query);
				preencherStatement(params);
				
				return this;				
			}
			
			/**
			 * @return True para executado com sucesso e False para Erro
			 * 
			 * Executa instrução de SQL
			 * @throws ExceptionDatabase 
			 */
			public Boolean execute() throws ExceptionDatabase {
				try {
					this.stm.execute();
					this.conn.commit();
					return true;
				} catch (Exception e) {
					cancelarTransacao();
					throw new ExceptionDatabase(e);					
				} finally {
					finalizar();					
				}
			}
			
			/**
			 * @param query
			 * 
			 * Prepara instrução de SQL para ser executada
			 * @throws ExceptionDatabase 
			 */
			private void prepararStatement(final String query) throws ExceptionDatabase {
				try {
					this.conn.setAutoCommit(false);
					this.stm = this.conn.prepareStatement(query);
				} catch (SQLException e) {
					throw new ExceptionDatabase("Problema ao preparar instrução", e);
				}								
			}
			
			/**
			 * @param params
			 * 
			 * Preenche um Objeto PreparedStatement com os parametros fornecidos
			 * pelo usuário
			 * @throws ExceptionDatabase 
			 * @throws Exception 
			 */
			private void preencherStatement(final Object...params) throws ExceptionDatabase {
				try {
					this.stm = Utilidades.preencherCampos(this.stm, params);
				} catch (Exception e) {
					throw new ExceptionDatabase("Erro ao preencher campos", e);
				}
			}
			
			/**
			 * Realiza uma transação de Rollback
			 * @throws ExceptionDatabase 
			 */
			private void cancelarTransacao() throws ExceptionDatabase{
				try {
					this.conn.rollback();
				} catch (Exception e) {
					throw new ExceptionDatabase("Erro ao Executar Rollback", e);
				}
			}
			
			/**
			 * Finaliza todas as conexões com o banco de dados
			 * @throws ExceptionDatabase 
			 */
			private void finalizar() throws ExceptionDatabase{				
				try {
					stm.close();
					conn.close();
				} catch (Exception e) {
					throw new ExceptionDatabase("Erro ao fechar conexao", e);
				}
			}
		}
	}	
}
