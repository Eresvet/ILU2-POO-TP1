package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.VillageSansChefException;


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
			
			Etal[] etalstrouvees = new Etal[nbEtalsOccupes];
			int indiceEtal = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalstrouvees[indiceEtal] = etals[i];
					indiceEtal++;
				}
			}
			
			return etalstrouvees;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche(){
			StringBuilder chaine = new StringBuilder();
			int i = 0;
			for(int j = 0; j < etals.length; j++) {
				if(etals[j].isEtalOccupe()) {
					chaine.append(etals[j].afficherEtal());
					i++;
				}
			}
			chaine.append("Il reste " + (etals.length - i) + " etals non utilises dans le marche.\n");
			return chaine.toString();
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

	public String afficherVillageois() throws VillageSansChefException {
		if(chef == null) {
			throw new VillageSansChefException("C'est un village sans chef");
		}
		StringBuilder chaine = new StringBuilder();
		
		
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les legendaires gaulois :\n");
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
		
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " +
				produit + ".\n");
		
		if (indiceEtalLibre == -1) {
			chaine.append("Malheureusement, il n'y a plus d'etal libre.\n");
		} else {
			marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " s'installe"
					+ " sur l'etal n" + (indiceEtalLibre+1) + "\n");
		}
		

		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal partir = marche.trouverVendeur(vendeur);
		return partir.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		if(marche.etals.length <= 0) {
			chaine.append("Malheureusement, il n'y a aucun etal dans ce marche.\n");
		}else {
			chaine.append("Le marche du village \"" + this.getNom() + "\" possede plusieurs etals :\n");
		}
		chaine.append(marche.afficherMarche());
		return chaine.toString();	}
}