/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoservicio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elliot
 */
public class CsvFileVarianceAndStandardDeviation {

    final String FILE_HEADER_VARIANZA_DESVIACION = "var decimosegundo,var 1 seg,var 5 seg,var 30 seg,var 1 min,var 5 min,var 10 min,"
            +""+ "desv decimosegundo,desv 1 seg,desv 5 seg,desv 30 seg,desv 1 min,desv 5 min,desv 10 min";
    final String COMMA_DELIMITER = ",";
    final String NEW_LINE_SEPARATOR = "\n";   
    String file_Name_Varianza_Desviacion="";
    FileWriter fileWriter;
    BufferedReader fileReader;
    
    double prom_acumulador;
    double prom_unSegundo;
    double prom_cincoSegundos;
    double prom_treintaSegundos;
    double prom_unMinuto;
    double prom_cincoMinuto;
    double prom_diezMinuto;    

    public String getFile_Name_Varianza_Desviacion() {
        return file_Name_Varianza_Desviacion;
    }

    public void setFile_Name_Varianza_Desviacion(String file_Name_Varianza_Desviacion) {
        this.file_Name_Varianza_Desviacion = file_Name_Varianza_Desviacion;
    }
    
    public void createFile(){
        try {
            fileWriter = new FileWriter(file_Name_Varianza_Desviacion);
//                Write the CSV file header
            fileWriter.append(FILE_HEADER_VARIANZA_DESVIACION);
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);         
        } catch (IOException ex) {
            Logger.getLogger(CsvFileVectorAndAverages.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void obtainAverages(String file_Name_Promedios){
	BufferedReader aux = null,fileReader = null;        
     
        try {
            String line = "",cell[];
            int ROW=0,ROWS=0;
            
            //Create the aux reader
            aux = new BufferedReader(new FileReader(file_Name_Promedios));      
            ROWS = (int) aux.lines().count();
//            System.out.println("ROWS: "+ROWS);
            
            
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(file_Name_Promedios));      
            
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                cell = line.split(",");     
                if(ROW == ROWS-1){
                    System.out.println("Averages values: " + cell[11] +"\t"+cell[12] +"\t"+cell[13] +"\t"+cell[14]
                     +"\t"+cell[15] +"\t"+cell[16] +"\t"+cell[17]);
                    prom_acumulador = Double.parseDouble(cell[11]);
                    prom_unSegundo = Double.parseDouble(cell[12]);
                    prom_cincoSegundos = Double.parseDouble(cell[13]);
                    prom_treintaSegundos = Double.parseDouble(cell[14]);
                    prom_unMinuto = Double.parseDouble(cell[15]);
                    prom_cincoMinuto = Double.parseDouble(cell[16]);
                    prom_diezMinuto = Double.parseDouble(cell[17]);                    
                }
                ROW++;
            }            
        }catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                aux.close();
                fileReader.close();              
            } catch (IOException e) {
            	System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }        
        
    }
    
    public void makeVariance(String file_Name_Promedios,String file_Name_Varianza_Desviacion) {

        String line = "",cell[];

        int cont_decseg=0,cont_unSeg=0,cont_cincoSeg=0,cont_treintaSeg=0,cont_unMinuto=0,
                cont_cincoMinuto=0,cont_diezMinuto=0;
        double decimosegundo_acum=0,unSegAcum=0,cincoSegundosAcum=0,
                treintaSegundosAcum=0,unMinutoAcum=0,cincoMinutoAcum=0,diezMinutoAcum=0;
        double cinco=0,seis=0,siete=0,ocho=0,nueve=0,diez=0,once=0;

        try {
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(file_Name_Promedios));         
            //Read the CSV file header to skip it
            fileReader.readLine();

            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                cell = line.split(",");                
//                        for(int x=0;x<cell.length;x++)
//                            System.out.print(cell[x] + "\t");
//                        
//                        System.out.println("");
                    try{
                        cinco = Double.parseDouble(cell[4]);
                        seis = Double.parseDouble(cell[5]);
                        siete = Double.parseDouble(cell[6]);
                        ocho = Double.parseDouble(cell[7]);
                        nueve = Double.parseDouble(cell[8]);
                        diez = Double.parseDouble(cell[9]);
                        once = Double.parseDouble(cell[10]);                            
                    }catch(ArrayIndexOutOfBoundsException e){
                        cinco = 0;
                        seis = 0;
                        siete = 0;
                        ocho = 0;
                        nueve = 0;
                        diez = 0;                 
                        once = 0;
                    }catch(NumberFormatException e){
                        cinco = 0;
                        seis = 0;
                        siete = 0;
                        ocho = 0;
                        nueve = 0;
                        diez = 0;                 
                        once = 0;
                    }

                    if(cinco != 0){
                        decimosegundo_acum += Math.pow(cinco,2);
                        cont_decseg++;
                    }
                    if(seis != 0){
                        unSegAcum += Math.pow(seis,2);
                        cont_unSeg++;
                    }
                    if(siete != 0){
                        cincoSegundosAcum += Math.pow(siete,2);
                        cont_cincoSeg++;
                    }
                    if(ocho != 0){
                        treintaSegundosAcum += Math.pow(ocho,2);
                        cont_treintaSeg++;
                    }
                    if(nueve != 0){
                        unMinutoAcum += Math.pow(nueve,2);
                        cont_unMinuto++;
                    }
                    if(diez != 0){
                        cincoMinutoAcum += Math.pow(diez,2);
                        cont_cincoMinuto++;
                    }
                    if(once != 0){
                        diezMinutoAcum += Math.pow(once,2);
                        cont_diezMinuto++;
                    }                
            }

            if( (line = fileReader.readLine()) == null ){
                System.out.println("Varianza y Desviacion terminada");
                    fileWriter.append(Double.toString( (decimosegundo_acum/cont_decseg) -  Math.pow(prom_acumulador, 2) ));  
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( (unSegAcum/cont_unSeg) -  Math.pow(prom_unSegundo, 2) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( (cincoSegundosAcum/cont_cincoSeg) -  Math.pow(prom_cincoSegundos, 2) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( (treintaSegundosAcum/cont_treintaSeg) -  Math.pow(prom_treintaSegundos, 2) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( (unMinutoAcum/cont_unMinuto) -  Math.pow(prom_unMinuto, 2) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( (cincoMinutoAcum/cont_cincoMinuto) -  Math.pow(prom_cincoMinuto, 2) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( (diezMinutoAcum/cont_diezMinuto) -  Math.pow(prom_diezMinuto, 2) ));

                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( Math.sqrt( (decimosegundo_acum/cont_decseg) -  Math.pow(prom_acumulador, 2) ) ));  
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( Math.sqrt( (unSegAcum/cont_unSeg) -  Math.pow(prom_unSegundo, 2) ) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( Math.sqrt( (cincoSegundosAcum/cont_cincoSeg) -  Math.pow(prom_cincoSegundos, 2) ) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( Math.sqrt( (treintaSegundosAcum/cont_treintaSeg) -  Math.pow(prom_treintaSegundos, 2) ) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( Math.sqrt( (unMinutoAcum/cont_unMinuto) -  Math.pow(prom_unMinuto, 2) ) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( Math.sqrt( (cincoMinutoAcum/cont_cincoMinuto) -  Math.pow(prom_cincoMinuto, 2) ) ));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString( Math.sqrt( (diezMinutoAcum/cont_diezMinuto) -  Math.pow(prom_diezMinuto, 2) ) ));               
            }  

        } catch (IOException ex) {
            System.out.println("Error in CsvFileReader !!!");
            ex.printStackTrace();
        } finally {
            try {            
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(CsvFileVarianceAndStandardDeviation.class.getName()).log(Level.SEVERE, null, ex);
            }
            closeFileAndRestoreValues();
        }
    }

    public void closeFileAndRestoreValues(){
        try {
            fileWriter.flush();
            fileWriter.close(); 
            prom_acumulador = 0;
            prom_unSegundo = 0;
            prom_cincoSegundos = 0;
            prom_treintaSegundos = 0;
            prom_unMinuto = 0;
            prom_cincoMinuto = 0;
            prom_diezMinuto = 0;                
        } catch (IOException e) {
            System.out.println("Error while closing fileReader !!!");
            e.printStackTrace();
        }        
    }
}
