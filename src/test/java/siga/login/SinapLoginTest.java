package siga.login;

import org.junit.Ignore;
import siga.login.exceptions.BadCredentialsException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@Ignore
public class SinapLoginTest {
    public static String USERNAME = "tu_username";
    public static String PASSWORD = "tu_password";

    @Test
    public void can_login_with_sinap() throws BadCredentialsException, IOException {
        SinapLogin sinap = new SinapLogin(USERNAME, PASSWORD);

        Map<String, String> sessionCookies = sinap.doLogin();

        Document actasDeFinalPage = Jsoup.connect("http://siga.frba.utn.edu.ar/alu/acfin.do").cookies(sessionCookies).get();

        assertTrue(actasDeFinalPage.toString().contains("Actas de Finales"));
    }

    @Test(expected = BadCredentialsException.class)
    public void throws_when_bad_credentials() throws BadCredentialsException {
        new SinapLogin("bad", "credentials").doLogin();
    }
}
