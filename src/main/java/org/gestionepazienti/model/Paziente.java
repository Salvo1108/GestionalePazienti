package org.gestionepazienti.model;
public class Paziente {

    private String nome;
    private String cognome;
    private String amka;
    private String number;
    private String email;
    private String indirizzo;

    public Paziente(){

    }


    public Paziente(String nome, String cognome, String amka, String number, String email, String indirizzo) {
        this.nome = nome;
        this.cognome = cognome;
        this.amka = amka;
        this.number = number;
        this.email = email;
        this.indirizzo = indirizzo;
    }


    // Getter e Setter per nome
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter per cognome
    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    // Getter e Setter per amka
    public String getAmka() {
        return this.amka;
    }

    public void setAmka(String amka) {
        this.amka = amka;
    }

    // Getter e Setter per number
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // Getter e Setter per email
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter e Setter per indirizzo
    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Cognome: " + cognome;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paziente paziente = (Paziente) obj;
        return nome.equals(paziente.nome) && cognome.equals(paziente.cognome);
    }


}
