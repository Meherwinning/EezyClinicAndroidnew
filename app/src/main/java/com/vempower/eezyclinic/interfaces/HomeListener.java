package com.vempower.eezyclinic.interfaces;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.Followup;

import java.util.List;

/**
 * Created by satish on 8/12/17.
 */

public interface HomeListener extends AbstractListener {
    void onUpcomingAppointmentClick(List<Appointment> list);
    void onUpcomingFollowupsClick(List<Followup> followups);
    void onSearchDocatorsClick();
}
