package com.emadesko.views;

import java.util.Scanner;

import com.emadesko.services.Service;

public class View <T>{
    
    protected Scanner scanner;
    protected Service<T> service;
    protected String objet;

    public View(Scanner scanner, Service<T> service, String objet) {
        this.scanner = scanner;
        this.service = service;
        this.objet = objet;
    }

    
    public void showAll(){
        if (service.getAll().isEmpty()) {
            System.out.println(this.objet+" n'existe");
        }
        service.getAll().stream().forEach(System.out::println);
    }

    public String obligatoire(String text){
        String champ;
        do {
            System.out.println(text);
            champ=scanner.nextLine();
            if (champ.trim().compareTo("")==0) {
                System.out.println("Le champ est obligatoire");
            }
        } while (champ.trim().compareTo("")==0);
        
        return champ;
    }

    public int choixSousMenu(String menuTxt,int choixSup){
        int choix;
        do {
            System.out.println(menuTxt);
            choix=scanner.nextInt();
            scanner.nextLine();
            if (choix<1 || choix>choixSup) {
                System.out.println("Veuillez faire un bon choix");
            }
        } while (choix<1 || choix>choixSup);
        
        return choix;
    }
}
