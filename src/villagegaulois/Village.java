package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private class Marche{
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur
				, String produit, int nbProduit) {
			System.out.println("Le vendeur " + vendeur.getNom() + " s'installe"
					+ " sur l'etal n�" + (indiceEtal+1));
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for(int i = 0; i < etals.length; i++) {
				if(!(etals[i].isEtalOccupe())){
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalsOccupes = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalsOccupes++;
				}
			}
			
			Etal[] ethalstrouvees = new Etal[nbEtalsOccupes];
			int indiceEtal = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					ethalstrouvees[indiceEtal] = etals[i];
					indiceEtal++;
				}
			}
			
			return ethalstrouvees;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}
		
		public void afficherMarche(){
			int i = 0;
			while(etals[i].isEtalOccupe()) {
				etals[i].afficherEtal();
				i++;
			}
			System.out.println("Il reste " + (etals.length - i) + " etals non utilises dans le marche.\n");
			
		}
	}
	
	

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalVendProduit = marche.trouverEtals(produit);
		if (etalVendProduit.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marche.\n");
		} else if(etalVendProduit.length == 1) {
			chaine.append("Seul le vendeur " + etalVendProduit[0].getVendeur().getNom() + " propose des " +
					produit + " au marche.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etalVendProduit.length; i++) {
				chaine.append("- " + etalVendProduit[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int indiceEtalLibre = marche.trouverEtalLibre();
		
		if (indiceEtalLibre == -1) {
			chaine.append("Malheureusement, il n'y a plus d'etal libre.\n");
		} else {
			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);	
		}
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " +
				produit + ".\n");
		return chaine.toString();
	}
	
//	public Etal rechercherEtal(Gaulois vendeur) {
//		
//	}
}