package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.APIResponce.SpecalitiyRemainData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.complex.Article;
import com.vempower.eezyclinic.activities.complex.Bike;
import com.vempower.eezyclinic.activities.complex.Car;
import com.vempower.eezyclinic.activities.complex.ComplexListAdapter;
import com.vempower.eezyclinic.activities.complex.DummyDataProvider;
import com.vempower.eezyclinic.activities.complex.GridItem;
import com.vempower.eezyclinic.activities.complex.Vehicle;
import com.vempower.eezyclinic.adapters.SpecialityListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.SpecalitiesMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by satish on 11/1/18.
 */

public class DemoFragment extends  AbstractFragment {


    private View fragmentView;
    private RecyclerView recyclerView;
    private ComplexListAdapter adapter;
    private List<SpecalitiyData> dataList;


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

        callSpecialityMapper();
    }

    private void myInit() {
        recyclerView =getFragemtView().findViewById(R.id.rcv_list);
    }

    protected void setUpAdapter() {
        GridLayoutManager glm = new GridLayoutManager(MyApplication.getCurrentActivityContext(), 3);
        ArrayList<SpecalitiyRemainData> dataList1=new ArrayList<>();
        for(SpecalitiyData data:dataList)
        {
            dataList1.add(new SpecalitiyRemainData(data)) ;
        }
         adapter = new ComplexListAdapter(dataList1);
        adapter.setSpanCount(3);

        glm.setSpanSizeLookup(adapter.getSpanSizeLookup());
        recyclerView.addItemDecoration(adapter.getItemDecorationManager());
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);

        // Single list
        List<Article> dataListThree = DummyDataProvider.getArticles();
        adapter.addSingleModelItem(dataListThree);

        // Grid items
       /* List<GridItem> gridDataList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            gridDataList.add(GridItem.generateGridItem(i));
        }*/
        //
        adapter.addGridItem(dataList);


        // Single list with two binders
        //List<Vehicle> carItemList = new ArrayList<>();
        List<Vehicle> bikeItemList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            if (random.nextBoolean()) {
               /* carItemList.add(new Car(i, "Car " + i, "Manufacturer " + i,
                        String.valueOf(1900 + random.nextInt(100))));*/
            } else {
                bikeItemList.add(new Bike(i, "Bike " + i, "Description of bike" + i));
            }
        }
        // adapter.addMultiItem(multiItemList);
        ArrayList<SpecalitiyRemainData> dataList2=new ArrayList<>();
        dataList2.add(new SpecalitiyRemainData(dataList.get(0)));
        adapter.addCarItem(dataList2);
        adapter.addBikeItem(bikeItemList);
    }

    private void callSpecialityMapper() {
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
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
