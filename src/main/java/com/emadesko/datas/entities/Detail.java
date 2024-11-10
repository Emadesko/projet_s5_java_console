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
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "details")
public class Detail extends Entite{
    private static int nbrObjet;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int qte;
    @Column(nullable = false)
    private Double prix;
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

    public Detail() {
        nbrObjet++;
        this.id = nbrObjet;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
    
}
