/*
* Copyright 2013 Chris Banes
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.vempower.eezyclinic.delegate;

import android.graphics.Rect;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.vempower.eezyclinic.views.FixedRecyclerView;

public class RecyclerViewDelegate implements ViewDelegate {

    private final int[] mViewLocationResult = new int[2];
    private final Rect mRect = new Rect();

    public boolean isViewBeingDragged(MotionEvent event, com.vempower.eezyclinic.views.FixedRecyclerView view) {

        if (view.getAdapter() == null || view.getAdapter().getItemCount()==0) {
            return true;
        }
        view.getLocationOnScreen(mViewLocationResult);
        final int viewLeft = mViewLocationResult[0], viewTop = mViewLocationResult[1];
        mRect.set(viewLeft, viewTop, viewLeft + view.getWidth(), viewTop + view.getHeight());
        final int rawX = (int) event.getRawX(), rawY = (int) event.getRawY();

        if (mRect.contains(rawX, rawY)) {
            return isReadyForPull(view, rawX - mRect.left, rawY - mRect.top);
        }

        return false;
    }

    @Override
    public boolean isReadyForPull(View view, final float x, final float y) {
        boolean ready = false;
        FixedRecyclerView fixedListView = (FixedRecyclerView) view;
        LinearLayoutManager layoutManager = ((LinearLayoutManager)((FixedRecyclerView)view).getLayoutManager());
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        // First we check whether we're scrolled to the top
       // AbsListView absListView = (AbsListView) view;
        if (fixedListView.getAdapter().getItemCount() == 0) {
            ready = true;
        } else if (layoutManager.findFirstVisibleItemPosition() == 0) {
            final View firstVisibleChild = fixedListView.getChildAt(0);
            ready = firstVisibleChild != null
                    && firstVisibleChild.getTop() >= fixedListView.getPaddingTop();
        }

        return ready;
    }
}