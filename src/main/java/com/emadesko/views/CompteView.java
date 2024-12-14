package com.emadesko.views;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.enums.Role;
import com.emadesko.services.CompteService;

public class CompteView extends View<Compte> {

    private CompteService compteService;

    public CompteService getCompteService() {
        return compteService;
    }

    public CompteView(Scanner scanner, CompteService compteService) {
        super(scanner, compteService, "Aucun compte", "Liste des comptes");
        this.compteService = compteService;
    }

    public Role selectRole() {
        String menuTxt = "Pour qui voulez vous créer le compte?";
        for (Role role : Role.values()) {
            menuTxt = menuTxt.concat(String.format("\n%s : %s ", role.ordinal() + 1, role));
        }
        menuTxt += "\n4 : Annuler";
        int position = super.choixSousMenu(menuTxt, Role.values().length+1);
        return position != 4 ? Role.values()[position - 1] : null;
    }

    public Compte saisie(Role role, ClientView clientView) {
        Client client = null;
        if (role == null) {
            role = selectRole();
            if (role == null) {
                return null;
            }
            if (role == Role.Client) {
                client = clientView.chooseClient(clientView.getClientService().getClientsByAccountStatus(false), "sans");
                if (client == null) {
                    return null;
                }
            }
        }
        String login, email, password, nom, prenom;
        Boolean ok;
        nom = super.obligatoire("Veuillez donneer le nom de l'utilisateur");
        prenom = super.obligatoire("Veuillez donneer le prenom de l'utilisateur");
        do {
            login = super.obligatoire("Veuillez donneer le login de l'utilisateur");
            Compte compte = compteService.getCompteByLogin(login);
            ok = compte != null;
            if (ok) {
                System.out.println(compte.getPrenom() + " " + compte.getNom() + " a déja ce login");
            }
        } while (ok);

        do {
            email = super.obligatoire("Veuillez donneer l'email de l'utilisateur");
            Compte compte = compteService.getCompteByEmail(email);
            ok = compte != null;
            if (ok) {
                System.out.println(compte.getPrenom() + " " + compte.getNom() + " a déja cet email");
            }
        } while (ok);
        password = super.obligatoire("Veuillez donneer le mot de passe de l'utilisateur");

        Compte compte = new Compte(login, email, password, nom, prenom, role);

        compteService.create(compte);
        System.out.println("Compte créé avec succès");
        if (client != null) {
            client.setUpdateAt(LocalDate.now());
            compte.setClient(client);
            client.setCompte(compte);
            clientView.service.update(client);
        }
        return compte;
    }

    public void changeActivationCompte() {
        Compte compte = selectByLogin(compteService.getAll());
        if (compte != null) {
            compte.setActive(!compte.isActive());
            compte.setUpdateAt(LocalDate.now());
            compteService.update(compte);
            System.out.println("Compte " + (compte.isActive() ? "activé" : "désactivé"));
        }
    }

    public Compte selectByLogin(List<Compte> tab) {
        if (tab.isEmpty()) {
            System.out.println(this.emptyTabTxt + " n'existe");
            return null;
        }else{
            tab.stream().forEach(System.out::println);
            System.out.println("Veuillez entrer le login du compte ou 0 pour annuler");
            String login = scanner.nextLine();
            
            if (login != "0") {
                Compte entity = this.compteService.getCompteByLogin(login);
                boolean ok = entity == null;
                while (ok) {
                    System.out.println("Aucun compte ne correspond à ce login");
                    System.out.println("Veuillez entrer le login du compte ou 0 pour annuler");
                    login = scanner.nextLine();
                    
                    if (login == "0") {
                        ok = false;
                    } else {
                        entity = this.compteService.getCompteByLogin(login);
                        ok = entity == null;
                    }
                }
                if (entity != null) {
                    System.out.println("\n" + entity + "\n");
                }
                return entity;
            }
            return null;
        }
    }

    public void listComptesParRole() {
        for (Role role : Role.values()) {
            List<Compte> comptes = compteService.getComptesByRole(role);
            System.out.println("#########################################################");
            super.emptyTabTxt="Aucun compte de rôle " + role.name();
            super.showList(comptes, "Comptes de rôle " + role.name());
            super.emptyTabTxt="Aucun compte";
        }
    }

    public void listComptesActifs() {
        List<Compte> comptes = compteService.getComptesByEtat(true);
        super.emptyTabTxt="Aucun compte actif";
        super.showList(comptes, "Liste des comptes actifs");
        super.emptyTabTxt="Aucun compte";
    }

    public int listComptesActifsOuParRole() {
        int choix = super.choixSousMenu("1- Comptes par rôle \n2- Comptes actifs \n3- Retour", 3);
        if (choix == 1) {
            listComptesParRole();
        } else if (choix == 2) {
            listComptesActifs();
        }
        return choix;
    }

}
