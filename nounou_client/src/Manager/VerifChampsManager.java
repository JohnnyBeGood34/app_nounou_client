package Manager;

public class VerifChampsManager {

	boolean result;
	
	public VerifChampsManager(){
	}
	
	public boolean nom(String nom){
		result = true;
		if (nom.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean prenom(String prenom){
		result = true;
		if (prenom.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean dateDeNaissance(String dateDeNaissance){
		result = true;
		if (dateDeNaissance.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean civilite(String civilité){
		result = true;
		if (civilité.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean adresse(String adresse){
		result = true;
		if (adresse.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean ville(String ville){
		result = true;
		if (ville.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean email(String email){
		result = true;
		if (email.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean tarif(String tarif){
		result = true;
		if (tarif.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean description(String description){
		result = true;
		if (description.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean tel(String tel){
		result = true;
		if (tel.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean dispo(String dispo){
		result = true;
		if (dispo.length() == 0){
			result = false;
		}
		return result;
	}
	public boolean mdp(String mdp){
		result = true;
		if (mdp.length() == 0){
			result = false;
		}
		return result;
	}

}
