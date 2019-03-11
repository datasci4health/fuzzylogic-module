/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_fuzzy_logic_medicina;

/**
 *
 * @author rayma
 */
public class CPossibleDiagnose implements Comparable<CPossibleDiagnose>{
    private String diagnose;
    private double probability;
    private String term;
    private double membership;

    public CPossibleDiagnose(String diagnose, double probability, String term, double membership) {
        this.diagnose = diagnose;
        this.probability = probability;
        this.term = term;
        this.membership = membership;
    }
    
    


    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getMembership() {
        return membership;
    }

    public void setMembership(double membership) {
        this.membership = membership;
    }
    @Override
    public int compareTo(CPossibleDiagnose o){
        return ((Double)o.probability).compareTo(probability);
    }
    
    @Override
    public String toString(){
        return diagnose+" "+probability+" "+term+" "+membership;
    }
}
