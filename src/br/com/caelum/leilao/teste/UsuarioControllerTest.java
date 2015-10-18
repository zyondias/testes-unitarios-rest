package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.modelo.Usuario;

import com.jayway.restassured.path.xml.XmlPath;

public class UsuarioControllerTest {

	@Test
	public void deveRetornaUsuario() {
		Usuario esperado1 = new Usuario("Mauricio Aniche",
				"mauricio.aniche@caelum.com.br");
		esperado1.setId(1L);
		
		Usuario esperado2 = new Usuario("Guilherme Silveira",
				"guilherme.silveira@caelum.com.br");
		esperado2.setId(2L);

		XmlPath xmlPath = given()
							.header("Accept", "application/xml")
							.get("/usuarios")
							.andReturn()
							.xmlPath();
		
//		Usuario user1 = xmlPath.getObject("list.usuario[0]", Usuario.class);
//		Usuario user2 = xmlPath.getObject("list.usuario[1]", Usuario.class);

		List<Usuario> usuarios = xmlPath.getList("list.usuario", Usuario.class);
		
		assertEquals(esperado1, usuarios.get(0));
		assertEquals(esperado2, usuarios.get(1));
	}
}
