package com.emadesko.views;

import java.util.List;
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

    
    public void showAll(String listTxt){
        showList(service.getAll(),listTxt);
    }

    public void showList(List<T> list, String listTxt){
        if (list.isEmpty()) {
            System.out.println(this.objet + " n'existe");
        }else{
            System.out.println(listTxt);
            list.stream().forEach(System.out::println);
        }
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

    public T select(List<T> tab, String entityTxt, String entityNone) {
        if (tab.isEmpty()) {
            System.out.println(this.objet + " n'existe");
            return null;
        }else{
            tab.stream().forEach(System.out::println);
            System.out.println("Veuillez entrer l'Id " + entityTxt + " ou 0 pour annuler");
            int id = scanner.nextInt();
            scanner.nextLine();
            if (id != 0) {
                T entity = this.service.getById(tab, id);
                boolean ok = entity == null;
                while (ok) {
                    System.out.println(entityNone + " ne correspond à ce Id");
                    System.out.println("Veuillez entrer l'Id " + entityTxt + " ou 0 pour annuler");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    if (id == 0) {
                        ok = false;
                    } else {
                        entity = this.service.getById(tab, id);
                        ok = entity == null;
                    }
                }
                return entity;
            }
            return null;
        }
    }
}
