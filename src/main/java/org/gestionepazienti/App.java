package org.gestionepazienti;

import org.gestionepazienti.db.MongoDBManager;
import org.gestionepazienti.gui.RubricaGUI;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class App {
    public static void main(String[] args) {
        final MongoDBManager manager = new MongoDBManager("mongodb://localhost:27017", "Pazienti", "Anagrafica");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RubricaGUI rubricaGUI = new RubricaGUI(manager);
                rubricaGUI.getFrame().addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        manager.close();
                    }
                });
            }
        });
    }
}
