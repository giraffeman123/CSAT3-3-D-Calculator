/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoservicio;

/**
 *
 * @author Elliot
 */
public class MainFile {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//                String noviembre[] = {
//                  "11-11-16_3883.ts_data_326.csv","12-11-16_3883.ts_data_327.csv","13-11-16_3883.ts_data_328.csv",
//                    "14-11-16_3883.ts_data_329.csv","15-11-16_3883.ts_data_330.csv","16-11-16_3883.ts_data_331.csv",
//                    "17-11-16_3883.ts_data_332.csv","18-11-16_3883.ts_data_333.csv","19-11-16_3883.ts_data_334.csv",
//                    "20-11-16_3883.ts_data_335.csv","21-11-16_3883.ts_data_336.csv","22-11-16_3883.ts_data_337.csv",
//                    "23-11-16_3883.ts_data_338.csv","24-11-16_3883.ts_data_339.csv","25-11-16_3883.ts_data_340.csv",
//                    "26-11-16_3883.ts_data_341.csv","27-11-16_3883.ts_data_342.csv","28-11-16_3883.ts_data_343.csv",
//                    "29-11-16_3883.ts_data_344.csv","30-11-16_3883.ts_data_345.csv"
//                };
//                String fileNamePromedios[] = {
//                  "Noviembre 12.csv","Noviembre 13.csv","Noviembre 14.csv","Noviembre 15.csv","Noviembre 16.csv",
//                    "Noviembre 17.csv","Noviembre 18.csv","Noviembre 19.csv","Noviembre 20.csv","Noviembre 21.csv",
//                    "Noviembre 22.csv","Noviembre 23.csv","Noviembre 24.csv","Noviembre 25.csv","Noviembre 26.csv",
//                    "Noviembre 27.csv","Noviembre 28.csv","Noviembre 29.csv","Noviembre 30.csv",
//                };            
                CsvRawFileReader CsvRawFileManager = new CsvRawFileReader();
                CsvFileVarianceAndStandardDeviation VarianzayDesviacion = new CsvFileVarianceAndStandardDeviation();
                CvsMonthFileVarianceAndStandardDeviationAndKAndC MonthFile = new CvsMonthFileVarianceAndStandardDeviationAndKAndC();
                
                String noviembre[] = {
                  "0-11-16_3883.ts_data_346.csv","1-12-16_3883.ts_data_347.csv","2-12-16_3883.ts_data_348.csv",
                    "3-12-16_3883.ts_data_349.csv","4-12-16_3883.ts_data_351.csv","5-12-16_3883.ts_data_352.csv",
                    "6-12-16_3883.ts_data_353.csv"
                };                

                String fileNamePromedios[] = {
                  "Diciembre 1.csv","Diciembre 2.csv","Diciembre 3.csv","Diciembre 4.csv","Diciembre 5.csv",
                    "Diciembre 6.csv"
                }; 

                String fileNameVarianzaDesviacion[] = {
                  "Diciembre 1 Varianza.csv","Diciembre 2 Varianza.csv","Diciembre 3 Varianza.csv","Diciembre 4 Varianza.csv",
                    "Diciembre 5 Varianza.csv","Diciembre 6 Varianza.csv"
                };  
                
                String monthFileNameVarianzaDesviacion[] = {
                  "Diciembre Promedio y Varianza.csv"
                };                 
                
                //******************************************
                for(int x=0;x<6;x++){
                    // SE NOMBRA AL ARCHIVO DE PROMEDIOS (EN ESTE CASO SERA LLAMADO NOVIEMBRE 12)
                    CsvRawFileManager.setFile_Name_Promedios(fileNamePromedios[x]);
                    
                    // SE LEE ULTIMA FILA DEL ARCHIVO ANTERIOR (NOVIEMBRE 11)
                    System.out.println("\nHaciendo Promedios (Read Last Row of CSV file)");
                    CsvRawFileManager.initiateAllOtherFiles();
                    CsvRawFileManager.getLastRowOfFile(noviembre[x]);       
                    
                    
                    // SE SACAN LOS PROMEDIOS
                    CsvRawFileManager.readCsvFile(noviembre[x+1]);         
                }
                
                /********************************************/                
                for(int x=0;x<6;x++){
                    System.out.println("\nHaciendo varianza y desviacion["+x+"]");
                    VarianzayDesviacion.setFile_Name_Varianza_Desviacion(fileNameVarianzaDesviacion[x]);
                    VarianzayDesviacion.createFile();
                    VarianzayDesviacion.obtainAverages(fileNamePromedios[x]);
                    VarianzayDesviacion.makeVariance(fileNamePromedios[x],fileNameVarianzaDesviacion[x]);
                }                

                /********************************************/
                System.out.println("\nHaciendo varianza y desviacion del mes["+"]");
                MonthFile.setMonth_File_Name_Varianza_Desviacion(monthFileNameVarianzaDesviacion[0]);
                MonthFile.createFile();
                for(int x=0;x<6;x++){
                    MonthFile.obtainAverages(fileNamePromedios[x]);
                }
                
                MonthFile.makeMonthAverage();
                for(int x=0;x<6;x++){
                    MonthFile.makeXi(fileNamePromedios[x]);
                }                
                MonthFile.makeMonthVarianceAndStandardDeviationAndKAndC();
                MonthFile.closeFileAndRestoreValues();

	}    
}
