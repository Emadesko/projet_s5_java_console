package com.emadesko.datas.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.emadesko.datas.enums.Role;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = { "client","password"})
@Entity
@Table(name = "comptes")
public class Compte extends Entite{
    private static int nbrObjet;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 30, unique = true,nullable = false)
    private String login;
    @Column(length = 80,nullable = false)
    private String password;
    @Column(length = 30, nullable = false)
    private String nom;
    @Column(length = 30, nullable = false)
    private String prenom;
    @Column(length = 30, unique = true,nullable = false)
    private String email;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private LocalDate createAt;
    @Column(nullable = false)
    private LocalDate updateAt;

    @JoinColumn()
    @OneToOne(mappedBy = "compte")
    private Client client;

    public Compte(String login, String email, String password, String nom, String prenom, Role role) {
      nbrObjet++;
      this.id=nbrObjet;
      this.login = login;
      this.password = password;
      this.nom = nom;
      this.prenom = prenom;
      this.email = email;
      this.role = role;
      this.isActive = true;
      this.createAt = LocalDate.now();
      this.updateAt = LocalDate.now();
    }

    public Compte() {
        //TODO Auto-generated constructor stub
    }
}
