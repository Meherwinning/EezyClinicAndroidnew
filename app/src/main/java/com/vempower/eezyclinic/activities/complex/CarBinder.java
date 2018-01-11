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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemViewHolder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.APIResponce.SpecalitiyRemainData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;

public class CarBinder extends ItemBinder<SpecalitiyRemainData, CarBinder.DoctorsListHolder> {
  LayoutInflater layoutInflater;
  List<SpecalitiyRemainData> doctorsList;
  public CarBinder(ItemDecorator itemDecorator,List<SpecalitiyRemainData> doctorsList) {

    super(itemDecorator);
    this.doctorsList=doctorsList;

  }

  @Override public DoctorsListHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
   this.layoutInflater=layoutInflater;
    return new DoctorsListHolder(layoutInflater.inflate(R.layout.popular_doctors_higental_scroll, parent, false));
  }

  @Override public void bind(DoctorsListHolder holder, SpecalitiyRemainData item) {
    holder.bindData(item);

   /* holder.tvName.setText(item.getModelName());
    holder.tvMake.setText(item.getMake());
    holder.tvYear.setText(item.getYear());*/
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof SpecalitiyRemainData;
  }

  @Override public int getSpanSize(int maxSpanCount) {
    return maxSpanCount;
  }

  public class DoctorsListHolder extends ItemViewHolder<SpecalitiyRemainData> {

    private LinearLayout horigental_linear;

    public DoctorsListHolder(View itemView) {
      super(itemView);
      horigental_linear = itemView.findViewById(R.id.horigental_linear);



    }

    public void bindData(final SpecalitiyRemainData data) {
      if (data == null) {
        return;
      }

      for(SpecalitiyRemainData myData:doctorsList)
      {
        if(myData!=null)
        {
         View view= layoutInflater.inflate(R.layout.popular_doctor_single_layout,  null);
          OrdersListHolder holder= new OrdersListHolder(view);
          holder.bindData(myData);
          horigental_linear.addView(view);
        }
      }
    //  horigental_linear.ad




    }
  }

  public class OrdersListHolder  {

    private MyTextViewRR item_name_tv;

    public OrdersListHolder(View itemView) {
      item_name_tv = itemView.findViewById(R.id.item_name_tv);

    }

    public void bindData(final SpecalitiyRemainData data) {
      if (data == null) {
        return;
      }

      item_name_tv.setText(data.getName());

     /* itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(data.isMoreButton())
          {
            Utils.showToastMsg("Now click More button");
            if(buttonListener!=null)
            {
              buttonListener.onClick(true);
            }
          }
        }
      });*/




    }
  }

}