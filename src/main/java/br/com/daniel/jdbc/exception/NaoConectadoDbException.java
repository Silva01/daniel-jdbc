package br.com.daniel.jdbc.exception;

import java.sql.SQLException;

/**
 * @author Daniel
 * @version 1.0.0
 * 
 * Classe representa uma exceção caso ocorra alguma falha na conexão com o banco de dados
 * 
 */
public class NaoConectadoDbException extends SQLException {

	/**
	 * Serial Version 
	 */
	private static final long serialVersionUID = 8312698609588431052L;

	/**
	 * @param Exception obtida
	 * 
	 * Faz o tratamento da exceção, recebe como parametro apenas a exceção
	 * Obtida
	 * 
	 */
	public NaoConectadoDbException(final NaoConectadoDbException exception) {
		super(exception);
	}
	
	/**
	 * @param Exception Obtida
	 * @param Mensagem de tratamento
	 * 
	 * Faz o tratamento da exceção, recebe a exceção obtida mais uma mensagem de tratamento
	 * definida pelo desenvolvedor
	 * 
	 */
	public NaoConectadoDbException(final NaoConectadoDbException exception, final String message) {
		super(message, exception);
	}
	
}
