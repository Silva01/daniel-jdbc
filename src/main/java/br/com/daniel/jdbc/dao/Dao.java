package br.com.daniel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.daniel.jdbc.exception.ExceptionDatabase;
import br.com.daniel.jdbc.exception.NaoConectadoDbException;
import br.com.daniel.jdbc.util.Utilidades;

/**
 * @author Daniel Silva
 * @version 1.0.1
 * 
 * Dao básico no qual realiza as principais operações de transação 
 * no banco de dados
 *
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
			 * @throws SQLException
			 * 
			 * Realiza uma consulta ao banco de dados utilizando parametros de filtros 
			 * 
			 */
			public Dql select(final String query, final Object...params) {			
				try {
					this.stm = (PreparedStatement) conn.prepareStatement(query);
					
					if (params != null) {
						if (params.length > 0) {
							this.stm = Utilidades.preencherCampos(this.stm, params);
						}					
					}			
					
					return this;
					 
				} catch (NaoConectadoDbException e) {
					new NaoConectadoDbException(e);
					return null;
				} catch (Exception e) {
					new Exception(e);
					return null;
				} 
			}
			
			/**
			 * @return Resultado do Select
			 * 
			 * Executa instrução de SQL de Select
			 */
			public ResultSet execute() {
				try {
					return this.stm.executeQuery();			
				} catch (Exception e) {
					new ExceptionDatabase(e);
					return null;
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
			 * @throws SQLException
			 * 
			 * Realiza um insert a base de dados
			 */
			public Dml insert(final String query, final Object...params) {			
				
				prepararStatement(query);
				preencherStatement(params);
				
				return this;	
								 
			}
			
			/**
			 * @param Instrução de banco de dados
			 * @param parametros
			 * @return True para atualizado ou false para não atualizado
			 * @throws SQLException
			 * 
			 * Realiza uma transação de update na base de dados
			 */
			public Dml update(final String query, final Object...params) throws SQLException{					
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
			 */
			public Dml delete(final String query, final Object...params) throws SQLException{		
				
				prepararStatement(query);
				preencherStatement(params);
				
				return this;				
			}
			
			/**
			 * @return True para executado com sucesso e False para Erro
			 * 
			 * Executa instrução de SQL
			 */
			public Boolean execute() {
				try {
					this.stm.execute();
					this.conn.commit();
					return true;
				} catch (Exception e) {
					new ExceptionDatabase(e);
					cancelarTransacao();
					return false;
				} finally {
					finalizar();					
				}
			}
			
			/**
			 * @param query
			 * 
			 * Prepara instrução de SQL para ser executada
			 */
			private void prepararStatement(final String query) {
				try {
					this.conn.setAutoCommit(false);
					this.stm = this.conn.prepareStatement(query);
				} catch (SQLException e) {
					new ExceptionDatabase("Problema ao preparar instrução", e);
				}								
			}
			
			/**
			 * @param params
			 * 
			 * Preenche um Objeto PreparedStatement com os parametros fornecidos
			 * pelo usuário
			 */
			private void preencherStatement(final Object...params) {
				try {
					this.stm = Utilidades.preencherCampos(this.stm, params);
				} catch (Exception e) {
					new Exception("Erro ao preencher campos", e);
				}
			}
			
			/**
			 * Realiza uma transação de Rollback
			 */
			private void cancelarTransacao(){
				try {
					this.conn.rollback();
				} catch (Exception e) {
					new ExceptionDatabase("Erro ao Executar Rollback", e);
				}
			}
			
			/**
			 * Finaliza todas as conexões com o banco de dados
			 */
			private void finalizar(){				
				try {
					stm.close();
					conn.close();
				} catch (Exception e) {
					new ExceptionDatabase("Erro ao fechar conexao", e);
				}
			}
		}
	}	
}
