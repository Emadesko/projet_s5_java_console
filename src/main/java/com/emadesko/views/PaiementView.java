package com.emadesko.views;

import com.emadesko.datas.entities.Paiement;
import com.emadesko.datas.entities.Dette;
import com.emadesko.services.PaiementService;

import java.util.List;
import java.util.Scanner;


public class PaiementView extends View<Paiement>{

    private PaiementService paiementService;

    public PaiementService getPaiementService() {
        return paiementService;
    }

    public PaiementView(Scanner scanner, PaiementService paiementService) {
        super(scanner,paiementService,"Aucun paiement" ,"Liste des paiements");
        this.paiementService = paiementService;
    }

    public Paiement saisie(DetteView detteView,ClientView clientView, Dette dette){
        if (dette == null) {
            List<Dette> dettes = detteView.getDettesNonSoldesByClient(clientView);
            if (dettes == null || dettes.isEmpty()) {
                return null;
            }
            dette = detteView.select(dettes, "de la dette", "Aucune dette" );
            if (dette == null) {
                return null;
            }
        }
        double montantVerser, montantRestant = dette.getMontant()-dette.getMontantVerser();
        boolean ok;
        do{
            System.out.println("Entrez le montant à verser");
            montantVerser = scanner.nextDouble();
            scanner.nextLine();
            ok = montantVerser <= 0 || montantVerser > montantRestant;
            if (ok) {
                System.out.println("Montant invalide \nIl reste à payer " + montantRestant);
            }
        }while(ok);

        Paiement paiement = new Paiement(montantVerser, dette);
        dette.getPaiements().add(paiement);
        dette.setMontantVerser(dette.getMontantVerser() + montantVerser);
        if (dette.getMontant() == dette.getMontantVerser()) {
            dette.setSolde(true);
        }
        this.paiementService.create(paiement);
        detteView.getDetteService().update(dette);
        return paiement;
    }
}
