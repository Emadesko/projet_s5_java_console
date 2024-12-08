package com.emadesko.views;

import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.entities.Paiement;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Detail;
import com.emadesko.services.DetteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DetteView extends View<Dette> {

    private DetteService detteService;

    public DetteService getDetteService() {
        return detteService;
    }

    public DetteView(Scanner scanner, DetteService detteService) {
        super(scanner, detteService, "Aucune dette");
        this.detteService = detteService;
    }

    public Detail check(List<Detail> tab, Article article) {
        return tab.stream().filter(detail -> detail.getArticle() == article).findFirst().orElse(null);
    }

    public Dette saisie(ClientView clientView, ArticleView articleView) {
        List<Client> clients = clientView.getClientService().getClientsByAccountStatus(true);
        if (clients.isEmpty()) {
            System.out.println("Aucun client actif.");
            return null;
        }
        List<Article> articles = articleView.getArticleService().getAvailableArticles();
        if (articles.isEmpty()) {
            System.out.println("Aucun article disponible.");
            return null;
        }
        Client client = clientView.chooseClient(clients, "avec");
        if (client == null) {
            return null;
        }
        boolean ok;
        List<Detail> tabDetail=new ArrayList<>();
        int montant = 0, qte;
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
                Detail detail = this.check(tabDetail, article);
                montant += article.getPrix() * qte;
                if (detail == null) {
                    tabDetail.add(new Detail(qte, article.getPrix(), article, null));
                } else {
                    detail.setPrix(detail.getPrix() + montant);
                    detail.setQte(detail.getQte() + qte);
                }
                article.setQteStock(article.getQteStock() - qte);
                articleView.getArticleService().update(article);
            }
            ok = super.choixSousMenu("Voulez vous ajouter un autre article? \n1- Oui \n2- Non", 2) == 1;
        } while (ok);
        int choix=super.choixSousMenu("Voulez vous payer une partie du montant?\n1-Oui\n2-Non",2);
        double montantVerser = 0;
        Paiement paiement = null;
        if (choix==1) {
            do{
                System.out.println("Entrez le montant à verser");
                montantVerser = scanner.nextDouble();
                scanner.nextLine();
                ok = montantVerser <= 0 || montantVerser > montant;
                if (ok) {
                    System.out.println("Montant invalide");
                }
            }while(ok);
            paiement = new Paiement(montantVerser, null);
        }
        Dette dette= new Dette(montant, montantVerser, client);
        for (Detail detail : tabDetail) {
            detail.setDette(dette);
        }
        if (paiement != null){
            paiement.setDette(dette);
        }
        detteService.create(dette);
        return dette;
    }

    public void showDettesNonSoldesByClient(ClientView clientView) {
        List<Client> clients = clientView.getClientService().getClientsByAccountStatus(true);
        if (clients.isEmpty()) {
            System.out.println("Aucun client actif.");
            return;
        }
        Client client = clientView.chooseClient(clients, "avec");
        if (client == null) {
            return;
        }
        List<Dette> dettes = detteService.getDettesNonSoldesByClient(client);
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette non solde pour ce client.");
            return;
        }
        showList(dettes, "Les dettes non soldes pour le client " + client.getSurname());
    }
}
