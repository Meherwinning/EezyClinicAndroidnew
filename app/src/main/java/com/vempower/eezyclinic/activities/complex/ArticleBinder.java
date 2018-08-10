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
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.HomeActivity;
import com.vempower.eezyclinic.activities.SearchActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.FromActivityListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.utils.Constants;

public class ArticleBinder extends ItemBinder<Article, ArticleBinder.ViewHolder> {

  public ArticleBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.new_home_header, parent, false));
  }

  @Override public void bind(ViewHolder holder, Article item) {
   /* holder.tvTitle.setText(item.getTitle());
    holder.tvTime.setText(item.getLastUpdated());
    holder.tvCategory.setText(item.getCategory());
    holder.ivCover.setBackgroundColor(item.getCategoryColor());
    holder.ivCover.setImageResource(item.getCoverImageId());*/
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Article && !((Article) item).isFeatured();
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends BaseViewHolder<Article> {


    private AppCompatButton search_bt;

    ViewHolder(View itemView) {
      super(itemView);
     /* tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
      ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);*/

      search_bt = itemView.findViewById(R.id.search_bt);

      search_bt.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         Intent intent =((Activity) MyApplication.getCurrentActivityContext()).getIntent();
         //Intent intent = new Intent(MyApplication.getCurrentActivityContext(),SearchActivity.class);
         intent.setClass(MyApplication.getCurrentActivityContext(),SearchActivity.class);
         intent.putExtra(Constants.Pref.IS_FROM_DASH_BOARD,true);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);

         MyApplication.getCurrentActivityContext().startActivity(intent);
         /*sendHandlerMessage(intent, ListenerKey.FROM_ACTIVITY_LISTENER_KEY, new FromActivityListener() {
           @Override
           public boolean isFromSearchActivity() {
             return true;
           }
         });*/
       }
     });
    }
  }
}