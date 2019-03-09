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
public class CsvRawFileReader {
    
    CsvFileVectorAndAverages PromediosyVector;
    String file_Name_Promedios="";

    
    public String getFile_Name_Promedios() {
        return file_Name_Promedios;
    }

    public void setFile_Name_Promedios(String file_Name_Promedios) {
        this.file_Name_Promedios = file_Name_Promedios;
    }
    
    public void initiateAllOtherFiles(){
        PromediosyVector = new CsvFileVectorAndAverages();
        PromediosyVector.setFile_Name_Promedios(file_Name_Promedios);
        PromediosyVector.createFile();
    }
    
    public void readCsvFile(String fileName) {

        BufferedReader fileReader = null;            
        try {
            String line = "",cell[];
            int ROW=0;

            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));         
            //Read the CSV file header to skip it
            fileReader.readLine();


            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                cell = line.split(",");                
                if(ROW > 2){
                    PromediosyVector.addVectorAndAverages(cell);
                }
                ROW++;
            }

            if( (line = fileReader.readLine()) == null ){
                  PromediosyVector.makeAveragesOfDay(true);
            }                


        } catch (IOException ex) {
            System.out.println("Error in CsvFileReader !!!");
            ex.printStackTrace();
        } finally {
            try {
                fileReader.close();                
            } catch (IOException e) {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
    }
        
    public void getLastRowOfFile(String fileName){
                
	BufferedReader aux = null,fileReader = null;        
     
        try {
            String line = "",cell[];
            int ROW=0,ROWS=0;
            
            //Create the aux reader
            aux = new BufferedReader(new FileReader(fileName));      
            ROWS = (int) aux.lines().count();
            System.out.println("ROWS: "+ROWS);
            
            
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));      
            
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                cell = line.split(",");     
                if(ROW == ROWS-1){
                    PromediosyVector.addLastRowOfFile(cell);
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

    public void getLastTwoRowOfFile(String fileName){
                
	BufferedReader aux = null,fileReader = null;        
     
        try {
            String line = "",cell[];
            int ROW=0,ROWS=0;
            
            //Create the aux reader
            aux = new BufferedReader(new FileReader(fileName));      
            ROWS = (int) aux.lines().count();
            System.out.println("ROWS: "+ROWS);
            
            
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));      
            
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                cell = line.split(",");     
                if(ROW >= ROWS-2){
                    PromediosyVector.addLastRowOfFile(cell);
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

    public void getLastThreeRowOfFile(String fileName){
                
	BufferedReader aux = null,fileReader = null;        
     
        try {
            String line = "",cell[];
            int ROW=0,ROWS=0;
            
            //Create the aux reader
            aux = new BufferedReader(new FileReader(fileName));      
            ROWS = (int) aux.lines().count();
            System.out.println("ROWS: "+ROWS);
            
            
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));      
            
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                cell = line.split(",");     
                if(ROW >= ROWS-3){
                    PromediosyVector.addLastRowOfFile(cell);
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
    
}
