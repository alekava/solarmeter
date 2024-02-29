package com.alekava.meters;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;




public class MeterGui  extends JPanel
implements ActionListener {
	private static  final String NEWLINE = "\n";
	JFrame frame;
    JButton openFolderConsumiButton,openFolderImmissioniButton, elaboraButton;
    JLabel lbProduzione;
    JTextField fProduzione;
	JFileChooser fcImmissioni;
	JFileChooser fcConsumi;
	File folderImmissioni;
	File folderPrelievi;
    JTextArea log;
    JCheckBox cbVerboseLog;
    JTextField tfBatteryMin;
    JTextField tfBatteryMax;
    JTextField tfCostoKWh;
    
	public MeterGui() {
		 super(new BorderLayout());
		 
	        //Create the log first, because the action listeners
	        //need to refer to it.
	        log = new JTextArea(30,70);
	        log.setMargin(new Insets(5,5,5,5));
	        log.setEditable(false);
	        JScrollPane logText = new JScrollPane(log);
	 
	        //Create a file chooser
	        fcConsumi = new JFileChooser();
	        fcImmissioni = new JFileChooser();

	        fcConsumi.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        fcImmissioni.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	        //Create the open button.  We use the image from the JLF
	        //Graphics Repository (but we extracted it from the jar).
	        openFolderConsumiButton = new JButton("Seleziona cartella consumi");
	        openFolderConsumiButton.addActionListener(this);
	 

	        openFolderImmissioniButton = new JButton("Seleziona cartella immissioni");
	        openFolderImmissioniButton.addActionListener(this);
	        
	        //Create the save button.  We use the image from the JLF
	        //Graphics Repository (but we extracted it from the jar).
	        elaboraButton = new JButton("Elabora");
	        elaboraButton.addActionListener(this);
	 
	        //For layout purposes, put the buttons in a separate panel
	        JPanel buttonPanel = new JPanel(); //use FlowLayout
	        buttonPanel.add(openFolderConsumiButton);
	        buttonPanel.add(openFolderImmissioniButton);
	        buttonPanel.add(elaboraButton);
	        
	        
	        JPanel pannelloInput = new JPanel(); //use FlowLayout
	        
	        lbProduzione = new JLabel("Produzione del periodo di riferimento (KWh interi, no decimali)");
	        fProduzione = new JTextField("");
	        fProduzione.setSize(100, 30);
	        fProduzione.setColumns(6);
	        fProduzione.setMinimumSize(new Dimension(200,30));
	        
	        cbVerboseLog = new JCheckBox("Mostra Log Completo", true);
	        
	        JLabel lbBatteryMin = new JLabel("Valore minimo batteria (intero)");
	        JLabel lbBatteryMax = new JLabel("Valore massimo batteria (intero)");
	        JLabel lbCostoKWh = new JLabel("CostoKWh (#.##)");
	        tfBatteryMin = new JTextField(4);
	        tfBatteryMax = new JTextField(4);
	        tfCostoKWh = new JTextField(4);
	        
	        pannelloInput.add(lbProduzione);
	        pannelloInput.add(fProduzione);

	        
	        pannelloInput.add(lbBatteryMin);
	        pannelloInput.add(tfBatteryMin);

	        pannelloInput.add(lbBatteryMax);
	        pannelloInput.add(tfBatteryMax);
	        
	        pannelloInput.add(lbCostoKWh);
	        pannelloInput.add(tfCostoKWh);
	        
	        pannelloInput.add(cbVerboseLog);
	        
	        
	        
	        
	        //Add the buttons and the log to this panel.
	        add(buttonPanel, BorderLayout.PAGE_START);
	        add(pannelloInput);
	        //add(pannelloInput);
	        add(logText, BorderLayout.SOUTH);
	}
	
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Solar Meter GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new MeterGui());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		 //Handle open button action.
        if (e.getSource() == openFolderConsumiButton) {
            int returnVal = fcConsumi.showOpenDialog(MeterGui.this);
           // log.setText("");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                folderPrelievi = fcConsumi.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Cartella Consumi: " + folderPrelievi.getAbsolutePath() + NEWLINE);
            } else {
               // log.append("Open command cancelled by user." + NEWLINE);
            }
            log.setCaretPosition(log.getDocument().getLength());
 
        //Handle save button action.
        }else if (e.getSource() == openFolderImmissioniButton) {
            int returnVal = fcImmissioni.showOpenDialog(MeterGui.this);
         //   log.setText("");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	folderImmissioni = fcImmissioni.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Cartella Immissioni: " + folderImmissioni.getAbsolutePath() + NEWLINE);
            } else {
                //log.append("Open command cancelled by user." + NEWLINE);
            }
            log.setCaretPosition(log.getDocument().getLength());
 
        } 
        
        else if (e.getSource() == elaboraButton) {
			try {
				int produzione = Integer.valueOf(fProduzione.getText());
				int batteryMin = Integer.valueOf(tfBatteryMin.getText());
				int batteryMax = Integer.valueOf(tfBatteryMax.getText());
				double costoKWh = Double.parseDouble(tfCostoKWh.getText().replaceAll(",", "."));
				
				log.setText("");
				
				Elaboratore.go(folderPrelievi, folderImmissioni, log, produzione, batteryMin, batteryMax, costoKWh, cbVerboseLog.isSelected());
				
			} catch (NumberFormatException ex) {
				log.append("CONTROLLARE IL FORMATO DEI CAMPI DI INPUT "+NEWLINE);
				for (int k=0; k< Math.min(ex.getStackTrace().length, 5); k++) {
					log.append((ex.getStackTrace()[k]).toString()+NEWLINE);
				}
				log.append("............................."+NEWLINE);
			}catch (Exception ex) {
				
				for (int k=0; k< Math.min(ex.getStackTrace().length, 10); k++) {
					log.append((ex.getStackTrace()[k]).toString()+NEWLINE);
				}
				log.append("............................."+NEWLINE);
			}

            log.setCaretPosition(log.getDocument().getLength());
        }
		
	}
}
