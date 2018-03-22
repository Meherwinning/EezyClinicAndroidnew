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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.vempower.eezyclinic.APICore.NewHomeDoctorsList;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.APIResponce.SpecalitiyRemainData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.TestSliderActivity;
import com.vempower.eezyclinic.fragments.FirstFragment;
import com.vempower.eezyclinic.utils.Utils;

import java.util.List;

import okhttp3.internal.Util;

public class ArticleBinder1 extends ItemBinder<NewHomeDoctorsList, ArticleBinder1.ViewHolder> {

  private FragmentManager fragmentManager;
  private List<NewHomeDoctorsList> doctorsLists;
  private  ViewHolder holder;
 // FragmentPagerAdapter adapterViewPager;
  public static ArticleBinder1 articleBinder1;
  public ArticleBinder1(List<NewHomeDoctorsList> doctorsLists, ItemDecorator itemDecorator, FragmentManager fragmentManager) {
    super(itemDecorator);
    this.fragmentManager=fragmentManager;
    this.doctorsLists=doctorsLists;

  }

  /*public static ArticleBinder1 getArticleBinder1(List<NewHomeDoctorsList> doctorsLists, ItemDecorator itemDecorator, FragmentManager fragmentManager)
  {
    if(articleBinder1==null)
    {
      articleBinder1= new ArticleBinder1( doctorsLists, itemDecorator, fragmentManager);
    }else
    {
      if(articleBinder1!=null &&  articleBinder1.holder!=null && articleBinder1.holder.adapterViewPager!=null )
      {
        articleBinder1.holder.adapterViewPager.setUpdatedList(doctorsLists);
      }

    }

   return articleBinder1;
  }*/

    public static boolean isNotHaveData() {
        return articleBinder1 == null;
    }

    @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    //if(holder==null)
   // {
          holder= new ViewHolder(layoutInflater.inflate(R.layout.test_slider, parent, false));
    //}
    return holder;
  }

  @Override public void bind(ViewHolder holder, NewHomeDoctorsList item) {
   /* holder.tvTitle.setText(item.getTitle());
    holder.tvTime.setText(item.getLastUpdated());
    holder.tvCategory.setText(item.getCategory());
    holder.ivCover.setBackgroundColor(item.getCategoryColor());
    holder.ivCover.setImageResource(item.getCoverImageId());*/
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof NewHomeDoctorsList /*&& !((SpecalitiyRemainData) item).isFeatured()*/;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

   class ViewHolder extends BaseViewHolder<NewHomeDoctorsList> {

   /* private TextView tvTitle;
    private TextView tvTime;
    private TextView tvCategory;
    private ImageView ivCover;*/
   ViewPager vpPager;
     MyPagerAdapter adapterViewPager ;
    ViewHolder(View itemView) {
      super(itemView);
     // Utils.showToastMessage("called ViewHolder");
     // if(vpPager==null) {
        vpPager = (ViewPager) itemView.findViewById(R.id.vpPager);
        vpPager.setClipToPadding(false);

        //vpPager.setOffscreenPageLimit(20);
        //vpPager.setPageMargin(12);
     // if(adapterViewPager==null) {
            adapterViewPager = new MyPagerAdapter(fragmentManager,doctorsLists);
            vpPager.setAdapter(adapterViewPager);
      //}/*else
     /* {
        adapterViewPager.setUpdatedList(doctorsLists);
      }*/
        vpPager.setCurrentItem(0);
        vpPager.invalidate();

     // }
     // {
        //vpPager.getAdapter().notifyDataSetChanged();

        //vpPager.setCurrentItem(0);
       // vpPager.getAdapter().notifyDataSetChanged();
        //vpPager.invalidate();
     // }
     /* tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
      ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);*/
    }
  }


  private  class MyPagerAdapter extends FragmentPagerAdapter {
    //private  int NUM_ITEMS = doctorsLists.size();
    private List<NewHomeDoctorsList> myDoctorsLists;
    public MyPagerAdapter(FragmentManager fragmentManager,List<NewHomeDoctorsList> doctorsLists) {
      super(fragmentManager);
      this.myDoctorsLists=doctorsLists;
    }

    public void setUpdatedList(List<NewHomeDoctorsList> newList) {
      if (newList == null || newList.size() == 0) {
        if (this.myDoctorsLists == null) {
          return;
        }
        this.myDoctorsLists.clear();
        notifyDataSetChanged();

        return;
      }
      this.myDoctorsLists = newList;
      notifyDataSetChanged();
    }


   /* @Override
    public float getPageWidth (int position) {
      return 0.75f;
      //return 0.93f;
    }*/

    // Returns total number of pages
    @Override
    public int getCount() {
      return myDoctorsLists==null?0:myDoctorsLists.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
      return new FirstFragment(myDoctorsLists.get(position));
            /*switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FirstFragment.newInstance(position, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FirstFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return FirstFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }*/
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
      return "Page " + position;
    }

  }
}