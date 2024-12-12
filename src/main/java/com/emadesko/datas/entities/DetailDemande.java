package com.emadesko.datas.entities;

import javax.persistence.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table()
public class DetailDemande extends DetailMere{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Demande demande;

    @Override
    public String toString() {
        return "Article \nid= " + super.article.getId()  + "\nLibelle=" + article.getLibelle() + "\nQuantité demandée = " + qte + "\nPrix de l'article = " + prix + "\nTotal = " + total;
    }

    public DetailDemande(int qte, Double prix, Article article, Demande demande) {
        super(qte, prix, article);
        nbrObjet++;
        this.id=nbrObjet;
        this.demande = demande;
    }

    public DetailDemande() {
    }
    
    public void setDetteMere(Demande demande){
        this.demande = demande;
    }
    public Demande getDetteMere(){
        return this.demande;
    }
}
