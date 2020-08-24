package com.e.mylittlechart.DiagnoseRecord;

import java.io.Serializable;

public class DiagnoseRecord implements Serializable {
    int id;
    String date;
    String place;
    String maininjurycode;
    String viceinjurycode;
    String disease;
    String diagnosisclassification;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DiagnoseRecord{" +
                "date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", maininjurycode='" + maininjurycode + '\'' +
                ", viceinjurycode='" + viceinjurycode + '\'' +
                ", disease='" + disease + '\'' +
                ", diagnosisclassification='" + diagnosisclassification + '\'' +
                '}';
    }

    public DiagnoseRecord() {
        date ="";
        place ="";
        maininjurycode = "";
        viceinjurycode = "";
        disease = "";
        diagnosisclassification = DiagnosisClassification.EMPTY;
    }

    public DiagnoseRecord(DiagnoseRecord diagnoseRecord){
        date = diagnoseRecord.getDate();
        place = diagnoseRecord.getPlace();
        maininjurycode = diagnoseRecord.getMaininjurycode();
        viceinjurycode = diagnoseRecord.getViceinjurycode();
        disease = diagnoseRecord.getDisease();
        diagnosisclassification = diagnoseRecord.getDiagnosisclassification();
    }

    public DiagnoseRecord(String date, String place, String maininjurycode, String viceinjurycode, String disease, String diagnosisclassification) {
        this.date = date;
        this.place = place;
        this.maininjurycode = maininjurycode;
        this.viceinjurycode = viceinjurycode;
        this.disease = disease;
        this.diagnosisclassification = diagnosisclassification;
    }

    public String getDate() {
        return date;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMaininjurycode() {
        return maininjurycode;
    }

    public void setMaininjurycode(String maininjurycode) {
        this.maininjurycode = maininjurycode;
    }

    public String getViceinjurycode() {
        return viceinjurycode;
    }

    public void setViceinjurycode(String viceinjurycode) {
        this.viceinjurycode = viceinjurycode;
    }

    public String getDiagnosisclassification() {
        return diagnosisclassification;
    }

    public void setDiagnosisclassification(String diagnosisclassification) {
        this.diagnosisclassification = diagnosisclassification;
    }
}
