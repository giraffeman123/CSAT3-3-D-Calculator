/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoservicio;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Elliot
 */
public class MainGUI extends JFrame implements ActionListener{

    JPanel promediosporDiaPanelUnaFila,promediosporDiaPanelDosFilas,promediosporDiaPanelTresFilas,
            varianzaDesviacionporDiaPanel,valoresMensualesPanel;
    JMenuItem promediosporDiaUnaFilaIt,promediosporDiaDosFilasIt,promediosporDiaTresFilasIt,varianzaDesviacionporDiaIt,
            valoresMensualesIt;
    CsvRawFileReader CsvRawFileManager;
    CsvFileVarianceAndStandardDeviation VarianzayDesviacion;
    CvsMonthFileVarianceAndStandardDeviationAndKAndC MonthFile;
    
    
    public MainGUI(){
        CsvRawFileManager = new CsvRawFileReader();
        VarianzayDesviacion = new CsvFileVarianceAndStandardDeviation();
        MonthFile = new CvsMonthFileVarianceAndStandardDeviationAndKAndC();
        
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(1,1,1050,1000);
        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);
        JMenu menu = new JMenu("Menu");
        JMenu porDiaMenu = new JMenu("Calcular Promedios por Dia");
        promediosporDiaUnaFilaIt = new JMenuItem("1 Ultima Fila");
        promediosporDiaUnaFilaIt.addActionListener(this);
        promediosporDiaDosFilasIt = new JMenuItem("2 Ultimas Filas");
        promediosporDiaDosFilasIt.addActionListener(this);
        promediosporDiaTresFilasIt = new JMenuItem("3 Ultimas Filas");
        promediosporDiaTresFilasIt.addActionListener(this);        
        varianzaDesviacionporDiaIt = new JMenuItem("Calcular Varianza|Desviacion por Dia");
        varianzaDesviacionporDiaIt.addActionListener(this);
        valoresMensualesIt = new JMenuItem("Calcular Valores Mensuales");
        valoresMensualesIt.addActionListener(this);
        
        jmb.add(menu);
        menu.add(porDiaMenu);
        porDiaMenu.add(promediosporDiaUnaFilaIt);
        porDiaMenu.add(promediosporDiaDosFilasIt);
        porDiaMenu.add(promediosporDiaTresFilasIt);
        menu.add(varianzaDesviacionporDiaIt);
        menu.add(valoresMensualesIt);
        
