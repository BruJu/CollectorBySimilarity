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
public class SimilarityStorage<T> {
	/**
	 * Fonction de hashage d'un élément
	 */
	private ToIntFunction<T> hashFunction;
	
	/**
	 * Fonction déterminant si deux éléments sont identiques
	 */
	private BiPredicate<T, T> equalityFunction;
	
	/**
	 * Carte associant clés et éléments similaires
	 */
	private Map<Key<T>, List<T>> similaritiesMap;

	/**
	 * Construit un stockage d'éléments similaires
	 * @param hashFunction La fonction de hash utilisée
	 * @param equalityFunction La fonction d'égalité utilisée
	 * @param similiaritiesMap La carte des éléments similaires construite
	 */
	public SimilarityStorage(ToIntFunction<T> hashFunction, BiPredicate<T, T> equalityFunction,
			Map<Key<T>, List<T>> similiaritiesMap) {
		this.hashFunction = hashFunction;
		this.equalityFunction = equalityFunction;
		this.similaritiesMap = similiaritiesMap;
	}

	/**
	 * Renvoie la carte construite des éléments similaires
	 * @return Une carte non mutable avec les clés associés aux éléments similaires
	 */
	public Map<Key<T>, List<T>> getMap() {
		return similaritiesMap;
	}
	
	/**
	 * Renvoie la liste des éléments similaires à l'élément donné
	 * @param element L'élément dont on souhaite avoir la liste des éléments similaires
	 * @return Une liste non mutable d'éléments similaires à l'élément donné 
	 */
	public List<T> getSimilarsTo(T element) {
		return similaritiesMap.get(new Key<>(element, hashFunction, equalityFunction));
	}
}
