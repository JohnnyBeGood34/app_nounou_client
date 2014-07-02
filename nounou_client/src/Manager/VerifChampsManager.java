package Manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifChampsManager {

	boolean result;
	
	public VerifChampsManager(){
	}
	
	public boolean nom(String nom){
		result = false;
		if (nom.length() != 0){
			//exclu les ciffres
			Pattern patternNom = Pattern.compile("[^\\(\\)0-9]*");
			Matcher matchNom = patternNom.matcher(nom);
			result = matchNom.matches();
		}
		return result;
	}
	public boolean prenom(String prenom){
		result = false;
		if (prenom.length() != 0){
			//exclu les ciffres
			Pattern patternPrenom = Pattern.compile("[^\\(\\)0-9]*");
			Matcher matchPrenom = patternPrenom.matcher(prenom);
			result = matchPrenom.matches();
		}
		return result;
	}
	public boolean dateDeNaissance(String dateDeNaissance){
		result = false;
		if (dateDeNaissance.length() != 0){
			// vérifi date au format français
			Pattern patternDate = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d{2}$");
			Matcher matchDate = patternDate.matcher(dateDeNaissance);
			result = matchDate.matches();
		}
		return result;
	}
	public boolean civilite(String civilité){
		result = false;
		if (civilité.length() != 0){
			//exclu les ciffres
			Pattern patternCivilite = Pattern.compile("[^\\(\\)0-9]*");
			Matcher matchCivilite = patternCivilite.matcher(civilité);
			result = matchCivilite.matches();
		}
		return result;
	}
	public boolean adresse(String adresse){
		result = false;
		if (adresse.length() != 0){
			result = true;
		}
		return result;
	}
	public boolean ville(String ville){
		result = false;
		if (ville.length() != 0){
			//exclu les ciffres
			Pattern patternVille = Pattern.compile("[^\\(\\)0-9]*");
			Matcher matchVille = patternVille.matcher(ville);
			result = matchVille.matches();
		}
		return result;
	}
	public boolean email(String email){
		result = false;
		if (email.length() != 0){
			//Vérif une adresse mail valide
			Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Matcher matchEmail = patternEmail.matcher(email);
			result = matchEmail.matches();
		}
		return result;
	}
	public boolean tarif(String tarif){
		result = false;
		if (tarif.length() != 0){
			//exclu les lettres
			Pattern patternTarif = Pattern.compile("[^\\(\\)a-zA-Z]*");
			Matcher matchTarif = patternTarif.matcher(tarif);
			result = matchTarif.matches();
		}
		return result;
	}
	public boolean description(String description){
		result = false;
		if (description.length() != 0){
			result = true;
		}		
		return result;
	}
	public boolean tel(String tel){
		result = false;
		if (tel.length() != 0){
			//Vérif format français
			Pattern patternTel = Pattern.compile("^((\\+|00)33\\s?|0)[1-9](\\s?\\d{2}){4}$");
			Matcher matchTel = patternTel.matcher(tel);
			result = matchTel.matches();
		}
		return result;
	}
	public boolean dispo(String dispo){
		result = false;
		if (dispo.length() != 0){
			result = true;
		}
		return result;
	}
	public boolean mdp(String mdp){
		result = false;
		if (mdp.length() != 0){
			//Contient au moin 1 chiffre, 1 minuscule, 1 majuscule de 6 a 20 caractères
			Pattern patternMdp = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
			Matcher matchMdp = patternMdp.matcher(mdp);
			result = matchMdp.matches();
		}
		return result;
	}

}
