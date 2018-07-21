package fr.bruju.util.similaire;
import java.util.Arrays;
import java.util.List;

public class Exemple {

	public static class Personne {
		
		private String nom;
		private String prenom;
		
		public Personne(String nom, String prenom) {
			this.nom = nom;
			this.prenom = prenom;
		}
		
		public int mauvaisHash() {
			return nom.length();
		}
		
		public boolean memeNom(Personne p) {
			return nom.equals(p.nom);
		}
		
		public String toString() {
			return nom + " " + prenom;
		}
		
	}
	
	public static void main(String[] args) {

		
		Personne[] personnes = {
			new Personne("Ying", "Paul"),
			new Personne("Yang", "Jacques"),
			new Personne("Zoo", "Alonzo"),
			new Personne("Paul", "Allan"),
			new Personne("Schwarzaieneruozregger", "Arnold"),
			new Personne("Ying", "Akira"),
			new Personne("Yang", "Jean-Claude"),
			new Personne("Ying", "Frederic"),
			new Personne("Ying", "Mohamed")
		};
		
		List<Personne> liste = Arrays.asList(personnes);
		
		GroupeurDeSimilaires<Personne> mapReducer =
				new GroupeurDeSimilaires<Personne>(Personne::mauvaisHash, Personne::memeNom);
		
		StockeurDeSimilaires<Personne> carte = liste.stream().collect(mapReducer);

		System.out.println("Groupements identifiés : ");
		carte.getMap().forEach((cle, personnesDeLaMemeFamille) -> System.out.println(personnesDeLaMemeFamille));
		System.out.println();
		System.out.println("Similaires à Yang Johan : ");
		System.out.println(carte.getSimilaires(new Personne("Yang", "Johan")));

	}



}
