package com.emadesko.core.factory.views;

import java.util.Scanner;

import com.emadesko.core.factory.services.PaiementServiceFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.views.PaiementView;

public abstract class PaiementViewFactory {

    private static PaiementView view;

    public static PaiementView getInstance(Scanner scanner, YamlService yamlService) {
        if (view == null) {
            view = new PaiementView(scanner, PaiementServiceFactory.getInstance(yamlService));
        }
        return view;
    }

}
