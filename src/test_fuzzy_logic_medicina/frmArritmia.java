package test_fuzzy_logic_medicina;

import java.io.File;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rayma
 */
public class frmArritmia extends javax.swing.JFrame {

    /**
     * Creates new form frmArritmia
     */
    public FIS fis;
    double age_years;
    double valueBloodPressure;
    double valueRespiratoryRate;
    double valueHeartRate;
    double valueArrhythmiaMedicine;
    double valueArrhythmiaShock;
    double valueArrhythmiaHug;
    
    public frmArritmia() {
        initComponents();
        // Load from 'FCL' file
        String fileName = "src"+File.separator+"test_fuzzy_logic_medicina"+File.separator+"arritmia.fcl";
        fis = FIS.load(fileName,true);
        // Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" 
                                   + fileName + "'");
        }
    }
    public void calculateBloodPressure(){
        //input variables
        fis.setVariable("calc_bloodPressure", "systolicPressure", (Integer)jspSystolicPressure.getValue());
        fis.setVariable("calc_bloodPressure", "diastolicPressure", (Integer)jspDiastolicPressure.getValue());
        //evaluate
        fis.getFunctionBlock("calc_bloodPressure").evaluate();
        valueBloodPressure=fis.getFunctionBlock("calc_bloodPressure").getVariable("bloodPressure").getValue();
        
        double max=0;
        double membership=0;
        String term="";
        // Show each rule (and degree of support)
        for(LinguisticTerm l : fis.getFunctionBlock("calc_bloodPressure").getVariable("bloodPressure").getLinguisticTerms().values() ){
            membership=fis.getFunctionBlock("calc_bloodPressure").getVariable("bloodPressure").getMembership(l.getTermName());
            if(membership>max){
                max=membership;
                term=l.getTermName();
            }
        }
        laBP_BloodPressure.setText(term.toUpperCase()+" with "+redondear(max, 2)+" merbership degree, value: "+redondear(valueBloodPressure,2));
        laA_BloodPressure.setText(term.toUpperCase());
    }
    public void calculateRespiratoryRate(){
        //input variables
        
        age_years=(Integer)jspMonths.getValue()/12.0+(Integer)jspYears.getValue();
        fis.setVariable("calc_respiratoryRate", "age_years", age_years);
        
        fis.setVariable("calc_respiratoryRate", "respiratoryRate0_1", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate1_7", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate8_12", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate13_20", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate21_64", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate65_79", 0);
        fis.setVariable("calc_respiratoryRate", "respiratoryRate80_more", 0);
        if(age_years<=1){
            fis.setVariable("calc_respiratoryRate", "respiratoryRate0_1", (Integer)jspRespiratoryRate.getValue());
        }else if(age_years>=1 && age_years<=7){
            fis.setVariable("calc_respiratoryRate", "respiratoryRate1_7", (Integer)jspRespiratoryRate.getValue());
        }else if(age_years>=8 && age_years<=12){
            fis.setVariable("calc_respiratoryRate", "respiratoryRate8_12", (Integer)jspRespiratoryRate.getValue());
        }else if(age_years>=13 && age_years<=20){
            fis.setVariable("calc_respiratoryRate", "respiratoryRate13_20", (Integer)jspRespiratoryRate.getValue());
        }else if(age_years>=21 && age_years<=64){
            fis.setVariable("calc_respiratoryRate", "respiratoryRate21_64", (Integer)jspRespiratoryRate.getValue());
        }else if(age_years>=65 && age_years<=79){
            fis.setVariable("calc_respiratoryRate", "respiratoryRate65_79", (Integer)jspRespiratoryRate.getValue());
        }else if(age_years>=80){
            fis.setVariable("calc_respiratoryRate", "respiratoryRate80_more", (Integer)jspRespiratoryRate.getValue());
        }
        
        //evaluate
        fis.getFunctionBlock("calc_respiratoryRate").evaluate();
        valueRespiratoryRate=fis.getFunctionBlock("calc_respiratoryRate").getVariable("respiratoryRate").getValue();
        
        double max=0;
        double membership=0;
        String term="";
        // Show each rule (and degree of support)
        for(LinguisticTerm l : fis.getFunctionBlock("calc_respiratoryRate").getVariable("respiratoryRate").getLinguisticTerms().values() ){
            membership=fis.getFunctionBlock("calc_respiratoryRate").getVariable("respiratoryRate").getMembership(l.getTermName());
            if(membership>max){
                max=membership;
                term=l.getTermName();
            }
        }
        laRR_RespiratoryRate.setText(term.toUpperCase()+" with "+redondear(max, 2)+" merbership degree, value: "+redondear(valueRespiratoryRate,2));
        laA_RespiratoryRate.setText(term.toUpperCase());
    }
    public void calculateHeartRate(){
        //input variables
        age_years=(Integer)jspMonths.getValue()/12.0+(Integer)jspYears.getValue();
        fis.setVariable("calc_heartRate", "age_years", age_years);
        
        fis.setVariable("calc_heartRate", "heartRate0_1_month", 0);
        fis.setVariable("calc_heartRate", "heartRate1_11_month", 0);
        fis.setVariable("calc_heartRate", "heartRate1_2", 0);
        fis.setVariable("calc_heartRate", "heartRate3_4", 0);
        fis.setVariable("calc_heartRate", "heartRate5_6", 0);
        fis.setVariable("calc_heartRate", "heartRate7_9", 0);
        fis.setVariable("calc_heartRate", "heartRate10_more", 0);
        if(age_years<=0.083){
            fis.setVariable("calc_heartRate", "heartRate0_1_month", (Integer)jspHeartRate.getValue());
        }else if(age_years>0.083 && age_years<=0.916){
            fis.setVariable("calc_heartRate", "heartRate1_11_month", (Integer)jspHeartRate.getValue());
        }else if(age_years>=1 && age_years<=2){
            fis.setVariable("calc_heartRate", "heartRate1_2", (Integer)jspHeartRate.getValue());
        }else if(age_years>=3 && age_years<=4){
            fis.setVariable("calc_heartRate", "heartRate3_4", (Integer)jspHeartRate.getValue());
        }else if(age_years>=5 && age_years<=6){
            fis.setVariable("calc_heartRate", "heartRate5_6", (Integer)jspHeartRate.getValue());
        }else if(age_years>=7 && age_years<=9){
            fis.setVariable("calc_heartRate", "heartRate7_9", (Integer)jspHeartRate.getValue());
        }else if(age_years>=10){
            fis.setVariable("calc_heartRate", "heartRate10_more", (Integer)jspHeartRate.getValue());
        }
        
        //evaluate
        fis.getFunctionBlock("calc_heartRate").evaluate();
        valueHeartRate=fis.getFunctionBlock("calc_heartRate").getVariable("heartRate").getValue();
        
        double max=0;
        double membership=0;
        String term="";
        // Show each rule (and degree of support)
        for(LinguisticTerm l : fis.getFunctionBlock("calc_heartRate").getVariable("heartRate").getLinguisticTerms().values() ){
            membership=fis.getFunctionBlock("calc_heartRate").getVariable("heartRate").getMembership(l.getTermName());
            if(membership>max){
                max=membership;
                term=l.getTermName();
            }
        }
        laHR_HeartRate.setText(term.toUpperCase()+" with "+redondear(max, 2)+" merbership degree, value: "+redondear(valueHeartRate,2));
        laHeartRate.setText(term.toUpperCase());
    }
    public void calculateArrhythmiaType(){
        //input variables
        /*
        VAR_INPUT
            oxygenSaturation : REAL;
            bloodPressure : REAL;
            electrocardiogram : REAL;
            respiratoryRate: REAL;
            heartRate: REAL;
            faint: REAL;
            breathlessness: REAL;
            palpitation: REAL;
            chestPain: REAL;
            dizziness: REAL;
        END_VAR
        // Define output variable
        VAR_OUTPUT
            arrhythmiaMedicine : REAL;
            arrhythmiaShock :  REAL;
            arrhythmiaHug:  REAL;

        END_VAR
        */
        fis.setVariable("calc_arrhythmiaType", "oxygenSaturation", (Integer)jspOxygenSaturation.getValue());
        fis.setVariable("calc_arrhythmiaType", "bloodPressure", valueBloodPressure);
        fis.setVariable("calc_arrhythmiaType", "electrocardiogram",(jchbElectrocardiogram.isSelected())?1:0);
        fis.setVariable("calc_arrhythmiaType", "respiratoryRate", valueRespiratoryRate);
        fis.setVariable("calc_arrhythmiaType", "heartRate", valueHeartRate);
        fis.setVariable("calc_arrhythmiaType", "faint",(jchbFaint.isSelected())?1:0);
        fis.setVariable("calc_arrhythmiaType", "breathlessness", (Integer)jspBreathlessness.getValue());
        fis.setVariable("calc_arrhythmiaType", "palpitation",(jchbPalpitation.isSelected())?1:0);
        fis.setVariable("calc_arrhythmiaType", "chestPain", (Integer)jspChestPain.getValue());
        fis.setVariable("calc_arrhythmiaType", "dizziness", (Integer)jspDizziness.getValue());
        //evaluate
        fis.getFunctionBlock("calc_arrhythmiaType").evaluate();
        
        valueArrhythmiaMedicine=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaMedicine").getValue();
        valueArrhythmiaShock=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaShock").getValue();
        valueArrhythmiaHug=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaHug").getValue();
        
        double maxMedicine=0;
        double membershipMed=0;
        String termMedicine="";
        // Show each rule (and degree of support)
        for(LinguisticTerm l : fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaMedicine").getLinguisticTerms().values() ){
            membershipMed=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaMedicine").getMembership(l.getTermName());
            if(membershipMed>maxMedicine){
                maxMedicine=membershipMed;
                termMedicine=l.getTermName();
            }
        }
        double maxShock=0;
        double membershipShock=0;
        String termShock="";
        // Show each rule (and degree of support)
        for(LinguisticTerm l : fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaShock").getLinguisticTerms().values() ){
            membershipShock=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaShock").getMembership(l.getTermName());
            if(membershipShock>maxShock){
                maxShock=membershipShock;
                termShock=l.getTermName();
            }
        }
        double maxHug=0;
        double membershipHug=0;
        String termHug="";
        // Show each rule (and degree of support)
        for(LinguisticTerm l : fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaHug").getLinguisticTerms().values() ){
            membershipHug=fis.getFunctionBlock("calc_arrhythmiaType").getVariable("arrhythmiaHug").getMembership(l.getTermName());
            if(membershipHug>maxHug){
                maxHug=membershipHug;
                termHug=l.getTermName();
            }
        }
        
        jtpTypeArrhytmia.setText(
                "Arrhythmia Medicine: "+termMedicine.toUpperCase()+" with "+redondear(maxMedicine, 2)+" merbership degree, value: "+redondear(valueArrhythmiaMedicine,2)+"\n"+
                "Arrhythmia Shock: "+termShock.toUpperCase()+" with "+redondear(maxShock, 2)+" merbership degree, value: "+redondear(valueArrhythmiaShock,2)+"\n"+
                "Arrhythmia Hug: "+termHug.toUpperCase()+" with "+redondear(maxHug, 2)+" merbership degree, value: "+redondear(valueArrhythmiaHug,2)+""
        );
                
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        laRR_RespiratoryRate = new javax.swing.JLabel();
        jspRespiratoryRate = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jspSystolicPressure = new javax.swing.JSpinner();
        laBP_BloodPressure = new javax.swing.JLabel();
        jspDiastolicPressure = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jspYears = new javax.swing.JSpinner();
        jspMonths = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        laHR_HeartRate = new javax.swing.JLabel();
        jspHeartRate = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        laA_BloodPressure = new javax.swing.JLabel();
        laA_RespiratoryRate = new javax.swing.JLabel();
        laHeartRate = new javax.swing.JLabel();
        jspOxygenSaturation = new javax.swing.JSpinner();
        jchbElectrocardiogram = new javax.swing.JCheckBox();
        jchbFaint = new javax.swing.JCheckBox();
        jspBreathlessness = new javax.swing.JSpinner();
        jchbPalpitation = new javax.swing.JCheckBox();
        jspChestPain = new javax.swing.JSpinner();
        jspDizziness = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpTypeArrhytmia = new javax.swing.JTextPane();
        jButton4 = new javax.swing.JButton();
        buCalculate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Respiratory rate"));

        jLabel3.setText("Breaths per minute");

        laRR_RespiratoryRate.setForeground(java.awt.Color.blue);
        laRR_RespiratoryRate.setText("type");

        jspRespiratoryRate.setModel(new javax.swing.SpinnerNumberModel(28, 0, 100, 1));

        jButton2.setText("see");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(50, 50, 50)
                .addComponent(jspRespiratoryRate, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(laRR_RespiratoryRate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(laRR_RespiratoryRate)
                .addComponent(jspRespiratoryRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Blood pressure"));

        jLabel1.setText("Systolic Pressure");

        jLabel2.setText("Diastolic Pressure");

        jspSystolicPressure.setModel(new javax.swing.SpinnerNumberModel(120, 40, 180, 1));

        laBP_BloodPressure.setForeground(java.awt.Color.blue);
        laBP_BloodPressure.setText("type");

        jspDiastolicPressure.setModel(new javax.swing.SpinnerNumberModel(90, 10, 130, 1));

        jButton1.setText("see");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jspSystolicPressure)
                    .addComponent(jspDiastolicPressure))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(laBP_BloodPressure)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jspSystolicPressure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jspDiastolicPressure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(laBP_BloodPressure))
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Age"));

        jLabel7.setText("Years");

        jLabel8.setText("Months");

        jspYears.setModel(new javax.swing.SpinnerNumberModel(20, 0, 150, 1));

        jspMonths.setModel(new javax.swing.SpinnerNumberModel(0, 0, 12, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspYears, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspMonths, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jspMonths, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jspYears, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Heart rate"));

        laHR_HeartRate.setForeground(java.awt.Color.blue);
        laHR_HeartRate.setText("type");

        jspHeartRate.setModel(new javax.swing.SpinnerNumberModel(110, 0, 220, 1));

        jLabel4.setText("Contractions per minute ");

        jButton3.setText("see");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspHeartRate, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(laHR_HeartRate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(jspHeartRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(laHR_HeartRate)
                .addComponent(jButton3))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Arrhythmia"));

        jLabel5.setText("oxygen saturation");

        jLabel6.setText("blood pressure");

        jLabel12.setText("electrocardiogram");

        jLabel13.setText("respiratory rate");

        jLabel14.setText(" heart rate");

        jLabel15.setText("faint");

        jLabel16.setText("breathlessness");

        jLabel17.setText("palpitation");

        jLabel18.setText("chest pain");

        jLabel19.setText("dizziness");

        laA_BloodPressure.setForeground(java.awt.Color.blue);
        laA_BloodPressure.setText("type");

        laA_RespiratoryRate.setForeground(java.awt.Color.blue);
        laA_RespiratoryRate.setText("type");

        laHeartRate.setForeground(java.awt.Color.blue);
        laHeartRate.setText("type");

        jspOxygenSaturation.setModel(new javax.swing.SpinnerNumberModel(88, 70, 100, 1));

        jchbElectrocardiogram.setText("is sinusoidal?");

        jchbFaint.setSelected(true);

        jspBreathlessness.setModel(new javax.swing.SpinnerNumberModel(90, 0, 100, 1));

        jchbPalpitation.setSelected(true);

        jspChestPain.setModel(new javax.swing.SpinnerNumberModel(95, 0, 100, 1));

        jspDizziness.setModel(new javax.swing.SpinnerNumberModel(83, 0, 100, 1));

        jLabel24.setText("%");

        jLabel25.setText("%");

        jtpTypeArrhytmia.setEditable(false);
        jScrollPane1.setViewportView(jtpTypeArrhytmia);

        jButton4.setText("see");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addGap(53, 53, 53)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(laHeartRate)
                                    .addComponent(laA_RespiratoryRate)
                                    .addComponent(laA_BloodPressure)
                                    .addComponent(jspOxygenSaturation, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(31, 31, 31)
                                .addComponent(jchbElectrocardiogram)))
                        .addGap(78, 78, 78)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jspBreathlessness, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jchbPalpitation)
                                    .addComponent(jchbFaint))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jspChestPain)
                            .addComponent(jspDizziness))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(130, 130, 130))
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jchbFaint, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel15)
                                .addComponent(jspOxygenSaturation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel16)
                            .addComponent(laA_BloodPressure)
                            .addComponent(jspBreathlessness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel17)
                            .addComponent(jchbElectrocardiogram)))
                    .addComponent(jchbPalpitation, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel18)
                    .addComponent(laA_RespiratoryRate)
                    .addComponent(jspChestPain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19)
                    .addComponent(laHeartRate)
                    .addComponent(jspDizziness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );

        buCalculate.setText("Calculate");
        buCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buCalculateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buCalculate)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buCalculate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buCalculateActionPerformed
        // TODO add your handling code here:
        fis.getFunctionBlock("calc_bloodPressure").reset(true);
        fis.getFunctionBlock("calc_respiratoryRate").reset(true);
        fis.getFunctionBlock("calc_heartRate").reset(true);
        fis.getFunctionBlock("calc_arrhythmiaType").reset(true);
        calculateBloodPressure();
        calculateRespiratoryRate();
        calculateHeartRate();
        calculateArrhythmiaType();
    }//GEN-LAST:event_buCalculateActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        fis.getFunctionBlock("calc_respiratoryRate").chart();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        fis.getFunctionBlock("calc_bloodPressure").chart();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        fis.getFunctionBlock("calc_heartRate").chart();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        fis.getFunctionBlock("calc_arrhythmiaType").chart();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmArritmia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmArritmia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmArritmia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmArritmia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmArritmia().setVisible(true);
            }
        });
    }
    public static double redondear( double numero, int decimales ) {
        return Math.round(numero*Math.pow(10,decimales))/Math.pow(10,decimales);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buCalculate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jchbElectrocardiogram;
    private javax.swing.JCheckBox jchbFaint;
    private javax.swing.JCheckBox jchbPalpitation;
    private javax.swing.JSpinner jspBreathlessness;
    private javax.swing.JSpinner jspChestPain;
    private javax.swing.JSpinner jspDiastolicPressure;
    private javax.swing.JSpinner jspDizziness;
    private javax.swing.JSpinner jspHeartRate;
    private javax.swing.JSpinner jspMonths;
    private javax.swing.JSpinner jspOxygenSaturation;
    private javax.swing.JSpinner jspRespiratoryRate;
    private javax.swing.JSpinner jspSystolicPressure;
    private javax.swing.JSpinner jspYears;
    private javax.swing.JTextPane jtpTypeArrhytmia;
    private javax.swing.JLabel laA_BloodPressure;
    private javax.swing.JLabel laA_RespiratoryRate;
    private javax.swing.JLabel laBP_BloodPressure;
    private javax.swing.JLabel laHR_HeartRate;
    private javax.swing.JLabel laHeartRate;
    private javax.swing.JLabel laRR_RespiratoryRate;
    // End of variables declaration//GEN-END:variables
}
