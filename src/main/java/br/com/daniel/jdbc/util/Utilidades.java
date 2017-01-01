package br.com.daniel.jdbc.util;

import java.sql.PreparedStatement;
import java.util.Date;

/**
 * @author Daniel
 * @version 1.0.0
 * 
 * Classe com métodos úteis para desenvolvimento
 *
 */
public class Utilidades {

	private Utilidades() {}
	
	/**
	 * Converte um tipo Objeto para um tipo mais especifico
	 * 
	 * @param obj
	 * @return Tipo especifico
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertTo(final Object obj){
		if (obj instanceof String) {
			return (T) String.valueOf(obj);
		} else if (obj instanceof Integer) {
			return ((T)((Integer) obj));
		} else if (obj instanceof Boolean) {
			return (T) Boolean.valueOf(String.valueOf(obj));
		} else if (obj instanceof Date) {
			return ((T)((Date) obj)); 
		} else if (obj instanceof Double) {
			return (T) Double.valueOf(String.valueOf(obj));
		} else if (obj instanceof Float) {
			return (T) Float.valueOf(String.valueOf(obj));
		}
		return null;
	}
	
	/**
	 * Preenche o PreparedStatemt por tipo
	 * 
	 * @param stm
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static PreparedStatement preencherCampos(final PreparedStatement stm, final Object...params) throws Exception{
		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof String) {
				stm.setString(i + 1, Utilidades.convertTo(params[i]));
			} else if (params[i] instanceof Integer) {
				stm.setInt(i + 1, Utilidades.convertTo(params[i]));
			} else if (params[i] instanceof Boolean) {
				stm.setBoolean(i + 1, Utilidades.convertTo(params[i]));
			} else if (params[i] instanceof Date) {
				stm.setDate(i + 1, Utilidades.convertTo(params[i]));
			} else if (params[i] instanceof Double) {
				stm.setDouble(i + 1, Utilidades.convertTo(params[i]));
			} else if (params[i] instanceof Float) {
				stm.setFloat(i + 1, Utilidades.convertTo(params[i]));
			} else {
				stm.setObject(i + 1, params[i]);
			}
		}
		
		return stm;
	}
}
