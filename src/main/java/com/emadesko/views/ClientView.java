package com.emadesko.views;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;
import com.emadesko.services.ClientService;

import java.util.List;
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
        Client client= selectByTelephone(clientService.getNonAccountedClients());
        this.objet="Aucun client";
        return client;
    }

    public Client selectByTelephone(List<Client> tab) {
        if (tab.isEmpty()) {
            System.out.println(this.objet + " n'existe");
            return null;
        }else{
            tab.stream().forEach(System.out::println);
            System.out.println("Veuillez entrer le téléphone du client ou 0 pour annuler");
            String telephone = scanner.nextLine();
            
            if (telephone != "0") {
                Client entity = this.clientService.getClientByTelephone(telephone);
                boolean ok = entity == null;
                while (ok) {
                    System.out.println("Aucun client ne correspond à ce téléphone");
                    System.out.println("Veuillez entrer le téléphone du client ou 0 pour annuler");
                    telephone = scanner.nextLine();
                    
                    if (telephone == "0") {
                        ok = false;
                    } else {
                        entity = this.clientService.getClientByTelephone(telephone);
                        ok = entity == null;
                    }
                }
                if (entity != null) {
                    System.out.println(entity.show());
                }
                return entity;
            }
            return null;
        }
    }
}
