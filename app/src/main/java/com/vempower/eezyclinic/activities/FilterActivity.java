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

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;
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
import com.vempower.eezyclinic.mappers.InsuranceListMapper;
import com.vempower.eezyclinic.mappers.LanguageListMapper;
import com.vempower.eezyclinic.mappers.NationalityMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MySwitch;
import com.vempower.eezyclinic.views.myseekbar.RangeSeekBar;

import java.util.List;

public class FilterActivity extends AbstractFragmentActivity /*implements MySwitch.OnChangeAttemptListener, CompoundButton.OnCheckedChangeListener*/ {

    private MySwitch search_type_switch;
    //private TextView search_type_switch_tv;
    private SearchRequest requestParms;
    private ExpandableLinearLayout expandableLayout_gender_view,expandableLayout_language_view,
            expandableLayout_nationality_view,expandableLayout_insurance_view1,expandableLayout_insurance_view,expandableLayout_fee_view;

    // private ExpandableLinearLayout expandableLayout_gender_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setActionBar();
        myInit();
    }

    private void myInit() {

        SearchRequest requestParms1 = MyApplication.getInstance().getSearchRequestParms();
        if (requestParms1 == null) {
            requestParms1 = new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);
        }
        requestParms = requestParms1.getCloneObject();


        search_type_switch = findViewById(R.id.switch1);

       final  View doctors_layout = findViewById(R.id.doctors_layout);
       final View clinics_layout  =findViewById(R.id.clinics_layout);
       if(requestParms.getSearchtype().equalsIgnoreCase(SearchRequest.DOCTOR_TYPE))
       {
           doctors_layout.setVisibility(View.VISIBLE);
           clinics_layout.setVisibility(View.GONE);
       }else
       {
           doctors_layout.setVisibility(View.GONE);
           clinics_layout.setVisibility(View.VISIBLE);
       }
        search_type_switch.setChecked(requestParms.getSearchtype().equalsIgnoreCase(SearchRequest.DOCTOR_TYPE) ? false : true);
        //search_type_switch_tv.setText(requestParms.getSearchtype());

        // search_type_switch.setOnChangeAttemptListener(this);
        //search_type_switch.setOnCheckedChangeListener(this);
        search_type_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    requestParms.setSearchtype(SearchRequest.DOCTOR_TYPE);
                    doctors_layout.setVisibility(View.VISIBLE);
                    clinics_layout.setVisibility(View.GONE);

                } else {
                    requestParms.setSearchtype(SearchRequest.CLINIC_TYPE);
                    doctors_layout.setVisibility(View.GONE);
                    clinics_layout.setVisibility(View.VISIBLE);
                }
            }
        });




        {
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

        {
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

        {
          /*  expandableLayout_nationality_view   expandableLayout_nationality_view
            nationality_expand_iv  nationality_expand_iv
            nationality_linear  nationality_linear*/

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



        {
           // expandableLayout_insurance_view  expandableLayout_insurance_view
            //insurance_linaer  insurance_linaer
          //  insurance_expand_iv  insurance_expand_iv

            expandableLayout_insurance_view = findViewById(R.id.expandableLayout_insurance_view);
            LinearLayout insurance_linaer = findViewById(R.id.insurance_linaer);
            callInsuranceMapper(R.id.insurance_group_toggle_views);

            setExpandedGenderViewListener(expandableLayout_insurance_view,R.id.insurance_expand_iv,true);
            insurance_linaer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colapseAllViews();
                    expandableLayout_insurance_view.toggle();
                }
            });
        }
        {
            // expandableLayout_insurance_view  expandableLayout_insurance_view
            //insurance_linaer  insurance_linaer
            //  insurance_expand_iv  insurance_expand_iv

            expandableLayout_insurance_view1 = findViewById(R.id.expandableLayout_insurance_view1);
            LinearLayout insurance_linaer = findViewById(R.id.insurance_linaer1);
            callInsuranceMapper(R.id.insurance_group_toggle_views1);

            setExpandedGenderViewListener(expandableLayout_insurance_view1,R.id.insurance_expand_iv1,true);
            insurance_linaer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colapseAllViews();
                    expandableLayout_insurance_view1.toggle();
                }
            });
        }

        {
            RangeSeekBar rangeSeekbar = (RangeSeekBar) findViewById(R.id.rangeSeekbar);
            rangeSeekbar.setNotifyWhileDragging(true);
            rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                    Log.i("Seekbar","Min Value- " + minValue + " & " + "Max Value- " + maxValue);
                   // Toast.makeText(getApplicationContext(), "Min Value- " + minValue + " & " + "Max Value- " + maxValue, Toast.LENGTH_LONG).show();
                }
            });
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


        //search_type_switch_tv = findViewById(R.id.search_type_switch_tv);




    }

    private void callLanguageListMapper()
    {
        LanguageListMapper mapper = new LanguageListMapper();
        mapper.setOnLanguageListListener(new LanguageListMapper.LanguageListListener() {
            @Override
            public void getLanguageListAPII(LanguageListAPI languageListAPI, String errorMessage) {
                if (!isValidResponse(languageListAPI, errorMessage)) {
                    return;
                }
                setToLanguagesToggleViews(languageListAPI.getData());
            }
        });

    }

    private void callNationalityMapper()
    {
        NationalityMapper mapper = new NationalityMapper();

        mapper.setOnNationalityListListener(new NationalityMapper.NationalityListListener() {
            @Override
            public void getNationalityListAPI(NationalityListAPI nationalityListAPI, String errorMessage) {

                if (!isValidResponse(nationalityListAPI, errorMessage)) {
                    return;
                }
                setToNationalityListToggleViews(nationalityListAPI.getData());
            }
        });
    }
    private void callInsuranceMapper(final int toggleId)
    {
        InsuranceListMapper mapper = new InsuranceListMapper();
        mapper.setOnInsuranceListListener(new InsuranceListMapper.InsuranceListListener() {
            @Override
            public void getInsuranceListAPI(InsuranceListAPI insuranceListAPI, String errorMessage) {
                if (!isValidResponse(insuranceListAPI, errorMessage)) {
                    return;
                }
                setToInsuranceToggleViews(insuranceListAPI.getData(),toggleId);
            }
        });
    }

    private void setToInsuranceToggleViews(List<InsuranceData> dataList,int toggleId)
    {
        MultiSelectToggleGroup multiDummy = (MultiSelectToggleGroup) findViewById(toggleId);
        multiDummy.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

               /* try {
                    String str = dummyText[checkedId];
                    showToastMessage(str + " " + isChecked);
                }catch(Exception e){
                }*/
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
            multiDummy.addView(toggle);
        }
    }



    public void setToNationalityListToggleViews(List<NationalityData> nationalityList) {
        MultiSelectToggleGroup multiDummy = (MultiSelectToggleGroup) findViewById(R.id.nationality_group_toggle_views);
        multiDummy.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

               /* try {
                    String str = dummyText[checkedId];
                    showToastMessage(str + " " + isChecked);
                }catch(Exception e){
                }*/
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
            multiDummy.addView(toggle);
        }

    }

    private void setToLanguagesToggleViews(List<LanguageData> dataList) {

        MultiSelectToggleGroup multiDummy = (MultiSelectToggleGroup) findViewById(R.id.launguage_group_toggle_views);
        multiDummy.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

               /* try {
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


    public void onApplyButtonClick(View view) {

        MyApplication.getInstance().setSearchRequestParms(requestParms);

        Intent intent = getIntent(); //new Intent(this,HomeActivity.class);
        intent.setClass(this, DoctorsListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        sendHandlerMessage(getIntent(), ListenerKey.FILTER_REFRESH_LIST_LISTENER_KEY, getFilterRefreshListListener(requestParms.getSearchtype()));


    }


    public void onClearAllButtonClick(View view) {

        Utils.showToastMsg("Coming soon");
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
        if(expandableLayout_insurance_view1!=null && expandableLayout_insurance_view1.isExpanded())
        {
            expandableLayout_insurance_view1.collapse();
        }


        if(expandableLayout_fee_view!=null && expandableLayout_fee_view.isExpanded())
        {
            expandableLayout_fee_view.collapse();
        }

    }



}
