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
import android.widget.GridView;

import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.ItemViewHolder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.decorator.GridInsetDecoration;
import com.vempower.eezyclinic.adapters.GridAdapter;
import com.vempower.eezyclinic.adapters.SpecialityListAdapter;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;

public class MyGridItemBinder extends ItemBinder<SpecalitiyData, MyGridItemBinder.OrdersListHolder> {

  private List<SpecalitiyData> dataList;
  public MyGridItemBinder(ItemDecorator itemDecorator,List<SpecalitiyData> dataList) {
    super(itemDecorator);
    this.dataList=dataList;


  }

  @Override public OrdersListHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new OrdersListHolder(layoutInflater.inflate(R.layout.grid_layout, parent, false));
  }



  @Override public void bind(OrdersListHolder holder, SpecalitiyData item) {
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
    return item instanceof SpecalitiyData;
  }


  public class OrdersListHolder extends ItemViewHolder<SpecalitiyData> {

    private GridView gridView;

    public OrdersListHolder(View itemView) {
      super(itemView);
     // item_name_tv = itemView.findViewById(R.id.item_name_tv);
      GridView gridView = (GridView) itemView.findViewById(R.id.grid_view);

      // Instance of ImageAdapter Class
      gridView.setAdapter(new GridAdapter(dataList));

    }

    public void bindData(final SpecalitiyData data) {
      if (data == null) {
        return;
      }

     // item_name_tv.setText(data.getName());





    }
  }


  /*static class ItemViewHolder1 extends BaseViewHolder<GridItem> {

    private ImageView ivIcon;
    private ImageView ivSelectionIndicator;

    ItemViewHolder(View itemView) {
      super(itemView);
      ivSelectionIndicator = (ImageView) itemView.findViewById(R.id.iv_selection_indicator);
      ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);

      setItemClickListener(new OnItemClickListener<GridItem>() {
        @Override public void onItemClick(View view, GridItem item) {
          toggleItemSelection();
          Toast.makeText(view.getContext(), item.getData(), Toast.LENGTH_SHORT).show();
        }
      });
      setItemLongClickListener(new OnItemLongClickListener<GridItem>() {
        @Override public boolean onItemLongClick(View view, GridItem item) {
          startDrag();
          return true;
        }
      });
    }

    @Override public int getDragDirections() {
      return ItemTouchHelper.LEFT
          | ItemTouchHelper.UP
          | ItemTouchHelper.RIGHT
          | ItemTouchHelper.DOWN;
    }
  }*/
}