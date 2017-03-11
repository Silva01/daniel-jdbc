package br.com.daniel.jdbc.exception;

/**
 * @author Daniel Silva
 * @version 1.0.0
 * 
 * Classe que trata a exception de banco de dados, caso ocorra algum erro
 *
 */
public class ExceptionDatabase extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2159976118804974994L;


	/**
	 * Recebe uma exception para tratamento
	 * 
	 * @param e
	 */
	public ExceptionDatabase(final Exception e) {
		super(e);
	}
	
	/**
	 * Recebe uma mensagem e uma exception para tratamento
	 * 
	 * @param message
	 * @param e
	 */
	public ExceptionDatabase(final String message, final Exception e) {
		super(message, e);
	}
	
	/**
	 * Recebe uma mensagem para tratamento da exception
	 * 
	 * @param message
	 */
	public ExceptionDatabase(final String message) {
		super(message);
	}
	
}
