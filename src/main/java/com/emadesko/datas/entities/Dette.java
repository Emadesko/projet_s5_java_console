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
import java.util.ArrayList;
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
    @Column(nullable = false)
    private double montant;
    @Column(nullable = false)
    private double montantVerser;

    
    @Column(nullable = false)
    private boolean isSolde;
    
    @OneToMany(mappedBy = "dette")
    private List <Detail> details = new ArrayList<>();

    @OneToMany(mappedBy = "dette")
    private List <Paiement> paiements = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(nullable = false )
    private Client client;
    @Column(nullable = false)
    private LocalDate createAt;
    @Column(nullable = false)
    private LocalDate updateAt;
    public Dette(double montant, Client client) {
        nbrObjet++;
        this.id=nbrObjet;
        this.montant = montant;
        this.montantVerser = 0;
        this.isSolde = false;
        this.client = client;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
    public Dette() {
        //TODO Auto-generated constructor stub
    }

}
