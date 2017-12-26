package com.vempower.eezyclinic.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.vempower.eezyclinic.application.MyApplication;

public class AutoFitFixedRecyclerView extends RecyclerView {
	private  int columnWidth=20;
	private FullToRefreshGridLayoutManager gridLayoutManager;

	public AutoFitFixedRecyclerView(Context context) {
		super(context);
		columnWidth=20;
	}

	public void setFullToRefreshGridLayoutManager(FullToRefreshGridLayoutManager gridLayoutManager)
	{
		this.gridLayoutManager=gridLayoutManager;
		setLayoutManager(gridLayoutManager);

	}

	public AutoFitFixedRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getColumnwithFromAttr(context, attrs);

		/*FullToRefreshGridLayoutManager gridLayoutManager= new FullToRefreshGridLayoutManager(MyApplication.getCurrentActivityContext(),2, RecyclerView.VERTICAL,false);
		gridLayoutManager.setOnMyScrollListener(new FullToRefreshLinerLayoutManager.MyScrollListener() {
			@Override
			public void fromBottomScroll() {
				// Utils.showToastMessage("Now from bottom scroll "+(page++));
				SwipedRecyclerViewFragment.this.fromBottomScroll();
			}

		});
		setLayoutManager(gridLayoutManager);

		manager = new GridLayoutManager(getContext(), 1);
		setLayoutManager(manager);*/
	}

	private void getColumnwithFromAttr(Context context, AttributeSet attrs) {
		if (attrs != null) {
			// Read android:columnWidth from xml
			int[] attrsArray = {
					android.R.attr.columnWidth
			};
			TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
			columnWidth = array.getDimensionPixelSize(0, -1);
			array.recycle();
		}
	}

	public AutoFitFixedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getColumnwithFromAttr(context, attrs);
	}

	protected void onMeasure(int widthSpec, int heightSpec) {
		super.onMeasure(widthSpec, heightSpec);
		if (columnWidth > 0 && gridLayoutManager!=null) {
			int spanCount = Math.max(1, getMeasuredWidth() / columnWidth);
			gridLayoutManager.setSpanCount(spanCount);
		}
	}

	@Override
	public boolean canScrollVertically(int direction) {
		// check if scrolling up
		if (direction < 1) {
			boolean original = super.canScrollVertically(direction);
			return !original && getChildAt(0) != null
					&& getChildAt(0).getTop() < 0 || original;
		}
		return super.canScrollVertically(direction);

	}

	public int getVerticalOffset() {
		return super.computeVerticalScrollOffset();
	}

}
