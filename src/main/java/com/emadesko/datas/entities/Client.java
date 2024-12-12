package com.emadesko.datas.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"dettes","compte","demandes"})
@Entity
@Table(name = "clients" )
public class Client extends Entite{
    
    public String show() {
        return "Client [id=" + id + ", surname=" + surname + ", telephone=" + telephone + ", adresse=" + adresse
                + ", createAt=" + createAt + ", updateAt=" + updateAt + ", compte=" + compte + ", dettes=" + dettes
                + "]";
    }

    public Client(String surname, String telephone, String adresse) {
        nbrObjet++;
        this.id=nbrObjet;
        this.surname = surname;
        this.telephone = telephone;
        this.adresse = adresse;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

    public Client() {
        //TODO Auto-generated constructor stub
    }

    private static int nbrObjet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 30, unique = true,nullable = false)
    private String surname;
    @Column(length = 11, unique = true,nullable = false)
    private String telephone;
    @Column(length = 255,nullable = false)
    private String adresse;

    @Column(nullable = false)
    private LocalDate createAt;
    @Column(nullable = false)
    private LocalDate updateAt;
    // 
    @JoinColumn()
    @OneToOne()
    private Compte compte;

    @OneToMany(mappedBy = "client")
    private List<Dette> dettes= new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Demande> demandes= new ArrayList<>();
}