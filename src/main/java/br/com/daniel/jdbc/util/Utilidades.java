package br.com.daniel.jdbc.util;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import br.com.daniel.jdbc.mapeamento.Coluna;

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
	
	
	/**
	 * Converte um dado recebido da consulta no banco de dados e o separa suas 
	 * colunas em atributos de um objeto que representa a tabela do banco de dados, 
	 * para que os dados da tabela seja atribuidos a seus atributos, cada método Set 
	 * deverá possuir a anotação @Coluna e o Nome que representa sua coluna.
	 * 
	 * Exemplo:
	 * 	@Coluna('ID')
	 * 	public void setId(Integer id){
	 * 		this.id = id;
	 * 	}
	 * 
	 * 
	 * @param obj
	 * @param resultSet
	 * @return
	 */
	public static Object convertToObject(final Object obj, final ResultSet resultSet) {
		try {
			Class<?> classe = obj.getClass();			
			
			for (Method metodo : classe.getDeclaredMethods()) {				
				if (metodo.isAnnotationPresent(Coluna.class)) {
					metodo.invoke(obj, resultSet.getObject(metodo.getAnnotation(Coluna.class).value()));				
				}
			}		
			
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
