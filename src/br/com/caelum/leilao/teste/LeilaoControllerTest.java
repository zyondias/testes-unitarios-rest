package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.modelo.Leilao;
import br.com.caelum.leilao.modelo.Usuario;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

public class LeilaoControllerTest {

	private Usuario mauricio;
	private Leilao leilao;
	
	@Before
	public void setUp() {
		mauricio = new Usuario(1L, "Mauricio Aniche",
				"mauricio.aniche@caelum.com.br");
		leilao = new Leilao(1L, "Geladeira", 800D, mauricio, false);
	}
	
	@Test
	public void retornaLeilaoPeloId(){
		JsonPath path = given()
						.header("Accept", "application/json")
						.parameter("leilao.id", 1)
						.get("leiloes/show").andReturn().jsonPath();
		
		Leilao actual = path.getObject("leilao", Leilao.class);
		
		assertEquals(leilao, actual);
	}
	
	@Test
	public void retornaValorTotalDeLeiloes(){
		XmlPath path = given()
						.header("Accept","application/xml")
						.get("/leiloes/total").andReturn().xmlPath();
		Integer valorTotal = path.getInt("int");
		Integer esperado = new Integer(2);
		assertEquals(esperado, valorTotal);
	}
	
}
