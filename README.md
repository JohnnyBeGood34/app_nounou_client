app_nounou_client
================
1- Connexion => Appel Serveur obligatoire, /api/connexionNounou (POST mail/mdp). Le serveur renvoi un objet nounou => mettre en cache.

2-Modification => Connexion obligatoire, api/nounou/:id

3- Inscription => Si connexion => Serveur /api/nounous (POST, les datas nounou + authentification), Si pas de connexion => stocke la requête dans sqlite. Dès qu'il y a connexion => envoi reqt serveur.

4- Liste des nounous => Par rapport à la current position du client, envoi position serveur, récupération de la liste des nounous. Si pas de co, message erreur. (/api/nounou/:lat/:lng. Connexion obligatoire.
