package com.vempower.eezyclinic.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.AbstractListener;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.interfaces.MenuScreenListener;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 17/11/17.
 */

public abstract class AbstractFragment extends Fragment {
    protected MenuScreenListener listener;
    protected AbstractListener myListener;


    private InputMethodManager keyBoardHide;
    private ProgressDialog progress;



    public void setOnMenuScreenListener(MenuScreenListener listener) {
        this.listener=listener;
    }

    public  void onTitleRightButtonClick(){

    }

    public void setScreenTitle(String screenTitle) {
        if(TextUtils.isEmpty(screenTitle) || getFragemtView()==null)
        {
            return;
        }
        // if(screen_title_tv==null) {
        //TextView    screen_title_tv = getFragemtView().findViewById(R.id.screen_title_tv);
        // }
        /*if(screen_title_tv==null) {
            return;
        }
        screen_title_tv.setText(screenTitle);*/
        // screen_title_tv get
    }


     View getFragemtView(){
        return null;
    }

    protected boolean isValidResponse(AbstractResponse response, String errorMessage/*,boolean isShowDialog,boolean isShowNothing*/)
    {

        return isValidResponse( response,  errorMessage, false,false);
    }

    protected boolean isValidResponse(AbstractResponse response, String errorMessage,boolean isShowDialog,boolean isFinish)
    {
        if(response==null && TextUtils.isEmpty(errorMessage))
        {
            if(isShowDialog)
            {
                showAlertDialog("Alert",  Utils.getStringFromResources(R.string.invalid_service_response_lbl),isFinish);
            }else {
                Utils.showToastMsg(R.string.invalid_service_response_lbl);
            }
            return false;
        }
        if(response==null && !TextUtils.isEmpty(errorMessage))
        {
            if(isShowDialog) {
                showAlertDialog("Alert", errorMessage,isFinish);
            }else {
                Utils.showToastMsg(errorMessage);
            }
            return false;
        }
        if(!response.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE))
        {
            if(isShowDialog) {
                showAlertDialog("Alert",response.getStatusMessage() ,isFinish);
            }else
            {
                Utils.showToastMsg(response.getStatusMessage());
            }
            //showMyAlertDialog("Alert",response.getStatusMessage() ,"Ok",false);

            return false;

        }
        return true;
    }

    public void setOnMyListener(AbstractListener myListener)
    {
        this.myListener=myListener;
    }


    public void showAlertDialog(String title, String msg,final boolean isFinish) {
        showAlertDialog(title, msg,isFinish, null);
    }

    protected void showAlertDialog(String title, String msg,final boolean isFinish, final Intent intent) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyApplication.getCurrentActivityContext());

        // Setting Dialog Title
        alertDialog.setTitle(TextUtils.isEmpty(title)?"":title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // setting Dialog cancelable to false 9010864578
        alertDialog.setCancelable(false);

        // On pressing Settings button
        alertDialog.setPositiveButton(Utils.getStringFromResources(R.string.ok_label),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                        if(intent!=null)
                        {
                            startActivity(intent);
                        }
                        if(isFinish)
                        {
                            ((Activity) MyApplication.getCurrentActivityContext()).finish();
                        }
                    }
                });


        alertDialog.show();

    }


    protected void showProgressView()
    {
        hideProgressView();
        progress = new ProgressDialog(MyApplication.getCurrentActivityContext());
        if(progress==null)
        {
            return;
        }
        progress.setMessage("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    protected void hideProgressView()
    {
        if(progress!=null)
        {
            progress.dismiss();
            progress=null;
        }
    }
    protected void showMyDialog(String title,int messageId,final ApiErrorDialogInterface dialogInterface)
    {
        showMyDialog( title,Utils.getStringFromResources(messageId),  dialogInterface);

    }
    protected void showMyCustomDialog(String title,String message, String possitiveButtonStr,String negetiveButtonStr,final MyDialogInterface dialogInterface)
    {
        SimpleDialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.onPossitiveClick();
                }
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.onNegetiveClick();
                }
            }
        };

        ((SimpleDialog.Builder)builder).message(message)
                .title(title)
                .positiveAction(possitiveButtonStr)
                .negativeAction(negetiveButtonStr);
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(), null);
    }

    protected void showMyCustomDialog(String title,String message,String possitiveButtonStr)
    {
        SimpleDialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Agreed", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
               /* if(dialogInterface!=null) {
                    dialogInterface.onPossitiveClick();
                }*/
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
                /*if(dialogInterface!=null) {
                    dialogInterface.onNegetiveClick();
                }*/
            }
        };

        ((SimpleDialog.Builder)builder).message(message)
                .title(title)
                .positiveAction(possitiveButtonStr);
        // .negativeAction(negetiveButtonStr);
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(), null);
    }

    protected void showMyDialog(String title,String message,String positiveButtonName,final ApiErrorDialogInterface dialogInterface)
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

            /*@Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Disagreed", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
                if(dialogInterface!=null) {
                    dialogInterface.onCloseClick();
                }
            }*/
        };

        ((SimpleDialog.Builder)builder).message(message)
                .title(title)
                .positiveAction(positiveButtonName);
               // .negativeAction("Close");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.setCancelable(false);
        fragment.show(getChildFragmentManager(), null);
    }


    protected void showMyDialog(String title,String message,final ApiErrorDialogInterface dialogInterface)
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
        fragment.show(getChildFragmentManager(), null);
    }



    public void hideKeyBord(View view) {
        if (view != null) {
            if (keyBoardHide == null) {
                keyBoardHide = (InputMethodManager) MyApplication.getCurrentActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                // keyBoardHide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
                // 0);
            }
            if (keyBoardHide != null && keyBoardHide.isActive()) {
                // to hide keyboard
                if (view != null) {
                    keyBoardHide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }



}
