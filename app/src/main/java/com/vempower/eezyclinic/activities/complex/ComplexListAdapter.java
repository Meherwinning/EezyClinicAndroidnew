/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vempower.eezyclinic.activities.complex;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;


import com.ahamed.multiviewadapter.DataItemManager;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.annotation.ExpandableMode;
import com.ahamed.multiviewadapter.util.SimpleDividerDecoration;
import com.vempower.eezyclinic.APICore.NewHomeData;
import com.vempower.eezyclinic.APICore.NewHomeDoctorsList;
import com.vempower.eezyclinic.APICore.NewHomeSpeciality;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.decorator.ArticleItemDecorator;
import com.vempower.eezyclinic.activities.decorator.ArticleItemDecorator1;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.Feature;
import com.vempower.eezyclinic.core.HealthTip;
import com.vempower.eezyclinic.interfaces.MyRecylerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

public class ComplexListAdapter extends RecyclerAdapter {


    private final MyRecylerViewScrollListener myRecylerViewScrollListener;
    private DataListManager<Article> singleModelManager;
    private DataListManager<NewHomeDoctorsList> popularDoctorslManager;
   // private DataListManager<SpecalitiyData> specialitiesModelManager;
  private DataListManager<NewHomeSpeciality> gridItemsManager;
   // private DataListManager<SpecalitiyRemainData> gridItemsManager1;

  /*private DataListManager<SpecalitiyRemainData> carItemsManager;*/

  private DataListManager<HealthTip> healthTipsManager;
    private DataListManager<Feature> featuresManager;

  private final int SINGLE_VIEW_TOT_ITEMS=6;

  //private List<SpecalitiyData> gridDataList

  private SimpleDividerDecoration simpleItemDecoration,simpleItemDecoration1;
 // private FragmentManager fragmentManager;
  private List<NewHomeSpeciality> newHomeSpecialities;
    private  NewHomeData homeData;

