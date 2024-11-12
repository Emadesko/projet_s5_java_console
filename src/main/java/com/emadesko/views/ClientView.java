package com.emadesko.views;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;
import com.emadesko.services.ClientService;

import java.util.Scanner;


public class ClientView extends View<Client>{

    private ClientService clientService;

    public ClientView(Scanner scanner, ClientService clientService) {
        super(scanner,clientService,"Aucun client");
        this.clientService = clientService;
    }

    public Client saisie(CompteView compteView){
        
        int choix;
        Boolean ok;
        String surname,telephone,adresse;
        do {
            surname=super.obligatoire("Veuillez donneer le surname du client");
            Client client=clientService.getClientBySurnom(surname);
            ok=client!=null;
            if (ok) {
                System.out.println("Un client a déja "+ client.getSurname() + " comme surnom");
            }
        } while (ok);
        
        do {
            telephone=super.obligatoire("Veuillez donneer le téléphone du client");
            Client client=clientService.getClientByTelephone(telephone);
            ok=client!=null;
            if (ok) {
                System.out.println(client.getSurname()+" a déja ce numero");
            }
        } while (ok);
        adresse=super.obligatoire("Veuillez donneer l'adresse du client");

        Client client=new Client(surname, telephone, adresse);

        choix=super.choixSousMenu("Voulez-vous lui créer un compte?\n1: Oui\n2: Non", 2);

        if (choix==1) {
            Compte compte=compteView.saisie(Role.Client,this);
            compte.setClient(client);
            client.setCompte(compte);
        }

        clientService.create(client);
        return client;
    }

    public Client chooseClient(){
        this.objet="Aucun client sans compte";
        Client client= super.select(clientService.getNonAccountedClients(), "du client", "Aucun client");
        this.objet="Aucun client";
        return client;
    }

    

}
