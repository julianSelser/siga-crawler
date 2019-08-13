package siga.domain.finales;

import fr.whimtrip.ext.jwhthtmltopojo.annotation.Selector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Stream.concat;

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

    public Double getPromedioSinReprobados() {
        Stream<Double> notas = notasOf(aprobados);

        Double notasSum = notas.reduce((n1, n2) -> n1 + n2).get();

        return notasSum / aprobados.size();
    }

    public Double getPromedio() {
        Stream<Double> notas = concat(notasOf(aprobados), notasOf(reprobados));
        Double notasSum = notas.reduce((n1, n2) -> n1 + n2).get();
        int notasNum = aprobados.size() + reprobados.size();

        return notasSum / notasNum;
    }

    private Stream<Double> notasOf(List<Final> finales) {
        //getNota() -> Optional<Double>
        //a veces viene frula en la nota
        return finales.stream()
                .map(Final::getNota)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
