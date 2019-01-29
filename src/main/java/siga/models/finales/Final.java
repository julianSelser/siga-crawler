package siga.models.finales;

import java.time.LocalDate;

public class Final {
    private String fecha;
    private String codigoMateria;
    private String nombreMateria;
    private String libro;
    private String folio;
    private String nota;

    public Final(String fecha, String codigoMateria, String nombreMateria, String libro, String folio, String nota) {
        this.fecha = fecha;
        this.codigoMateria = codigoMateria;
        this.nombreMateria = nombreMateria;
        this.libro = libro;
        this.folio = folio;
        this.nota = nota;
    }

    public LocalDate getFecha() {
        return LocalDate.parse(fecha);
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
