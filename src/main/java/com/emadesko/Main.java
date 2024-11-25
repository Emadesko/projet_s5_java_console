package com.emadesko;

import java.util.Scanner;

import com.emadesko.datas.enums.Role;
import com.emadesko.datas.repositories.ClientRepository;
import com.emadesko.datas.repositories.CompteRepository;
// import com.emadesko.datas.repositories.db.CompteRepositoryDb;
// import com.emadesko.datas.repositories.db.ClientRepositoryDb;
import com.emadesko.datas.repositories.jpa.ClientRepositoryJpa;
import com.emadesko.datas.repositories.jpa.CompteRepositoryJpa;
// import com.emadesko.datas.repositories.list.ClientRepositoryList;
// import com.emadesko.datas.repositories.list.CompteRepositoryList;
import com.emadesko.services.ClientService;
import com.emadesko.services.CompteService;
import com.emadesko.views.ClientView;
import com.emadesko.views.CompteView;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        CompteRepository compteRepository = new CompteRepositoryJpa();
        CompteService compteService = new CompteService(compteRepository);
        CompteView compteView = new CompteView(scanner, compteService);

        ClientRepository clientRepository = new ClientRepositoryJpa();
        ClientService clientService = new ClientService(clientRepository);
        ClientView clientView = new ClientView(scanner, clientService);

        int choix;
        Role role = compteView.selectRole();
        switch (role) {
            case Admin:
                do {
                    System.out.println("1: Créer un compte");
                    System.out.println("2: Activer ou désactiver un compte");
                    System.out.println("3: Lister les comptes utilisateurs  actifs ou par rôle");
                    System.out.println("4: Créer des articles");
                    System.out.println("5: Lister des articles");
                    System.out.println("6: Filtrer les articles par disponibilité");
                    System.out.println("7: Mettre à jour la quantité en stock d'un article");
                    System.out.println("8: Archiver les dettes soldées");
                    System.out.println("0: Quitter");
                    choix = scanner.nextInt();
                    scanner.nextLine();
        
                    switch (choix) {
                        case 1:
                            compteView.saisie(null,clientView);
                            break;
        
                        case 2:
                            break;
        
                        case 3:
                            
                            break;
        
                        case 4:
                            break;
        
                        case 0:
        
                            break;
        
                        default:
                            System.out.println("Veuillez faire un bon choix!!");
                            break;
                    }
                } while (choix != 0);
                break;

            case Boutiquier:
                do {
                    System.out.println("1: Créer un client");
                    System.out.println("2: Lister les clients");
                    System.out.println("3: Rechercher client par téléphone");
                    System.out.println("4: Créer une dette");
                    System.out.println("5: Lister les dettes non soldées d'un client");
                    System.out.println("6: Lister les demandes de dette");
                    System.out.println("0: Quitter");
                    choix = scanner.nextInt();
                    scanner.nextLine();
        
                    switch (choix) {
                        case 1:
                            clientView.saisie(compteView);
                            break;
        
                        case 2:
                            clientView.showAll();
                            break;
        
                        case 3:
                            clientView.selectByTelephone(clientService.getAll());
                            break;
        
                        case 4:
                            compteView.showAll();
                            break;
        
                        case 0:
        
                            break;
        
                        default:
                            System.out.println("Veuillez faire un bon choix!!");
                            break;
                    }
                } while (choix != 0);
                break;
            case Client:
                
                break;
        
            default:
                break;
        }
        
        scanner.close();
    }

}