package com.emadesko.datas.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "dettes")
public class Dette extends DetteMere{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double montantVerser;

    
    @Column(nullable = false)
    private boolean isSolde;
    
    @OneToMany(mappedBy = "dette")
    private List <Paiement> paiements = new ArrayList<>();

        
    @OneToMany(mappedBy = "dette")
    private List <Detail> details = new ArrayList<>();


    public Dette(double montant, Client client) {
        super(montant, client);
        nbrObjet++;
        this.id=nbrObjet;
        this.montantVerser = 0;
        this.isSolde = false;
    }
    public Dette() {
        //TODO Auto-generated constructor stub
    }
    @Override
    public String toString() {
        return "Dette [id=" + id + ", montant=" + montant + ", client=" + client + ", createAt=" + createAt
                + ", montantVerser=" + montantVerser + ", updateAt=" + updateAt + ", isSolde=" + isSolde + "]";
    }

}
