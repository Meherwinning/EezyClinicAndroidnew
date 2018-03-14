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
import com.vempower.eezyclinic.APIResponce.SpecalitiyRemainData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.TestSliderActivity;
import com.vempower.eezyclinic.fragments.FirstFragment;

public class ArticleBinder1 extends ItemBinder<SpecalitiyRemainData, ArticleBinder1.ViewHolder> {

  private FragmentManager fragmentManager;
  public ArticleBinder1(ItemDecorator itemDecorator,FragmentManager fragmentManager) {
    super(itemDecorator);
    this.fragmentManager=fragmentManager;
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.test_slider, parent, false));
  }

  @Override public void bind(ViewHolder holder, SpecalitiyRemainData item) {
   /* holder.tvTitle.setText(item.getTitle());
    holder.tvTime.setText(item.getLastUpdated());
    holder.tvCategory.setText(item.getCategory());
    holder.ivCover.setBackgroundColor(item.getCategoryColor());
    holder.ivCover.setImageResource(item.getCoverImageId());*/
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof SpecalitiyRemainData /*&& !((SpecalitiyRemainData) item).isFeatured()*/;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

   class ViewHolder extends BaseViewHolder<SpecalitiyRemainData> {

   /* private TextView tvTitle;
    private TextView tvTime;
    private TextView tvCategory;
    private ImageView ivCover;*/

    ViewHolder(View itemView) {
      super(itemView);
      ViewPager vpPager = (ViewPager) itemView.findViewById(R.id.vpPager);
      vpPager.setClipToPadding(false);
      vpPager.setPageMargin(12);

      FragmentPagerAdapter  adapterViewPager = new MyPagerAdapter(fragmentManager);
      vpPager.setAdapter(adapterViewPager);
     /* tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
      ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);*/
    }
  }


  public static class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 9;

    public MyPagerAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public float getPageWidth (int position) {
      return 0.93f;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
      return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
      return FirstFragment.newInstance(position, "Page # "+position);
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