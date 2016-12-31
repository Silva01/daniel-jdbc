package br.com.daniel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.daniel.jdbc.conexao.Conexao;
import br.com.daniel.jdbc.exception.NaoConectadoDbException;
import br.com.daniel.jdbc.util.Utilidades;

public class Dao {

	
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
