/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_fuzzy_logic_medicina;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;

/**
 *
 * @author rayma
 */
public class CDiagnostician {
    public FIS fis;
    
    public CDiagnostician(){
        String fileName = "src"+File.separator+"test_fuzzy_logic_medicina"+File.separator+"acid_base_disturbances.fcl";
        //String fileName = "src"+File.separator+"test_fuzzy_logic_medicina"+File.separator+"arritmia.fcl";
        fis = FIS.load(fileName,true);
        // Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" 
                                   + fileName + "'");
        }
    }
    public static double round_decimals( double number, int decimals ) {
        return Math.round(number*Math.pow(10,decimals))/Math.pow(10,decimals);
    }
    public double calculateSbe(double hco3,double ph){
        return hco3-24.8+(16*(ph-7.4));
    }
    public void evaluateAcidBaseDisturbances(double ph,double pco2,double hco3){
        double sbe=calculateSbe(hco3, ph);
        evaluateAcidBaseDisturbances(ph, pco2, hco3, sbe);
    }
    public Object[] maxTermMembership(String disturbancesName,String nameFunctionBlock){
        double max=0;
        double membership=0;
        String term="";
        // Show each rule (and degree of support)
        for(LinguisticTerm l : fis.getFunctionBlock(nameFunctionBlock).getVariable(disturbancesName).getLinguisticTerms().values() ){
            membership=fis.getFunctionBlock(nameFunctionBlock).getVariable(disturbancesName).getMembership(l.getTermName());
            if(membership>max){
                max=membership;
                term=l.getTermName();
            }
        }
        Object[] maxterm_membership={term,max};
        return maxterm_membership;
    }
    public String calculateSecondDisturbance(CPossibleDiagnose primary_diagnose,double ph,double pco2,double hco3,double sbe){
        double dhco3=hco3-20;
        double dpco2=pco2-40;
        double dhco3exp;
        double sbeexp;
        double pco2exp;
        double dpco2exp;

        switch (primary_diagnose.getDiagnose()) {
            case "acute_respiratory_acidosis":
                fis.setVariable("calc_sbe_sec","compare_sbe",sbe-(-2));
                fis.setVariable("calc_sbe_sec","compare_dhco3",dhco3-40);
                fis.setVariable("calc_sbe_sec","compare_sbe_2",sbe-2);
                dhco3exp=10*dpco2;
                fis.setVariable("calc_sbe_sec","compare_dhco3_2",dhco3-dhco3exp);
                break;
            case "acute_respiratory_alkalosis":
                dhco3exp=5*dpco2;
                fis.setVariable("calc_sbe_sec","compare_sbe",sbe-(-2));
                fis.setVariable("calc_sbe_sec","compare_dhco3",dhco3-dhco3exp);
                fis.setVariable("calc_sbe_sec","compare_sbe_2",sbe-2);
                fis.setVariable("calc_sbe_sec","compare_dhco3_2",dhco3-20);
                break;
            case "chronic_respiratory_acidosis":
                sbeexp=0.4*(pco2-40);
                fis.setVariable("calc_sbe_sec","compare_sbe",sbe-sbeexp);
                fis.setVariable("calc_sbe_sec","compare_dhco3",dhco3-8);
                dhco3exp=2.5*dpco2;
                fis.setVariable("calc_sbe_sec","compare_sbe_2",sbe-sbeexp);
                fis.setVariable("calc_sbe_sec","compare_dhco3_2",dhco3-dhco3exp);
                break;
            case "chronic_respiratory_alkalosis":
                sbeexp=0.4*(pco2-40);
                dhco3exp=2*dpco2;
                fis.setVariable("calc_sbe_sec","compare_sbe",sbe-sbeexp);
                fis.setVariable("calc_sbe_sec","compare_dhco3",dhco3-dhco3exp);
                fis.setVariable("calc_sbe_sec","compare_sbe_2",sbe-sbeexp);
                fis.setVariable("calc_sbe_sec","compare_dhco3_2",dhco3-10);
                break;
            case "metabolic_acidosis":
                dpco2exp=sbe;
                pco2exp=1.5*hco3+6;
                fis.setVariable("calc_sbe_sec","compare_dpco2",dpco2-dpco2exp);
                fis.setVariable("calc_sbe_sec","compare_pco2",pco2-pco2exp);
                pco2exp=1.5*hco3+10;
                fis.setVariable("calc_sbe_sec","compare_dpco2_2",dpco2-dpco2exp);
                fis.setVariable("calc_sbe_sec","compare_pco2_2",pco2-pco2exp);
                break;
            default: //"metabolic_alkalosis":
                dpco2exp=sbe;
                pco2exp=0.7*(hco3-24)+38;
                fis.setVariable("calc_sbe_sec","compare_dpco2",dpco2-dpco2exp);
                fis.setVariable("calc_sbe_sec","compare_pco2",pco2-pco2exp);
                pco2exp=0.7*(hco3-24)+42;
                fis.setVariable("calc_sbe_sec","compare_dpco2_2",dpco2-dpco2exp);
                fis.setVariable("calc_sbe_sec","compare_pco2_2",pco2-pco2exp);
                break;
        }
        fis.getFunctionBlock("calc_sbe_sec").evaluate();
        
        //fis.getFunctionBlock("calc_sbe_sec").chart();
        
        List<CPossibleDiagnose> list_possible_diagnose=new ArrayList<>();
        Object[] maxterm_membership;
        
        String[] disturbancesNamesSec={
            "additional_respiratory_acidosis",
            "additional_respiratory_alkalosis",
            "additional_metabolic_acidosis",
            "additional_metabolic_alkalosis"    
        };
        
        for (int i = 0; i < 4; i++) {
            maxterm_membership=maxTermMembership(disturbancesNamesSec[i],"calc_sbe_sec");
            list_possible_diagnose.add(new CPossibleDiagnose(disturbancesNamesSec[i],
                    round_decimals(fis.getFunctionBlock("calc_sbe_sec").getVariable(disturbancesNamesSec[i]).getValue(),2),
                    (String)maxterm_membership[0],round_decimals((Double)maxterm_membership[1],2)));
        }        
        Collections.sort(list_possible_diagnose);
        
        return list_possible_diagnose.get(0).toString();
                
        
    }
    public String evaluateAcidBaseDisturbances(double ph,double pco2,double hco3,double sbe){
        String res;
        //array of the names the possible primary disturbances
        String[] disturbancesNames={
            "acute_respiratory_acidosis",
            "acute_respiratory_alkalosis",
            "chronic_respiratory_acidosis",
            "chronic_respiratory_alkalosis",
            "metabolic_acidosis",
            "metabolic_alkalosis"};
        //set the input values to the block rule calc_sbe of Fuzzy Logic Inference System (FIS)
        fis.setVariable("calc_sbe", "ph", ph);
        fis.setVariable("calc_sbe", "pco2", pco2);
        fis.setVariable("calc_sbe", "sbe", sbe);
        //evaluate the input values
        fis.getFunctionBlock("calc_sbe").evaluate();
        //fis.getFunctionBlock("calc_sbe").chart();
        
        List<CPossibleDiagnose> list_possible_diagnose=new ArrayList<>();
        Object[] maxterm_membership;
        
        for (int i = 0; i < 6; i++) {
            maxterm_membership=maxTermMembership(disturbancesNames[i],"calc_sbe");
            list_possible_diagnose.add(new CPossibleDiagnose(disturbancesNames[i],
                    round_decimals(fis.getFunctionBlock("calc_sbe").getVariable(disturbancesNames[i]).getValue(),2),
                    (String)maxterm_membership[0],round_decimals((Double)maxterm_membership[1],2)));
        }        
        Collections.sort(list_possible_diagnose);
        int i=0;
        for (CPossibleDiagnose next : list_possible_diagnose) {
            
            System.out.println((++i)+" "+next);
        }
        res=list_possible_diagnose.get(0).toString()+" with "+calculateSecondDisturbance(list_possible_diagnose.get(0), ph, pco2, hco3, sbe);
        
        return res;
                
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        CDiagnostician cDiagnostician=new CDiagnostician();
        
        //ph,pco2,hco3,sbe
        double[][] cases={
            {7.21,23,9,-8},//1
            {7.3,16,10,-8},//2
            {7.1,32,11,-5},//3 
            {7.28,74,34,12},//4
            {7.56,47,38,5}, //5
            {7.58,22,20,-3},//6
            {7.26,16,12,-8},//7
            {7.08,40,10,-9},//8
            {7.49,43,31,7},//9
            {7.26,82,32,8},//10
            {7.19,16.3,6,-21.2},//11
            {7.08,40,10,-9},//12
            {7.46,51,34,6},//13
            {7.28,34,14,-5},//14
            {7.51,46,33,6}//15
            };
        
        int i=1;//the number of case
        System.out.println(cDiagnostician.evaluateAcidBaseDisturbances(cases[i-1][0], cases[i-1][1], cases[i-1][2], cases[i-1][3]));
    }
    
}
