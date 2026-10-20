package com.mycompany.windowmanager;

import java.math.BigDecimal;

// Représente une offre d'emploi
public class Offer {
    int id;
    BigDecimal remuneration;
    String titre;
    String offre;
    int ownerId;

    public Offer(int id, BigDecimal remuneration,
                 String titre, String offre, int ownerId) {
        this.id = id;
        this.remuneration = remuneration;
        this.titre = titre;
        this.offre = offre;
        this.ownerId = ownerId;
    }
}
