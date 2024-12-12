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
@Table(name = "details")
public class Detail extends DetailMere{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Dette dette;

    @Override
    public String toString() {
        return "Article \nid= " + super.article.getId()  + "\nLibelle=" + article.getLibelle() + "\nQuantité prise = " + qte + "\nPrix de vente = " + prix + "\nTotal = " + total;
    }

    public Detail(int qte, Double prix, Article article, Dette dette) {
        super(qte, prix, article);
        nbrObjet++;
        this.id=nbrObjet;
        this.dette = dette;
    }

    public Detail() {
    }

    public Dette getDetteMere(){
        return this.dette;
    }
    
}
