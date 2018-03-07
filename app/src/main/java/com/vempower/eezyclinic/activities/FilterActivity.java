package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.Switch;
import com.vempower.eezyclinic.APIResponce.InsuranceData;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.LanguageData;
import com.vempower.eezyclinic.APIResponce.LanguageListAPI;
import com.vempower.eezyclinic.APIResponce.NationalityData;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.FilterRefreshListListener;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.InsuranceListMapper;
import com.vempower.eezyclinic.mappers.LanguageListMapper;
import com.vempower.eezyclinic.mappers.NationalityMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyCheckBoxRR;
import com.vempower.eezyclinic.views.MySwitch;
 ;
import com.vempower.eezyclinic.views.myseekbar.RangeSeekBar;

import java.util.List;

public class FilterActivity extends AbstractFragmentActivity /*implements MySwitch.OnChangeAttemptListener, CompoundButton.OnCheckedChangeListener*/ {

    private MySwitch search_type_switch;
    //private TextView search_type_switch_tv;
    private SearchRequest requestParms;
    private ExpandableLinearLayout expandableLayout_gender_view,expandableLayout_language_view,
            expandableLayout_nationality_view,expandableLayout_insurance_view,expandableLayout_fee_view;


    private LinearLayout  consultation_fee_linear,gender_view_linear,
            language_known_linear ,nationality_view_linear;
    private Switch online_booking_switch;

