package siga.models.finales;

import fr.whimtrip.ext.jwhthtmltopojo.annotation.Selector;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ActaDeFinales {
    @Selector(value = "table:nth-child(1) tr:not(:first-child)") private List<Final> aprobados;
    @Selector(value = "table:nth-child(2) tr:not(:first-child)") private List<Final> reprobados;

    public List<Final> getAprobados() {
        return unmodifiableList(aprobados);
    }

    public List<Final> getReprobados() {
        return unmodifiableList(reprobados);
    }
}
