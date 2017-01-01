package br.com.daniel.jdbc.teste.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import br.com.daniel.jdbc.conexao.Conexao;
import br.com.daniel.jdbc.dao.Dao;
import br.com.daniel.jdbc.exception.ExceptionDatabase;

public class DaoTest {

	@Test
	public void deveraRealizarUmSelect() throws ExceptionDatabase, Exception{
		Dao dao = new Dao();
		ResultSet result = dao.sid()
				.dql(new Conexao().conectarMysql())
				.select("SELECT idade, nome FROM teste.teste")
				.execute();
		
		String nome = null;
		Integer idade = null;
		
		while (result.next()) {
			nome = result.getString("nome");
			idade = result.getInt("idade");
			break;			
		}
		
		Assert.assertEquals("Daniel", nome);
		Assert.assertEquals(Integer.valueOf(27), idade);
	}
	
	@Test
	public void deveraRealizarUmSelectComParametros() throws ExceptionDatabase, Exception{
		Dao dao = new Dao();
		ResultSet result = dao.sid()
				.dql(new Conexao().conectarMysql())
				.select("SELECT idade, nome FROM teste.teste WHERE idade = ? AND nome = ?", 27, "Daniel")
				.execute();
		
		String nome = null;
		Integer idade = null;
		
		while (result.next()) {
			nome = result.getString("nome");
			idade = result.getInt("idade");
			break;			
		}
		
		Assert.assertEquals("Daniel", nome);
		Assert.assertEquals(Integer.valueOf(27), idade);
	}
	
	@Test
	public void deveraRealizarUmInsertNaBaseDeDados() throws ExceptionDatabase, Exception{
 		Dao dao = new Dao();
 		
 		Boolean result = dao.sid().dml(new Conexao().conectarMysql())
 				.insert("INSERT INTO `teste`.`teste` (`nome`, `idade`) VALUES (?, ?);", "Teste", 10)
 				.execute();
 		
 		Assert.assertEquals(true, result);
 		
 		ResultSet resultado = dao.sid().dql(new Conexao().conectarMysql())
 				.select("SELECT nome, idade FROM teste.teste WHERE nome = ? AND idade = ?", "Teste", 10)
 				.execute();
 		
 		String nome = null;
 		Integer idade = null;
 		
 		while (resultado.next()) {
 			nome = resultado.getString("nome");
 			idade = resultado.getInt("idade");			
 		}
 		
 		Assert.assertEquals("Teste", nome);
 		Assert.assertEquals(Integer.valueOf(10), idade);
	}

	
	@Test
	public void deveraAtualizarUmRegistroNaBaseDeDados() throws ExceptionDatabase, Exception{
		Dao dao = new Dao();
		Integer id = null;
		String nomeSemUpdate = null;
		Integer idadeSemUpdate = null;
		
		String nomeAtualizado = null;
		Integer idadeAtualizada = null;
		
		// Busca usuário a ser alterado na Base de dados
		
		ResultSet result1 = dao.sid().dql(new Conexao().conectarMysql())
				.select("SELECT * FROM teste.teste WHERE nome = ? AND idade = ?", "Teste", 10)
				.execute();
		
		while (result1.next()) {
			nomeSemUpdate = result1.getString("nome");
			idadeSemUpdate = result1.getInt("idade");
			id = result1.getInt("id");
		}
		
		Assert.assertEquals("Teste", nomeSemUpdate);
		Assert.assertEquals(Integer.valueOf(10), idadeSemUpdate);
		result1.close();
		
		// Atualiza dados
		
		Boolean response = dao.sid().dml(new Conexao().conectarMysql())
				.update("UPDATE `teste`.`teste` SET `nome`= ?, `idade`= ? WHERE `id`= ? ","Teste2", 22, id)
				.execute();
		
		Assert.assertEquals(true, response);
		
		
		// Verifica se os dados estão atualizados
		
		result1 = dao.sid().dql(new Conexao().conectarMysql())
				.select("SELECT * FROM teste.teste WHERE nome = ? AND idade = ?", "Teste2", 22)
				.execute();
		
		while (result1.next()) {
			nomeAtualizado = result1.getString("nome");
			idadeAtualizada = result1.getInt("idade");			
		}
		
		Assert.assertNotEquals(nomeSemUpdate, nomeAtualizado);
		Assert.assertNotEquals(idadeSemUpdate, idadeAtualizada);
		
		Assert.assertEquals("Teste2", nomeAtualizado);
		Assert.assertEquals(Integer.valueOf(22), idadeAtualizada);
		
	}
	
	@Test
	public void deveraExcluirUmRegistro() throws ExceptionDatabase, Exception {
		Dao dao = new Dao();
		int quantidadeDeRegistros = 0;		
		
		// Busca um registro para ver se ele existe, caso ele exista o retorno será 1 caso contrário 0
		
		ResultSet result = dao.sid().dql(new Conexao().conectarMysql())
				.select("SELECT count(id) AS quant FROM teste.teste WHERE nome = ? AND idade = ?", "Teste2", 22)
				.execute();
		
		while (result.next()) {
			quantidadeDeRegistros = result.getInt("quant");			
		}
		
		result.close();
		Assert.assertEquals(1, quantidadeDeRegistros);
		
		// Exclui o registro 
		
		Boolean response = dao.sid().dml(new Conexao().conectarMysql())
				.delete("DELETE FROM `teste`.`teste` WHERE nome = ? AND idade = ?", "Teste2", 22)
				.execute();
		
		Assert.assertEquals(true, response);
		
		
		/* Verifica se o dado realmente foi excluido, se for excluido retorna 0 caso contrário
		* retorna 1
		*/
		
		result = dao.sid().dql(new Conexao().conectarMysql())
				.select("SELECT count(id) AS quant FROM teste.teste WHERE nome = ? AND idade = ?", "Teste2", 22)
				.execute();
		
		while (result.next()) {
			quantidadeDeRegistros = result.getInt("quant");			
		}
		
		result.close();
		Assert.assertEquals(0, quantidadeDeRegistros);
		
	}
}
