package it.amattioli.dominate.util;

import it.amattioli.dominate.properties.Properties;

import java.util.Comparator;

/**
 * Implementazione di un Comparator che confronta due JavaBean in base
 * al contenuto di una loro propriet&agrave;. Ovviamente la classe della
 * propriet&agrave; indicata deve implementare a sua volta l'interfaccia
 * {@link Comparable}
 *
 * @author a.mattioli
 *
 * @param <T>
 */
public class GenericComparator<T> implements Comparator<T> {
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private String property;
    private boolean nullFirst = false;

    /**
     * Costruisce un GenericComparator fornendo la property il cui contenuto
     * deve essere confrontato.
     *
     * @param property la property il cui contenuto deve essere confrontato
     */
    public GenericComparator(final String property) {
        this.property = property;
    }

    /**
     * Costruisce un GenericComparator fornendo la property il cui contenuto
     * deve essere confrontato e indicando come trattare eventuali valori
     * nulli di questa property.
     *
     * @param property la property il cui contenuto deve essere confrontato
     * @param nullFirst se true i valori nulli della property saranno trattati
     *        come maggiori di tutti gli altri, se false saranno trattati come
     *        minori di tutti gli altri
     */
    public GenericComparator(final String property, final boolean nullFirst) {
        this(property);
        this.nullFirst = nullFirst;
    }

    private Comparable<?> getProperty(final T o) {
        return (Comparable<?>) Properties.get(o, property);
    }

    @SuppressWarnings("unchecked")
    public int compare(final T o1, final T o2) {
        Comparable p1 = getProperty(o1);
        Comparable p2 = getProperty(o2);
        if (p1 == null) {
            return nullFirst ? LESS_THAN : GREATER_THAN;
        }
        if (p2 == null) {
            return nullFirst ? GREATER_THAN : LESS_THAN;
        }
        return p1.compareTo(p2);
    }

}
