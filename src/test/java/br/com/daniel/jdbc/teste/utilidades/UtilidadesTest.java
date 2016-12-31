package br.com.daniel.jdbc.teste.utilidades;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.com.daniel.jdbc.util.Utilidades;

public class UtilidadesTest {

	@Test
	public void DeveraRetornarUmaString(){
		String dado = Utilidades.convertTo("Daniel");
		Assert.assertEquals(true, (dado instanceof String));
	}
	
	@Test
	public void DeveraRetornarUmInteger(){
		Integer dado = Utilidades.convertTo(1);
		Assert.assertEquals(true, (dado instanceof Integer));
	}
	
	@Test
	public void DeveraRetornarUmBoolean(){
		Boolean dado = Utilidades.convertTo(true);
		Assert.assertEquals(true, (dado instanceof Boolean));
	}
	
	@Test
	public void DeveraRetornarUmDate(){
		Date dado = Utilidades.convertTo(new Date());
		Assert.assertEquals(true, (dado instanceof Date));
	}
	
	@Test
	public void DeveraRetornarUmDouble(){
		Double dado = Utilidades.convertTo(1.0D);
		Assert.assertEquals(true, (dado instanceof Double));
	}
	
	@Test
	public void DeveraRetornarUmaFloat(){
		Float dado = Utilidades.convertTo(1.0f);
		Assert.assertEquals(true, (dado instanceof Float));
	}
}
