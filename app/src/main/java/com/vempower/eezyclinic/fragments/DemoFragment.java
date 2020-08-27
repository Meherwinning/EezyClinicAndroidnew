package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.vempower.eezyclinic.APICore.NewHomeData;
import com.vempower.eezyclinic.APIResponce.NewHomeAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.complex.Article;
import com.vempower.eezyclinic.activities.complex.ComplexListAdapter;
import com.vempower.eezyclinic.activities.complex.DummyDataProvider;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.MyRecylerViewScrollListener;
import com.vempower.eezyclinic.mappers.NewHomeAPIMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.util.List;

/**
 * Created by satish on 11/1/18.
 */

public class DemoFragment extends AbstractFragment implements MyRecylerViewScrollListener {


    private View fragmentView;
    private RecyclerView recyclerView;
    private ComplexListAdapter adapter;
    // private List<SpecalitiyData> dataList;
    private NewHomeData newHomeData;
    private NewHomeAPIMapper mapper;
    private RecyclerView.SmoothScroller smoothScroller;
    private GridLayoutManager glm;
    private int viewIndex = -1;

    private boolean isshowFeaturesView = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_sample, container, false);


        myInit();
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callHomePageMapper();


    }

    public void refreshData() {
        //mapper=null;
        callHomePageMapper();
    }

   /* @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }*/

    private void myInit() {
        recyclerView = getFragemtView().findViewById(R.id.rcv_list);
        smoothScroller = new
                LinearSmoothScroller(MyApplication.getCurrentActivityContext()) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_END;
                    }
                };
        //scrollToView(recyclerView,null);
        // Point childOffset = new Point();
        // getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);

    }


    public void scrollMyRecylerView(int pos) {
        smoothScroller.setTargetPosition(pos);
        glm.startSmoothScroll(smoothScroller);

    }

    protected void setUpAdapter() {

        if (!isAdded()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setUpAdapter();
                }
            }, 1000);
            return;
        }

        /*if(adapter!=null)
        {
            adapter.setNewHomeData(newHomeData);
            adapter.notifyDataSetChanged();

                    return;
        }*/
        glm = new GridLayoutManager(MyApplication.getCurrentActivityContext(), 3);
        //ArrayList<SpecalitiyRemainData> dataList1=new ArrayList<>();
        /*for(NewHomeSpeciality data:newHomeData.getSpecialities())
        {
            dataList1.add(new SpecalitiyRemainData(data)) ;
        }*/
        // if(adapter==null) {
        adapter = new ComplexListAdapter(newHomeData, getChildFragmentManager(), this);
        adapter.setSpanCount(3);
        // }


        glm.setSpanSizeLookup(adapter.getSpanSizeLookup());
        recyclerView.addItemDecoration(adapter.getItemDecorationManager());
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);


        // Single list

        List<Article> dataListThree = DummyDataProvider.getArticles();
        adapter.addSingleModelItem(dataListThree);

        //scrollMyRecylerView(21);


        //scrollMyRecylerView(adapter.getItemCount()-1);
        //scrollMyRecylerView(1);


        if (isshowFeaturesView) {
            gotToFeaturesAndBenifitsView();
            isshowFeaturesView = false;

        }


    }

    public void setToShowFeaturesView(boolean isShow) {
        isshowFeaturesView = isShow;
    }

    public void gotToFeaturesAndBenifitsView() {
        gotToFeaturesAndBenifitsView(false);
    }

    public void gotToFeaturesAndBenifitsView(final boolean isNonLogin) {
        if (glm == null || adapter==null) {
            return;
        }

        glm.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getFeaturesListView() != null && viewIndex == -1) {
                    // scrollToView1(recyclerView,recyclerView.getChildViewHolder(adapter.getHealthtipsView()).itemView,glm);

                    //recyclerView.getChildAt()
                    // if(viewIndex==-1) {
                    viewIndex = recyclerView.getChildLayoutPosition(adapter.getFeaturesListView());
                    // }

                }

                if (viewIndex != -1) {
                    //scrollMyRecylerView(viewIndex-1);
                    if (isNonLogin) {
                        glm.scrollToPositionWithOffset(viewIndex - 1, 0);
                    } else {
                        glm.scrollToPositionWithOffset(viewIndex - 2, 0);
                    }

                }
            }
        }, 1000);
    }


    private void callHomePageMapper() {
        if (!isAdded()) {
            return;
        }
      /*  if(mapper!=null)
        {
            if(isAdded())
            setUpAdapter();
            //recyclerView.invalidate();
            return;
        }*/
        mapper = new NewHomeAPIMapper();
        mapper.setOnNewHomeAPIListener(new NewHomeAPIMapper.NewHomeAPIListener() {
            @Override
            public void getNewHomeAPI(NewHomeAPI homeAPI, String errorMessage) {

                if ((!isValidResponse(homeAPI, errorMessage)) || homeAPI.getData() == null) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_home_page_details), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callHomePageMapper();
                        }
                    });
                    return;
                }
                //dataList = specalitiesAPI.getData();
                newHomeData = homeAPI.getData();
                // setSpecialitiesToAdapter(dataList);

                setUpAdapter();

            }
        });

    }

   /* private void callSpecialityMapper() {
        MyApplication.showTransparentDialog();
        SpecalitiesMapper mapper = new SpecalitiesMapper();
        mapper.setOnSpecalitiesdListener(new SpecalitiesMapper.SpecalitiesdListener() {
            @Override
            public void getSpecalitiesAPII(SpecalitiesAPI specalitiesAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if ((!isValidResponse(specalitiesAPI, errorMessage))|| specalitiesAPI.getData()==null) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_speciality_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callSpecialityMapper();
                        }
                    });
                    return;
                }
                dataList = specalitiesAPI.getData();
               // setSpecialitiesToAdapter(dataList);

                setUpAdapter();

            }
        });
    }*/


    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
