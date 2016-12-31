package br.com.daniel.jdbc.exception;

public class ExceptionDatabase extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2159976118804974994L;


	public ExceptionDatabase(final Exception e) {
		super(e);
	}
	
	public ExceptionDatabase(final String message, final Exception e) {
		super(message, e);
	}
	
	public ExceptionDatabase(final String message) {
		super(message);
	}
	
}
