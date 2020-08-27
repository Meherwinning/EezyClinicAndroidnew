package com.vempower.eezyclinic.APICore;

public class RequestAppointmentDetails {
    private static final String APP_FOR_SELF="Self";
    private static final String APP_FOR_OTHERS="Others";

    public final String   doctor_id ;
    public final String  branch_id ;
    public final String  appointmenttime;


    private  String  appfor;
    private  String  patientname;
    private String  reasonsforappoinment;
    private  boolean  isSelfAppointment;
    private  String  consultationtype;

    //if appointment for other then need to provide following parameters
    private  String  familymember;
    private  String  email;
    private  String  mobilenum;


    public RequestAppointmentDetails(String doctor_id, String branch_id, String appointmenttime) {
        this.doctor_id = doctor_id;
        this.branch_id = branch_id;
        this.appointmenttime = appointmenttime;
        setSelfAppointment(true);
    }

    public void setReasonsforappoinment(String reasonsforappoinment) {
        this.reasonsforappoinment = reasonsforappoinment;
    }

    public String getReasonsforappoinment() {
        return reasonsforappoinment;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public void setSelfAppointment(boolean selfAppointment) {
        this.isSelfAppointment=selfAppointment;
        if(isSelfAppointment)
        {
            this.appfor = APP_FOR_SELF;
        }else
        {
            this.appfor = APP_FOR_OTHERS;
        }
    }

    public String getAppfor() {
        return appfor;
    }

    public boolean isSelfAppointment() {
        return isSelfAppointment;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setConsultationtype(String consultationtype) {
        this.consultationtype = consultationtype;
    }

    public void setFamilymember(String familymember) {
        this.familymember = familymember;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getFamilymember() {
        return familymember;
    }

    public String getConsultationtype() {
        return consultationtype;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    @Override
    public String toString() {
        return "RequestAppointmentDetails{" +
                "doctor_id='" + doctor_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", appointmenttime='" + appointmenttime + '\'' +
                ", appfor='" + appfor + '\'' +
                ", patientname='" + patientname + '\'' +
                ", reasonsforappoinment='" + reasonsforappoinment + '\'' +
                ", isSelfAppointment=" + isSelfAppointment +
                ", familymember='" + familymember + '\'' +
                ", consultationtype='" + consultationtype + '\'' +
                ", email='" + email + '\'' +
                ", mobilenum='" + mobilenum + '\'' +
                '}';
    }
}
