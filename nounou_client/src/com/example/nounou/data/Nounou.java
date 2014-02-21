package com.example.nounou.data;

public class Nounou {
	private String idNounou;
	private String nom;
	private String prenom;
	private String dateDeNaissance;
	private String civilite;
	private String adresse;
	private String email;
	private String tarifHoraire;
	private String descriptionPrestation;
	private String telephone;
	private String disponibilite;
	private String cheminPhoto;
	private String password;
	
	public Nounou(String id,String n,String p,String dN,String c,String ad,String e, String tH, String des,String tel, String dispo,String cP,String MDP){
		this.idNounou=id;
		this.nom=n;
		this.prenom=p;
		this.dateDeNaissance=dN;
		this.civilite=c;
		this.adresse=ad;
		this.email=e;
		this.tarifHoraire=tH;
		this.descriptionPrestation=des;
		this.telephone=tel;
		this.disponibilite=dispo;
		this.cheminPhoto=cP;
		this.password=MDP;
	}
	public Nounou(){
		this.idNounou="";
		this.nom="";
		this.prenom="";
		this.dateDeNaissance="";
		this.civilite="";
		this.adresse="";
		this.email="";
		this.tarifHoraire="";
		this.descriptionPrestation="";
		this.telephone="";
		this.disponibilite="";
		this.cheminPhoto="";
		this.password="";
		
	}
	public String getIdNounou() {
		return idNounou;
	}
	public void setIdNounou(String idNounou) {
		this.idNounou = idNounou;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDateDeNaissance() {
		return dateDeNaissance;
	}
	public void setDateDeNaissance(String dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}
	public String getCivilite() {
		return civilite;
	}
	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTarifHoraire() {
		return tarifHoraire;
	}
	public void setTarifHoraire(String tarifHoraire) {
		this.tarifHoraire = tarifHoraire;
	}
	public String getDescriptionPrestation() {
		return descriptionPrestation;
	}
	public void setDescriptionPrestation(String descriptionPrestation) {
		this.descriptionPrestation = descriptionPrestation;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDisponibilite() {
		return disponibilite;
	}
	public void setDisponibilite(String disponibilite) {
		this.disponibilite = disponibilite;
	}
	public String getCheminPhoto() {
		return cheminPhoto;
	}
	public void setCheminPhoto(String cheminPhoto) {
		this.cheminPhoto = cheminPhoto;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
