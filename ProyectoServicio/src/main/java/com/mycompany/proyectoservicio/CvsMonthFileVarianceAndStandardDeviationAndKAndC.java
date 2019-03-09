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
public class CvsMonthFileVarianceAndStandardDeviationAndKAndC {
    
    FileWriter fileWriter;
    final String FILE_HEADER = "prom mes,var mes 10hz,desv mes10hz,K10hz,C10hz,var mes 1seg,desv mes 1seg,K 1seg,C 1seg,var mes 5seg,desv mes 5seg,K 5seg,C 5seg,var mes 30seg,desv mes 30seg,K 30seg,C 30seg,var mes 1min,desv mes 1min,K 1min,C 1min,var mes 5min,desv mes 5min,K 5min,C 5min,var mes 10min,desv mes 10min,K 10min,C 10min";
    final String COMMA_DELIMITER = ",";
    final String NEW_LINE_SEPARATOR = "\n";     
    String month_File_Name_Varianza_Desviacion="";
    int prom_cont=0,cont_10Hz=0,cont_unSeg=0,cont_cincoSeg=0,cont_treintaSeg=0,cont_unMinuto=0,cont_cincoMinuto=0,cont_diezMinuto=0;
    double xi_10Hz=0,xi_1seg=0,xi_5seg=0,xi_30seg=0,xi_1min=0,xi_5min=0,xi_10min=0;
    double prom_acum=0;
    double prom_mes=0;
    double var_mes_10Hz,var_mes_1seg,var_mes_5seg,var_mes_30seg,var_mes_1min,var_mes_5min,var_mes_10min;
    double desv_mes_10Hz,desv_mes_1seg,desv_mes_5seg,desv_mes_30seg,desv_mes_1min,desv_mes_5min,desv_mes_10min;
    double C_10Hz,C_1seg,C_5seg,C_30seg,C_1min,C_5min,C_10min;
    double K_10Hz,K_1seg,K_5seg,K_30seg,K_1min,K_5min,K_10min;
    double x_10Hz,x_1seg,x_5seg,x_30seg,x_1min,x_5min,x_10min;
    double cinco = 0,seis = 0,siete = 0,ocho = 0,nueve = 0,diez = 0,once = 0;
    final double C0 = 0.886259184149;
    final double C1 = 0.00852888014766;
    final double C2 = 0.0257748943765;
    final double C3 = 0.002117760028167;
    final double C4 = 0.000664358428;    
    

    public String getMonth_File_Name_Varianza_Desviacion() {
        return month_File_Name_Varianza_Desviacion;
    }

    public void setMonth_File_Name_Varianza_Desviacion(String month_File_Name_Varianza_Desviacion) {
        this.month_File_Name_Varianza_Desviacion = month_File_Name_Varianza_Desviacion;
    }

    public int getProm_cont() {
        return prom_cont;
    }

    public void setProm_cont(int prom_cont) {
        this.prom_cont = prom_cont;
    }

    public double getProm_acum() {
        return prom_acum;
    }

    public void setProm_acum(double prom_acum) {
        this.prom_acum = prom_acum;
    }

    public double getProm_mes() {
        return prom_mes;
    }

    public void setProm_mes(double prom_mes) {
        this.prom_mes = prom_mes;
    }
    
    
    
