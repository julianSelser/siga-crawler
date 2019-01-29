package siga.models.finales;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Finales {
    private List<Final> aprobados;
    private List<Final> reprobados;

    public Finales(List<Final> aprobados, List<Final> reprobados) {
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
