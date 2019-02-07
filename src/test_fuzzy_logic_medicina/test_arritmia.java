/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_fuzzy_logic_medicina;

import java.io.File;
import net.sourceforge.jFuzzyLogic.FIS;

/**
 *
 * @author rayma
 */
public class test_arritmia {
    public static void main(String[] args) throws Exception {
        // Load from 'FCL' file
        String fileName = "src"+File.separator+"test_fuzzy_logic_medicina"+File.separator+"arritmia.fcl";
        FIS fis = FIS.load(fileName,true);
        // Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" 
                                   + fileName + "'");
            return;
        }

        // Show 
        //fis.chart();

        // Set inputs
        //bloco calc_bloodPressure
        fis.setVariable("calc_bloodPressure", "systolicPressure", 120);
        fis.setVariable("calc_bloodPressure", "diastolicPressure", 90);
        // Evaluate
        fis.getFunctionBlock("calc_bloodPressure").evaluate();
        fis.getFunctionBlock("calc_bloodPressure").chart();
        fis.getFunctionBlock("calc_bloodPressure").getVariable("bloodPressure").chartDefuzzifier(true);
        double pressaoArterial=fis.getFunctionBlock("calc_bloodPressure").getVariable("bloodPressure").getValue();
        

        //bloco calc_respiratoryRate
        fis.setVariable("calc_respiratoryRate", "age_years", 20);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate0_1", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate1_7", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate8_12", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate13_20", 28);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate21_64", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate65_79", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate80_more", 0);
        
        // Evaluate
        fis.getFunctionBlock("calc_respiratoryRate").evaluate();
        fis.getFunctionBlock("calc_respiratoryRate").chart();
        fis.getFunctionBlock("calc_respiratoryRate").getVariable("respiratoryRate").chartDefuzzifier(true);
        double frequenciaRespiratoria=fis.getFunctionBlock("calc_respiratoryRate").getVariable("respiratoryRate").getValue();
        
        
        //bloco calc_heartRate
        fis.setVariable("calc_heartRate", "age_years", 20);
        fis.setVariable("calc_heartRate", "heartRate0_1_month", 0);
        fis.setVariable("calc_heartRate", "heartRate1_11_month", 0);
        fis.setVariable("calc_heartRate", "heartRate1_2", 0);
        fis.setVariable("calc_heartRate", "heartRate3_4", 0);
        fis.setVariable("calc_heartRate", "heartRate5_6", 0);
        fis.setVariable("calc_heartRate", "heartRate7_9", 0);
        fis.setVariable("calc_heartRate", "heartRate10_more", 110);
        // Evaluate
        //fis.getFunctionBlock("calc_frequenciaCardiaca").chart();
        fis.getFunctionBlock("calc_heartRate").evaluate();
        
        //fis.getFunctionBlock("calc_frequenciaCardiaca").chart();
        
        //fis.getFunctionBlock("calc_frequenciaCardiaca").getVariable("frequenciaCardiaca").chartDefuzzifier(true);
        double frequenciaCardiaca=fis.getFunctionBlock("calc_heartRate").getVariable("heartRate").getValue();
        
        
        //bloco calc_arrhythmiaType
        fis.setVariable("calc_arrhythmiaType", "oxygenSaturation", 88);// normal 96-99
        fis.setVariable("calc_arrhythmiaType", "bloodPressure", pressaoArterial);//
        fis.setVariable("calc_arrhythmiaType", "electrocardiogram", 0);
        fis.setVariable("calc_arrhythmiaType", "respiratoryRate", frequenciaRespiratoria);
        fis.setVariable("calc_arrhythmiaType", "heartRate", frequenciaCardiaca);
        fis.setVariable("calc_arrhythmiaType", "faint", 1);
        fis.setVariable("calc_arrhythmiaType", "breathlessness", 90);
        fis.setVariable("calc_arrhythmiaType", "palpitation", 1);
        fis.setVariable("calc_arrhythmiaType", "chestPain", 95);
        fis.setVariable("calc_arrhythmiaType", "dizziness", 83);
        
        
        //bloco calc_tipoArritmia
        /*fis.setVariable("calc_tipoArritmia", "saturacaoOxigenio", 96);// normal 96-99
        fis.setVariable("calc_tipoArritmia", "pressaoArterial", 35);//normal 30-50
        fis.setVariable("calc_tipoArritmia", "electroCardiograma", 0);
        fis.setVariable("calc_tipoArritmia", "frequenciaRespiratoria", 60);//normal 60
        fis.setVariable("calc_tipoArritmia", "frequenciaCardiaca", 60);//normal
        fis.setVariable("calc_tipoArritmia", "desmaio", 1);
        fis.setVariable("calc_tipoArritmia", "faltaDeAr", 90);
        fis.setVariable("calc_tipoArritmia", "palpitacao", 1);
        fis.setVariable("calc_tipoArritmia", "dorDeTorax", 20);
        fis.setVariable("calc_tipoArritmia", "tontura", 89);
        */
        //fis.getFunctionBlock("calc_tipoArritmia").chart();
                
        fis.getFunctionBlock("calc_arrhythmiaType").evaluate();
        
        
        double arritmiaRemedio=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaMedicine").getValue();
        double arritmiaChoque=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaShock").getValue();
        double arritmiaAbraco=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaHug").getValue();
        
        // Print ruleSet
        System.out.println("é Arritmia Remedio: "+arritmiaRemedio+" ");
        System.out.println("é Arritmia Choque: "+arritmiaChoque+" ");
        System.out.println("é Arritmia Abraco: "+arritmiaAbraco+" ");
    }
}
