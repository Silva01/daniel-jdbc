package br.com.daniel.jdbc.exception;

/**
 * @author Daniel Silva
 * @version 1.0.0
 * 
 * Trata as exception causada por erros de leitura de arquivos de propriedades
 *
 */
public class NaoEncontradoPropertiesException extends Exception {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -1480590513624460327L;

	/**
	 * Trata a exception utilizando o contrutor da superclasse Exception
	 */
	public NaoEncontradoPropertiesException() {
		super();
	}	
	
	/**
	 * Recebe uma exception para tratamento
	 * @param e
	 */
	public NaoEncontradoPropertiesException(final NaoEncontradoPropertiesException e) {
		super(e);
	}
	
	/**
	 * Recebe uma mensagem e uma exception para tratamento
	 * 
	 * @param e
	 * @param message
	 */
	public NaoEncontradoPropertiesException(final NaoEncontradoPropertiesException e, final String message) {
		super(message, e);
	}
	
	/**
	 * Recebe uma mensagem para tratamento da exception
	 * 
	 * @param message
	 */
	public NaoEncontradoPropertiesException(final String message) {
		super(message);
	}
}
