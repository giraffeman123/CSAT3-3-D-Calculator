/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoservicio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elliot
 */
public class CsvFileVectorAndAverages{

    FileWriter fileWriter;
    
    final String FILE_HEADER = "TIME,Ux,Uy,Uz,Vector,Promedio(1 Seg),Promedio(5 Seg),Promedio(30 Seg),Promedio(1 min),Promedio(5 min),Promedio(10 min)"
        + ",media decimosegundo,media 1 seg,media 5 seg,media 30 seg,media 1 min,media 5 min,media 10 min";
    final String COMMA_DELIMITER = ",";
    final String NEW_LINE_SEPARATOR = "\n";  
    String file_Name_Promedios="";
    int DECIMO_SEGUNDO=1;
    int DECIMO_UN_SEGUNDO=1;
    int DECIMO_CINCO_SEGUNDOS=1;
    int DECIMO_TREINA_SEGUNDOS=1;
    int DECIMO_UN_MINUTO=1;  
    int DECIMO_CINCO_MINUTOS=1;    
    int DECIMO_DIEZ_MINUTOS=1;        
    double acumulador=0;
    double unSegundoAc=0;
    double cincoSegundosAc=0;
    double treintaSegundosAc=0;
    double unMinutoAc=0;
    double cincoMinutoAc=0;
    double diezMinutoAc=0;
    
    // VARIABLES DE LA VARIANZA
    int CONT_PROM_DECIMO_UN_SEGUNDO = 0;
    int CONT_PROM_DECIMO_CINCO_SEGUNDOS = 0; 
    int CONT_PROM_DECIMO_TREINA_SEGUNDOS=0;
    int CONT_PROM_DECIMO_UN_MINUTO=0;  
    int CONT_PROM_DECIMO_CINCO_MINUTOS=0;    
    int CONT_PROM_DECIMO_DIEZ_MINUTOS=0;     
    double prom_unSegundoAc=0;
    double prom_cincoSegundosAc=0;
    double prom_treintaSegundosAc=0;
    double prom_unMinutoAc=0;
    double prom_cincoMinutoAc=0;
    double prom_diezMinutoAc=0;  

    public String getFile_Name_Promedios() {
        return file_Name_Promedios;
    }

    public void setFile_Name_Promedios(String file_Name_Promedios) {
        this.file_Name_Promedios = file_Name_Promedios;
    }
    
