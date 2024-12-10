package com.emadesko.views;

import com.emadesko.datas.entities.Article;
import com.emadesko.services.ArticleService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class ArticleView extends View<Article>{

    private ArticleService articleService;

    public ArticleService getArticleService() {
        return articleService;
    }

    public ArticleView(Scanner scanner, ArticleService articleService) {
        super(scanner,articleService,"Aucun Article", "Liste des articles");
        this.articleService = articleService;
    }

    public Article saisie(){
        
        Boolean ok;
        String libelle,reference;
        int qteStock;
        double prix;
        do {
            libelle=super.obligatoire("Veuillez donneer le libelle de l'article");
            Article article=articleService.getArticleByLibelle(libelle);
            ok=article!=null;
            if (ok) {
                System.out.println("Un article a déja "+ article.getLibelle() + " comme libelle");
            }
        } while (ok);
        
        reference=super.obligatoire("Veuillez donneer la réference de l'article");
        do {
            System.out.println("Veuillez donneer le prix de l'article");
            prix=scanner.nextInt();
            scanner.nextLine();
            ok=prix<0;
            if (ok) {
                System.out.println("Veuillez donner un prix correct");
            }
        } while (ok);
        do {
            System.out.println("Veuillez donneer la quantité en stock de l'article");
            qteStock=scanner.nextInt();
            scanner.nextLine();
            ok=qteStock<0;
            if (ok) {
                System.out.println("Veuillez donner une quantité correct");
            }
        } while (ok);

        Article article= new Article(reference, libelle, prix, qteStock);
        System.out.println(article.getId());
        articleService.create(article);
        return article;
    }

    public void listArticleParDisponibilité() {
        List<Article> articles = articleService.getAvailableArticles();
        System.out.println("#########################################################");
        super.emptyTabTxt="Aucun article disponible";
        super.showList(articles, "Articles disponibles");
        System.out.println();
        articles = articleService.getUnavailableArticles();
        System.out.println("#########################################################");
        super.emptyTabTxt="Aucun article indisponible";
        super.showList(articles, "Articles indisponibles");
        super.emptyTabTxt="Aucun article";
        System.out.println();
    }

    public Article chooseArticle(){
        this.emptyTabTxt="Aucun article disponible";
        Article article= selectByLibelle(articleService.getAvailableArticles());
        this.emptyTabTxt="Aucun article";
        return article;
    }

    public Article selectByLibelle(List<Article> tab) {
        if (tab.isEmpty()) {
            System.out.println(this.emptyTabTxt + " n'existe");
            return null;
        }else{
            tab.stream().forEach(System.out::println);
            System.out.println("Veuillez entrer le libelle de l'article ou 0 pour annuler");
            String libelle = scanner.nextLine();
            
            if (libelle.compareToIgnoreCase("0") != 0) {
                Article entity = this.articleService.getArticleByLibelle(libelle);
                boolean ok = entity == null;
                while (ok) {
                    System.out.println("Aucun article ne correspond à ce libelle");
                    System.out.println("Veuillez entrer le libelle de l'article ou 0 pour annuler");
                    libelle = scanner.nextLine();
                    
                    if (libelle.compareToIgnoreCase("0") == 0) {
                        ok = false;
                    } else {
                        entity = this.articleService.getArticleByLibelle(libelle);
                        ok = entity == null;
                    }
                }
                if (entity != null) {
                    System.out.println(entity);
                }
                return entity;
            }
            return null;
        }
    }

    public void updateQteStock() {
        Article article = selectByLibelle(articleService.getAll());
        if (article != null) {
            boolean ok;
            int choix=super.choixSousMenu("Voulez-vous \n1- Ajouter une quantité \n2- Modifier la quantité \n3-Annuler", 3);
            int qteStock;
            switch (choix) {
                case 1:
                    do {
                        System.out.println("Veuillez entrer la quantité à ajouter pour l'article");
                        qteStock = scanner.nextInt();
                        scanner.nextLine();
                        ok=qteStock<0;
                        if (ok) {
                            System.out.println("Veuillez donner une quantité correcte");
                        }
                    } while (ok);
                    article.setQteStock(article.getQteStock() + qteStock);
                    break;

                case 2:
                    do {
                        System.out.println("Veuillez entrer la nouvelle quantité pour l'article");
                        qteStock = scanner.nextInt();
                        scanner.nextLine();
                        ok=qteStock<0;
                        if (ok) {
                            System.out.println("Veuillez donner une quantité correcte");
                        }
                    } while (ok);
                    article.setQteStock(qteStock);
                    break;
            }
            if (choix != 3) {
                article.setUpdateAt(LocalDate.now());
                articleService.update(article);
                System.out.println("Nouvelle quantité en stock : " + article.getQteStock());
            }
        }
    }

}
