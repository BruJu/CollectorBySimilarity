package fr.bruju.util.similaire;

import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;

/**
 * Représentation d'une clé pour un élément de type T
 * 
 * @author Bruju
 *
 * @param <T> Le type des éléments
 */
public class Key<T> {
	/**
	 * Element dont on constitue la clé
	 */
	private T element;
	/**
	 * Fonction permettant de déterminer le hash
	 */
	private ToIntFunction<T> hashFunction;
	/**
	 * Fonction permettant de déterminer si deux clés sont identiques
	 */
	private BiPredicate<T, T> equalsFunction;

	/**
	 * Construit une clé pour l'élément donné
	 * 
	 * @param element L'élément donné
	 * @param hashFunction La fonction déterminant le hashcode de la clé
	 * @param equalsFunction La fonction déterminant si deux clés sont identiques
	 */
	public Key(T element, ToIntFunction<T> hashFunction, BiPredicate<T, T> equalsFunction) {
		this.element = element;
		this.hashFunction = hashFunction;
		this.equalsFunction = equalsFunction;
	}

	@Override
	public int hashCode() {
		return hashFunction.applyAsInt(this.element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Key)) {
			return false;
		}
		Key<T> cle = (Key<T>) obj;

		return equalsFunction.test(this.element, cle.element);
	}
}
