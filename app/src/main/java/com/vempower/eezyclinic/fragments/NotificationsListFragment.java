package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.AbstractNotification;
import com.vempower.eezyclinic.APICore.Notification;
import com.vempower.eezyclinic.APICore.NotificationsComingappointment;
import com.vempower.eezyclinic.APICore.NotificationsListData;
import com.vempower.eezyclinic.APICore.NotificationsSendrequest;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.NotificationsListAPI;
import com.vempower.eezyclinic.APIResponce.PrescriptionsListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AddPrescriptionReportActivity;
import com.vempower.eezyclinic.adapters.FamilyMembersListAdapter;
import com.vempower.eezyclinic.adapters.NotificationsListAdapter;
import com.vempower.eezyclinic.adapters.PrescriptionListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.CustomComparator;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.AcceptNotificationMapper;
import com.vempower.eezyclinic.mappers.DeleteNotificationMapper;
import com.vempower.eezyclinic.mappers.NotificationsListMapper;
import com.vempower.eezyclinic.mappers.PrescriptionsListMapper;
import com.vempower.eezyclinic.mappers.RejectNotificationMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by satish on 6/12/17.
 */

public class NotificationsListFragment extends  SwipedRecyclerViewFragment {

    private View fragmentView;
    private NotificationsListAdapter adapter;
    private List<AbstractNotification> notificationsList;

    public static PrescriptionsFragment newInstance() {

       /* if(fragment==null)
        {
            fragment= new PrescriptionsFragment();
        }*/
        return new PrescriptionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.notifications_list, container, false);

