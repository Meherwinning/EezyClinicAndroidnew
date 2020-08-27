package com.vempower.eezyclinic.interfaces;

import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.PatientRequestAppointment;
import com.vempower.eezyclinic.APICore.TeleConsultation;

import java.util.List;

/**
 * Created by satish on 8/12/17.
 */

public interface HomeListener extends AbstractListener {
    void onTeleconsultationClick(List<TeleConsultation> teleConsultationList);
    void onPatientRequestAppointmentClick(List<PatientRequestAppointment> patientRequestAppointmentList);

    void onUpcomingAppointmentClick(List<Appointment> list);
    void onUpcomingFollowupsClick(List<Followup> followups);
    void onSearchDocatorsClick();
}
