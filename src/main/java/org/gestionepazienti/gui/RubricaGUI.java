package org.gestionepazienti.gui;

import org.gestionepazienti.db.MongoDBManager;
import org.gestionepazienti.model.Paziente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RubricaGUI {
    private JPanel mainPanel;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField amkaField;
    private JTextField numberField;
    private JTextField emailField;
    private JTextField indirizzoField;
    private MongoDBManager db;

    public RubricaGUI(MongoDBManager conn) {
        this.db = conn;
        openMenuGUI();
    }
    private void openMenuGUI() {
        final JFrame frame = new JFrame("Gestionale Pazienti");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Benvenuto nel Gestionale Pazienti");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JButton insertButton = new JButton("Inserisci Paziente");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiudi la finestra corrente
                openInsertGUI(); // Apri la schermata di inserimento
            }
        });

        JButton searchButton = new JButton("Cerca Paziente");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiudi la finestra corrente
                openSearchGUI(); // Apri la schermata di ricerca
            }
        });

        buttonPanel.add(insertButton);
        buttonPanel.add(searchButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        JLabel messageLabel = new JLabel("Scegli cosa fare dal menu sotto");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(messageLabel, BorderLayout.SOUTH);

        frame.add(panel);

        setWindowSize(frame, 50, 50); // Imposta le dimensioni al 50% dello schermo

        frame.setVisible(true);
    }


    private void openInsertGUI() {
        final JFrame frame = new JFrame("Inserisci Paziente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

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
                // Codice per l'inserimento del paziente
            }
        });

        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiudi la finestra corrente
                openMenuGUI(); // Apri la schermata del menu
            }
        });

        panel.add(addButton);
        panel.add(backButton);
        frame.add(panel, BorderLayout.CENTER);

        setWindowSize(frame, 50, 50); // Imposta le dimensioni al 50% dello schermo

        frame.setVisible(true);
    }
    private void openSearchGUI() {
        final JFrame frame = new JFrame("Cerca Paziente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Ricerca Paziente");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new BorderLayout());

        JLabel amkaLabel = new JLabel("Inserisci AMKA:");
        final JTextField amkaField = new JTextField();
        JButton searchButton = new JButton("Cerca");

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputPanel.add(amkaLabel);
        inputPanel.add(amkaField);

        searchPanel.add(inputPanel, BorderLayout.NORTH);
        searchPanel.add(searchButton, BorderLayout.CENTER);

        panel.add(searchPanel, BorderLayout.CENTER);

        JPanel resultPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Nome", "Cognome", "AMKA", "Numero", "Email", "Indirizzo"};
        final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        resultPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(resultPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amka = amkaField.getText();
                if (!amka.isEmpty()) {
                    Paziente paziente = db.getPazienteByAmka(amka);
                    if (paziente != null) {
                        Object[] rowData = {paziente.getNome(), paziente.getCognome(), paziente.getAmka(), paziente.getNumber(), paziente.getEmail(), paziente.getIndirizzo()};
                        tableModel.addRow(rowData);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Paziente non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Chiudi la finestra corrente
                openMenuGUI(); // Apri la schermata del menu
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);

        setWindowSize(frame, 50, 50); // Imposta le dimensioni al 50% dello schermo

        frame.pack();
        frame.setVisible(true);
    }






    private void setWindowSize(JFrame frame, int widthPercentage, int heightPercentage) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width * widthPercentage / 100;
        int height = screenSize.height * heightPercentage / 100;
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
    }

    public JPanel getPanel() {
        return mainPanel;
    }



}
