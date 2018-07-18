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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemViewHolder;
import com.vempower.eezyclinic.APICore.NewHomeSpeciality;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.DoctorsListActivity;
import com.vempower.eezyclinic.activities.decorator.GridInsetDecoration;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
 ;

public class GridItemBinder extends ItemBinder<NewHomeSpeciality, GridItemBinder.OrdersListHolder> {
private ExpandColapseButtonListener buttonListener;
  public GridItemBinder(int insetInPixels,ExpandColapseButtonListener buttonListener) {
    super(new GridInsetDecoration(insetInPixels,insetInPixels));
    this.buttonListener=buttonListener;
  }

  @Override public OrdersListHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new OrdersListHolder(layoutInflater.inflate(R.layout.speciality_single_layout, parent, false));
  }



  @Override public void bind(OrdersListHolder holder, NewHomeSpeciality item) {
    holder.bindData(item);
    /*holder.itemView.setBackgroundColor(item.getColor());
    holder.ivIcon.setImageResource(item.getDrawable());
    if (holder.isItemSelected()) {
      holder.ivSelectionIndicator.setVisibility(View.VISIBLE);
    } else {
      holder.ivSelectionIndicator.setVisibility(View.GONE);
    }*/
  }



  @Override public boolean canBindData(Object item) {
    return (item instanceof NewHomeSpeciality);
  }


  public class OrdersListHolder extends ItemViewHolder<NewHomeSpeciality> {

    private TextView item_name_tv;
    private ImageView new_home_speciality_iv;

    public OrdersListHolder(View itemView) {
      super(itemView);
      item_name_tv = itemView.findViewById(R.id.item_name_tv);

      new_home_speciality_iv   = itemView.findViewById(R.id.new_home_speciality_iv);

    }

    public void bindData(final NewHomeSpeciality data) {
      if (data == null) {
        return;
      }

      item_name_tv.setText(data.getName());
      new_home_speciality_iv.setBackground(null);
      switch (data.getButtonType())
      {

        case NewHomeSpeciality.ButtonType.MORE:
          MyApplication.getInstance().setBitmapToImageview(R.drawable.more_button,new_home_speciality_iv);
          item_name_tv.setText("More");
          itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // Utils.showToastMsg("Now click More button");
                if(buttonListener!=null)
                {
                  itemView.setOnClickListener(null);
                  buttonListener.onClick(true);
                }
               // profile_iv.setBackground(null);
              }
          });
          break;
        case NewHomeSpeciality.ButtonType.LESS:
          MyApplication.getInstance().setBitmapToImageview(R.drawable.less_button,new_home_speciality_iv);
          item_name_tv.setText("Less");
          itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //Utils.showToastMsg("Now click Less button");
              if(buttonListener!=null)
              {
                itemView.setOnClickListener(null);
                buttonListener.onClick(false);
              }
             // profile_iv.setBackground(null);
            }
          });
          break;

        default:
          //MyApplication.getInstance().setBitmapToImageview(R.drawable.empty_specification,profile_iv);

          MyApplication.getInstance().setBitmapToImageview(R.drawable.empty_specification,new_home_speciality_iv,data.getIcon());

          itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SearchRequest  searchRequestParams = new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);


              searchRequestParams.setSpecality(data.getName());

              MyApplication.getInstance().setSearchRequestParms(searchRequestParams);
              Intent intent= new Intent(MyApplication.getCurrentActivityContext(), DoctorsListActivity.class);

              intent.putExtra(Constants.Pref.IS_FROM_NEW_HOME_SPESHALITY,true);
              intent.putExtra(Constants.Pref.DIPLAY_SPESHALITY_NAME,data.getName());


              MyApplication.getCurrentActivityContext().startActivity(intent);
              //( (Activity) MyApplication.getCurrentActivityContext()).finish();
            }
          });
          break;
      }
     /* if(data.isMoreButton())
      {

      }else
      {
        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_icon,profile_iv);
      }*/






    }
  }




  public interface  ExpandColapseButtonListener
  {
    public  void onClick(boolean isExpand);
  }



}