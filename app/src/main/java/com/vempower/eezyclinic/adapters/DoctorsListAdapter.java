package com.vempower.eezyclinic.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.DoctorProfileActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;

/**
 * Created by Satishk on 9/7/2017.
 */

public class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListAdapter.OrdersListHolder> {

    private List<SearchResultDoctorListData> doctorsList;

    private LayoutInflater inflater;


    public DoctorsListAdapter(List<SearchResultDoctorListData> doctorsList) {
        this.doctorsList = doctorsList;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public OrdersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.doctor_single_item, parent, false);
        return new OrdersListHolder(convertView);
    }


    @Override
    public void onBindViewHolder(OrdersListHolder holder, int position) {


        holder.bindData(doctorsList.get(position), position);


    }

    @Override
    public int getItemCount() {
        return doctorsList == null ? 0 : doctorsList.size();
    }


    public void setUpdatedList(List<SearchResultDoctorListData> newList) {
        if (newList == null || newList.size() == 0) {
            if (this.doctorsList == null) {
                return;
            }
            this.doctorsList.clear();
            notifyDataSetChanged();
            return;
        }
        this.doctorsList = newList;
        notifyDataSetChanged();
    }


    public class OrdersListHolder extends RecyclerView.ViewHolder {

        private ImageView profile_iv;

        private MyTextViewRM title_tv;
        private MyTextViewRR designation_tv, time_tv, address_tv,
                reviews_count_tv, recommendations_count_tv, book_appointment_tv;

        public OrdersListHolder(View itemView) {
            super(itemView);
            profile_iv = itemView.findViewById(R.id.profile_iv);

            title_tv = itemView.findViewById(R.id.title_tv);
            designation_tv = itemView.findViewById(R.id.designation_tv);
            time_tv = itemView.findViewById(R.id.time_tv);
            address_tv = itemView.findViewById(R.id.address_tv);

            reviews_count_tv = itemView.findViewById(R.id.reviews_count_tv);
            recommendations_count_tv = itemView.findViewById(R.id.recommendations_count_tv);
            book_appointment_tv = itemView.findViewById(R.id.book_appointment_tv);


        }

        public void bindData(final SearchResultDoctorListData data, final int position) {
            if (data == null) {
                return;
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* "doctorid":"46",
                            "clinicid":"21",
                            "branchid":40*/
                   Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),DoctorProfileActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                    intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY,new Messenger(new AbstractIBinder(){
                        @Override
                        protected IntentObjectListener getMyObject() {
                            return new IntentObjectListener(){

                                @Override
                                public Object getObject() {
                                    return data;
                                }
                            };
                        }
                    }));

                    MyApplication.getCurrentActivityContext().startActivity(intent);
                }
            });

            MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, data.getDoctorLogo());


            title_tv.setText(data.getDoctorName());
            designation_tv.setText(data.getSpecalities());
            String timings = data.getConsultTimings();
            if(timings!=null) {
                timings = timings.replaceAll("\\r\\n", " ");
                time_tv.setText(timings);
            }else
            {
                time_tv.setText("-");
            }


            String address = data.getAddress();
            if(address!=null) {
                address = address.replaceAll("\\r\\n", " ");
                address_tv.setText(address);
            }else
            {
                address_tv.setText("-");
            }


            reviews_count_tv.setText(TextUtils.isEmpty(data.getDoctorTotalReviews())?"0":data.getDoctorTotalReviews());

            recommendations_count_tv.setText(TextUtils.isEmpty(data.getDoctorRecommendedCount())?"0":data.getDoctorRecommendedCount());
            book_appointment_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.showToastMsg(data.getDoctorName()+"");
                }
            });



           /* date_display_tv.setText(order.getDate()+"");
            total_amount_tv.setText(Utils.getStringFromResources(R.string.indian_currency_symbol)+order.getTotalAmount());
            order_status_tv.setText(order.getOrderstatus());

            if(!order.getOrder_status_code().equalsIgnoreCase(Constants.OrderStatus.PAYMENT_ACCEPTED))
            {
              //  write_a_review_linear.setVisibility(View.GONE);
               // rating_bar_relative.setVisibility(View.GONE);
               feedback_and_rating_bt.setVisibility(View.GONE);
                order_status_tv.setTextColor(MyApplication.getCurrentActivityContext().getResources().getColor(R.color.app_red));
            }else
            {
                //write_a_review_linear.setVisibility(View.VISIBLE);
               // rating_bar_relative.setVisibility(View.VISIBLE);
                feedback_and_rating_bt.setVisibility(View.VISIBLE);
                order_status_tv.setTextColor(MyApplication.getCurrentActivityContext().getResources().getColor(R.color.app_green));

            }

            feedback_and_rating_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent= new Intent(MyApplication.getCurrentActivityContext(), FeedbackAndRatingActivity.class);
                    intent.putExtra(Constants.Pref.ORDER_ID_KEY,order.getOrderId());
                    MyApplication.getCurrentActivityContext().startActivity(intent);

                   // Utils.showToastMessage(order.getOrderId()+"");
                }
            });*/
        }
    }

   /* protected void showMyDialog(String title,String message,final ApiErrorDialogInterface dialogInterface)
    {
        SimpleDialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.retryClick();
                }
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.onCloseClick();
                }
            }
        };

        ((SimpleDialog.Builder)builder).message(message)
                .title(title)
                .positiveAction("Retry")
                .negativeAction("Close");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.setCancelable(false);
        fragment.show(, null);
    }*/

}
