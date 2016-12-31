package br.com.daniel.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.daniel.jdbc.conexao.Conexao;
import br.com.daniel.jdbc.exception.NaoConectadoDbException;
import br.com.daniel.jdbc.util.Utilidades;

public class Dao {

	private Conexao conn;
	private PreparedStatement stm;
	
	public Dao(final Conexao conn) {
		this.conn = conn;
	}
	
	public ResultSet select(final String query){
		try {
			this.stm = (PreparedStatement) conn.conectarMysql().prepareStatement(query);
			ResultSet result = stm.executeQuery();
			this.stm.close();
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
			this.stm = (PreparedStatement) conn.conectarMysql().prepareStatement(query);
			this.stm = Utilidades.preencherCampos(this.stm, params);
			
			ResultSet resul = this.stm.executeQuery();
			this.stm.close();
			this.conn.conectarMysql().close();
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
			this.stm = conn.conectarMysql().prepareStatement(query);
			this.stm = Utilidades.preencherCampos(stm, params);
			boolean result = this.stm.execute();
			this.stm.close();
			this.conn.conectarMysql().close();
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
