package com.emadesko;

import java.util.Scanner;

import com.emadesko.core.factory.views.ArticleViewFactory;
import com.emadesko.core.factory.views.ClientViewFactory;
import com.emadesko.core.factory.views.CompteViewFactory;
import com.emadesko.core.factory.views.DemandeViewFactory;
import com.emadesko.core.factory.views.DetailDemandeViewFactory;
import com.emadesko.core.factory.views.DetailViewFactory;
import com.emadesko.core.factory.views.DetteViewFactory;
import com.emadesko.core.factory.views.PaiementViewFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.core.services.impl.YamlServiceImpl;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.enums.Role;
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

        YamlService yamlService = new YamlServiceImpl();

        CompteView compteView = CompteViewFactory.getInstance(scanner, yamlService);

        ClientView clientView = ClientViewFactory.getInstance(scanner, yamlService);
        
        ArticleView articleView = ArticleViewFactory.getInstance(scanner, yamlService);

        DetteView detteView = DetteViewFactory.getInstance(scanner,yamlService);
        
        PaiementView paiementView = PaiementViewFactory.getInstance(scanner, yamlService);

        DetailView detailView = DetailViewFactory.getInstance(scanner, yamlService);

        DemandeView demandeView = DemandeViewFactory.getInstance(scanner, yamlService);
        
        DetailDemandeView detailDemandeView = DetailDemandeViewFactory.getInstance(scanner, yamlService);

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
                Client client = clientView.selectBy(clientView.getClientService().getAll());
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