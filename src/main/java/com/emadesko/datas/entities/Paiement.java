package com.emadesko.datas.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "paiements")
public class Paiement extends Entite{
    private static int nbrObjet;
    public Paiement() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private double montant;
    
    @Column(nullable = false)
    private LocalDate createAt;
    @Column(nullable = false)
    private LocalDate updateAt;
    
    @ManyToOne()
    @JoinColumn(nullable = false)
    private Dette dette;
    public Paiement(double montant, Dette dette) {
        nbrObjet++;
        this.id = nbrObjet;
        this.montant = montant;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
        this.dette = dette;
    }
}