        add(promediosporDia1Fila());
        add(promediosporDia2Filas());
        add(promediosporDia3Filas());
        add(varianzaDesviacionporDia());
        add(valoresMensuales());
    }
    
    public JPanel promediosporDia1Fila(){        
        promediosporDiaPanelUnaFila = new JPanel();
        promediosporDiaPanelUnaFila.setVisible(false);        
        promediosporDiaPanelUnaFila.setLayout(new GridLayout(3,0));
        promediosporDiaPanelUnaFila.setBounds(1,1,1000,500);
        
        JFileChooser choix = new JFileChooser();
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choix.setMultiSelectionEnabled(true) ;      
        
        JLabel titulo = new JLabel("Promedios por dia con 1 fila");
        JTextArea textoValores = new JTextArea();
        JScrollPane scroll = new JScrollPane(textoValores);      
        
        JButton cargarArchivos = new JButton("Cargar Archivos");
        
        cargarArchivos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textoValores.setText(null);
                int retour = choix.showOpenDialog(new JFrame());
                if(retour == JFileChooser.APPROVE_OPTION){
                   // des fichiers ont été choisis 
                   // (sortie par OK)
                   File[] fs=choix.getSelectedFiles();
                   if(fs.length > 1){
                        String[] dia = new String[fs.length];
                        String[] fileNamePromedios = new String[fs.length]; 
                        textoValores.append("Numero de Archivos: " +fs.length+"\n");
                        
                        // Se obtienen los nombres de los archivos
                        for( int i = 0; i<fs.length; ++i){ 
                           textoValores.append( i + ".-" +"Nombre de Archivo Crudo: " +fs[i].getName()+"\n");
                           // nom du fichier 
                           dia[i] = fs[i].getAbsolutePath();                         
                        }
                        for(int i = 0; i<fs.length-1; i++){
                           fileNamePromedios[i] = "Promedios" + fs[i+1].getName();   
                           textoValores.append("  " +"Nombre de Archivo con Promedios: " +
                                   fileNamePromedios[i]+"\n");                              
                        }
                        
                        // Se procesa la informacion
                        for(int i = 0; i<fs.length-1;i++){
                           
                            // SE NOMBRA AL ARCHIVO DE PROMEDIOS
                            CsvRawFileManager.setFile_Name_Promedios(fileNamePromedios[i]);

                            // SE LEE ULTIMA FILA DEL ARCHIVO ANTERIOR
                            CsvRawFileManager.initiateAllOtherFiles();
                            CsvRawFileManager.getLastRowOfFile(dia[i]);       


                            // SE SACAN LOS PROMEDIOS
                            CsvRawFileManager.readCsvFile(dia[i+1]); 
                        }
                        JOptionPane.showMessageDialog(null,"Listo","",JOptionPane.INFORMATION_MESSAGE);                                               
                   }else{
                        JOptionPane.showMessageDialog(null,"No se seleccionaron mas de 1 archivo","WARNING",JOptionPane.WARNING_MESSAGE);                       
                   }
                }               
            }
        });
        
        promediosporDiaPanelUnaFila.add(titulo);
        promediosporDiaPanelUnaFila.add(cargarArchivos);
        promediosporDiaPanelUnaFila.add(scroll);
        return promediosporDiaPanelUnaFila;
    }
    
    public JPanel promediosporDia2Filas(){        
        String[] dia,fileNamePromedios;        
        promediosporDiaPanelDosFilas = new JPanel();
        promediosporDiaPanelDosFilas.setVisible(false);        
        promediosporDiaPanelDosFilas.setLayout(new GridLayout(3,0));
        promediosporDiaPanelDosFilas.setBounds(1,1,1000,500);
        
        JFileChooser choix = new JFileChooser();
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choix.setMultiSelectionEnabled(true) ;      
        
        JLabel titulo = new JLabel("Promedios por dia con 2 filas");
        JTextArea textoValores = new JTextArea();
        JScrollPane scroll = new JScrollPane(textoValores);      
        
        JButton cargarArchivos = new JButton("Cargar Archivos");
        
        cargarArchivos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textoValores.setText(null);
                int retour = choix.showOpenDialog(new JFrame());
                if(retour == JFileChooser.APPROVE_OPTION){
                   // des fichiers ont été choisis 
                   // (sortie par OK)
                   File[] fs=choix.getSelectedFiles();
                   if(fs.length > 1){
                        String[] dia = new String[fs.length];
                        String[] fileNamePromedios = new String[fs.length]; 
                        textoValores.append("Numero de Archivos: " +fs.length+"\n");
                        
                        // Se obtienen los nombres de los archivos
                        for( int i = 0; i<fs.length; ++i){ 
                           textoValores.append( i + ".-" +"Nombre de Archivo Crudo: " +fs[i].getName()+"\n");
                           // nom du fichier 
                           dia[i] = fs[i].getAbsolutePath();                         
                        }
                        for(int i = 0; i<fs.length-1; i++){
                           fileNamePromedios[i] = "Promedios" + fs[i+1].getName();   
                           textoValores.append("  " +"Nombre de Archivo con Promedios: " +
                                   fileNamePromedios[i]+"\n");                              
                        }
                        
                        // Se procesa la informacion
                        for(int i = 0; i<fs.length-1;i++){
                           
                            // SE NOMBRA AL ARCHIVO DE PROMEDIOS
                            CsvRawFileManager.setFile_Name_Promedios(fileNamePromedios[i]);

                            // SE LEE ULTIMA FILA DEL ARCHIVO ANTERIOR
                            CsvRawFileManager.initiateAllOtherFiles();
                            CsvRawFileManager.getLastTwoRowOfFile(dia[i]);       


                            // SE SACAN LOS PROMEDIOS
                            CsvRawFileManager.readCsvFile(dia[i+1]); 
                        }
                        JOptionPane.showMessageDialog(null,"Listo","",JOptionPane.INFORMATION_MESSAGE);                                               
                   }else{
                        JOptionPane.showMessageDialog(null,"No se seleccionaron mas de 1 archivo","WARNING",JOptionPane.WARNING_MESSAGE);                       
                   }
                }             
            }
        });
        
        promediosporDiaPanelDosFilas.add(titulo);
        promediosporDiaPanelDosFilas.add(cargarArchivos);
        promediosporDiaPanelDosFilas.add(scroll);
        return promediosporDiaPanelDosFilas;
    }

    public JPanel promediosporDia3Filas(){    
        String[] dia,fileNamePromedios;        
        promediosporDiaPanelTresFilas = new JPanel();
        promediosporDiaPanelTresFilas.setVisible(false);        
        promediosporDiaPanelTresFilas.setLayout(new GridLayout(3,0));
        promediosporDiaPanelTresFilas.setBounds(1,1,1000,500);
        
        JFileChooser choix = new JFileChooser();
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choix.setMultiSelectionEnabled(true) ;      
        
        JLabel titulo = new JLabel("Promedios por dia con 3 filas");
        JTextArea textoValores = new JTextArea();
        JScrollPane scroll = new JScrollPane(textoValores);      
        
        JButton cargarArchivos = new JButton("Cargar Archivos");
        
        cargarArchivos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textoValores.setText(null);
                int retour = choix.showOpenDialog(new JFrame());
                if(retour == JFileChooser.APPROVE_OPTION){
                   // des fichiers ont été choisis 
                   // (sortie par OK)
                   File[] fs=choix.getSelectedFiles();
                   if(fs.length > 1){
                        String[] dia = new String[fs.length];
                        String[] fileNamePromedios = new String[fs.length]; 
                        textoValores.append("Numero de Archivos: " +fs.length+"\n");
                        
                        // Se obtienen los nombres de los archivos
                        for( int i = 0; i<fs.length; ++i){ 
                           textoValores.append( i + ".-" +"Nombre de Archivo Crudo: " +fs[i].getName()+"\n");
                           // nom du fichier 
                           dia[i] = fs[i].getAbsolutePath();                         
                        }
                        for(int i = 0; i<fs.length-1; i++){
                           fileNamePromedios[i] = "Promedios" + fs[i+1].getName();   
                           textoValores.append("  " +"Nombre de Archivo con Promedios: " +
                                   fileNamePromedios[i]+"\n");                              
                        }
                        
                        // Se procesa la informacion
                        for(int i = 0; i<fs.length-1;i++){
                           
                            // SE NOMBRA AL ARCHIVO DE PROMEDIOS
                            CsvRawFileManager.setFile_Name_Promedios(fileNamePromedios[i]);

                            // SE LEE ULTIMA FILA DEL ARCHIVO ANTERIOR
                            CsvRawFileManager.initiateAllOtherFiles();
                            CsvRawFileManager.getLastThreeRowOfFile(dia[i]);       


                            // SE SACAN LOS PROMEDIOS
                            CsvRawFileManager.readCsvFile(dia[i+1]); 
                        }
                        JOptionPane.showMessageDialog(null,"Listo","",JOptionPane.INFORMATION_MESSAGE);                                               
                   }else{
                        JOptionPane.showMessageDialog(null,"No se seleccionaron mas de 1 archivo","WARNING",JOptionPane.WARNING_MESSAGE);                       
                   }
                }              
            }
        });
        
        promediosporDiaPanelTresFilas.add(titulo);
        promediosporDiaPanelTresFilas.add(cargarArchivos);
        promediosporDiaPanelTresFilas.add(scroll);
        return promediosporDiaPanelTresFilas;
    }    
    
    public JPanel varianzaDesviacionporDia(){
        varianzaDesviacionporDiaPanel = new JPanel();
        varianzaDesviacionporDiaPanel.setVisible(false);
        varianzaDesviacionporDiaPanel.setLayout(new GridLayout(3,0));
        varianzaDesviacionporDiaPanel.setBounds(1,1,1000,500);
        
        JFileChooser choix = new JFileChooser();
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choix.setMultiSelectionEnabled(true) ;      
        
        JLabel titulo = new JLabel("Varianza y Desviacion de 1 Dia");
        JTextArea textoValores = new JTextArea();
        JScrollPane scroll = new JScrollPane(textoValores);      
        
        JButton cargarArchivos = new JButton("Cargar Archivos");
        
        cargarArchivos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textoValores.setText(null);
                int retour = choix.showOpenDialog(new JFrame());
                if(retour == JFileChooser.APPROVE_OPTION){
                   // des fichiers ont été choisis 
                   // (sortie par OK)
                   File[] fs=choix.getSelectedFiles();
                   if(fs.length > 0){
                        String[] fileNameVarianzaDesviacion = new String[fs.length];
                        String[] fileNamePromedios = new String[fs.length]; 
                        textoValores.append("Numero de Archivos: " +fs.length+"\n");
                        
                        // Se obtienen los nombres de los archivos
                        for( int i = 0; i<fs.length; ++i){ 
                           textoValores.append( i + ".-" +"Nombre de Archivo con Promedios: " +fs[i].getName()+"\n");
                           // nom du fichier 
                           fileNamePromedios[i] = fs[i].getAbsolutePath();   
                           fileNameVarianzaDesviacion[i] = "VarDesv"+fs[i].getName();
                           textoValores.append("  " +"Nombre de Archivo con Varianza y Desviacion: " +
                                   fileNameVarianzaDesviacion[i]+"\n");     
                        }
                        
                        // Se procesa la informacion
                        for(int i = 0; i<fs.length;i++){
                            VarianzayDesviacion.setFile_Name_Varianza_Desviacion(fileNameVarianzaDesviacion[i]);
                            VarianzayDesviacion.createFile();
                            VarianzayDesviacion.obtainAverages(fileNamePromedios[i]);
                            VarianzayDesviacion.makeVariance(fileNamePromedios[i],fileNameVarianzaDesviacion[i]);
                        }
                        JOptionPane.showMessageDialog(null,"Listo","",JOptionPane.INFORMATION_MESSAGE);                                               
                   }else{
                        JOptionPane.showMessageDialog(null,"No se seleccionaron archivos","WARNING",JOptionPane.WARNING_MESSAGE);                       
                   }
                }                
            }
        });
        
        varianzaDesviacionporDiaPanel.add(titulo);
        varianzaDesviacionporDiaPanel.add(cargarArchivos);
        varianzaDesviacionporDiaPanel.add(scroll);
        return varianzaDesviacionporDiaPanel;
    }
    
    public JPanel valoresMensuales(){
        valoresMensualesPanel = new JPanel();
        valoresMensualesPanel.setVisible(false);
        valoresMensualesPanel.setLayout(new GridLayout(4,0));
        valoresMensualesPanel.setBounds(1,1,1000,500);
        
        JFileChooser choix = new JFileChooser();
        choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choix.setMultiSelectionEnabled(true) ;      
        
        JLabel titulo = new JLabel("Varianza y Desviacion del Mes(Ingrese nombre del archivo)");
        final JTextField nombreArchivoDelMes = new JTextField(100);
        JTextArea textoValores = new JTextArea();
        JScrollPane scroll = new JScrollPane(textoValores);      
        
        JButton cargarArchivos = new JButton("Cargar Archivos");
        
        cargarArchivos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String monthFileNameVarianzaDesviacion = nombreArchivoDelMes.getText();
                textoValores.setText(null);
                if(monthFileNameVarianzaDesviacion.equalsIgnoreCase(""))
                    JOptionPane.showMessageDialog(null,"Ingrese Nombre del Archivo","WARNING",JOptionPane.WARNING_MESSAGE);
                else{
                    monthFileNameVarianzaDesviacion += ".csv";
                    int retour = choix.showOpenDialog(new JFrame());
                    if(retour == JFileChooser.APPROVE_OPTION){
                       // des fichiers ont été choisis 
                       // (sortie par OK)
                       File[] fs=choix.getSelectedFiles();
                       if(fs.length > 0){
                            String[] fileNamePromedios = new String[fs.length]; 
                            textoValores.append("Numero de Archivos: " +fs.length+"\n");

                           textoValores.append("  " +"Nombre de Archivo con Varianza y Desviacion del mes: " +
                                       monthFileNameVarianzaDesviacion+"\n");                         
                            // Se obtienen los nombres de los archivos
                            for( int i = 0; i<fs.length; ++i){ 
                               textoValores.append( i + ".-" +"Nombre de Archivo con Promedios: " +fs[i].getName()+"\n");
                               // nom du fichier 
                               fileNamePromedios[i] = fs[i].getAbsolutePath();       
                            }
                            
                            // Se procesa la informacion
                            MonthFile.setMonth_File_Name_Varianza_Desviacion(monthFileNameVarianzaDesviacion);
                            MonthFile.createFile();
                            for(int i = 0; i<fs.length;i++){
                                MonthFile.obtainAverages(fileNamePromedios[i]);
                            }                            
                            MonthFile.makeMonthAverage();
                            for(int i = 0; i<fs.length;i++){
                                MonthFile.makeXi(fileNamePromedios[i]);
                            }
                            MonthFile.makeMonthVarianceAndStandardDeviationAndKAndC();
                            MonthFile.closeFileAndRestoreValues();
                            JOptionPane.showMessageDialog(null,"Listo","",JOptionPane.INFORMATION_MESSAGE);                                               
                       }else{
                            JOptionPane.showMessageDialog(null,"No se seleccionaron archivos","WARNING",JOptionPane.WARNING_MESSAGE);                       
                       }
                    }
                }                
            }
        });
        
        valoresMensualesPanel.add(titulo);
        valoresMensualesPanel.add(nombreArchivoDelMes);
        valoresMensualesPanel.add(cargarArchivos);
        valoresMensualesPanel.add(scroll);
        return valoresMensualesPanel;
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == promediosporDiaUnaFilaIt){
            promediosporDiaPanelUnaFila.setVisible(true);
            promediosporDiaPanelDosFilas.setVisible(false);
            promediosporDiaPanelTresFilas.setVisible(false);
            varianzaDesviacionporDiaPanel.setVisible(false);
            valoresMensualesPanel.setVisible(false);
        }
        if(e.getSource() == promediosporDiaDosFilasIt){
            promediosporDiaPanelUnaFila.setVisible(false);
            promediosporDiaPanelDosFilas.setVisible(true); 
            promediosporDiaPanelTresFilas.setVisible(false);           
            varianzaDesviacionporDiaPanel.setVisible(false);
            valoresMensualesPanel.setVisible(false);
        }
        if(e.getSource() == promediosporDiaTresFilasIt){
            promediosporDiaPanelUnaFila.setVisible(false);
            promediosporDiaPanelDosFilas.setVisible(false); 
            promediosporDiaPanelTresFilas.setVisible(true);           
            varianzaDesviacionporDiaPanel.setVisible(false);
            valoresMensualesPanel.setVisible(false);
        }
        
        if(e.getSource() == varianzaDesviacionporDiaIt){
            promediosporDiaPanelUnaFila.setVisible(false);
            promediosporDiaPanelDosFilas.setVisible(false); 
            promediosporDiaPanelTresFilas.setVisible(false);           
            varianzaDesviacionporDiaPanel.setVisible(true);
            valoresMensualesPanel.setVisible(false);            
        }
        if(e.getSource() == valoresMensualesIt){
            promediosporDiaPanelUnaFila.setVisible(false);
            promediosporDiaPanelDosFilas.setVisible(false); 
            promediosporDiaPanelTresFilas.setVisible(false);           
            varianzaDesviacionporDiaPanel.setVisible(false);
            valoresMensualesPanel.setVisible(true);            
        }
    }    
    
    public static void main(String[] args){
        MainGUI main = new MainGUI();
        main.setVisible(true);
    }    
    
}
