package com.emadesko.datas.entities;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
@Getter
@Setter
@MappedSuperclass
public abstract class DetteMere extends Entite{
    protected static int nbrObjet;

    @Column(nullable = false)
    protected double montant;

    @ManyToOne()
    @JoinColumn(nullable = false )
    protected Client client;
    @Column(nullable = false)
    protected LocalDate createAt;
    @Column(nullable = false)
    protected LocalDate updateAt;
    
    public DetteMere(double montant, Client client) {
        this.montant = montant;
        this.client = client;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
    public DetteMere() {
        //TODO Auto-generated constructor stub
    }

}