        setupSwipeRefreshLayout(fragmentView);
        init();
        return fragmentView;

    }

    private void init() {

        // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
        // match_found_tv.setText("0");
        adapter = null;
        notificationsList= new ArrayList<>();
        //AddModifyPrescriptionActivity



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callNotificationsMapper();
    }

    private void callNotificationsMapper()
    {
        NotificationsListMapper mapper= new NotificationsListMapper();
        mapper.setOnNotificationsListListener(new NotificationsListMapper.NotificationsListListener() {
            @Override
            public void getNotificationsList(NotificationsListAPI listAPI, String errorMessage) {
                if (!isValidResponse(listAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_notifications_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callNotificationsMapper();
                        }
                    });
                    return;
                }

                if(listAPI.getData()!=null )
                {
                    notificationsList.addAll(NotificationsListFragment.this.getNotificationsList(listAPI.getData()));

                }
                setPrescriptionListToAdapter(notificationsList);
            }
        });
    }

    private ArrayList<? extends AbstractNotification> getNotificationsList(NotificationsListData data) {
        ArrayList<AbstractNotification>  list= new ArrayList<>();

        if(data==null)
        {
            return list;
        }

        if(data.getNotificationsList()!=null && data.getNotificationsList().size()>0)
        {
            for(Notification notification:data.getNotificationsList())
            {
                if(notification!=null)
                {
                    list.add(notification);
                }
            }
        }


        if(data.getSendrequest()!=null && data.getSendrequest().size()>0)
        {
            for(NotificationsSendrequest sendrequest:data.getSendrequest())
            {
                if(sendrequest!=null)
                {
                    list.add(sendrequest);
                }
            }
        }

        if(data.getComingappointments()!=null && data.getComingappointments().size()>0)
        {
            for(NotificationsComingappointment comingappointment:data.getComingappointments())
            {
                if(comingappointment!=null)
                {
                    list.add(comingappointment);
                }
            }
        }

        Collections.sort(list, new CustomComparator<AbstractNotification>() {

            @Override
            public int compare(AbstractNotification o1, AbstractNotification o2) {

                //Date format in RSS field should be in 'Thu Feb 23 2017 10:37:04' format
                //2018-07-30 16:33:13
                //yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    try {
                        if((TextUtils.isEmpty(o1.getSendDateTime()) || TextUtils.isEmpty(o1.getSendDateTime()))) {
                            return 0;
                        }
                        Date d1 = sdf.parse(o1.getSendDateTime());
                        Date d2 = sdf.parse(o2.getSendDateTime());
                        return d2.compareTo(d1);
                    }
                    catch (ParseException e){
                       // e.printStackTrace();
                        return 0;
                    }

            }
        });


        return list;
    }



  /*  private void callPrescriptionListMapper()
    {
        PrescriptionsListMapper mapper= new PrescriptionsListMapper();
        mapper.setOnPrescriptionsListListener(new PrescriptionsListMapper.PrescriptionsListListener() {
            @Override
            public void getPrescriptionsListAPI(PrescriptionsListAPI prescriptionsListAPI, String errorMessage) {
                if (!isValidResponse(prescriptionsListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_prescriptions_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callPrescriptionListMapper();
                        }
                    });
                    return;
                }

                if(prescriptionsListAPI.getData()!=null )
                {
                    prescriptionList.addAll(prescriptionsListAPI.getData());
                }
                setPrescriptionListToAdapter(prescriptionList);
                // Utils.showToastMessage(searchResultDoctorListAPI.toString());

            }
        });
    }
*/

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }

    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        notificationsList= new ArrayList<>();
        // requestParms.setPage("1");
        callNotificationsMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
      /*  requestParms.setPage((Integer.parseInt(requestParms.getPage())+1)+"");
        callPrescriptionListMapper();*/

    }

    public void setPrescriptionListToAdapter(List<AbstractNotification> notificationsList) {
        hideProgressView();

        if (adapter == null) {

            adapter = new NotificationsListAdapter(notificationsList);
            adapter.setOnSendRequestListener(new NotificationsListAdapter.SendRequestListener() {
                @Override
                public void status(boolean isAcceptClick, final NotificationsSendrequest sendrequest) {
                    if(isAcceptClick)
                    {
                        showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_accept_family_member_lbl), "Yes", "No", new MyDialogInterface() {
                            @Override
                            public void onPossitiveClick() {
                                callAcceptFamilyMember(sendrequest.getId());
                            }

                            @Override
                            public void onNegetiveClick() {

                            }
                        });
                       // Utils.showToastMsg("Accept Click");
                    }else
                    {
                        showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_reject_family_member_lbl), "Yes", "No", new MyDialogInterface() {
                            @Override
                            public void onPossitiveClick() {
                                callRejectFamilyMember(sendrequest.getId());
                            }

                            @Override
                            public void onNegetiveClick() {

                            }
                        });
                       // Utils.showToastMsg("Reject Click");
                    }
                }
            });

            adapter.setOnDeleteListener(new NotificationsListAdapter.DeleteListener() {
                @Override
                public void delete(final Notification notification) {
                    showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_notification_lbl), "Yes", "No", new MyDialogInterface() {
                        @Override
                        public void onPossitiveClick() {
                            callDeleteNotification(notification);

                        }

                        @Override
                        public void onNegetiveClick() {

                        }
                    });
                }


            });

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(notificationsList);
        }
        if(notificationsList==null || notificationsList.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }

    }

    private void callDeleteNotification(Notification notification) {
        DeleteNotificationMapper mapper= new DeleteNotificationMapper(Integer.parseInt(notification.getId()));

        mapper.setOnDeleteListener(new DeleteNotificationMapper.DeleteListener() {
            @Override
            public void delete(AbstractResponse response, String errorMessage) {
                if(!isValidResponse(response,errorMessage))
                {
                    return;
                }
                Utils.showToastMsg(response.getStatusMessage());
                fromTopScroll();
            }
        });

    }

    private void callRejectFamilyMember(int id) {
        RejectNotificationMapper mapper= new RejectNotificationMapper(id);
        mapper.setOnRejectNotificationListener(new RejectNotificationMapper.RejectNotificationListener() {
            @Override
            public void reject(AbstractResponse response, String errorMessage) {
                if(!isValidResponse(response,errorMessage,true,false))
                {
                    return;
                }
                Utils.showToastMsg(response.getStatusMessage());
                fromTopScroll();
            }
        });

    }

    private void callAcceptFamilyMember(int id) {

        AcceptNotificationMapper mapper= new AcceptNotificationMapper(id);

        mapper.setOnAcceptNotificationListener(new AcceptNotificationMapper.AcceptNotificationListener() {
            @Override
            public void accept(AbstractResponse response, String errorMessage) {
                if(!isValidResponse(response,errorMessage,true,false))
                {
                    return;
                }
                Utils.showToastMsg(response.getStatusMessage());
                fromTopScroll();

            }
        });

    }


}