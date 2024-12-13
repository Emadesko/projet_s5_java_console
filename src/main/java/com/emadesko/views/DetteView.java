package com.emadesko.views;

import com.emadesko.datas.entities.Dette;
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
        super(scanner, detteService, "Aucune dette", "Liste des dettes");
        this.detteService = detteService;
    }

    public Detail check(List<Detail> tab, Article article) {
        return tab.stream().filter(detail -> detail.getArticle().getId() == article.getId()).findFirst().orElse(null);
    }

    public Dette saisie(ClientView clientView, ArticleView articleView, PaiementView paiementView, DetailView detailView) {
        List<Client> clients = clientView.getClientService().getAll();
        if (clients.isEmpty()) {
            System.out.println("Aucun client.");
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
                Detail detail = this.check(tabDetail, article);
                montant += article.getPrix() * qte;
                if (detail == null) {
                    tabDetail.add(new Detail(qte, article.getPrix(), article, null));
                } else {
                    detail.setQte(detail.getQte() + qte);
                    detail.setTotal(detail.getQte() * detail.getPrix());
                }
                article.setQteStock(article.getQteStock() - qte);
                articleView.getArticleService().update(article);
            }
            ok = super.choixSousMenu("Voulez vous ajouter un autre article? \n1- Oui \n2- Non", 2) == 1;
        } while (ok);
        Dette dette= new Dette(montant, client);
        detteService.create(dette);
        client.getDettes().add(dette);
        int choix=super.choixSousMenu("Voulez vous payer une partie du montant?\n1-Oui\n2-Non",2);
        if (choix==1) {
            paiementView.saisie(this, clientView, dette);
        }
        for (Detail detail : tabDetail) {
            detail.setDette(dette);
            dette.getDetails().add(detail);
            detailView.getDetailService().create(detail);
        }
        return dette;
    }

    public List <Dette> getDettesNonSoldesByClient(ClientView clientView) {
        List<Client> clients = clientView.getClientService().getAll();
        if (clients.isEmpty()) {
            System.out.println("Aucun client .");
            return null;
        }
        Client client = clientView.chooseClient(clients, "avec");
        if (client == null) {
            return null;
        }
        List<Dette> dettes = detteService.getDettesNonSoldesByClient(client);
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette non solde pour ce client.");
            return null;
        }
        return dettes;
    }

    public void showDettesDettesNonSoldesByClientWith(ClientView clientView, PaiementView paiementView, DetailView detailView, Client client) {
        List<Dette> dettes;
        if (client == null) {
            dettes = this.getDettesNonSoldesByClient(clientView);
        }else{
            dettes = this.detteService.getDettesNonSoldesByClient(client);
            client.setDettes(dettes);
        }
        if (dettes!= null) {
            System.out.println("###############################################");
            this.showList(dettes, "Liste des dettes non soldées");
            if (!dettes.isEmpty()) {
                int choix = super.choixSousMenu("1- Voir les paiements d'une dette \n2- Voir les articles d'une dette \n3- Retour", 3);
                while (choix != 3){
                    Dette dette = super.select(dettes, "de la dette", "Aucune dette");
                    if (dette!= null) {
                        if (choix == 1) {
                            System.out.println("###############################################");
                            paiementView.showList(paiementView.getPaiementService().getPaiementsByDette(dette), null);;
                        } else if (choix == 2) {
                            System.out.println("###############################################");
                            detailView.showList(detailView.getDetailService().getDetailsByDette(dette), null);;
                        }
                        choix = super.choixSousMenu("1- Voir les paiements d'une dette \n2- Voir les articles d'une dette \n3- Retour", 3);
                    }
                }
            }
        }
       
    }
}
