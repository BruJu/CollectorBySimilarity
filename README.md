# CollectorBySimilarity

## Objectif

Il s'agit d'une implémentation des collecteurs afin de pouvoir grouper des éléments en utilisant une fonction d'égalité autre que equals. On parle alors d'éléments similaires par rapport à une fonction.

Elle se repose sur :
- Une fonction de hash pour accélérer l'identification des éléments similaires

- Une fonction d'égalité renvoyant vrai si deux éléments sont similaires


## Exemple

```
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

CollectorBySimilarity<Personne> collectNom =
		new CollectorBySimilarity<Personne>(Personne::mauvaisHash, Personne::memeNom);

SimilarityStorage<Personne> carte = liste.stream().collect(collectNom);

System.out.println("Groupements identifiés : ");
carte.getMap().forEach((cle, personnesDeLaMemeFamille) -> System.out.println(personnesDeLaMemeFamille));
System.out.println();
System.out.println("Similaires à Yang Johan : ");
System.out.println(carte.getSimilarsTo(new Personne("Yang", "Johan")));
```

Renvoie

```
Groupements identifiés : 
[Zoo Alonzo]
[Ying Paul, Ying Akira, Ying Frederic, Ying Mohamed]
[Yang Jacques, Yang Jean-Claude]
[Paul Allan]
[Schwarzaieneruozregger Arnold]

Similaires à Yang Johan : 
[Yang Jacques, Yang Jean-Claude]
```

Le code complet de cet exemple peut être trouvé dans le fichier Exemple.java

## Licence

Cette bibliothèque est distribuée sous licence [https://creativecommons.org/publicdomain/zero/1.0/deed.fr](Creative Commons Zero).

