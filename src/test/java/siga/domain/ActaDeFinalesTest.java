package siga.domain;

import org.junit.Test;
import siga.domain.finales.ActaDeFinales;
import siga.domain.finales.Final;

import java.io.Console;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static junit.framework.TestCase.assertEquals;

public class ActaDeFinalesTest {
    @Test
    public void can_get_promedio_without_reprobados() {
        List<Final> aprobados = asList(
                new Final("21/11/2018", "", "", "", "", "6"));
        List<Final> reprobados = asList(
                new Final("22/11/2018", "", "", "", "", "4"));

        //no se cuenta reprobados, promedio 6
        ActaDeFinales acta = new ActaDeFinales(aprobados, reprobados);

        assertEquals(6D, acta.getPromedioSinReprobados());
    }

    @Test
    public void can_get_promedio_with_reprobados() {
        List<Final> aprobados = asList(
                new Final("21/11/2018", "", "", "", "", "6"));
        List<Final> reprobados = asList(
                new Final("22/11/2018", "", "", "", "", "4"));

        // (4 + 6)/2 = 5
        ActaDeFinales acta = new ActaDeFinales(aprobados, reprobados);

        assertEquals(5D, acta.getPromedio());
    }

    @Test
    public void can_get_promedio_considering_new_scale_change() {
        //despues del 2017 la nota de aprobacion es 7
        //hay que convertir las notas previas a ese año
        //usando esto: https://www.utnianos.com.ar/foro/tema-muy-imp-nuevo-reglamento
        List<Final> finals = asList(
                new Final("21/11/2018", "", "", "", "", "6"),
                new Final("21/11/2015", "", "", "", "", "4")
        );

        // (6 + 6(nueva escala))/2 = 6
        ActaDeFinales acta = new ActaDeFinales(finals, emptyList());

        assertEquals(6D, acta.getPromedioSinReprobados());
    }
}
