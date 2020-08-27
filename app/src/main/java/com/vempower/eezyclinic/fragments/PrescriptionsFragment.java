package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.APIResponce.PrescriptionsListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AddPrescriptionReportActivity;
import com.vempower.eezyclinic.adapters.PrescriptionListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.PrescriptionsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionsFragment extends  SwipedRecyclerViewFragment {

    public static final String TITLE = "Prescriptions";
    private View fragmentView;
    private PrescriptionListAdapter adapter;
    private List<PrescriptionAPIData> prescriptionList;
    private AppCompatButton add_new_bt;

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

        fragmentView = inflater.inflate(R.layout.fragment_prescriptions, container, false);

        setupSwipeRefreshLayout(fragmentView);
        init();
        return fragmentView;

    }

    private void init() {

        // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
        // match_found_tv.setText("0");
        adapter = null;
        prescriptionList= new ArrayList<>();
        //AddModifyPrescriptionActivity

        add_new_bt =  fragmentView.findViewById(R.id.add_new_bt);
        add_new_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MyApplication.getCurrentActivityContext(),AddPrescriptionReportActivity.class);
                intent.putExtra(Constants.Pref.IS_FROM_ADD_PRESCRIPTION_KEY,true);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callPrescriptionListMapper();
    }


    private void callPrescriptionListMapper()
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
        prescriptionList= new ArrayList<>();
       // requestParms.setPage("1");
        callPrescriptionListMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
      /*  requestParms.setPage((Integer.parseInt(requestParms.getPage())+1)+"");
        callPrescriptionListMapper();*/

    }

    public void setPrescriptionListToAdapter(List<PrescriptionAPIData> prescriptionList) {
        hideProgressView();

        if (adapter == null) {

            adapter = new PrescriptionListAdapter(prescriptionList);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(prescriptionList);
        }
        if(prescriptionList==null || prescriptionList.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }



    }



}