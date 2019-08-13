package siga.domain.finales;

import fr.whimtrip.ext.jwhthtmltopojo.annotation.Selector;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ActaDeFinales {
    @Selector(value = "table:nth-child(1) tr:not(:first-child)") private List<Final> aprobados;
    @Selector(value = "table:nth-child(2) tr:not(:first-child)") private List<Final> reprobados;

    public ActaDeFinales() {} //empty constructor needed, dont remove
    public ActaDeFinales(List<Final> aprobados, List<Final> reprobados) {
        this.aprobados = aprobados;
        this.reprobados = reprobados;
    }

    public List<Final> getAprobados() {
        return unmodifiableList(aprobados);
    }

    public List<Final> getReprobados() {
        return unmodifiableList(reprobados);
    }
}
