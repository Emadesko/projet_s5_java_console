package com.emadesko;

import java.util.Scanner;

import com.emadesko.datas.repositories.ClientRepository;
import com.emadesko.datas.repositories.CompteRepository;
import com.emadesko.datas.repositories.db.CompteRepositoryDb;
import com.emadesko.datas.repositories.db.ClientRepositoryDb;
import com.emadesko.datas.repositories.jpa.ClientRepositoryJpa;
import com.emadesko.datas.repositories.jpa.CompteRepositoryJpa;
import com.emadesko.datas.repositories.list.ClientRepositoryList;
import com.emadesko.datas.repositories.list.CompteRepositoryList;
import com.emadesko.services.ClientService;
import com.emadesko.services.CompteService;
import com.emadesko.views.ClientView;
import com.emadesko.views.CompteView;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        CompteRepository compteRepository = new CompteRepositoryJpa();
        CompteService compteService = new CompteService(compteRepository);
        CompteView compteView = new CompteView(scanner, compteService);

        ClientRepository clientRepository = new ClientRepositoryJpa();
        ClientService clientService = new ClientService(clientRepository);
        ClientView clientView = new ClientView(scanner, clientService, compteView);

        int choix;

        do {
            System.out.println("1: Créer un client");
            System.out.println("2: Lister les clients");
            System.out.println("3: Créer un compte");
            System.out.println("4: Lister les comptes");
            System.out.println("5: Quitter");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    clientView.saisie();
                    break;

                case 2:
                    clientView.showAll();
                    break;

                case 3:
                    compteView.saisie(null);
                    break;

                case 4:
                    compteView.showAll();
                    break;

                case 5:

                    break;

                default:
                    System.out.println("Veuillez faire un bon choix!!");
                    break;
            }
        } while (choix != 5);
        scanner.close();
    }
}