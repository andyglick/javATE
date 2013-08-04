package it.amattioli.encapsulate.range;

/**
 * Identifica una classe di valori confrontabili discreti per i quali posso
 * definire qual'e' l'elemento precedente e quello successivo.
 */
public interface Discrete<T> extends Comparable<T> {

	/**
	 * L'elemento successivo a questo.
	 * 
	 * @return l'elemento successivo a questo
	 */
	public T next();

	/**
	 * L'elemento precedente a questo.
	 * 
	 * @return l'elemento precedente a questo
	 */
	public T previous();

}
