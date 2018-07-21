package fr.bruju.util.similaire;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;

/**
 * Cette classe permet de sauver les éléments similaires, et d'avoir un accés simple à partir d'un objet de retrouver
 * les éléments qui lui sont similaires
 * 
 * @author Bruju
 *
 * @param <T> Le type des éléments stockés
 */
public class StockeurDeSimilaires<T> {
	/**
	 * Fonction de hashage d'un élément
	 */
	private ToIntFunction<T> fonctionHash;
	
	/**
	 * Fonction déterminant si deux éléments sont identiques
	 */
	private BiPredicate<T, T> fonctionEgalite;
	
	/**
	 * Carte associant clés et éléments similaires
	 */
	private Map<Cle<T>, List<T>> carteSimilaires;

	/**
	 * Construit un stockage d'éléments similaires
	 * @param fonctionHash La fonction de hash utilisée
	 * @param fonctionEgalite La fonction d'égalité utilisée
	 * @param carteSimilaires La carte des éléments similaires construite
	 */
	public StockeurDeSimilaires(ToIntFunction<T> fonctionHash, BiPredicate<T, T> fonctionEgalite,
			Map<Cle<T>, List<T>> carteSimilaires) {
		this.fonctionHash = fonctionHash;
		this.fonctionEgalite = fonctionEgalite;
		this.carteSimilaires = carteSimilaires;
	}

	/**
	 * Renvoie la carte construite des éléments similaires
	 * @return Une carte non mutable avec les clés associés aux éléments similaires
	 */
	public Map<Cle<T>, List<T>> getMap() {
		return carteSimilaires;
	}
	
	/**
	 * Renvoie la liste des éléments similaires à l'élément donné
	 * @param element L'élément dont on souhaite avoir la liste des éléments similaires
	 * @return Une liste non mutable d'éléments similaires à l'élément donné 
	 */
	public List<T> getSimilaires(T element) {
		return carteSimilaires.get(new Cle<>(element, fonctionHash, fonctionEgalite));
	}
}
