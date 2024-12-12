package com.emadesko.views;

import com.emadesko.datas.entities.Demande;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.DetailDemande;
import com.emadesko.datas.enums.Etat;
import com.emadesko.services.DemandeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemandeView extends View<Demande> {

    private DemandeService demandeService;

    public DemandeService getDemandeService() {
        return demandeService;
    }

    public DemandeView(Scanner scanner, DemandeService demandeService) {
        super(scanner, demandeService, "Aucune demande", "Liste des demandes");
        this.demandeService = demandeService;
    }

    public DetailDemande check(List<DetailDemande> tab, Article article) {
        return tab.stream().filter(detailDemande -> detailDemande.getArticle().getId() == article.getId()).findFirst().orElse(null);
    }

    public Demande saisie(Client client, ArticleView articleView, DetailDemandeView detailDemandeView) {
        List<Article> articles = articleView.getArticleService().getAvailableArticles();
        if (articles.isEmpty()) {
            System.out.println("Aucun article disponible.");
            return null;
        }
        boolean ok;
        List<DetailDemande> tabDetailDemande=new ArrayList<>();
        double montant = 0;
        int qte;
        do {
            Article article = articleView.chooseArticle();

            if (article != null) {
                do {
                    System.out.println("Veuillez donneer la quantité de " + article.getLibelle() + " que vous voulez");
                    qte = scanner.nextInt();
                    scanner.nextLine();
                    ok = qte > article.getQteStock() || qte <= 0;
                    if (ok) {
                        System.out.println(
                                "Il y'a " + article.getQteStock() + " de " + article.getLibelle() + " disponible");
                    }
                } while (ok);
                DetailDemande detailDemande = this.check(tabDetailDemande, article);
                montant += article.getPrix() * qte;
                if (detailDemande == null) {
                    tabDetailDemande.add(new DetailDemande(qte, article.getPrix(), article, null));
                } else {
                    detailDemande.setQte(detailDemande.getQte() + qte);
                    detailDemande.setTotal(detailDemande.getQte() * detailDemande.getPrix());
                }
                article.setQteStock(article.getQteStock() - qte);
                articleView.getArticleService().update(article);
            }
            ok = super.choixSousMenu("Voulez vous ajouter un autre article? \n1- Oui \n2- Non", 2) == 1;
        } while (ok);
        Demande demande= new Demande(montant, client);
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
            List<Demande> demandes = demandeService.getDemandesByEtatAndClient(etat,client);
            System.out.println("#########################################################");
            super.emptyTabTxt="Aucune demande  " + etat.name();
            super.showList(demandes, "Comptes de rôle " + etat.name());
            super.emptyTabTxt="Aucune demande";
        }
    }

}
