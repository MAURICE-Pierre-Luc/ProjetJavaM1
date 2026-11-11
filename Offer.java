package com.mycompany.windowmanager;
public class Offer {
    int id;
    double remuneration;
    String titre;
    String offre;
    int ownerId;

    public Offer(int id, double remuneration, String titre, String offre, int ownerId) {
        this.id = id;
        this.remuneration = remuneration;
        this.titre = titre;
        this.offre = offre;
        this.ownerId = ownerId;
    }
}
