package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.delegate.AbsListViewDelegate;
import com.vempower.eezyclinic.delegate.ScrollViewDelegate;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;

/**
 * Created by satish on 18/12/17.
 */

public class DoctorProfileTabFragment extends BaseViewPagerFragment {

    private ScrollView mScrollView;
    private ScrollViewDelegate mScrollViewDelegate = new ScrollViewDelegate();

    public DoctorProfileTabFragment()
    {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, 0);
        setArguments(args);
    }

  /*  public static ScrollViewFragment newInstance(int index) {
        ScrollViewFragment fragment = new ScrollViewFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doctor_profile_tap_layout, container, false);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollview);
       // MyButtonRectangleRM rectangleRM= view.findViewById(R.id.appointment_bt);
       // rectangleRM.setBackground(getResources().getDrawable(R.drawable.app_gradient_bg1));
        //TextView textView = (TextView) view.findViewById(R.id.text1);
        //textView.setText(Utils.getStringFromResources(R.string.sample_txt));
        return view;
    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        return mScrollViewDelegate.isViewBeingDragged(event, mScrollView);
    }

   /* private String loadContentString() {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[8 * 1024];
        int len;
        String content = "";
        try {
            inputStream = getActivity().getAssets().open("lorem.txt");
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            content = outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSilently(inputStream);
            closeSilently(outputStream);
        }

        return content;
    }

    private void closeSilently(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }*/
}