    public void createFile(){
        try {
            fileWriter = new FileWriter(month_File_Name_Varianza_Desviacion);
    //                Write the CSV file header
            fileWriter.append(FILE_HEADER);
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
                    prom_acum += Double.parseDouble(cell[17]);
                    prom_cont++;
                    System.out.println("Averages values: "+prom_cont+"\t"+cell[17]);                    
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
    
    
    public void makeXi(String file_Name_Promedios){
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
                        xi_10Hz += Math.pow(cinco,2);
                        cont_10Hz++;
                    }
                    if(seis != 0){
                        xi_1seg += Math.pow(seis,2);
                        cont_unSeg++;
                    }
                    if(siete != 0){
                        xi_5seg += Math.pow(siete,2);
                        cont_cincoSeg++;
                    }
                    if(ocho != 0){
                        xi_30seg += Math.pow(ocho,2);
                        cont_treintaSeg++;
                    }
                    if(nueve != 0){
                        xi_1min += Math.pow(nueve,2);
                        cont_unMinuto++;
                    }
                    if(diez != 0){
                        xi_5min += Math.pow(diez,2);
                        cont_cincoMinuto++;
                    }
                    if(once != 0){
                        xi_10min += Math.pow(once,2);
                        cont_diezMinuto++;
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
    
    public void makeMonthVarianceAndStandardDeviationAndKAndC(){
        try {  
            //10Hz
            var_mes_10Hz = (xi_10Hz/cont_10Hz) -  Math.pow(prom_mes, 2);
            desv_mes_10Hz = Math.sqrt((xi_10Hz/cont_10Hz) -  Math.pow(prom_mes, 2));
            K_10Hz = Math.pow((desv_mes_10Hz/prom_mes), -1.086);
            x_10Hz = (4*(1 + (1/K_10Hz)) ) - 6;
            C_10Hz = prom_mes / ( C0 + (C1*x_10Hz) + ( C2*(Math.pow(x_10Hz, 2)) ) + ( C3*(Math.pow(x_10Hz, 3)) ) + ( C4*(Math.pow(x_10Hz, 4)) ));            
            
            fileWriter.append(Double.toString( var_mes_10Hz ));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString( desv_mes_10Hz ));
            fileWriter.append(COMMA_DELIMITER);            
            fileWriter.append(Double.toString(K_10Hz));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(C_10Hz));
            fileWriter.append(COMMA_DELIMITER);
            
            //1seg
            var_mes_1seg = (xi_1seg/cont_unSeg) -  Math.pow(prom_mes, 2);
            desv_mes_1seg = Math.sqrt((xi_1seg/cont_unSeg) -  Math.pow(prom_mes, 2));
            K_1seg = Math.pow((desv_mes_1seg/prom_mes), -1.086);
            x_1seg = (4*(1 + (1/K_1seg)) ) - 6;
            C_1seg = prom_mes / ( C0 + (C1*x_1seg) + ( C2*(Math.pow(x_1seg, 2)) ) + ( C3*(Math.pow(x_1seg, 3)) ) + ( C4*(Math.pow(x_1seg, 4)) ));            
                        
            fileWriter.append(Double.toString( var_mes_1seg ));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString( desv_mes_1seg ));
            fileWriter.append(COMMA_DELIMITER);            
            fileWriter.append(Double.toString(K_1seg));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(C_1seg));  
            fileWriter.append(COMMA_DELIMITER);
            
            //5seg
            var_mes_5seg = (xi_5seg/cont_cincoSeg) -  Math.pow(prom_mes, 2);
            desv_mes_5seg = Math.sqrt((xi_5seg/cont_cincoSeg) -  Math.pow(prom_mes, 2));
            K_5seg = Math.pow((desv_mes_5seg/prom_mes), -1.086);
            x_5seg = (4*(1 + (1/K_5seg)) ) - 6;
            C_5seg = prom_mes / ( C0 + (C1*x_5seg) + ( C2*(Math.pow(x_5seg, 2)) ) + ( C3*(Math.pow(x_5seg, 3)) ) + ( C4*(Math.pow(x_5seg, 4)) ));            
                        
            fileWriter.append(Double.toString( var_mes_5seg ));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString( desv_mes_5seg ));
            fileWriter.append(COMMA_DELIMITER);            
            fileWriter.append(Double.toString(K_5seg));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(C_5seg));   
            fileWriter.append(COMMA_DELIMITER);
            
            //30seg
            var_mes_30seg = (xi_30seg/cont_treintaSeg) -  Math.pow(prom_mes, 2);
            desv_mes_30seg = Math.sqrt((xi_30seg/cont_treintaSeg) -  Math.pow(prom_mes, 2));
            K_30seg = Math.pow((desv_mes_30seg/prom_mes), -1.086);
            x_30seg = (4*(1 + (1/K_30seg)) ) - 6;
            C_30seg = prom_mes / ( C0 + (C1*x_30seg) + ( C2*(Math.pow(x_30seg, 2)) ) + ( C3*(Math.pow(x_30seg, 3)) ) + ( C4*(Math.pow(x_30seg, 4)) ));            
                        
            fileWriter.append(Double.toString( var_mes_30seg ));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString( desv_mes_30seg ));
            fileWriter.append(COMMA_DELIMITER);            
            fileWriter.append(Double.toString(K_30seg));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(C_30seg));   
            fileWriter.append(COMMA_DELIMITER);
            
            //1min
            var_mes_1min = (xi_1min/cont_unMinuto) -  Math.pow(prom_mes, 2);
            desv_mes_1min = Math.sqrt((xi_1min/cont_unMinuto) -  Math.pow(prom_mes, 2));
            K_1min = Math.pow((desv_mes_1min/prom_mes), -1.086);
            x_1min = (4*(1 + (1/K_1min)) ) - 6;
            C_1min = prom_mes / ( C0 + (C1*x_1min) + ( C2*(Math.pow(x_1min, 2)) ) + ( C3*(Math.pow(x_1min, 3)) ) + ( C4*(Math.pow(x_1min, 4)) ));            
                        
            fileWriter.append(Double.toString( var_mes_1min ));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString( desv_mes_1min ));
            fileWriter.append(COMMA_DELIMITER);            
            fileWriter.append(Double.toString(K_1min));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(C_1min));   
            fileWriter.append(COMMA_DELIMITER);   
            
            //5min
            var_mes_5min = (xi_5min/cont_cincoMinuto) -  Math.pow(prom_mes, 2);
            desv_mes_5min = Math.sqrt((xi_5min/cont_cincoMinuto) -  Math.pow(prom_mes, 2));
            K_5min = Math.pow((desv_mes_5min/prom_mes), -1.086);
            x_5min = (4*(1 + (1/K_5min)) ) - 6;
            C_5min = prom_mes / ( C0 + (C1*x_5min) + ( C2*(Math.pow(x_5min, 2)) ) + ( C3*(Math.pow(x_5min, 3)) ) + ( C4*(Math.pow(x_5min, 4)) ));            
                        
            fileWriter.append(Double.toString( var_mes_5min ));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString( desv_mes_5min ));
            fileWriter.append(COMMA_DELIMITER);            
            fileWriter.append(Double.toString(K_5min));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(C_5min));   
            fileWriter.append(COMMA_DELIMITER); 
            
            //10min
            var_mes_10min = (xi_10min/cont_diezMinuto) -  Math.pow(prom_mes, 2);
            desv_mes_10min = Math.sqrt((xi_10min/cont_diezMinuto) -  Math.pow(prom_mes, 2));
            K_10min = Math.pow((desv_mes_10min/prom_mes), -1.086);
            x_10min = (4*(1 + (1/K_10min)) ) - 6;
            C_10min = prom_mes / ( C0 + (C1*x_10min) + ( C2*(Math.pow(x_10min, 2)) ) + ( C3*(Math.pow(x_10min, 3)) ) + ( C4*(Math.pow(x_10min, 4)) ));            
                        
            fileWriter.append(Double.toString( var_mes_10min ));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString( desv_mes_10min ));
            fileWriter.append(COMMA_DELIMITER);            
            fileWriter.append(Double.toString(K_10min));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(Double.toString(C_10min));   
            fileWriter.append(COMMA_DELIMITER);            
            
            
        } catch (IOException ex) {
            Logger.getLogger(CvsMonthFileVarianceAndStandardDeviationAndKAndC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void makeMonthAverage(){
        try {
            prom_mes = prom_acum / prom_cont;
            fileWriter.append(Double.toString( prom_mes ));       
            fileWriter.append(COMMA_DELIMITER);
        } catch (IOException ex) {
            Logger.getLogger(CvsMonthFileVarianceAndStandardDeviationAndKAndC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeFileAndRestoreValues(){
        try {
            fileWriter.flush();
            fileWriter.close(); 
            xi_10Hz = 0;
            xi_1seg = 0;
            xi_5seg = 0;
            xi_30seg = 0;
            xi_1min = 0;
            xi_5min = 0;
            xi_10min = 0;
            cont_10Hz = 0;
            cont_unSeg = 0;
            cont_cincoSeg = 0;
            cont_treintaSeg = 0;
            cont_unMinuto = 0;
            cont_cincoMinuto = 0;
            cont_diezMinuto = 0;
            prom_acum = 0;   
            prom_cont = 0;
        } catch (IOException e) {
            System.out.println("Error while closing fileReader !!!");
            e.printStackTrace();
        }        
    }    
}
