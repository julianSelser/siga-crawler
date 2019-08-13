package siga;

import fr.whimtrip.ext.jwhthtmltopojo.HtmlToPojoEngine;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import siga.login.SinapLogin;
import siga.login.exceptions.BadCredentialsException;
import siga.domain.finales.ActaDeFinales;

import java.io.IOException;
import java.util.Map;

public class Siga {
    public static String URL = "http://siga.frba.utn.edu.ar/";
    private final Map<String, String> session;

    public Siga(SinapLogin login) throws BadCredentialsException {
        this.session = login.doLogin();
    }

    public ActaDeFinales actasDeFinal() throws IOException {
        Document actasPage = Jsoup.connect(URL + "/alu/acfin.do").cookies(session).get();
        HtmlToPojoEngine parser = HtmlToPojoEngine.create();

        return parser.adapter(ActaDeFinales.class).loadFromNode(actasPage);
    }
}
