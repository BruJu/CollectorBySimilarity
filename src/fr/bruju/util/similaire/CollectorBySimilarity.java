package fr.bruju.util.similaire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

/**
 * Collecteur collectant des objets, et les regroupant en suivant une fonction de hashage et une fonction pour
 * déterminer si deux objets sont similaires.
 * 
 * Les objets similaires sont ainsi regroupés dans une Map.
 * 
 * Les éléments portant la même clé qu'un élément connu peuvent ensuite être accédés via un get avec un nouvel objet
 * new Cle<elementCherché, fonctionDeHashUtilisée, fonctionDegalitéUtilisée>
 * 
 * @author Bruju
 *
 * @param <T> Le type des éléments
 */
public class CollectorBySimilarity<T> implements Collector<T, Map<Key<T>, List<T>>, SimilarityStorage<T>> {
	/**
	 * Fonction de hashage d'un élément
	 */
	private ToIntFunction<T> hashFunction;
	
	/**
	 * Fonction déterminant si deux éléments sont identiques
	 */
	private BiPredicate<T, T> equalsFunction;

	/**
	 * Construit un collecteur qui regroupe les éléments en fonction
	 * @param hashFunction Fonction de hashage des éléments
	 * @param equalsFunction Fonction de similarité des éléments
	 */
	public CollectorBySimilarity(ToIntFunction<T> hashFunction, BiPredicate<T, T> equalsFunction) {
		this.hashFunction = hashFunction;
		this.equalsFunction = equalsFunction;
	}

	@Override
	public Supplier<Map<Key<T>, List<T>>> supplier() {
		return () -> new HashMap<Key<T>, List<T>>();
	}
	
	@Override
	public BiConsumer<Map<Key<T>, List<T>>, T> accumulator() {
		return (carte, element) -> {
			Key<T> newKey = new Key<T>(element, hashFunction, equalsFunction);
			
			List<T> liste = carte.get(newKey);
			
			if (liste != null) {
				liste.add(element);
			} else {
				liste = new ArrayList<>();
				liste.add(element);
				carte.put(newKey, liste);
			}
		};
	}


	@Override
	public BinaryOperator<Map<Key<T>, List<T>>> combiner() {
		return (mapDestination, mapSource) -> {
			
			mapSource.entrySet().forEach(
					entree -> {
						Key<T> cle = entree.getKey();
						List<T> valeur = entree.getValue();
						
						List<T> destination = mapDestination.get(cle);
						
						if (destination == null) {
							mapSource.put(cle, valeur);
						} else {
							destination.addAll(valeur);
						}
					});
			return mapDestination;
		};
	}

	@Override
	public Function<Map<Key<T>, List<T>>, SimilarityStorage<T>> finisher() {
		return carte -> new SimilarityStorage<>(hashFunction, equalsFunction, carte);
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}
}
