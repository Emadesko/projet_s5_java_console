package com.emadesko.core.factory.views;

import java.util.Scanner;

import com.emadesko.core.factory.services.DetteServiceFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.views.DetteView;

public abstract class DetteViewFactory {

    private static DetteView view;

    public static DetteView getInstance(Scanner scanner, YamlService yamlService) {
        if (view == null) {
            view = new DetteView(scanner, DetteServiceFactory.getInstance(yamlService));
        }
        return view;
    }

}
