package com.emadesko.views;

import java.util.Scanner;

import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;
import com.emadesko.services.CompteService;

public class CompteView extends View<Compte> {

    private CompteService compteService;

    public CompteView(Scanner scanner, CompteService compteService) {
        super(scanner,compteService,"Aucun compte");
        this.compteService = compteService;
    }

    public Role selectRole() {
        String menuTxt = "Pour qui voulez vous créer le compte?";
        for (Role role : Role.values()) {
            menuTxt =menuTxt.concat(String.format("\n%s : %s ", role.ordinal() + 1,role));
        }
        return Role.values()[super.choixSousMenu(menuTxt, Role.values().length)-1];
    }

    public Compte saisie(Role role) {

        if (role == null) {
            role=selectRole();
        }
        String login, email, password, nom, prenom;
        Boolean ok;
        nom = super.obligatoire("Veuillez donneer le nom de l'utilisateur");
        prenom = super.obligatoire("Veuillez donneer le prenom de l'utilisateur");
        do {
            login = super.obligatoire("Veuillez donneer le login de l'utilisateur");
            Compte compte=compteService.getCompteByLogin(login);
            ok=compte!=null;
            if (ok) {
                System.out.println(compte.getPrenom() + " " + compte.getNom() + " a déja ce login");
            }
        } while (ok);

        do {
            email = super.obligatoire("Veuillez donneer l'email de l'utilisateur");
            Compte compte=compteService.getCompteByEmail(email);
            ok=compte!=null;
            if (ok) {
                System.out.println(compte.getPrenom() + " " + compte.getNom() + " a déja cet email");
            }
        } while (ok);
        password = super.obligatoire("Veuillez donneer le mot de passe de l'utilisateur");

        Compte compte = new Compte(login, email, password, nom, prenom, role);
        compteService.create(compte);
        return compte;
    }
}
