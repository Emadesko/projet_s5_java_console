package com.emadesko.datas.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.emadesko.datas.enums.Etat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "demandes")
public class Demande extends DetteMere{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Etat etat;

        
    @OneToMany(mappedBy = "demande")
    private List <DetailDemande> detailsDemandes = new ArrayList<>();

    
    public Demande(double montant, Client client) {
        super(montant, client);
        nbrObjet++;
        this.id=nbrObjet;
        this.etat = Etat.EnCours;
    }
    public Demande() {
        //TODO Auto-generated constructor stub
    }
    @Override
    public String toString() {
        return "Demande [id=" + id + ", montant=" + montant + ", client=" + client + ", createAt=" + createAt + ", updateAt="
                + updateAt + ", etat=" + etat + "]";
    }

}
