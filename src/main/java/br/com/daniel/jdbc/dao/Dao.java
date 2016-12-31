package br.com.daniel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.daniel.jdbc.conexao.Conexao;
import br.com.daniel.jdbc.exception.NaoConectadoDbException;
import br.com.daniel.jdbc.util.Utilidades;

/**
 * @author Daniel Silva
 * @version 1.0.0
 * 
 * Dao básico no qual realiza as principais operações de transação 
 * no banco de dados
 *
 */
public class Dao {

	
	/**
	 * @param Instrução de banco de dados
	 * @return <b>Dados da base de dados</b>
	 * @throws SQLException
	 * 
	 * Realiza uma consulta no banco de dados e retorna todos os 
	 * registro dessa base
	 */
	public ResultSet select(final String query) throws SQLException{
		Connection conn = new Conexao().conectarMysql();
		PreparedStatement stm = null;
		try {			
			stm = (PreparedStatement) conn.prepareStatement(query);
			ResultSet result = stm.executeQuery();
			return result;
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			return null;
		} catch (Exception e) {
			new Exception(e);
			return null;
		} finally {
			stm.close();
			conn.close();
		}
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
	public ResultSet select(final String query, final Object...params) throws SQLException{
		Connection conn = new Conexao().conectarMysql();
		PreparedStatement stm = null;
		try {
			stm = (PreparedStatement) conn.prepareStatement(query);
			stm = Utilidades.preencherCampos(stm, params);
			
			ResultSet resul = stm.executeQuery();
			return resul;
			 
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			return null;
		} catch (Exception e) {
			new Exception(e);
			return null;
		} finally {
			stm.close();
			conn.close();
		}
	}
	
	/**
	 * @param Instrução de banco de dados
	 * @param parametros
	 * @return True para inserido ou false para não inserido
	 * @throws SQLException
	 * 
	 * Realiza um insert a base de dados
	 */
	public boolean insert(final String query, final Object...params) throws SQLException{
		Connection conn = new Conexao().conectarMysql();
		PreparedStatement stm = null;
		try {			
			conn.setAutoCommit(false);
			stm = conn.prepareStatement(query);
			stm = Utilidades.preencherCampos(stm, params);
			boolean result = stm.execute();
			conn.commit();
			return result;			
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);		
			conn.rollback();
			return false;
		} catch (Exception e) {
			new Exception(e);		
			conn.rollback();
			return false;
		} finally {
			stm.close();
			conn.close();
		}
	}
	
	/**
	 * @param Instrução de banco de dados
	 * @param parametros
	 * @return True para atualizado ou false para não atualizado
	 * @throws SQLException
	 * 
	 * Realiza uma transação de update na base de dados
	 */
	public boolean update(final String query, final Object...params) throws SQLException{
		Connection conn = new Conexao().conectarMysql();
		PreparedStatement stm = null;
		try {
			conn.setAutoCommit(false);
			stm = conn.prepareStatement(query);
			stm = Utilidades.preencherCampos(stm, params);
			boolean result = stm.execute();
			conn.commit();
			return result;
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			conn.rollback();
			return false;
		} catch (Exception e) {
			new Exception(e);
			conn.rollback();
			return false;
		} finally {
			stm.close();
			conn.close();
		}
	}
	
	/**
	 * @param Instrução de banco de dados
	 * @param parametros
	 * @return True para excluido ou false para não excluido
	 * @throws SQLException
	 * 
	 * Realiza a exclusão de um dados na base de dados
	 */
	public boolean delete(final String query, final Object...params) throws SQLException{
		Connection conn = new Conexao().conectarMysql();
		PreparedStatement stm = null;
		try {
			conn.setAutoCommit(false);
			stm = conn.prepareStatement(query);
			stm = Utilidades.preencherCampos(stm, params);
			boolean response = stm.execute();
			conn.commit();
			return response;
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			conn.rollback();
			return false;
		} catch (Exception e) {
			new Exception(e);
			conn.rollback();
			return false;
		} finally {
			stm.close();
			conn.close();
		}
	}
}
