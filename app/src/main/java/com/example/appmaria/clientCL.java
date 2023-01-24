package com.example.appmaria;

import org.json.JSONObject;

public class clientCL {
    private Integer id;
    private String nom,prenom, adresse, tel, fax, email;

    public clientCL(JSONObject jObject) {
        this.id = jObject.optInt("id");
        this.nom = jObject.optString("nom");
        this.prenom = jObject.optString("prenom");
        this.adresse = jObject.optString("adresse");
        this.tel = jObject.optString("tel");
        this.fax = jObject.optString("fax");
        this.email = jObject.optString("email");
    }

    public Integer getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getAdresse() {
        return adresse;
    }
    public String getTel() {
        return tel;
    }
    public String getFax() {
        return fax;
    }
    public String getEmail() {
        return email;
    }

}

