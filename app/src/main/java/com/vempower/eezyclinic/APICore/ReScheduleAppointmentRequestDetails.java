package com.vempower.eezyclinic.APICore;

/**
 * Created by challa on 25/12/17.
 */

public class ReScheduleAppointmentRequestDetails {

   /* patientId (1)
    doctorId (1)
    appointmentDateTime (1)
    branchId (1)
    oldAppointmentId (1)*/

    public final int   doctor_id ;
    public final int  branch_id ;
    public final String  patientId;
    public final String  appointmentDateTime;
    public final String  oldAppointmentId;


    private  String  new_appointmenttime;




    public ReScheduleAppointmentRequestDetails(int doctor_id, int branch_id,
                                               String patientId,String appointmentDateTime,
                                               String oldAppointmentId) {
        this.doctor_id = doctor_id;
        this.branch_id = branch_id;
        this.patientId = patientId;
        this.appointmentDateTime=appointmentDateTime;
        this.oldAppointmentId=oldAppointmentId;
    }

    public void setNew_appointmenttime(String new_appointmenttime) {
        this.new_appointmenttime = new_appointmenttime;
    }

    public String getNew_appointmenttime() {
        return new_appointmenttime;
    }

    @Override
    public String toString() {
        return "ReScheduleAppointmentRequestDetails{" +
                "doctor_id=" + doctor_id +
                ", branch_id=" + branch_id +
                ", patientId='" + patientId + '\'' +
                ", appointmentDateTime='" + appointmentDateTime + '\'' +
                ", oldAppointmentId='" + oldAppointmentId + '\'' +
                ", new_appointmenttime='" + new_appointmenttime + '\'' +
                '}';
    }
}
