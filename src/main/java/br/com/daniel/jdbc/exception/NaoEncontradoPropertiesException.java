package br.com.daniel.jdbc.exception;

public class NaoEncontradoPropertiesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1480590513624460327L;

	public NaoEncontradoPropertiesException() {
		super();
	}	
	
	public NaoEncontradoPropertiesException(final NaoEncontradoPropertiesException e) {
		super(e);
	}
	
	public NaoEncontradoPropertiesException(final NaoEncontradoPropertiesException e, final String message) {
		super(message, e);
	}
	
	public NaoEncontradoPropertiesException(final String message) {
		super(message);
	}
}
