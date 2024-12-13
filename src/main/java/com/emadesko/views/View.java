package com.emadesko.views;

import java.util.List;
import java.util.Scanner;

import com.emadesko.services.Service;

public class View <T>{
    
    protected Scanner scanner;
    protected Service<T> service;
    protected String emptyTabTxt;
    protected String listTxt;

    public View(Scanner scanner, Service<T> service, String emptyTabTxt, String listTxt) {
        this.scanner = scanner;
        this.service = service;
        this.emptyTabTxt = emptyTabTxt;
        this.listTxt = listTxt;
    }

    
    public void showAll(){
        showList(service.getAll(),this.listTxt);
    }

    public void showList(List<T> list, String txt){
        if (list.isEmpty()) {
            System.out.println(this.emptyTabTxt + " n'existe\n");
        }else{
            if (txt == null) {
                txt = this.listTxt;
            }
            System.out.println(txt);
            list.stream().forEach(data -> System.out.println("\n" + data));
            System.out.println("\n");
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
        if (tab == null || tab.isEmpty()) {
            System.out.println(this.emptyTabTxt + " n'existe");
            return null;
        }else{
            System.out.println("#########################################");
            this.showList(tab, this.listTxt);
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
