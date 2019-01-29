package siga;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import siga.login.SinapLogin;
import siga.login.exceptions.BadCredentialsException;
import siga.models.finales.Finales;

import java.io.IOException;
import java.util.Map;

public class Siga {
    public static String URL = "http://siga.frba.utn.edu.ar/";
    private final Map<String, String> session;

    public Siga(SinapLogin login) throws BadCredentialsException {
        this.session = login.doLogin();
    }

    public Finales actasDeFinal() throws IOException {
        Document actasPage = Jsoup.connect(URL + "/alu/acfin.do").cookies(session).get();

        return null;
    }
}
