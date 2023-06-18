package org.gestionepazienti.gui;

import org.gestionepazienti.db.MongoDBManager;
import org.gestionepazienti.model.Paziente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RubricaGUI {
    private JFrame frame;
    private JPanel cards;
    private MongoDBManager db;
    private JTextField nomeField, cognomeField, amkaField, numberField, emailField, indirizzoField, amkaSearchField;

    public RubricaGUI(MongoDBManager conn) {
        this.db = conn;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Gestionale Pazienti");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main container for the different panels
        cards = new JPanel(new CardLayout());

        // Create the different panels and add to the main container
        cards.add(createMenuGUI(), "Menu");
        cards.add(createInsertGUI(), "Inserisci");
        cards.add(createSearchGUI(), "Cerca");
        cards.add(createDeleteGUI(), "Elimina");


        // Create a support panel with a border layout and add the 'cards' panel at the center
        JPanel supportPanel = new JPanel(new BorderLayout());
        supportPanel.add(cards, BorderLayout.CENTER);

        // Add a margin around the 'cards' panel
        int marginSize = 50; // Margin size in pixels
        supportPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize, marginSize, marginSize));

        frame.add(supportPanel);

        setWindowSize(frame); // Imposta le dimensioni dello schermo

        frame.setVisible(true);
    }


    private JPanel createMenuGUI() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Benvenuto nel Gestionale Pazienti");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JButton insertButton = new JButton("Inserisci Paziente");
        insertButton.addActionListener(e -> ((CardLayout) cards.getLayout()).show(cards, "Inserisci"));

        JButton searchButton = new JButton("Cerca Paziente");
        searchButton.addActionListener(e -> ((CardLayout) cards.getLayout()).show(cards, "Cerca"));

        JButton deleteButton = new JButton("Elimina Paziente");
        deleteButton.addActionListener(e -> ((CardLayout) cards.getLayout()).show(cards, "Elimina"));



        buttonPanel.add(insertButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.CENTER);


        return panel;
    }

    private JPanel createInsertGUI() {
        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        panel.add(nomeLabel);
        panel.add(nomeField);

        JLabel cognomeLabel = new JLabel("Cognome:");
        cognomeField = new JTextField();
        panel.add(cognomeLabel);
        panel.add(cognomeField);

        JLabel amkaLabel = new JLabel("AMKA:");
        amkaField = new JTextField();
        panel.add(amkaLabel);
        panel.add(amkaField);

        JLabel numberLabel = new JLabel("Number:");
        numberField = new JTextField();
        panel.add(numberLabel);
        panel.add(numberField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        panel.add(emailLabel);
        panel.add(emailField);

        JLabel indirizzoLabel = new JLabel("Indirizzo:");
        indirizzoField = new JTextField();
        panel.add(indirizzoLabel);
        panel.add(indirizzoField);

        JButton addButton = new JButton("Add Paziente");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String amka = amkaField.getText();
                String numero = numberField.getText();
                String email = emailField.getText();
                String indirizzo = indirizzoField.getText();

                // Controlla se tutti i campi sono stati inseriti
                if (nome.isEmpty() || cognome.isEmpty() || amka.isEmpty() || numero.isEmpty() || email.isEmpty() || indirizzo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Per favore, inserisci tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Paziente newPaziente = new Paziente();
                newPaziente.setNome(nome);
                newPaziente.setCognome(cognome);
                newPaziente.setAmka(amka);
                newPaziente.setNumber(numero);
                newPaziente.setEmail(email);
                newPaziente.setIndirizzo(indirizzo);

                if (db.insertPaziente(newPaziente)) {
                    JOptionPane.showMessageDialog(frame, "Paziente inserito con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);

                    // Clear the text fields after successful insertion
                    nomeField.setText("");
                    cognomeField.setText("");
                    amkaField.setText("");
                    numberField.setText("");
                    emailField.setText("");
                    indirizzoField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Errore durante l'inserimento del paziente.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(e -> ((CardLayout) cards.getLayout()).show(cards, "Menu"));

        panel.add(addButton);
        panel.add(backButton);

        return panel;
    }

    private JPanel createSearchGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Ricerca Paziente");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel);

        JPanel searchPanel = new JPanel();
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

        JLabel amkaLabel = new JLabel("Inserisci AMKA:");
        final JTextField amkaField = new JTextField();
        JButton searchButton = new JButton("Cerca");

        searchPanel.add(amkaLabel);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(amkaField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchButton);

        panel.add(Box.createVerticalStrut(20));
        panel.add(searchPanel);

        JPanel resultPanel = new JPanel(new GridBagLayout());

        String[] columnNames = {"Nome", "Cognome", "Numero", "Email", "Indirizzo"};
        final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Abilita il ridimensionamento automatico delle colonne in base al contenuto
        JScrollPane scrollPane = new JScrollPane(resultTable);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        resultPanel.add(scrollPane, gbc);

        panel.add(Box.createVerticalStrut(20));
        panel.add(resultPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amka = amkaField.getText();
                if (!amka.isEmpty()) {
                    // Svuota la tabella
                    tableModel.setRowCount(0);
                    Paziente paziente = db.getPazienteByAmka(amka);
                    if (paziente != null) {
                        Object[] rowData = {paziente.getNome(), paziente.getCognome(), paziente.getNumber(), paziente.getEmail(), paziente.getIndirizzo()};
                        tableModel.addRow(rowData);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Paziente non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Svuota il campo AMKA
                amkaField.setText("");

            }
        });


        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(e -> ((CardLayout) cards.getLayout()).show(cards, "Menu"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);

        panel.add(Box.createVerticalStrut(20));
        panel.add(buttonPanel);

        return panel;
    }


    private JPanel createDeleteGUI() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Elimina Paziente");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel deletePanel = new JPanel(new GridBagLayout());

        JLabel amkaLabel = new JLabel("Inserisci AMKA:");
        final JTextField amkaField = new JTextField();
        JButton deleteButton = new JButton("Elimina");
        JLabel pazienteInfoLabel = new JLabel();
        pazienteInfoLabel.setFont(pazienteInfoLabel.getFont().deriveFont(Font.BOLD)); // Testo in grassetto
        pazienteInfoLabel.setVerticalAlignment(JLabel.TOP);
        JScrollPane infoScrollPane = new JScrollPane(pazienteInfoLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.anchor = GridBagConstraints.WEST;
        deletePanel.add(amkaLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        deletePanel.add(amkaField, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        deletePanel.add(infoScrollPane, gbc);

        gbc.gridy = 3;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        deletePanel.add(deleteButton, gbc);

        panel.add(deletePanel, BorderLayout.CENTER);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amka = amkaField.getText();
                if (!amka.isEmpty()) {
                    Paziente paziente = db.getPazienteByAmka(amka);
                    if (paziente != null) {
                        String pazienteInfo = "<html><b>Nome:</b> " + paziente.getNome() + "<br>"
                                + "<b>Cognome:</b> " + paziente.getCognome() + "<br>"
                                + "<b>AMKA:</b> " + paziente.getAmka() + "<br>"
                                + "<b>Numero:</b> " + paziente.getNumber() + "<br>"
                                + "<b>Email:</b> " + paziente.getEmail() + "<br>"
                                + "<b>Indirizzo:</b> " + paziente.getIndirizzo() + "</html>";
                        pazienteInfoLabel.setText(pazienteInfo);

                        int confirm = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare il paziente?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            db.deletePaziente(paziente);
                            JOptionPane.showMessageDialog(frame, "Paziente eliminato con successo.", "Eliminazione Completata", JOptionPane.INFORMATION_MESSAGE);
                            amkaField.setText("");
                            pazienteInfoLabel.setText("");
                        } else {
                            amkaField.setText("");
                            pazienteInfoLabel.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Paziente non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(e -> ((CardLayout) cards.getLayout()).show(cards, "Menu"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }







    public JFrame getFrame() {
        return frame;
    }


    private void setWindowSize(JFrame frame) {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
