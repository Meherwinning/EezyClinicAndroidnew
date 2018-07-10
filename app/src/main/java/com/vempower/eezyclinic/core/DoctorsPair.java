package com.vempower.eezyclinic.core;

import com.vempower.eezyclinic.APICore.NewHomeDoctorsList;

public class DoctorsPair implements  Cloneable{
    public NewHomeDoctorsList doc1,doc2;

    public NewHomeDoctorsList getDoc1() {
        return doc1;
    }

    public void setDoc1(NewHomeDoctorsList doc1) {
        this.doc1 = doc1;
    }

    public NewHomeDoctorsList getDoc2() {
        return doc2;
    }

    public void setDoc2(NewHomeDoctorsList doc2) {
        this.doc2 = doc2;
    }

    public DoctorsPair getCloneObject() {
       DoctorsPair other= new DoctorsPair();
        other.doc1=this.doc1 ;
        other.doc2= this.doc2;
        return other;
    }
}