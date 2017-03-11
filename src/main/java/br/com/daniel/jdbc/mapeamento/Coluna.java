package br.com.daniel.jdbc.mapeamento;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @author Daniel Silva
 * @version 1.0.0
 * 
 * Anotação responsável por conter o nome da coluna do banco de dados. 
 * 
 * Exemplo:
 *  @Coluna("ID")
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Coluna {
	String value();
}
