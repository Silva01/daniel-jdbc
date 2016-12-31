package br.com.daniel.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.daniel.jdbc.conexao.Conexao;
import br.com.daniel.jdbc.exception.NaoConectadoDbException;
import br.com.daniel.jdbc.util.Utilidades;

public class Dao {

	
	public ResultSet select(final String query){
		try {
			Conexao conn = new Conexao();
			PreparedStatement stm = (PreparedStatement) conn.conectarMysql().prepareStatement(query);
			ResultSet result = stm.executeQuery();
			stm.close();
			conn.conectarMysql().close();
			return result;
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			return null;
		} catch (Exception e) {
			new Exception(e);
			return null;
		}
	}
	
	public ResultSet select(final String query, final Object...params){
		try {
			Conexao conn = new Conexao();
			PreparedStatement stm = (PreparedStatement) conn.conectarMysql().prepareStatement(query);
			stm = Utilidades.preencherCampos(stm, params);
			
			ResultSet resul = stm.executeQuery();
			stm.close();
			conn.conectarMysql().close();
			return resul;
			 
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			return null;
		} catch (Exception e) {
			new Exception(e);
			return null;
		}
	}
	
	public boolean insert(final String query, final Object...params){
		try {
			Conexao conn = new Conexao();
			PreparedStatement stm = conn.conectarMysql().prepareStatement(query);
			stm = Utilidades.preencherCampos(stm, params);
			boolean result = stm.execute();
			stm.close();
			conn.conectarMysql().close();
			return result;
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			return false;
		} catch (Exception e) {
			new Exception(e);
			return false;
		}
	}
}
