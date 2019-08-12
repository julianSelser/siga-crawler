package siga;


import fr.whimtrip.ext.jwhthtmltopojo.HtmlToPojoEngine;
import org.junit.Test;
import siga.models.finales.ActaDeFinales;
import siga.models.finales.Final;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ParsingTest {

    private HtmlToPojoEngine parser = HtmlToPojoEngine.create();

    @Test
    public void can_parse_final_from_table_row() {
        String finalHtml =
                "<table>" +
                "    <tr>" +
                "        <td>21/12/2018</td>" +
                "        <td>082037</td>" +
                "        <td>Proyecto Final</td>" +
                "        <td>K0112 </td>" +
                "        <td>101</td>" +
                "        <td>9 </td>" +
                "    </tr>" +
                "</table>";

        Final aFinal = parser.adapter(Final.class).fromHtml(finalHtml);

        assertEquals(aFinal.getCodigoMateria(), "082037");
        assertEquals(aFinal.getFecha(), "21/12/2018");
        assertEquals(aFinal.getFolio(), "101");
        assertEquals(aFinal.getLibro(), "K0112");
        assertEquals(aFinal.getNombreMateria(), "Proyecto Final");
        assertEquals(aFinal.getNota(), "9");
    }

    @Test
    public void ignores_table_header_when_parsing_actas() {
        String actaTableWithUselessHeader =
                "<table>" +
                "  <tr>" +
                "   <th>Fecha</th>" +
                "   <th>Materia</th>" +
                "   <th>Libro</th>" +
                "   <th>Folio</th>" +
                "   <th>Nota</th>" +
                "  </tr>" +
                "  <tr>" +
                "    <td>21/12/2018</td>" +
                "    <td>082037</td>" +
                "    <td>Proyecto Final</td>" +
                "    <td>K0112 </td>" +
                "    <td>101</td>" +
                "    <td>9 </td>" +
                "  </tr>" +
                "</table>";

        ActaDeFinales anActa = parser.adapter(ActaDeFinales.class).fromHtml(actaTableWithUselessHeader);

        assertTrue(anActa.getAprobados().size() == 1);

        Final theOnlyValidFinal = anActa.getAprobados().get(0);

        assertEquals(theOnlyValidFinal.getCodigoMateria(), "082037");
        assertEquals(theOnlyValidFinal.getFecha(), "21/12/2018");
        assertEquals(theOnlyValidFinal.getFolio(), "101");
        assertEquals(theOnlyValidFinal.getLibro(), "K0112");
        assertEquals(theOnlyValidFinal.getNombreMateria(), "Proyecto Final");
        assertEquals(theOnlyValidFinal.getNota(), "9");
    }
}
