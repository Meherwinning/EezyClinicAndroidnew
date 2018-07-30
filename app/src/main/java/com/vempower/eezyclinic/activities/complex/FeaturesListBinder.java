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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.core.Feature;
import com.vempower.eezyclinic.core.HealthTip;

public class FeaturesListBinder extends ItemBinder<Feature, FeaturesListBinder.ViewHolder> {
  private static View  view;
  public FeaturesListBinder(ItemDecorator itemDecorator) {
    super(itemDecorator);
  }

  @Override public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ViewHolder(layoutInflater.inflate(R.layout.item_feature, parent, false));
  }

  @Override public void bind(ViewHolder holder, Feature item) {
    holder.health_tip_tv2.setText(item.feature);
    if(view==null) {
      view = holder.itemView;
    }
   // holder.tvDescription.setText(item.getDescription());
  }

  public  View getView() {
    return view;
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof Feature;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  static class ViewHolder extends BaseViewHolder<Feature> {

    private TextView health_tip_tv2;
   // private TextView tvDescription;

    ViewHolder(View itemView) {
      super(itemView);
      health_tip_tv2 = (TextView) itemView.findViewById(R.id.health_tip_tv2);
      //tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
    }
  }
}