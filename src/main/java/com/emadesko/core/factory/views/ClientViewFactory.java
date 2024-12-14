package com.emadesko.core.factory.views;

import java.util.Scanner;

import com.emadesko.core.factory.services.ClientServiceFactory;
import com.emadesko.core.services.YamlService;
import com.emadesko.views.ClientView;

public abstract class ClientViewFactory {

    private static ClientView view;

    public static ClientView getInstance(Scanner scanner, YamlService yamlService) {
        if (view == null) {
            view = new ClientView(scanner,ClientServiceFactory.getInstance(yamlService));
        }
        return view;
    }

}
