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
public class Cle<T> {
	/**
	 * Element dont on constitue la clé
	 */
	private T element;
	/**
	 * Fonction permettant de déterminer le hash
	 */
	private ToIntFunction<T> fonctionHash;
	/**
	 * Fonction permettant de déterminer si deux clés sont identiques
	 */
	private BiPredicate<T, T> fonctionEgalite;

	/**
	 * Construit une clé pour l'élément donné
	 * 
	 * @param element L'élément donné
	 * @param fonctionHash La fonction déterminant le hashcode de la clé
	 * @param fonctionEgalite La fonction déterminant si deux clés sont identiques
	 */
	public Cle(T element, ToIntFunction<T> fonctionHash, BiPredicate<T, T> fonctionEgalite) {
		this.element = element;
		this.fonctionHash = fonctionHash;
		this.fonctionEgalite = fonctionEgalite;
	}

	@Override
	public int hashCode() {
		return fonctionHash.applyAsInt(this.element);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cle)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Cle<T> cle = (Cle<T>) obj;

		return fonctionEgalite.test(this.element, cle.element);
	}
}
