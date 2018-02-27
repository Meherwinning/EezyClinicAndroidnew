package com.vempower.eezyclinic.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Messenger;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.ImageExpandViewActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.AbstractListener;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.interfaces.MenuScreenListener;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.DownloadTask;
import com.vempower.eezyclinic.utils.MyDownloadfFile;
import com.vempower.eezyclinic.utils.PrintPDF;
import com.vempower.eezyclinic.utils.Utils;

import java.io.File;

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

    public void showAlertDialog(String title, int msgId,final boolean isFinish) {
        showAlertDialog(title, Utils.getStringFromResources(msgId),isFinish, null);
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
        fragment.dismissAllowingStateLoss();

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


    /**
     * Used to scroll to the given view.
     *
     * @param scrollViewParent Parent ScrollView
     * @param view             View to which we need to scroll.
     */
    protected void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumalated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    protected void displayImageInLarge(final Drawable drawable) {

        if(drawable==null)
        {
            Utils.showToastMsg("Invalid Image");
            return;
        }
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(), ImageExpandViewActivity.class);

        // Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),ClinicProfileActivity.class);
               /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
        intent.putExtra(ListenerKey.ObjectKey.IMAGE_DRAWABLE_KEY,new Messenger(new AbstractIBinder(){
            @Override
            protected IntentObjectListener getMyObject() {
                return new IntentObjectListener(){
                    @Override
                    public Object getObject() {
                        // if(patient_profile_iv1.getDrawable()!=null)
                        return drawable;
                    }
                };
            }
        }));
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
    }

    protected void printPDF(Uri uri) {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            PrintPDF printPDF = new PrintPDF(uri);
            printPDF.printPreview();
        }else
        {
            Utils.showToastMsg(Utils.getStringFromResources(R.string.printing_not_supported_device_lbl));
        }

    }


    protected void downloadTaskStart(final String downloadUrlStr) {

        if (TextUtils.isEmpty(downloadUrlStr)) {
            return;
        }
        DownloadTask downloadTask = new DownloadTask(downloadUrlStr, new DownloadTask.DownloadListener() {
            @Override
            public void download(String status) {
                switch (status) {
                    case DownloadTask.DownloadStatus.DOWNLOAD_STARTED:
                        Utils.showToastMsg(Utils.getStringFromResources(R.string.download_file_start_lbl));

                        break;

                    case DownloadTask.DownloadStatus.DOWNLOAD_COMPLETED:
                        openDownloadedFolder();
                        break;

                    case DownloadTask.DownloadStatus.DOWNLOAD_FAILED:
                        Utils.showToastMsg(Utils.getStringFromResources(R.string.download_file_failed_lbl));
                        showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_download_file_lbl), new ApiErrorDialogInterface() {
                            @Override
                            public void onCloseClick() {

                            }

                            @Override
                            public void retryClick() {
                                downloadTaskStart(downloadUrlStr);
                            }
                        });
                        break;
                }
            }
        });

        downloadTask.start();
    }


    private void openDownloadedFolder() {

        /*
        API >= 19 you can use
        Intent.ACTION_OPEN_DOCUMENT
        (or API >= 21 - Intent.ACTION_OPEN_DOCUMENT_TREE)
         */
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        /*Intent intent =null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        }
        else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }*/
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + File.separator+ Constants.APP_DIRECTORY_NAME+ File.separator );
        //intent.setDataAndType(uri, "resource/folder");
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "Open Download Folder"));
    }



    public interface DownloadPDFAsURIListener
    {
        void onComplete(Uri uri);
    }

    protected class DownloadPDFAsURI extends AsyncTask<String, Void, Uri> {

        private DownloadPDFAsURIListener listener;
        public DownloadPDFAsURI()
        {

        }

        public DownloadPDFAsURI(DownloadPDFAsURIListener listener)
        {
            this.listener=listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyApplication.showTransparentDialog();
        }


        @Override
        protected Uri doInBackground(String... strings) {
            MyDownloadfFile myDownloadfFile = new MyDownloadfFile();
            return Uri.fromFile(myDownloadfFile.downloadFile(strings[0]));
        }

        @Override
        protected void onPostExecute(Uri uri) {
            MyApplication.hideTransaprentDialog();
            super.onPostExecute(uri);
            showPDFInScreen(uri);
            if(listener!=null)
            {
                listener.onComplete(uri);
            }
        }
    }

    protected void showPDFInScreen(Uri uri)
    {

    }


}
