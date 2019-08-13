package siga.domain.finales;

import fr.whimtrip.ext.jwhthtmltopojo.annotation.Selector;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class ActaDeFinales {
    @Selector(value = "table tr:not(:first-child)") private List<Final> finales;

    public ActaDeFinales() {} //empty constructor needed, dont remove
    public ActaDeFinales(List<Final> finales) {
        this.finales = finales;
    }

    public List<Final> getFinales() {
        return unmodifiableList(finales);
    }

    public Double getPromedioSinReprobados() {
        return getPromedioFiltering(nota -> nota >= 6);
    }

    public Double getPromedio() {
        return getPromedioFiltering(n -> true);
    }

    private Double getPromedioFiltering(Predicate<Double> someFilter) {
        //getNota() -> Optional<Double>
        //a veces viene frula en la nota
        List<Double> notas = finales.stream()
                .map(Final::getNota)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(someFilter)
                .collect(toList());

        Double notasSum = notas.stream()
                .reduce((n1, n2) -> n1 + n2)
                .get();

        return notasSum / notas.size();
    }

}
