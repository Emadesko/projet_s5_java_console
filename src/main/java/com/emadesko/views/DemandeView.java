package com.emadesko.views;

import com.emadesko.datas.entities.Demande;
import com.emadesko.datas.entities.Detail;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.DetailDemande;
import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.enums.Etat;
import com.emadesko.services.DemandeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemandeView extends View<Demande> {

    private DemandeService demandeService;

    public DemandeService getDemandeService() {
        return demandeService;
    }

    public DemandeView(Scanner scanner, DemandeService demandeService) {
        super(scanner, demandeService, "Aucune demande", "Liste des demandes\n");
        this.demandeService = demandeService;
    }

    public DetailDemande check(List<DetailDemande> tab, Article article) {
        return tab.stream().filter(detailDemande -> detailDemande.getArticle().getId() == article.getId()).findFirst()
                .orElse(null);
    }

    public Demande saisie(Client client, ArticleView articleView, DetailDemandeView detailDemandeView) {
        List<Article> articles = articleView.getArticleService().getAvailableArticles();
        if (articles.isEmpty()) {
            System.out.println("Aucun article disponible.\n");
            return null;
        }
        boolean ok;
        List<DetailDemande> tabDetailDemande = new ArrayList<>();
        double montant = 0;
        int qte;
        do {
            Article article = articleView.chooseArticle();

            if (article != null) {
                do {
                    System.out.println("Veuillez donneer la quantité de " + article.getLibelle() + " que vous voulez\n");
                    qte = scanner.nextInt();
                    scanner.nextLine();
                    ok = qte > article.getQteStock() || qte <= 0;
                    if (ok) {
                        System.out.println(
                                "Il y'a " + article.getQteStock() + " de " + article.getLibelle() + " disponible\n");
                    }
                } while (ok);
                DetailDemande detailDemande = this.check(tabDetailDemande, article);
                montant += article.getPrix() * qte;
                if (detailDemande == null) {
                    tabDetailDemande.add(new DetailDemande(qte, article.getPrix(), article, null));
                } else {
                    detailDemande.setQte(qte);
                    detailDemande.setTotal(detailDemande.getQte() * detailDemande.getPrix());
                }
            }
            ok = super.choixSousMenu("Voulez vous ajouter un autre article? \n1- Oui \n2- Non", 2) == 1;
        } while (ok);
        Demande demande = new Demande(montant, client);
        demandeService.create(demande);
        client.getDemandes().add(demande);
        for (DetailDemande detailDemande : tabDetailDemande) {
            detailDemande.setDemande(demande);
            demande.getDetailsDemandes().add(detailDemande);
            detailDemandeView.getDetailDemandeService().create(detailDemande);
        }
        return demande;
    }

    public void showMyDemandes(Client client) {
        for (Etat etat : Etat.values()) {
            List<Demande> demandes = demandeService.getDemandesByEtatAndClient(etat, client);
            System.out.println("#########################################################\n");
            super.emptyTabTxt = "Aucune demande " + etat.name();
            super.showList(demandes, "Liste des demandes " + etat.name());
            super.emptyTabTxt = "Aucune demande";
        }
    }

    public void showDemandesByEtat(){
        for (Etat etat : Etat.values()) {
            List<Demande> demandes = demandeService.getDemandesByEtat(etat);
            System.out.println("#########################################################\n");
            super.emptyTabTxt = "Aucune demande " + etat.name();
            super.showList(demandes, "Liste des demandes " + etat.name());
            super.emptyTabTxt = "Aucune demande";
        }
    }

    public void showAllDemandes(DetailDemandeView detailDemandeView, DetailView detailView, DetteView detteView, PaiementView paiementView, ArticleView articleView) {
        this.showDemandesByEtat();
        List<Demande> demandes = demandeService.getAll();
        if (!demandes.isEmpty()) {
            int choix = super.choixSousMenu(
                    "1- Accepter ou Rejeter une demande \n2- Voir les articles d'une demande \n3- Retour", 3);
            while (choix != 3) {
                if (choix == 1) {
                    List<Demande> demandesEnCours = demandeService.getDemandesByEtat(Etat.EnCours);
                    if (!demandesEnCours.isEmpty()) {
                        Demande demande = super.select(demandesEnCours, "de la demande", "Aucune demande\n");
                        if (demande != null) {
                            choix = super.choixSousMenu("1- Accepter la demande \n2- Rejeter la demande \n3- Retour", 3);
                            if (choix == 1) {
                                List<DetailDemande> qteNonDispo = new ArrayList<>();
                                demande.setDetailsDemandes(detailDemandeView.getDetailDemandeService().getDetailsByDemande(demande));
                                for (DetailDemande detailDemande : demande.getDetailsDemandes()) {
                                    int qteRestante= articleView.getArticleService().getArticleByLibelle(detailDemande.getArticle().getLibelle()).getQteStock();
                                    if (detailDemande.getQte()  > qteRestante) {
                                        qteNonDispo.add(detailDemande);
                                        System.out.
                                        println(detailDemande.getQte() + " " + detailDemande.getArticle().getLibelle() + " a(ont) été demandé. Actuellement il n'en reste que " + qteRestante);
                                    }
                                }
                                if (qteNonDispo.isEmpty()) {
                                    demande.setEtat(Etat.Acceptée);
                                    demande.setUpdateAt(LocalDate.now());
                                    demandeService.update(demande);
                                    Dette dette = new Dette(demande.getMontant(), demande.getClient());
                                    detteView.getDetteService().create(dette);
                                    choix = super.choixSousMenu("Voulez vous payer une partie du montant?\n1-Oui\n2-Non",2);
                                    if (choix == 1) {
                                        paiementView.saisie(detteView, null, dette);
                                    }
                                    for (DetailDemande detailDemande : demande.getDetailsDemandes()) {
                                        Detail detail = new Detail(detailDemande.getQte(), detailDemande.getPrix(),detailDemande.getArticle(), dette);
                                        Article article = articleView.getArticleService().getArticleByLibelle(detail.getArticle().getLibelle());
                                        article.setQteStock(article.getQteStock() - detail.getQte());
                                        article.setUpdateAt(LocalDate.now());
                                        articleView.getArticleService().update(article);
                                        detail.setDette(dette);
                                        dette.getDetails().add(detail);
                                        detailView.getDetailService().create(detail);
                                    }
                                }else{
                                    System.out.println("La demande ne peut pas être acceptée\n");
                                }
                            } else if (choix == 2) {
                                demande.setEtat(Etat.Rejetée);
                                demande.setUpdateAt(LocalDate.now());
                                demandeService.update(demande);
                            } else {
                                System.out.println("Opération annulée.\n");
                            }
                        }
                    } else {
                        System.out.println("Aucune demande en cours.\n");
                    }

                } else if (choix == 2) {
                    Demande demande = super.select(demandeService.getAll(), "de la demande", "Aucune demande\n");
                    if (demande != null) {
                        System.out.println("###############################################\n");
                        detailDemandeView.showList(
                                detailDemandeView.getDetailDemandeService().getDetailsByDemande(demande), null);
                    }
                }
                choix = super.choixSousMenu(
                        "1- Accepter ou Rejeter une demande \n2- Voir les articles d'une demande \n3- Retour", 3);
            }
        }

    }

    public void sendRelanceDemandeAnnulee(Client client){
        List<Demande> demandes = demandeService.getDemandesByEtatAndClient(Etat.Rejetée, client);
        if (demandes == null || demandes.isEmpty()) {
            System.out.println("Aucune demande rejetée\n");
        }else{
            Demande demande = this.select(demandes, "de la demande", "Aucune demande");
            if (demande != null) {
                demande.setEtat(Etat.EnCours);
                demande.setUpdateAt(LocalDate.now());
                demandeService.update(demande);
                System.out.println("Votre demande n°" + demande.getId() + " a été relancée.\n");
            }
        }
    }

}
