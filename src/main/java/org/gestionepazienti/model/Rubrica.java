package org.gestionepazienti.model;

import org.gestionepazienti.db.MongoDBManager;

import java.util.List;


public class Rubrica {
    private MongoDBManager db;

    public Rubrica(String uri, String dbName, String collectionName) {
        this.db = new MongoDBManager(uri, dbName, collectionName);
    }

    public void addPaziente(Paziente paziente) {
        db.insertPaziente(paziente);
    }

    public void removePaziente(Paziente paziente) {
        db.deletePaziente(paziente);
    }

    public List<Paziente> getAllPazienti() {
        return db.getAllPazienti();
    }

    public void updatePaziente(Paziente oldPaziente, Paziente newPaziente) {
        db.updatePaziente(oldPaziente,newPaziente);
    }
    public Paziente getPazienteByAmka(String amka) {
        return db.getPazienteByAmka(amka);
    }

}