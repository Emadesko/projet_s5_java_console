package com.emadesko.datas.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "details")
public class Detail extends Entite{
    @Override
    public String toString() {
        return "Article \nid= " + article.getId()  + "\nLibelle=" + article.getLibelle() + "\nQuantité prise = " + qte + "\nPrix de vente = " + prix + "\nTotal = " + total;
    }

    private static int nbrObjet;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int qte;
    @Column(nullable = false)
    private Double prix;
    @Column(nullable = false)
    private Double total;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Article article;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Dette dette;

    @Column(nullable = false)
    private LocalDate createAt;
    @Column(nullable = false)
    private LocalDate updateAt;

    public Detail(int qte, Double prix, Article article, Dette dette) {
        nbrObjet++;
        this.id = nbrObjet;
        this.qte = qte;
        this.prix = prix;
        this.total = prix * qte;
        this.article = article;
        this.dette = dette;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

    public Detail() {
        
    }
    
}
