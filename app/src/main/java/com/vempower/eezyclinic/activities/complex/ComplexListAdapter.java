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
import android.util.DisplayMetrics;


import com.ahamed.multiviewadapter.DataItemManager;
import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.util.SimpleDividerDecoration;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.APIResponce.SpecalitiyRemainData;
import com.vempower.eezyclinic.activities.decorator.ArticleItemDecorator;
import com.vempower.eezyclinic.application.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class ComplexListAdapter extends RecyclerAdapter {

  private DataListManager<Article> singleModelManager;
   // private DataListManager<SpecalitiyData> specialitiesModelManager;
  private DataListManager<SpecalitiyData> gridItemsManager;
   // private DataListManager<SpecalitiyRemainData> gridItemsManager1;
  private DataListManager<Vehicle> carItemsManager;
  private DataListManager<Vehicle> bikeItemsManager;

  private final int SINGLE_VIEW_TOT_ITEMS=9;

  //private List<SpecalitiyData> gridDataList

  private SimpleDividerDecoration simpleItemDecoration,simpleItemDecoration1;

  public ComplexListAdapter() {
   // simpleItemDecoration = new SimpleDividerDecoration(MyApplication.getCurrentActivityContext(), SimpleDividerDecoration.VERTICAL);
      simpleItemDecoration1 = new SimpleDividerDecoration(MyApplication.getCurrentActivityContext(), SimpleDividerDecoration.HORIZONTAL);

    singleModelManager = new DataListManager<>(this);
    gridItemsManager = new DataListManager<>(this);
      //gridItemsManager1 = new DataListManager<>(this);
    carItemsManager = new DataListManager<>(this);
    bikeItemsManager = new DataListManager<>(this);
     // specialitiesModelManager= new DataListManager<>(this);



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
    addDataManager(carItemsManager);
    addDataManager(new DataItemManager<>(this, new Header("Health Tips")));
    addDataManager(bikeItemsManager);




    //addDataManager(new DataItemManager<>(this, DummyDataProvider.getAdvertisementThree()));

    registerBinder(new HeaderBinder());
    registerBinder(new GridItemBinder(convertDpToPixel(4, MyApplication.getCurrentActivityContext()), new GridItemBinder.ExpandColapseButtonListener(){
        @Override
        public void onClick(boolean isExpand) {
            loadMoreValues(remainingList);
        }
    }));

     // registerBinder(new GridItemRemainBinder(convertDpToPixel(4, context)));

      registerBinder(new CarBinder(simpleItemDecoration1));

    registerBinder(new BikeBinder(simpleItemDecoration1));
    registerBinder(new FeaturedArticleBinder(new ArticleItemDecorator()));
    registerBinder(new ArticleBinder(new ArticleItemDecorator()));
   /* registerBinder(new AdvertisementBinder());
    registerBinder(new ShufflingHeaderBinder(new ShufflingHeaderBinder.ShuffleListener() {
      @Override public void onShuffleClicked() {
        Collections.shuffle(gridDataList);
        gridItemsManager.set(gridDataList);
      }
    }));*/
  }

  public static int convertDpToPixel(float dp, Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    return (int) (dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
  }

 /* public void addMultiItem(List<Vehicle> dataList) {
    multiItemsManager.addAll(dataList);
  }*/
 public void addCarItem(List<Vehicle> dataList) {
   carItemsManager.addAll(dataList);
 }
  public void addBikeItem(List<Vehicle> dataList) {
    bikeItemsManager.addAll(dataList);
  }

    List<SpecalitiyData> displayList= new ArrayList<>();
    ArrayList<SpecalitiyData> remainingList= new ArrayList<>();
  public void addGridItem(List<SpecalitiyData> dataList) {
   // this.gridDataList = dataList;
      //registerBinder(new MyGridItemBinder(simpleItemDecoration,dataList));

      //ArrayList<SpecalitiyData> displayList= new ArrayList<>();
      //ArrayList<SpecalitiyRemainData> remainingList= new ArrayList<>();

      for(int i=0;i<dataList.size();i++)
      {
          SpecalitiyData data=dataList.get(i);
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
                  SpecalitiyData moreData= new SpecalitiyData();
                  moreData.setMoreButton(true);
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

  private void loadMoreValues(List<SpecalitiyData> dataList)
  {
      int addedCount=0;
      if(displayList.size()>(SINGLE_VIEW_TOT_ITEMS-1)) {
          if(displayList.remove(displayList.size() - 1)!=null)
          {
              addedCount=-1;
          }

      }
      ArrayList<SpecalitiyData> tempList  =  new ArrayList<>();

      for(int i=0;i<dataList.size();i++)
      {
          SpecalitiyData data=dataList.get(i);
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
                  SpecalitiyData moreData= new SpecalitiyData();
                  moreData.setMoreButton(true);
                  displayList.add(moreData);
                  addedCount++;
                  continue;
                  //break;

              }

              addedCount++;
              displayList.add(data);
          }

      }
      remainingList=tempList;
      //gridItemsManager.addAll(newList);
      gridItemsManager.set(displayList);

  }

  public void addSingleModelItem(List<Article> dataList) {
    singleModelManager.addAll(dataList);
  }


  private int getScreenWidth()
  {
      DisplayMetrics displayMetrics = new DisplayMetrics();
      ((Activity)MyApplication.getCurrentActivityContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
      //int height = displayMetrics.heightPixels;
      int width = displayMetrics.widthPixels;
      return width;
  }
}