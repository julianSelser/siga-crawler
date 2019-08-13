package siga;

import org.junit.Test;
import siga.login.SinapLogin;
import siga.login.exceptions.BadCredentialsException;
import siga.domain.finales.ActaDeFinales;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static siga.login.SinapLoginTest.PASSWORD;
import static siga.login.SinapLoginTest.USERNAME;

public class SigaTest {
    @Test
    public void can_get_actas_de_final() throws IOException, BadCredentialsException {
        SinapLogin login = new SinapLogin(USERNAME, PASSWORD);
        Siga siga = new Siga(login);

        ActaDeFinales acta = siga.actasDeFinal();

        // confio en que si estas aca, aprobaste algun final
        // para que pase el test :D :D :D :D :D :D :D :D :D
        assertTrue(!acta.getAprobados().isEmpty());
    }
}
