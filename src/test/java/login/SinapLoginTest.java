package login;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SinapLoginTest {
    private static String USERNAME = "tu_usuario";
    private static String PASSWORD = "tu_password";

    @Test
    public void can_login_with_sinap() throws IOException, InterruptedException {
        SinapLogin sinap = new SinapLogin(USERNAME, PASSWORD);

        Map<String, String> sessionCookies = sinap.doLogin();

        Document actasDeFinalPage = Jsoup.connect("http://siga.frba.utn.edu.ar/alu/acfin.do").cookies(sessionCookies).get();

        assertTrue(actasDeFinalPage.toString().contains("Actas de Finales"));
    }
}
