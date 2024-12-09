package com.emadesko.views;

import com.emadesko.datas.entities.Detail;
import com.emadesko.services.DetailService;

import java.util.Scanner;


public class DetailView extends View<Detail>{

    private DetailService detailService;

    public DetailService getDetailService() {
        return detailService;
    }

    public DetailView(Scanner scanner, DetailService detailService) {
        super(scanner,detailService,"Aucun detail");
        this.detailService = detailService;
    }

}
