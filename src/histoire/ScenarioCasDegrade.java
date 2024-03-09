package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		village.setChef(abraracourcix);
		village.ajouterHabitant(bonemine);
		
		System.out.println(village.installerVendeur(bonemine, "fleurs", 20));
		Etal etalFleur = village.rechercherEtal(bonemine);

		System.out.println(etalFleur.acheterProduit(10, abraracourcix));
		System.out.println("Fin du test");
		}
	
}
