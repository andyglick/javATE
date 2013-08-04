package it.amattioli.encapsulate.range;

import java.util.Set;

/**
 * Un range rappresenta un intervallo di valori. Un range e' parametrizzato
 * tramite la classe dei valori dell'intervallo stesso. Questa classe non può
 * essere una classe qualsiasi perche' e' fondamentale che i suoi elementi siano
 * confrontabili tra di loro, dunque deve implementare l'interfaccia Comparable.
 * L'intervallo puo' essere limitato o illimitato sia superiormente che
 * inferiormente. Non e' definito se gli estremi dell'intervallo debbano far
 * parte o meno dell'intervallo. Questa scelta e' lasciata alle singole
 * implementazioni.
 */
public interface Range<T extends Comparable<? super T>> {

	/**
	 * Ritorna l'estremo inferiore dell'intervallo. L'operazione puo' essere
	 * effettuata solamente per intervalli che possiedono un estremo inferiore,
	 * in caso contrario viene sollevata un'eccezione.
	 * 
	 * @return l'estremo inferiore dell'intervallo.
	 * @throws UnboundedRangeException
	 *             se l'intervallo non e' limitato inferiormente.
	 */
	public T getLow();

	/**
	 * Ritorna l'estremo inferiore dell'intervallo. L'operazione puo' essere
	 * effettuata solamente per intervalli che possiedono un estremo superiore,
	 * in caso contrario viene sollevata un'eccezione.
	 * 
	 * @return l'estremo inferiore dell'intervallo.
	 * @throws UnboundedRangeException
	 *             se l'intervallo non e' limitato superiormente.
	 */
	public T getHigh();

	/**
	 * Indica se l'intervallo e' limitato inferiormente.
	 * 
	 * @return true se l'intervallo e' limitato inferiormente, altrimenti false.
	 */
	public boolean isLowBounded();

	/**
	 * Indica se l'intervallo e' limitato superiormente.
	 * 
	 * @return true se l'intervallo e' limitato superiormente, altrimenti false.
	 */
	public boolean isHighBounded();

	/**
	 * Controlla se un valore e' compreso nell'intervallo. E' lasciata alle
	 * singole implementazioni la scelta di includere o meno gli estremi
	 * dell'intervallo e dunque il valore effettivamente ritornato da questo
	 * metodo se viene passato come parametro un valore corrispondente ad uno
	 * dei due estremi.
	 * 
	 * @param test
	 *            il valore da testare se e' incluso nell'intervallo
	 * @return true se il valore passato come parametro e' compreso in questo
	 *         intervallo, altrimenti false
	 */
	public boolean includes(T test);

	/**
	 * Controlla se un altro range e' completamente contenuto in questo o,
	 * equivalentemente, se l'intervallo passato come parametro e' un
	 * sottointervallo di questo.
	 * 
	 * @param r
	 *            il range da controllare se e' incluso in questo
	 * @return true se il valore passato come parametro e' compreso in questo,
	 *         altrimenti false
	 */
	public boolean contains(Range<? extends T> r);

	/**
	 * Controlla se un altro range si sovrappone a questo.
	 */
	public boolean overlaps(Range<? extends T> r);

	/**
	 * Costruisce un nuovo range corrispondente all'unione di queso con quello
	 * passato come parametro. Questa operazione e' definita solamente se i due
	 * range si sovrappongono, altrimenti verra' sollevata un'eccezione.
	 */
	public Range<T> mergeWith(Range<T> r);

	/**
	 * Controlla se un altro range e' adiacente a questo.
	 */
	public boolean abutOn(Range<? extends T> r);

	/**
	 * Costruisce un nuovo range che rappresenta lo spazio vuoto tra questo e
	 * quello passato come parametro. Questa operazione e' definita solamente se
	 * i due range non si sovrappongono, altrimenti verra' sollevata
	 * un'eccezione.
	 */
	public Range<T> gap(Range<T> r);

	/**
	 * Controlla se il range passato come parametro ha lo stesso estremo
	 * inferiore di questo. Il risultato e' definito anche per intervalli non
	 * limitati inferiormente.
	 * 
	 * @param r
	 *            il range da controllare
	 * @return true se il range passato come parametro ha lo stesso estremo
	 *         inferiore di questo oppure se entrambi non sono limitati
	 *         inferiormente, altrimenti false
	 */
	public boolean hasSameLow(Range<? extends T> r);

	/**
	 * Controlla se il range passato come parametro ha lo stesso estremo
	 * superiore di questo. Il risultato e' definito anche per intervalli non
	 * limitati superiormente.
	 * 
	 * @param r
	 *            il range da controllare
	 * @return true se il range passato come parametro ha lo stesso estremo
	 *         superiore di questo oppure se entrambi non sono limitati
	 *         superiormente, altrimenti false
	 */
	public boolean hasSameHigh(Range<? extends T> r);
	
	public Range<T> intersect(Range<T> r);
	
	public Set<Range<T>> minus(Range<T> r);

}