  public ComplexListAdapter(final NewHomeData homeData1, FragmentManager fragmentManager, MyRecylerViewScrollListener myRecylerViewScrollListener) {
   // simpleItemDecoration = new SimpleDividerDecoration(MyApplication.getCurrentActivityContext(), SimpleDividerDecoration.VERTICAL);
      //this.fragmentManager=fragmentManager;
     // this.doctorsList=doctorsList;
      this.myRecylerViewScrollListener=myRecylerViewScrollListener;
      this.homeData=homeData1;
      newHomeSpecialities=homeData.getSpecialities();
      simpleItemDecoration1 = new SimpleDividerDecoration(MyApplication.getCurrentActivityContext(), SimpleDividerDecoration.HORIZONTAL);

    singleModelManager = new DataListManager<>(this);
      popularDoctorslManager  = new DataListManager<>(this);
    gridItemsManager = new DataListManager<>(this);
      //gridItemsManager1 = new DataListManager<>(this);

    /*carItemsManager = new DataListManager<>(this);*/

    healthTipsManager = new DataListManager<>(this);
      featuresManager= new DataListManager<>(this);
     // specialitiesModelManager= new DataListManager<>(this);

      //



    //addDataManager(new DataItemManager<>(this, null/*new Header("Articles")*/));
    addDataManager(singleModelManager);


     // addDataManager(new DataItemManager<>(this, null/*new Header("Articles")*/));
     // addDataManager(specialitiesModelManager);



      //addDataManager(new DataItemManager<>(this, DummyDataProvider.getAdvertisementOne()));
   // addDataManager(new DataItemManager<>(this, null/*new Header("Grid", true))*/));
    addDataManager(gridItemsManager);



      //addDataManager(new DataItemManager<>(this, null/*new Header("Grid", true))*/));
     // addDataManager(gridItemsManager1);






      //addDataManager(new DataItemManager<>(this, DummyDataProvider.getAdvertisementTwo()));

    addDataManager(new DataItemManager<>(this, new Header("Popular Doctors")));
    //addDataManager(carItemsManager);
    addDataManager(popularDoctorslManager);

    addDataManager(new DataItemManager<>(this, new Header("Health Tips")));
    addDataManager(healthTipsManager);

      addDataManager(new DataItemManager<>(this, new Header("Features / Benefits")));
      addDataManager(featuresManager);




    //addDataManager(new DataItemManager<>(this, DummyDataProvider.getAdvertisementThree()));

    registerBinder(new HeaderBinder());
    registerBinder(new GridItemBinder(/*convertDpToPixel(MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._3dp), MyApplication.getCurrentActivityContext())*/0, new GridItemBinder.ExpandColapseButtonListener(){
        @Override
        public void onClick(boolean isExpand) {
            if(isExpand) {
                loadMoreValues(remainingList);
            }else
            {
                remainingList= new ArrayList<>();
                displayList= new ArrayList<>();
                remainingList.addAll(originalList);
                loadMoreValues(remainingList);
               // Utils.showToastMessage("Now click on Less button");
            }
          /*  if(homeData!=null && homeData.getDoctorsList()!=null&&homeData.getDoctorsList().size()>0) {

                if(popularDoctorslManager.getCount()>=1) {
                    popularDoctorslManager.remove(0);
                }
                popularDoctorslManager.addAll(homeData.getDoctorsList().subList(0, 1));
            }*/
        }
    }));

     // registerBinder(new GridItemRemainBinder(convertDpToPixel(4, context)));

      //registerBinder(new CarBinder(simpleItemDecoration1,doctorsList));

      //////registerBinder(new ArticleBinder1(homeData.getDoctorsList(),new ArticleItemDecorator1(),fragmentManager));

      registerBinder(new ArticleBinder1(homeData.getDoctorsList(),new ArticleItemDecorator1(),fragmentManager));

      registerBinder(new HealthTipsBinder(simpleItemDecoration1));
      registerBinder(new FeaturesListBinder(simpleItemDecoration1));

   /* registerBinder(new FeaturedArticleBinder(new ArticleItemDecorator()));*/
    registerBinder(new ArticleBinder(new ArticleItemDecorator()));

   /* registerBinder(new AdvertisementBinder());
    registerBinder(new ShufflingHeaderBinder(new ShufflingHeaderBinder.ShuffleListener() {
      @Override public void onShuffleClicked() {
        Collections.shuffle(gridDataList);
        gridItemsManager.set(gridDataList);
      }
    }));*/

      //


           // Utils.showToastMessage("Now called Complex adapter");
      refreshData();






  }
    protected void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }


  private void refreshData()
  {

      if(homeData!=null )
      {
          if( homeData.getDoctorsList()!=null&&homeData.getDoctorsList().size()>0) {
              // if (ArticleBinder1.isRefresh()) {
              popularDoctorslManager.clear();
              popularDoctorslManager.addAll(homeData.getDoctorsList().subList(0, 1));
              // }
          }

          if(homeData.getHealthTips()!=null && homeData.getHealthTips().size()>0)
          {
              healthTipsManager.addAll(homeData.getHealthTips());
          }

          if(homeData.getFeatures()!=null && homeData.getFeatures().size()>0)
          {
              featuresManager.addAll(homeData.getFeatures());
          }
          addGridItem();



      }
  }

  private  int convertDpToPixel(float dp, Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    return (int) (dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
  }

 /* public void addMultiItem(List<Vehicle> dataList) {
    multiItemsManager.addAll(dataList);
  }*/
 /*public void addCarItem() {
   //carItemsManager.addAll(dataList);
 }*/
  /*public void addBikeItem(List<Vehicle> dataList) {
    healthTipsManager.addAll(dataList);
  }*/

    List<NewHomeSpeciality> displayList= new ArrayList<>();
    ArrayList<NewHomeSpeciality> remainingList= new ArrayList<>();
    ArrayList<NewHomeSpeciality> originalList= new ArrayList<>();
  private void addGridItem() {
   // this.gridDataList = dataList;
      originalList.addAll(newHomeSpecialities);
      //registerBinder(new MyGridItemBinder(simpleItemDecoration,dataList));

      //ArrayList<SpecalitiyData> displayList= new ArrayList<>();
      //ArrayList<SpecalitiyRemainData> remainingList= new ArrayList<>();

      for(int i=0;i<newHomeSpecialities.size();i++)
      {
          NewHomeSpeciality data=newHomeSpecialities.get(i);
          if(data==null)
          {
              continue;
          }
          if(displayList.size()>=SINGLE_VIEW_TOT_ITEMS)
          {
              remainingList.add(data) ;
          }else
          {
              if(displayList.size()==(SINGLE_VIEW_TOT_ITEMS-1))
              {
                  NewHomeSpeciality moreData= new NewHomeSpeciality();
                  //moreData.setMoreButton(true);
                  moreData.setButtonType(SpecalitiyData.ButtonType.MORE);
                  displayList.add(moreData);
                 continue;

              }


              displayList.add(data);
          }

      }
      //gridItemsManager.addAll(newList);
      gridItemsManager.set(displayList);

      //gridItemsManager1.set(remainingList);
    //  specialitiesModelManager.setMyList(dataList);
  }

  private void loadMoreValues(List<NewHomeSpeciality> dataList)
  {
      int addedCount=0;
      if(displayList.size()>(SINGLE_VIEW_TOT_ITEMS-1)) {
          if(displayList.remove(displayList.size() - 1)!=null)
          {
              addedCount=-1;
          }

      }
      ArrayList<NewHomeSpeciality> tempList  =  new ArrayList<>();

      boolean isMoreButtonAdded=false;
      NewHomeSpeciality moreData= new NewHomeSpeciality();
      for(int i=0;i<dataList.size();i++)
      {
          NewHomeSpeciality data=dataList.get(i);
          if(data==null)
          {
              continue;
          }
          if(addedCount>=SINGLE_VIEW_TOT_ITEMS)
          {
              tempList.add( data) ;
          }else
          {
              if(addedCount==(SINGLE_VIEW_TOT_ITEMS-1))
              {

                  //moreData.setMoreButton(true);

                  moreData.setButtonType(SpecalitiyData.ButtonType.MORE);
                  displayList.add(moreData);
                  addedCount++;
                  isMoreButtonAdded=true;
                  continue;
                  //break;

              }/*else
              {
                  SpecalitiyData moreData= new SpecalitiyData();
                  //moreData.setMoreButton(true);
                  moreData.setButtonType(SpecalitiyData.ButtonType.LESS);
                  displayList.add(moreData);
                  addedCount++;
                  continue;
              }
*/
              addedCount++;
              displayList.add(data);
          }

      }

      if(!isMoreButtonAdded || tempList.size()==0)
      {
          //NewHomeSpeciality moreData= new NewHomeSpeciality();
          //moreData.setMoreButton(true);
          displayList.remove(moreData);
           moreData= new NewHomeSpeciality();
          moreData.setButtonType(SpecalitiyData.ButtonType.LESS);
          displayList.add(moreData);
      }
      remainingList=tempList;
      //gridItemsManager.addAll(newList);
      gridItemsManager.set(displayList);
      if(myRecylerViewScrollListener!=null) {
          myRecylerViewScrollListener.scrollMyRecylerView(displayList.size());
      }
     // scrollToView(final ScrollView scrollViewParent, gridItemsManager);

  }

  public void addSingleModelItem(List<Article> dataList) {
    singleModelManager.addAll(dataList);
    /*if(homeData!=null && homeData.getDoctorsList()!=null && homeData.getDoctorsList().size()>0) {
        popularDoctorslManager.addAll(homeData.getDoctorsList().subList(0, 1));
    }*/
     /* ArrayList<Article1> list= new ArrayList<Article1>();
      list.add(new Article1());*/
      //popularDoctorslManager.addAll(dataList);
  }
   /* public void addSingleModelItem(List<Article> dataList) {
        singleModelManager.addAll(dataList);
     *//* ArrayList<Article1> list= new ArrayList<Article1>();
      list.add(new Article1());*//*
        popularDoctorslManager.addAll(dataList);
    }*/


  private int getScreenWidth()
  {
      DisplayMetrics displayMetrics = new DisplayMetrics();
      ((Activity)MyApplication.getCurrentActivityContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
      //int height = displayMetrics.heightPixels;
      int width = displayMetrics.widthPixels;
      return width;
  }

    public void setNewHomeData(NewHomeData newHomeData) {
        this.homeData = newHomeData;
        refreshData();
        //setExpandableMode(EXPANDABLE_MODE_MULTIPLE);
        refreshDataBinders();
        notifyDataSetChanged();

    }
}