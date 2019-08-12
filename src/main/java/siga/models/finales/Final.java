package siga.models.finales;

import fr.whimtrip.ext.jwhthtmltopojo.annotation.Selector;

import java.time.LocalDate;

public class Final {
    @Selector(value = "td:nth-child(1)") private String fecha;
    @Selector(value = "td:nth-child(2)") private String codigoMateria;
    @Selector(value = "td:nth-child(3)") private String nombreMateria;
    @Selector(value = "td:nth-child(4)") private String libro;
    @Selector(value = "td:nth-child(5)") private String folio;
    @Selector(value = "td:nth-child(6)") private String nota;

    public String getFecha() {
        return fecha;// return LocalDate.parse(fecha);
    }

    public String getCodigoMateria() {
        return codigoMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public String getLibro() {
        return libro;
    }

    public String getFolio() {
        return folio;
    }

    public String getNota() {
        return nota;
    }
}
