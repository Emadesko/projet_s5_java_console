package com.emadesko.views;

import com.emadesko.datas.entities.Dette;
import com.emadesko.datas.entities.Article;
import com.emadesko.datas.entities.Client;
import com.emadesko.services.DetteService;

import java.util.List;
import java.util.Scanner;


public class DetteView extends View<Dette>{

    private DetteService detteService;

    public DetteService getDetteService() {
        return detteService;
    }

    public DetteView(Scanner scanner, DetteService detteService) {
        super(scanner,detteService,"Aucune dette");
        this.detteService = detteService;
    }

    public Dette saisie(ClientView clientView, ArticleView articleView){
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
        do {
            Article article = articleView.chooseArticle();
            if (article != null) {
                
            }
            ok= super.choixSousMenu("Voulez vous ajouter un autre article? \n1- Oui \n2- Non", 2) == 1;
        } while (ok);

        return new Dette();
    }

    public List <Dette> getDettesNonSoldesByClient(Client client) {
        return null;
    }
}