    public void createFile(){
        try {
            fileWriter = new FileWriter(file_Name_Promedios);
            //Write the CSV file header
            fileWriter.append(FILE_HEADER);
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);         
        } catch (IOException ex) {
            Logger.getLogger(CsvFileVectorAndAverages.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void addLastRowOfFile(String[] cell){
        
        double vector,x,y,z;
        
        try{
            x = Double.parseDouble(cell[2]);
            y = Double.parseDouble(cell[3]);
            z = Double.parseDouble(cell[4]);        
        }catch(ArrayIndexOutOfBoundsException e){
            x = 0;
            y = 0;
            z = 0;
        }catch(NumberFormatException e){
            x = 0;
            y = 0;
            z = 0;
        }
        vector = Math.sqrt((x*x)+(y*y)+(z*z));
        System.out.println("00:00.0  Ux: "+cell[2] + "\t Uy: "+ cell[3] +"t Uz: "+ cell[4] + "\t Vector: " + vector);                                                    
        if(!Double.isNaN(vector)){
            acumulador += vector;
            unSegundoAc += vector;
            cincoSegundosAc += vector;
            treintaSegundosAc += vector;
            unMinutoAc += vector;
            cincoMinutoAc += vector;
            diezMinutoAc += vector;
        }
        DECIMO_SEGUNDO++;
        DECIMO_UN_SEGUNDO++;
        DECIMO_CINCO_SEGUNDOS++;
        DECIMO_TREINA_SEGUNDOS++;
        DECIMO_UN_MINUTO++;
        DECIMO_CINCO_MINUTOS++;
        DECIMO_DIEZ_MINUTOS++;        
    }
    
    public void addVectorAndAverages(String[] cell){
        double vector,x,y,z,promedio;
        try{
            x = Double.parseDouble(cell[2]);
            y = Double.parseDouble(cell[3]);
            z = Double.parseDouble(cell[4]);        
        }catch(ArrayIndexOutOfBoundsException e){
            x = 0;
            y = 0;
            z = 0;
        }catch(NumberFormatException e){
            x = 0;
            y = 0;
            z = 0;
        }
        try {      
            vector = Math.sqrt((x*x)+(y*y)+(z*z));
            if(!Double.isNaN(vector)){
                acumulador += vector;
                unSegundoAc += vector;
                cincoSegundosAc += vector;
                treintaSegundosAc += vector;
                unMinutoAc += vector;
                cincoMinutoAc += vector;
                diezMinutoAc += vector;
//                System.out.println(DECIMO_SEGUNDO+"\t Ux: "+cell[2] + "\t Uy: "+ cell[3] +"\t Uz: "+ cell[4] +
//                        "\t Vector: " + vector + "\t Acum(1 seg): " +unSegundoAc + "\t Acum(5 seg): " + cincoSegundosAc +
//                 "\t Acum(30 seg): " + treintaSegundosAc + "\t Acum(1 min): " + unMinutoAc +
//                        "\t Acum(5 min): " + cincoMinutoAc + "\t Acum(10 min): " + diezMinutoAc);
                fileWriter.append(Integer.toString(DECIMO_SEGUNDO));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(cell[2]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(cell[3]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(cell[4]);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(vector));
                if(DECIMO_UN_SEGUNDO % 10 == 0){
                    promedio = (double) (unSegundoAc / DECIMO_UN_SEGUNDO);

                    prom_unSegundoAc += promedio;
                    CONT_PROM_DECIMO_UN_SEGUNDO++;

                    fileWriter.append("");
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString(promedio));
//                    System.out.println(DECIMO_UN_SEGUNDO+"\t Ux: "+cell[2] + "\t Uy: "+ cell[3] +"\t Uz: "+ cell[4] + "\t Vector: " + vector + "\t Acum(1 seg): " +unSegundoAc
//                    + "\t Promedio: " +promedio );
                    unSegundoAc = 0;
                    DECIMO_UN_SEGUNDO=0;
                }
                if(DECIMO_CINCO_SEGUNDOS % 50 == 0){
                    promedio = (double) (cincoSegundosAc / DECIMO_CINCO_SEGUNDOS);

                    prom_cincoSegundosAc += promedio;
                    CONT_PROM_DECIMO_CINCO_SEGUNDOS++;

                    fileWriter.append("");
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString(promedio));
//                    System.out.println(DECIMO_CINCO_SEGUNDOS+"\t Ux: "+cell[2] + "\t Uy: "+ cell[3] +"t Uz: "+ cell[4] + "\t Vector: " + vector
//                    + "\t Promedio: " +promedio );
                    cincoSegundosAc=0;
                    DECIMO_CINCO_SEGUNDOS=0;
                }
                if(DECIMO_TREINA_SEGUNDOS % 300 == 0){
                    promedio = (double) (treintaSegundosAc / DECIMO_TREINA_SEGUNDOS);

                    prom_treintaSegundosAc += promedio;
                    CONT_PROM_DECIMO_TREINA_SEGUNDOS++;

                    fileWriter.append("");
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString(promedio));
//                    System.out.println(DECIMO_TREINA_SEGUNDOS+"\t Ux: "+cell[2] + "\t Uy: "+ cell[3] +"t Uz: "+ cell[4] + "\t Vector: " + vector
//                    + "\t Promedio: " +promedio );
                    treintaSegundosAc=0;
                    DECIMO_TREINA_SEGUNDOS=0;
                }
                if(DECIMO_UN_MINUTO % 600 == 0){
                    promedio = (double) (unMinutoAc / DECIMO_UN_MINUTO);

                    prom_unMinutoAc += promedio;
                    CONT_PROM_DECIMO_UN_MINUTO++;

                    fileWriter.append("");
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString(promedio));
//                    System.out.println(DECIMO_UN_MINUTO+"\t Ux: "+cell[2] + "\t Uy: "+ cell[3] +"t Uz: "+ cell[4] + "\t Vector: " + vector
//                    + "\t Promedio: " +promedio );
                    unMinutoAc=0;
                    DECIMO_UN_MINUTO=0;
                }
                if(DECIMO_CINCO_MINUTOS % 3000 == 0){
                    promedio = (double) (cincoMinutoAc / DECIMO_CINCO_MINUTOS);

                    prom_cincoMinutoAc += promedio;
                    CONT_PROM_DECIMO_CINCO_MINUTOS++;

                    fileWriter.append("");
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString(promedio));
//                    System.out.println(DECIMO_CINCO_MINUTOS+"\t Ux: "+cell[2] + "\t Uy: "+ cell[3] +"t Uz: "+ cell[4] + "\t Vector: " + vector
//                    + "\t Promedio: " +promedio );
                    cincoMinutoAc = 0;
                    DECIMO_CINCO_MINUTOS=0;
                }
                if(DECIMO_DIEZ_MINUTOS % 6000 == 0){
                    promedio = (double) (diezMinutoAc / DECIMO_DIEZ_MINUTOS);
                    
                    prom_diezMinutoAc += promedio;
                    CONT_PROM_DECIMO_DIEZ_MINUTOS++; 
                    
                    fileWriter.append("");
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(Double.toString(promedio));
//                    System.out.println(DECIMO_DIEZ_MINUTOS+"\t Ux: "+cell[2] + "\t Uy: "+ cell[3] +"t Uz: "+ cell[4] + "\t Vector: " + vector
//                    + "\t Promedio: " +promedio );
                    diezMinutoAc = 0;
                    DECIMO_DIEZ_MINUTOS=0;
                }
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            DECIMO_SEGUNDO++;
            DECIMO_UN_SEGUNDO++;
            DECIMO_CINCO_SEGUNDOS++;
            DECIMO_TREINA_SEGUNDOS++;
            DECIMO_UN_MINUTO++;
            DECIMO_CINCO_MINUTOS++;
            DECIMO_DIEZ_MINUTOS++;
        } catch (IOException ex) {
            Logger.getLogger(CsvFileVectorAndAverages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void makeAveragesOfDay(boolean isEndOfArchive){
        if(isEndOfArchive){
            try {
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append("-");
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(acumulador/DECIMO_SEGUNDO));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(prom_unSegundoAc/CONT_PROM_DECIMO_UN_SEGUNDO));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(prom_cincoSegundosAc/CONT_PROM_DECIMO_CINCO_SEGUNDOS));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(prom_treintaSegundosAc/CONT_PROM_DECIMO_TREINA_SEGUNDOS));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(prom_unMinutoAc/CONT_PROM_DECIMO_UN_MINUTO));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(prom_cincoMinutoAc/CONT_PROM_DECIMO_CINCO_MINUTOS));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(Double.toString(prom_diezMinutoAc/CONT_PROM_DECIMO_DIEZ_MINUTOS));
            } catch (IOException ex) {
                Logger.getLogger(CsvFileVectorAndAverages.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                closeFileAndRestoreValues();
            }
        }
        
    }
    
    public void closeFileAndRestoreValues(){
        try {
            fileWriter.flush();
            fileWriter.close();

            DECIMO_SEGUNDO = 1;
            DECIMO_UN_SEGUNDO = 1;
            DECIMO_CINCO_SEGUNDOS = 1;
            DECIMO_TREINA_SEGUNDOS = 1;
            DECIMO_UN_MINUTO = 1;
            DECIMO_CINCO_MINUTOS = 1;
            DECIMO_DIEZ_MINUTOS = 1; 

            acumulador = 0;
            unSegundoAc = 0;
            cincoSegundosAc = 0;
            treintaSegundosAc = 0;
            unMinutoAc = 0;
            cincoMinutoAc = 0;
            diezMinutoAc = 0;                    
        } catch (IOException e) {
            System.out.println("Error while closing fileReader !!!");
            e.printStackTrace();
        }        
    }
}
