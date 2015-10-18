package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.modelo.Usuario;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

public class UsuarioControllerTest {

	private Usuario mauricio;
	private Usuario guilherme;

	@Before
	public void setUp() {
		mauricio = new Usuario(1L, "Mauricio Aniche",
				"mauricio.aniche@caelum.com.br");
		guilherme = new Usuario(2l, "Guilherme Silveira",
				"guilherme.silveira@caelum.com.br");
	}

	@Test
	public void deveRetornaUsuario() {
		XmlPath xmlPath = given().header("Accept", "application/xml")
				.get("/usuarios").andReturn().xmlPath();

		// Usuario user1 = xmlPath.getObject("list.usuario[0]", Usuario.class);
		// Usuario user2 = xmlPath.getObject("list.usuario[1]", Usuario.class);

		List<Usuario> usuarios = xmlPath.getList("list.usuario", Usuario.class);

		assertEquals(mauricio, usuarios.get(0));
		assertEquals(guilherme, usuarios.get(1));
	}

	@Test
	public void retornaUsuarioPeloId() {
		JsonPath path = given()
						.header("Accept", "application/json")
						.parameter("usuario.id", 1)
						.get("usuarios/show").andReturn().jsonPath();
		Usuario usuario = path.getObject("usuario", Usuario.class);
		
		assertEquals(mauricio, usuario);
		
	}
	
	@Test
	public void criarUsuarioComPost(){
		XmlPath path = given()
						.header("Accept", "application/xml")
						.contentType("application/xml")
						.body(new Usuario("Zyon Dias", "zyon.dias@hotmail.com"))
					//testando que a resposta tem q ser 200
					.expect()
						.statusCode(200)
					.when()
						.post("/usuarios")
					.andReturn()
						.xmlPath();
		
		Usuario actual = path.getObject("usuario", Usuario.class);
		
		assertEquals("Zyon Dias", actual.getNome());
		assertEquals("zyon.dias@hotmail.com",actual.getEmail());
		
		//deletando usuario
		given()
			.contentType("application/xml")
			.body(actual)
		.expect()
			.statusCode(200)
		.when()
			.delete("/usuarios/deleta").andReturn().asString();
	}
}
