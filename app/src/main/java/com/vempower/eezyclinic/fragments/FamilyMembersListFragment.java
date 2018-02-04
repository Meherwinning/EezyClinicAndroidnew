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

import com.vempower.eezyclinic.APICore.GetFamilyMemberData;
import com.vempower.eezyclinic.APICore.MedicalHistoryData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.GetFamilyMembersAPI;
import com.vempower.eezyclinic.APIResponce.MedicalHistoryListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AddFamilyMemberActivity;
import com.vempower.eezyclinic.adapters.FamilyMembersListAdapter;
import com.vempower.eezyclinic.adapters.MedicalHistoryListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.DeleteFamilyMemberMapper;
import com.vempower.eezyclinic.mappers.GetFamilyMembersMapper;
import com.vempower.eezyclinic.mappers.MedicalHistoryMapper;
import com.vempower.eezyclinic.mappers.SaveMedicalHistorySelfMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class FamilyMembersListFragment extends  SwipedRecyclerViewFragment {

    private View fragmentView;

    //List<MedicalHistoryData>

    private FamilyMembersListAdapter adapter;
    private List<GetFamilyMemberData> familyMembersList;

 /*   private MyEditTextBlackCursorRR medical_history_et;
    private MyTextViewRM save_tv;
*/
  private AppCompatButton search_family_member_bt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.family_members_list, container, false);

        setupSwipeRefreshLayout(fragmentView);
        init();
        return fragmentView;
    }

    private void init() {
        adapter = null;
        familyMembersList= new ArrayList<>();

        search_family_member_bt = getFragemtView().findViewById(R.id.search_family_member_bt);

        search_family_member_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Utils.showToastMessage("Now click on add new family member");

                Intent intent= new Intent(MyApplication.getCurrentActivityContext(),AddFamilyMemberActivity.class);
               /* intent.putExtra(ListenerKey.ObjectKey.MEDICAL_HISTORY_OBJECT_KEY,new Messenger(new AbstractIBinder(){
                    @Override
                    protected IntentObjectListener getMyObject() {
                        return new IntentObjectListener(){

                            @Override
                            public Object getObject() {
                                return data;
                            ,L9KKJ             }
                        };
                    }
                }));*/
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                MyApplication.getCurrentActivityContext().startActivity(intent);
            }
        });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callMedicalHistoryListMapper();
    }

    private void callMedicalHistoryListMapper() {

        GetFamilyMembersMapper mapper= new GetFamilyMembersMapper();
        mapper.setOnGetFamilyMembersAPIListener(new GetFamilyMembersMapper.GetFamilyMembersAPIListener() {
            @Override
            public void getFamilyMembers(GetFamilyMembersAPI membersAPI, String errorMessage) {
                if (!isValidResponse(membersAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_family_members_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callMedicalHistoryListMapper();
                        }
                    });
                    return;
                }

                if(membersAPI.getData()!=null )
                {
                    familyMembersList.addAll(membersAPI.getData());
                }
                setPrescriptionListToAdapter(familyMembersList);
            }
        });

/*
        MedicalHistoryMapper mapper= new MedicalHistoryMapper();
        mapper.setOnMedicalHistoryListener(new MedicalHistoryMapper.MedicalHistoryListener() {
            @Override
            public void getMedicalHistory(MedicalHistoryListAPI historyListAPI, String errorMessage) {
                if (!isValidResponse(historyListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_medical_history_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callMedicalHistoryListMapper();
                        }
                    });
                    return;
                }

                if(historyListAPI.getData()!=null )
                {
                    medicalHistoryList.addAll(historyListAPI.getData());
                }
                setPrescriptionListToAdapter(medicalHistoryList);
                // Utils.showToastMessage(searchResultDoctorListAPI.toString());
            }
        });*/

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
        familyMembersList= new ArrayList<>();
        callMedicalHistoryListMapper();

    }



    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);

        }


    }


    public void setPrescriptionListToAdapter(List<GetFamilyMemberData> familyMembersList) {
        hideProgressView();

        if (adapter == null) {
            adapter = new FamilyMembersListAdapter(familyMembersList);

            adapter.setOnDeleteMemberListener(new FamilyMembersListAdapter.DeleteMemberListener() {
                @Override
                public void delete(final int id) {
                    showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_family_member_lbl), "Yes", "No", new MyDialogInterface() {
                        @Override
                        public void onPossitiveClick() {
                            callDeleteFamilyMember(id);
                        }

                        @Override
                        public void onNegetiveClick() {

                        }
                    });
                }
            });

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(familyMembersList);
        }
        if(familyMembersList==null || familyMembersList.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }

    }

    private void callDeleteFamilyMember(int id) {

        DeleteFamilyMemberMapper mapper= new DeleteFamilyMemberMapper(id);
        mapper.setOnDeleteFamilyMemberListener(new DeleteFamilyMemberMapper.DeleteFamilyMemberListener() {
            @Override
            public void deleteFamilyMemeber(AbstractResponse response, String errorMessage) {
                if(!isValidResponse(response,errorMessage,true,false))
                {
                    return;
                }
                showAlertDialog("Success",response.getStatusMessage(),false);
                fromTopScroll();
            }
        });
    }

}
