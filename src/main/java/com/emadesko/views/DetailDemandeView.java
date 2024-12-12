package com.emadesko.views;

import com.emadesko.datas.entities.DetailDemande;
import com.emadesko.services.DetailDemandeService;

import java.util.Scanner;


public class DetailDemandeView extends View<DetailDemande>{

    private DetailDemandeService detailDemandeService;

    public DetailDemandeService getDetailDemandeService() {
        return detailDemandeService;
    }

    public DetailDemandeView(Scanner scanner, DetailDemandeService detailDemandeService) {
        super(scanner,detailDemandeService,"Aucun article", "Articles de la demande");
        this.detailDemandeService = detailDemandeService;
    }

}
