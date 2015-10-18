package br.com.caelum.leilao.teste;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;;
public class OutrosWsTest {
	
	@Test
	public void temQueSetarCookie(){
		expect()
			.cookie("rest-assured","funciona")
		.get("/cookie/teste");
	}
	
	@Test
	public void deveConterHeader(){
		expect()
			.header("novo-header", "abc")
		.get("/cookie/teste");
	}
}
