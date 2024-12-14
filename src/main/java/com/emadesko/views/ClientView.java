package com.emadesko.views;

import com.emadesko.datas.entities.Client;
import com.emadesko.datas.entities.Compte;
import com.emadesko.datas.enums.Role;
import com.emadesko.services.ClientService;

import java.util.List;
import java.util.Scanner;


public class ClientView extends View<Client>{

    private ClientService clientService;

    public ClientService getClientService() {
        return clientService;
    }

    public ClientView(Scanner scanner, ClientService clientService) {
        super(scanner,clientService,"Aucun client", "Liste des clients");
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

    public Client chooseClient(List <Client> tab , String txt){
        this.emptyTabTxt="Aucun client " + txt + " compte";
        Client client= selectBy(tab);
        this.emptyTabTxt="Aucun client";
        return client;
    }

    public Client selectBy(List<Client> tab) {
        if (tab.isEmpty()) {
            System.out.println(this.emptyTabTxt + " n'existe");
            return null;
        }else{
            this.showList(tab, null);
            System.out.println("Veuillez entrer le surnom du client ou 0 pour annuler");
            String telephone = scanner.nextLine();
            
            if (telephone.compareToIgnoreCase("0") != 0) {
                Client entity = this.clientService.getClientBySurnom(telephone);
                boolean ok = entity == null;
                while (ok) {
                    System.out.println("Aucun client ne correspond à ce surnom");
                    System.out.println("Veuillez entrer le surnom du client ou 0 pour annuler");
                    telephone = scanner.nextLine();
                    
                    if (telephone.compareToIgnoreCase("0") == 0) {
                        ok = false;
                    } else {
                        entity = this.clientService.getClientBySurnom(telephone);
                        ok = entity == null;
                    }
                }
                if (entity != null) {
                    System.out.println("\n" + entity + "\n");
                }
                return entity;
            }
            return null;
        }
    }

    public void searchClientByTelephone(DetteView detteview) {
        Client client = this.selectBy(this.clientService.getAll());
        if (client!= null) {
            client.setDettes(detteview.getDetteService().getDetteMeresByClient(client));
            System.out.println(client.show());
        }
    }

    public void showClientsByAccountStatus(){
        List<Client> clients=clientService.getClientsByAccountStatus(true);
        System.out.println("#########################################################");
        super.emptyTabTxt="Aucun client avec compte";
        this.showList(clients,"Clients avec compte");
        System.out.println();

        clients=clientService.getClientsByAccountStatus(false);
        System.out.println("#########################################################");
        super.emptyTabTxt="Aucun client sans compte";
        this.showList(clients,"Clients sans compte");
        System.out.println();

        super.emptyTabTxt="Aucun client";
    }
}
