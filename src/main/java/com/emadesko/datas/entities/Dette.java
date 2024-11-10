package com.emadesko.datas.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "dettes")
@ToString(exclude = {"client","details","paiements"}) 
public class Dette extends Entite{
    private static int nbrObjet;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double montant;
    private double montantVerser;
    
    @OneToMany(mappedBy = "dette")
    private List <Detail> details;

    @OneToMany(mappedBy = "dette")
    private List <Paiement> paiements;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;
    @Column(nullable = false)
    private LocalDate createAt;
    @Column(nullable = false)
    private LocalDate updateAt;

    public Dette() {
        nbrObjet++;
        this.id=nbrObjet;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
    
}
