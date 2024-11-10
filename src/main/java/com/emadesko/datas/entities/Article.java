package com.emadesko.datas.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "articles")
public class Article extends Entite{
    private static int nbrObjet;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 30, unique = true,nullable = false)
    private String reference;
    @Column(length = 55,nullable = false)
    private String libelle;
    @Column(nullable = false)
    private double prix;
    @Column(nullable = false)
    private int qteStock;

    @OneToMany(mappedBy = "dette")
    private List <Detail> details;

    @Column(nullable = false)
    private LocalDate createAt;
    @Column(nullable = false)
    private LocalDate updateAt;

    public Article() {
        nbrObjet++;
        this.id=nbrObjet;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