    // private ExpandableLinearLayout expandableLayout_gender_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setActionBar();
        myInit();
    }

    private void myInit() {

        consultation_fee_linear = findViewById(R.id. consultation_fee_linear);
        gender_view_linear= findViewById(R.id.  gender_view_linear);
        language_known_linear = findViewById(R.id. language_known_linear);
        nationality_view_linear  = findViewById(R.id.nationality_view_linear);

        online_booking_switch  = findViewById(R.id.online_booking_switch);

        SearchRequest requestParms1 = MyApplication.getInstance().getSearchRequestParms();
        if (requestParms1 == null) {
            requestParms1 = new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);
        }
        requestParms = requestParms1.getCloneObject();
        search_type_switch = findViewById(R.id.switch1);

    //   final  View doctors_layout = findViewById(R.id.doctors_layout);
      // final View clinics_layout  =findViewById(R.id.clinics_layout);
        setParamsValuesToviews();




    }

    private void setParamsValuesToviews() {
        if(requestParms.getSearchtype().equalsIgnoreCase(SearchRequest.DOCTOR_TYPE))
        {
            showViewType(true);
        }else
        {
            showViewType(false);
        }
        online_booking_switch.setOnCheckedChangeListener(null);
        online_booking_switch.setChecked(false);

        if(requestParms.getOnlinebooking()==1)
        {
            online_booking_switch.setChecked(true);
        }

        online_booking_switch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                requestParms.setOnlinebooking(checked?1:0);
            }
        });

        search_type_switch.setChecked(requestParms.getSearchtype().equalsIgnoreCase(SearchRequest.DOCTOR_TYPE) ? false : true);
        //search_type_switch_tv.setText(requestParms.getSearchtype());
        search_type_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    requestParms.setSearchtype(SearchRequest.DOCTOR_TYPE);
                    showViewType(true);

                } else {
                    requestParms.setSearchtype(SearchRequest.CLINIC_TYPE);
                    showViewType(false);
                }
            }
        });

        computeGenderView();
        computeLanguageView();
        computeConsultationFeeView();
    }

    public void onClearAllButtonClick(View view) {

        showMyDialog("Clear Filters", Utils.getStringFromResources(R.string.claer_filter_message_lbl), "Clear", "Cancel", new ApiErrorDialogInterface() {
            @Override
            public void onCloseClick() {
                //TODO nothing
            }

            @Override
            public void retryClick() {
                requestParms = new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);
                setParamsValuesToviews();
            }
        });


       // Utils.showToastMsg("Coming soon");
    }

    public void onApplyButtonClick(View view) {

        //showToastMessage(requestParms.toString());
       // MyApplication.getInstance().setSearchRequestParms(requestParms);

       MyApplication.getInstance().setSearchRequestParms(requestParms);

        Intent intent = getIntent(); //new Intent(this,HomeActivity.class);
        intent.setClass(this, DoctorsListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        sendHandlerMessage(getIntent(), ListenerKey.FILTER_REFRESH_LIST_LISTENER_KEY, getFilterRefreshListListener(requestParms.getSearchtype()));


    }

    private void computeConsultationFeeView() {

        setAmountRageSeekBar();
        /*RangeSeekBar rangeSeekbar = (RangeSeekBar) findViewById(R.id.rangeSeekbar);
        rangeSeekbar.setNotifyWhileDragging(true);
        rangeSeekbar.resetSelectedValues();
        rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                Log.i("Seekbar","Min Value- " + minValue + " & " + "Max Value- " + maxValue);
               // Toast.makeText(getApplicationContext(), "Min Value- " + minValue + " & " + "Max Value- " + maxValue, Toast.LENGTH_LONG).show();
            }
        });*/
        expandableLayout_fee_view = findViewById(R.id.expandableLayout_fee_view);
        LinearLayout fee_linear = findViewById(R.id.fee_linear);
        // callInsuranceMapper();

        setExpandedGenderViewListener(expandableLayout_fee_view,R.id.fee_expand_iv,true);
        fee_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colapseAllViews();
                expandableLayout_fee_view.toggle();
            }
        });
    }

    private void setAmountRageSeekBar() {
        final CrystalRangeSeekbar rangeSeekbar = findViewById(R.id.rangeSeekbar1);

// get min and max text view
        final  TextView tvMin =  findViewById(R.id.textMin1);
        final  TextView tvMax = findViewById(R.id.textMax1);
       rangeSeekbar.setOnRangeSeekbarChangeListener(null);
        rangeSeekbar.setMinValue(Constants.RangeBarValues.MIN_VALUE);
        rangeSeekbar.setMaxValue(Constants.RangeBarValues.MAX_VALUE);
        rangeSeekbar.setMinStartValue(requestParms.getAmountRangeMin());
        rangeSeekbar.setMaxStartValue(requestParms.getAmountRangeMax());

        rangeSeekbar.apply();

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
             }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                requestParms.setAmountRangeMin(minValue.floatValue());
                requestParms.setAmountRangeMax(maxValue.floatValue());

                // Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });
    }

    private void computeInsuranceView() {
        expandableLayout_insurance_view = findViewById(R.id.expandableLayout_insurance_view);
        LinearLayout insurance_linaer = findViewById(R.id.insurance_linaer);

        setExpandedGenderViewListener(expandableLayout_insurance_view,R.id.insurance_expand_iv,true);
        // setExpandedGenderViewListener(expandableLayout_insurance_view1,R.id.insurance_expand_iv1,true);

        insurance_linaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colapseAllViews();
                expandableLayout_insurance_view.toggle();
                //expandableLayout_insurance_view1.toggle();
            }
        });

        callInsuranceMapper(R.id.insurance_group_toggle_views);
    }

    private void computeNationalityView() {
        expandableLayout_nationality_view = findViewById(R.id.expandableLayout_nationality_view);
        LinearLayout nationality_linear = findViewById(R.id.nationality_linear);
        callNationalityMapper();
        setExpandedGenderViewListener(expandableLayout_nationality_view,R.id.nationality_expand_iv,true);
        nationality_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colapseAllViews();
                expandableLayout_nationality_view.toggle();
            }
        });
    }

    private void computeLanguageView() {

        expandableLayout_language_view = findViewById(R.id.expandableLayout_language_view);
        LinearLayout language_linear = findViewById(R.id.language_linear);
        callLanguageListMapper();

        setExpandedGenderViewListener(expandableLayout_language_view,R.id.language_expand_iv,true);
        language_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colapseAllViews();
                expandableLayout_language_view.toggle();
            }
        });
    }

    private void computeGenderView() {
        final MyCheckBoxRR male_checkbox= findViewById(R.id.male_checkbox);
                final MyCheckBoxRR female_checkbox = findViewById(R.id.female_checkbox);
        male_checkbox.setChecked(requestParms.getGendersearch().contains(Constants.GenderValues.MALE));
        female_checkbox.setChecked(requestParms.getGendersearch().contains(Constants.GenderValues.FEMALE));

        male_checkbox.setOnCheckedChangeListener(new GenderCheckedListener(true));
        female_checkbox.setOnCheckedChangeListener(new GenderCheckedListener(false));
        expandableLayout_gender_view = findViewById(R.id.expandableLayout_gender_view);
        LinearLayout gender_linear = findViewById(R.id.gender_linear);

        setExpandedGenderViewListener(expandableLayout_gender_view,R.id.gender_expand_iv,true);
        gender_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colapseAllViews();
                expandableLayout_gender_view.toggle();
            }
        });
    }

    private class GenderCheckedListener implements CompoundButton.OnCheckedChangeListener
    {

        final boolean isMale;
        public GenderCheckedListener(boolean isMale) {
            this.isMale=isMale;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked) {
                requestParms.addGendersearch(isMale?Constants.GenderValues.MALE:Constants.GenderValues.FEMALE);
            }else
            {
                requestParms.removeGendersearch(isMale?Constants.GenderValues.MALE:Constants.GenderValues.FEMALE);
            }

        }
    }



    private void showViewType(boolean isDoctorView) {
        consultation_fee_linear.setVisibility(isDoctorView?View.VISIBLE:View.GONE);
                gender_view_linear.setVisibility(isDoctorView?View.VISIBLE:View.GONE);
                language_known_linear.setVisibility(isDoctorView?View.VISIBLE:View.GONE);
                nationality_view_linear.setVisibility(isDoctorView?View.VISIBLE:View.GONE);
        colapseAllViews();
    }



    private void callLanguageListMapper()
    {
        MyApplication.showTransparentDialog();
        LanguageListMapper mapper = new LanguageListMapper();
        mapper.setOnLanguageListListener(new LanguageListMapper.LanguageListListener() {
            @Override
            public void getLanguageListAPII(LanguageListAPI languageListAPI, String errorMessage) {
                //fsdfdsf
                MyApplication.hideTransaprentDialog();
                if (!isValidResponse(languageListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_languages_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callLanguageListMapper();
                        }
                    });
                    return;
                }
                setToLanguagesToggleViews(languageListAPI.getData());
                computeNationalityView();
            }
        });

    }

    private void callNationalityMapper()
    {
        MyApplication.showTransparentDialog();
        NationalityMapper mapper = new NationalityMapper();

        mapper.setOnNationalityListListener(new NationalityMapper.NationalityListListener() {
            @Override
            public void getNationalityListAPI(NationalityListAPI nationalityListAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if (!isValidResponse(nationalityListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_nationality_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callNationalityMapper();
                        }
                    });
                    return;
                }
                setToNationalityListToggleViews(nationalityListAPI.getData());
                computeInsuranceView();
            }
        });
    }
    private void callInsuranceMapper(final int toggleId)
    {
        MyApplication.showTransparentDialog();
        InsuranceListMapper mapper = new InsuranceListMapper();
        mapper.setOnInsuranceListListener(new InsuranceListMapper.InsuranceListListener() {
            @Override
            public void getInsuranceListAPI(InsuranceListAPI insuranceListAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if (!isValidResponse(insuranceListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_insurance_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            callInsuranceMapper(toggleId);
                        }
                    });
                    return;
                }
                setToInsuranceToggleViews(insuranceListAPI.getData(),toggleId);
            }
        });
    }

    private void setToInsuranceToggleViews(final List<InsuranceData> dataList,int toggleId)
    {
        MultiSelectToggleGroup multiDummy = (MultiSelectToggleGroup) findViewById(toggleId);
       // MultiSelectToggleGroup multiDummy1 = (MultiSelectToggleGroup) findViewById(toggleId1);
        /*multiDummy1.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

               *//* try {
                    String str = dummyText[checkedId];
                    showToastMessage(str + " " + isChecked);
                }catch(Exception e){
                }*//*
            }
        });*/
        multiDummy.clearCheck();
        multiDummy.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

                //start
                InsuranceData data= new InsuranceData();
                data.setId(checkedId+"");
                int index= dataList.indexOf(data);

                if(index>=0)
                {
                    InsuranceData insuranceData=dataList.get(index);
                    if(isChecked)
                    {
                        requestParms.addInsurence(insuranceData.getCompanyName());
                    }else
                    {
                        requestParms.removeInsurence(insuranceData.getCompanyName());
                    }


                }


            }
        });
        for (InsuranceData data:dataList) {
            if(data==null)
            {
                continue;
            }
            //String text=dummyText[i];
            LabelToggle toggle = new LabelToggle(this);
            toggle.setText(data.getCompanyName());
            toggle.setId(Integer.parseInt(data.getId()));
            toggle.setChecked(requestParms.getInsurenceList().contains(data.getCompanyName()));
            multiDummy.addView(toggle);


           /* LabelToggle toggle1 = new LabelToggle(this);
            toggle1.setText(data.getCompanyName());
            toggle1.setId(Integer.parseInt(data.getId()));
            multiDummy1.addView(toggle1);*/
        }
    }



    public void setToNationalityListToggleViews(List<NationalityData> nationalityList) {
        MultiSelectToggleGroup multiDummy = (MultiSelectToggleGroup) findViewById(R.id.nationality_group_toggle_views);
        multiDummy.clearCheck();
        multiDummy.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
                    if(isChecked)
                    {
                        requestParms.addNationality(checkedId+"");
                    }else
                    {
                        requestParms.removeNationality(checkedId+"");
                    }
            }
        });
        for (NationalityData data:nationalityList) {
            if(data==null)
            {
                continue;
            }
            //String text=dummyText[i];
            LabelToggle toggle = new LabelToggle(this);
            toggle.setText(data.getNationalityName());
            toggle.setId(Integer.parseInt(data.getId()));
            toggle.setChecked(requestParms.getNationalityList().contains(data.getId()));
            multiDummy.addView(toggle);
        }

    }

    private void setToLanguagesToggleViews(final List<LanguageData> dataList) {

        MultiSelectToggleGroup multiDummy = (MultiSelectToggleGroup) findViewById(R.id.launguage_group_toggle_views);
        multiDummy.clearCheck();
        multiDummy.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {


                LanguageData data= new LanguageData();
                data.setId(checkedId+"");
               int index= dataList.indexOf(data);

               if(index>=0)
               {
                   LanguageData languageData=dataList.get(index);
                   if(isChecked)
                   {
                       requestParms.addLaunguage(languageData.getLanguageName());
                   }else
                   {
                       requestParms.removeLanguage(languageData.getLanguageName());
                   }


               }
               /* requestParms.addLaunguage(selectedLanguage.getLanguageName());

                try {
                    String str = dummyText[checkedId];
                    showToastMessage(str + " " + isChecked);
                }catch(Exception e){
                }*/
            }
        });
        for (LanguageData data:dataList) {
            if(data==null)
            {
                continue;
            }
            //String text=dummyText[i];
            LabelToggle toggle = new LabelToggle(this);
            toggle.setText(data.getLanguageName());
            toggle.setId(Integer.parseInt(data.getId()));
            toggle.setChecked(requestParms.getLaunguage().contains(data.getLanguageName()));
            multiDummy.addView(toggle);
        }
    }

    private void setExpandedGenderViewListener(ExpandableLinearLayout expandableLayout,int imageId,final boolean isScrolDoewn) {
        expandableLayout.setInRecyclerView(false);
        //expandableLayout.setBackgroundColor(ContextCompat.getColor(this, item.colorId2));
        expandableLayout.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        //expandableLayout.setExpanded(expandState.get(position));
        expandableLayout.setExpanded(false);
        //final ImageView imageView= view.findViewById(R.id.advanced_search_iv);
        // imageView.setBackgroundResource(R.drawable.plus_icon);

        final ImageView imageView = findViewById(imageId);
        imageView.setBackgroundResource(R.drawable.plus_icon_grey);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setBackgroundResource(R.drawable.minus_icon_grey);
                        if(isScrolDoewn) {
                            ((ScrollView) findViewById(R.id.scroll)).fullScroll(View.FOCUS_DOWN);
                        }


                    }
                }, 150);
            }

            @Override
            public void onPreClose() {
                imageView.setBackgroundResource(R.drawable.plus_icon_grey);
                //imageView.setBackgroundResource(0);
                //imageView.setBackgroundResource(R.drawable.plus_icon);
                //createRotateAnimator(buttonLayout, 180f, 0f).start();
                // expandState.put(position, false);
            }
        });
    }








    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.close_iv:
                // ProjectsActivity is my 'home' activity
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        if (titleName != null)
            titleName.setText(Utils.getStringFromResources(R.string.title_activity_filter_title));

        ImageView imageView = toolbar.findViewById(R.id.close_iv);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();
            }
        });
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //mImageView = findViewById(R.id.image);
        //View  mToolbarView = findViewById(R.id.toolbar);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        getSupportActionBar().setTitle("");
        // mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.app_red)));

        //super.setActionBar(false);
    }

    private FilterRefreshListListener getFilterRefreshListListener(final String searchType) {
        return new FilterRefreshListListener() {
            public String refreshList() {
                return searchType;
            }
        };
    }

    private void colapseAllViews()
    {
        if(expandableLayout_gender_view!=null && expandableLayout_gender_view.isExpanded())
        {
            expandableLayout_gender_view.collapse();
        }

        if(expandableLayout_language_view!=null && expandableLayout_language_view.isExpanded())
        {
            expandableLayout_language_view.collapse();
        }

        if(expandableLayout_nationality_view!=null && expandableLayout_nationality_view.isExpanded())
        {
            expandableLayout_nationality_view.collapse();
        }

        if(expandableLayout_insurance_view!=null && expandableLayout_insurance_view.isExpanded())
        {
            expandableLayout_insurance_view.collapse();
        }
       /* if(expandableLayout_insurance_view1!=null && expandableLayout_insurance_view1.isExpanded())
        {
            expandableLayout_insurance_view1.collapse();
        }*/


        if(expandableLayout_fee_view!=null && expandableLayout_fee_view.isExpanded())
        {
            expandableLayout_fee_view.collapse();
        }

    }



}
