package br.com.daniel.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

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
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof String) {
					this.stm.setString(i, Utilidades.convertTo(params[i]));
				} else if (params[i] instanceof Integer) {
					this.stm.setInt(i, Utilidades.convertTo(params[i]));
				} else if (params[i] instanceof Boolean) {
					this.stm.setBoolean(i, Utilidades.convertTo(params[i]));
				} else if (params[i] instanceof Date) {
					this.stm.setDate(i, Utilidades.convertTo(params[i]));
				} else if (params[i] instanceof Double) {
					this.stm.setDouble(i, Utilidades.convertTo(params[i]));
				} else if (params[i] instanceof Float) {
					this.stm.setFloat(i, Utilidades.convertTo(params[i]));
				} else {
					this.stm.setObject(i, params[i]);
				}				
			}
			
			ResultSet resul = this.stm.executeQuery();
			
			return resul;
			 
		} catch (NaoConectadoDbException e) {
			new NaoConectadoDbException(e);
			return null;
		} catch (Exception e) {
			new Exception(e);
			return null;
		}
	}
}
