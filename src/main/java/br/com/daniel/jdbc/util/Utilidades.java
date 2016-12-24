package br.com.daniel.jdbc.util;

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
}
