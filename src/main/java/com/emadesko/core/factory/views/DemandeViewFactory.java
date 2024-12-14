package com.emadesko.core.factory.views;

import java.util.Scanner;

import com.emadesko.core.factory.services.DemandeServiceFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.views.DemandeView;

public abstract class DemandeViewFactory {

    private static DemandeView view;

    public static DemandeView getInstance(Scanner scanner, YamlService yamlService) {
        if (view == null) {
            view = new DemandeView(scanner,DemandeServiceFactory.getInstance(yamlService));
        }
        return view;
    }

}
