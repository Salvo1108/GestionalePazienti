package org.gestionepazienti.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.gestionepazienti.model.Paziente;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBManager {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDBManager(String uri, String dbName, String collectionName) {
        mongoClient = new MongoClient(new MongoClientURI(uri));
        database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(collectionName);
    }

    public boolean insertPaziente(Paziente paziente) {
        try {
            Document doc = new Document("nome", paziente.getNome())
                    .append("cognome", paziente.getCognome())
                    .append("amka", paziente.getAmka())
                    .append("numero", paziente.getNumber())
                    .append("email", paziente.getEmail())
                    .append("indirizzo", paziente.getIndirizzo());
            // Aggiungi altri campi del paziente qui
            collection.insertOne(doc);

            return true; // Inserimento avvenuto con successo
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Errore durante l'inserimento
        }
    }

    public void deletePaziente(Paziente paziente) {
        collection.deleteOne(eq("amka", paziente.getAmka()));
    }

    public void updatePaziente(Paziente oldPaziente, Paziente newPaziente) {
        Document newDoc = new Document("nome", newPaziente.getNome())
                .append("cognome", newPaziente.getCognome())
                .append("amka", newPaziente.getAmka())
                .append("numero", newPaziente.getNumber())
                .append("email", newPaziente.getEmail())
                .append("indirizzo",newPaziente.getIndirizzo());
        // Aggiungi altri campi del paziente qui
        collection.replaceOne(eq("amka", oldPaziente.getAmka()), newDoc);
    }

    public Paziente getPazienteByAmka(String amka) {
        Document doc = collection.find(Filters.eq("amka", amka)).first();
        if (doc != null) {
            Paziente paziente = new Paziente();
            paziente.setNome(doc.getString("nome"));
            paziente.setCognome(doc.getString("cognome"));
            paziente.setAmka(doc.getString("amka"));
            paziente.setNumber(doc.getString("number"));
            paziente.setEmail(doc.getString("email"));
            paziente.setIndirizzo(doc.getString("indirizzo"));
            // Aggiungi altri campi del paziente qui
            return paziente;
        } else {
            return null;
        }
    }

    public List<Paziente> getAllPazienti() {
        List<Paziente> pazienti = new ArrayList<Paziente>();
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            Paziente paziente = new Paziente();
            paziente.setNome(doc.getString("nome"));
            paziente.setCognome(doc.getString("cognome"));
            paziente.setAmka(doc.getString("amka"));
            paziente.setNumber(doc.getString("number"));
            paziente.setEmail(doc.getString("email"));
            paziente.setIndirizzo(doc.getString("indirizzo"));
            // Aggiungi altri campi del paziente qui
            pazienti.add(paziente);
        }
        return pazienti;
    }

    // Chiudi la connessione quando hai finito
    public void close() {
        mongoClient.close();
    }
}
