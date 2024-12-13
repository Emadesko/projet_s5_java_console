package com.emadesko;

import java.util.Scanner;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.enums.Role;
import com.emadesko.datas.repositories.ArticleRepository;
import com.emadesko.datas.repositories.ClientRepository;
import com.emadesko.datas.repositories.CompteRepository;
import com.emadesko.datas.repositories.DemandeRepository;
import com.emadesko.datas.repositories.DetailDemandeRepository;
import com.emadesko.datas.repositories.DetailRepository;
import com.emadesko.datas.repositories.DetteRepository;
import com.emadesko.datas.repositories.PaiementRepository;
import com.emadesko.datas.repositories.list.ArticleRepositoryList;
import com.emadesko.datas.repositories.db.CompteRepositoryDb;
import com.emadesko.datas.repositories.db.DemandeRepositoryDb;
import com.emadesko.datas.repositories.db.DetailDemandeRepositoryDb;
import com.emadesko.datas.repositories.db.DetailMereRepositoryDb;
import com.emadesko.datas.repositories.db.DetailRepositoryDb;
import com.emadesko.datas.repositories.db.DetteRepositoryDb;
import com.emadesko.datas.repositories.db.PaiementRepositoryDb;
import com.emadesko.datas.repositories.jpa.ArticleRepositoryJpa;
import com.emadesko.datas.repositories.jpa.ClientRepositoryJpa;
import com.emadesko.datas.repositories.jpa.CompteRepositoryJpa;
import com.emadesko.datas.repositories.jpa.DemandeRepositoryJpa;
import com.emadesko.datas.repositories.jpa.DetailDemandeRepositoryJpa;
import com.emadesko.datas.repositories.jpa.DetailRepositoryJpa;
import com.emadesko.datas.repositories.jpa.DetteRepositoryJpa;
import com.emadesko.datas.repositories.jpa.PaiementRepositoryJpa;
import com.emadesko.datas.repositories.db.ArticleRepositoryDb;
import com.emadesko.datas.repositories.db.ClientRepositoryDb;
import com.emadesko.datas.repositories.list.ClientRepositoryList;
import com.emadesko.datas.repositories.list.CompteRepositoryList;
import com.emadesko.datas.repositories.list.DetailRepositoryList;
import com.emadesko.datas.repositories.list.DetteRepositoryList;
import com.emadesko.datas.repositories.list.PaiementRepositoryList;
import com.emadesko.datas.repositories.list.CompteRepositoryList;
import com.emadesko.services.ArticleService;
// import com.emadesko.datas.repositories.list.ClientRepositoryList;
// import com.emadesko.datas.repositories.list.CompteRepositoryList;
import com.emadesko.services.ClientService;
import com.emadesko.services.CompteService;
import com.emadesko.services.DemandeService;
import com.emadesko.services.DetailDemandeService;
import com.emadesko.services.DetailService;
import com.emadesko.services.DetteService;
import com.emadesko.services.PaiementService;
import com.emadesko.views.ArticleView;
import com.emadesko.views.ClientView;
import com.emadesko.views.CompteView;
import com.emadesko.views.DemandeView;
import com.emadesko.views.DetailDemandeView;
import com.emadesko.views.DetailView;
import com.emadesko.views.DetteView;
import com.emadesko.views.PaiementView;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        CompteRepository compteRepository = new CompteRepositoryJpa();
        CompteService compteService = new CompteService(compteRepository);
        CompteView compteView = new CompteView(scanner, compteService);

        ClientRepository clientRepository = new ClientRepositoryJpa();
        ClientService clientService = new ClientService(clientRepository);
        ClientView clientView = new ClientView(scanner, clientService);
        
        ArticleRepository articleRepository = new ArticleRepositoryJpa();
        ArticleService articleService = new ArticleService(articleRepository);
        ArticleView articleView = new ArticleView(scanner, articleService);

        DetteRepository detteRepository = new DetteRepositoryJpa();
        DetteService detteService = new DetteService(detteRepository);
        DetteView detteView = new DetteView(scanner, detteService);
        
        PaiementRepository paiementRepository = new PaiementRepositoryJpa();
        PaiementService paiementService = new PaiementService(paiementRepository);
        PaiementView paiementView = new PaiementView(scanner, paiementService);

        DetailRepository detailRepository = new DetailRepositoryJpa();
        DetailService detailService = new DetailService(detailRepository);
        DetailView detailView = new DetailView(scanner, detailService);

        DemandeRepository demandeRepository = new DemandeRepositoryJpa();
        DemandeService demandeService = new DemandeService(demandeRepository);
        DemandeView demandeView = new DemandeView(scanner, demandeService);
        
        DetailDemandeRepository detailDemandeRepository = new DetailDemandeRepositoryJpa();
        DetailDemandeService detailDemandeService = new DetailDemandeService(detailDemandeRepository);
        DetailDemandeView detailDemandeView = new DetailDemandeView(scanner, detailDemandeService);

        int choix;
        Role role = compteView.selectRole();
        switch (role) {
            case Admin:
                do {
                    System.out.println("1: Créer un compte");
                    System.out.println("2: Activer ou désactiver un compte");
                    System.out.println("3: Lister les comptes utilisateurs");
                    System.out.println("4: Créer des articles");
                    System.out.println("5: Lister des articles");
                    System.out.println("6: Filtrer les articles par disponibilité");
                    System.out.println("7: Mettre à jour la quantité en stock d'un article");
                    System.out.println("0: Déconnexion");
                    choix = scanner.nextInt();
                    scanner.nextLine();
        
                    switch (choix) {
                        case 1:
                            compteView.saisie(null,clientView);
                            break;
        
                        case 2:
                            compteView.changeActivationCompte();
                            break;
        
                        case 3:
                            do {
                                
                            } while (compteView.listComptesActifsOuParRole() != 3);
                            break;
        
                        case 4:
                            articleView.saisie();
                            break;
        
                        case 5:
                            articleView.showAll();
                            break;
        
                        case 6:
                            articleView.listArticleParDisponibilité();
                            break;
        
                        case 7:
                            articleView.updateQteStock();
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
                    System.out.println("6: Enregistrer un paiement pour une dette");
                    System.out.println("7: Lister les demandes de dette");
                    System.out.println("0: Déconnexion");
                    choix = scanner.nextInt();
                    scanner.nextLine();
        
                    switch (choix) {
                        case 1:
                            clientView.saisie(compteView);
                            break;
        
                        case 2:
                            clientView.showClientsByAccountStatus();
                            break;
        
                        case 3:
                            clientView.searchClientByTelephone(detteView);
                            break;
        
                        case 4:
                            detteView.saisie(clientView, articleView, paiementView, detailView);
                            break;
        
                        case 5:
                            detteView.showDettesDettesNonSoldesByClientWith(clientView, paiementView, detailView, null);
                            break;
        
                        case 6:
                            paiementView.saisie(detteView, clientView, null);
                            break;
        
                        case 7:
                            demandeView.showAllDemandes(detailDemandeView,detailView,detteView,paiementView,articleView);
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
                Client client = clientView.selectByTelephone(clientService.getAll());
                do {
                    System.out.println("1: Lister mes dettes non soldées");
                    System.out.println("2: Faire une demande de dette");
                    System.out.println("3: Lister mes demandes de dette");
                    System.out.println("4: Envoyer une relance pour une demande de dette annuler");
                    System.out.println("0: Déconnexion");
                    choix = scanner.nextInt();
                    scanner.nextLine();
        
                    switch (choix) {
                        case 1:
                            detteView.showDettesDettesNonSoldesByClientWith(clientView, paiementView, detailView, client);
                            break;
        
                        case 2:
                            demandeView.saisie(client, articleView, detailDemandeView);
                            break;
        
                        case 3:
                            demandeView.showMyDemandes(client);
                            break;
        
                        case 4:
                            demandeView.sendRelanceDemandeAnnulee(client);
                            break;
        
                        case 0:
        
                            break;
        
                        default:
                            System.out.println("Veuillez faire un bon choix!!");
                            break;
                    }
                } while (choix != 0);
                break;
        
            default:
                break;
        }
        
        scanner.close();
    }

}