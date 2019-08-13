package siga.domain.finales;

import fr.whimtrip.ext.jwhthtmltopojo.annotation.Selector;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.time.LocalDate.of;

public class Final {
    @Selector(value = "td:nth-child(1)") private String fecha;
    @Selector(value = "td:nth-child(2)") private String codigoMateria;
    @Selector(value = "td:nth-child(3)") private String nombreMateria;
    @Selector(value = "td:nth-child(4)") private String libro;
    @Selector(value = "td:nth-child(5)") private String folio;
    @Selector(value = "td:nth-child(6)") private String nota;

    public Final() {}
    public Final(String fecha, String codigoMateria, String nombreMateria, String libro, String folio, String nota) {
        this.fecha = fecha;
        this.codigoMateria = codigoMateria;
        this.nombreMateria = nombreMateria;
        this.libro = libro;
        this.folio = folio;
        this.nota = nota;
    }

    public LocalDate getFecha() {
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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

    public String getNotaRaw() {
        return nota;
    }

    public Optional<Double> getNota() {
        try {
            String cleanNota = nota.replace(",", ".");
            Double parsed = Double.valueOf(cleanNota);

            // ver https://www.utnianos.com.ar/foro/tema-muy-imp-nuevo-reglamento
            // gracias Caro, sos muy bonita
            Double converted
                    = getFecha().isBefore(of(2017, 3, 13))
                        ? 2*((parsed + 5)/3)
                        : parsed;

            return Optional.of(converted);
        } catch (Throwable e) {
            return Optional.empty();
        }
    }
}
