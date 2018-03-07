/*
 * Copyright (C) 2016 Ronald Martin <hello@itsronald.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 10/12/16 11:22 PM.
 */

package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.utils.Utils;
 ;


/**
 * A simple {@link Fragment} subclass that displays its page number in a ViewPager.
 * <p>
 * <p>
 * Use the {@link PageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "param1";

    //private TextView myTextView;

    @Nullable
    private int page;

    public PageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageText Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    public static PageFragment newInstance(@NonNull final int pageText) {
        PageFragment fragment = new PageFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TEXT, pageText);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_TEXT,-1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=null;
        switch (page)
        {
            case 0:
                return inflater.inflate(R.layout.fragment_apptour_page1, container, false);
                //myTextView= view.findViewById(R.id.from_text);

               // break;
            case 1:
                return inflater.inflate(R.layout.fragment_apptour_page2, container, false);
               // myTextView= view.findViewById(R.id.from_text);
                //break;
            case 2:
                return inflater.inflate(R.layout.fragment_apptour_page3, container, false);
               // myTextView= view.findViewById(R.id.from_text);
                //break;

                default: {
                    return inflater.inflate(R.layout.fragment_apptour_page3, container, false);
                    //myTextView = view.findViewById(R.id.from_text);
                }


        }
       /*  TextView textView = (TextView) view.findViewById(R.id.text_view);
        if (textView != null) {
            textView.setText(pageText);
        }*/

        //myTextView.setText("Screen :"+ Utils.getStringFromResources(R.string.screen)+" Dimen:"+getResources().getDimension(R.dimen.text_size_normal));

       // return view;
    }
}
