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
public class GroupeurDeSimilaires<T> implements Collector<T, Map<Cle<T>, List<T>>, StockeurDeSimilaires<T>> {
	/**
	 * Fonction de hashage d'un élément
	 */
	private ToIntFunction<T> fonctionHash;
	
	/**
	 * Fonction déterminant si deux éléments sont identiques
	 */
	private BiPredicate<T, T> fonctionEgalite;

	/**
	 * Construit un collecteur qui regroupe les éléments en fonction
	 * @param fonctionHash Fonction de hashage des éléments
	 * @param fonctionEgalite Fonction de similarité des éléments
	 */
	public GroupeurDeSimilaires(ToIntFunction<T> fonctionHash, BiPredicate<T, T> fonctionEgalite) {
		this.fonctionHash = fonctionHash;
		this.fonctionEgalite = fonctionEgalite;
	}

	@Override
	public Supplier<Map<Cle<T>, List<T>>> supplier() {
		return () -> new HashMap<Cle<T>, List<T>>();
	}
	
	@Override
	public BiConsumer<Map<Cle<T>, List<T>>, T> accumulator() {
		return (carte, element) -> {
			Cle<T> nouvelleCle = new Cle<T>(element, fonctionHash, fonctionEgalite);
			
			List<T> liste = carte.get(nouvelleCle);
			
			if (liste != null) {
				liste.add(element);
			} else {
				liste = new ArrayList<>();
				liste.add(element);
				carte.put(nouvelleCle, liste);
			}
		};
	}


	@Override
	public BinaryOperator<Map<Cle<T>, List<T>>> combiner() {
		return (mapDestination, mapSource) -> {
			
			mapSource.entrySet().forEach(
					entree -> {
						Cle<T> cle = entree.getKey();
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
	public Function<Map<Cle<T>, List<T>>, StockeurDeSimilaires<T>> finisher() {
		return carte -> new StockeurDeSimilaires<>(fonctionHash, fonctionEgalite, carte);
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}
}
