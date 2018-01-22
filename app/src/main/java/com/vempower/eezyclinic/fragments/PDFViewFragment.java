package com.vempower.eezyclinic.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.APICore.PDFDetails;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.DownloadTask;
import com.vempower.eezyclinic.utils.MyDownloadfFile;
import com.vempower.eezyclinic.utils.PrintPDF;
import com.vempower.eezyclinic.utils.Utils;



/**
 * Created by satish on 6/12/17.
 */

public class PDFViewFragment extends AbstractFragment implements
        OnPageChangeListener,
        OnLoadCompleteListener,
        OnPageErrorListener {

    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int PERMISSION_CODE = 42842;
    private LinearLayout delete_bottom_linear, download_bottom_linear,
            print_bottom_linear, edit_bottom_linear;


    private View fragmentView;
    private com.vempower.eezyclinic.APICore.PDFDetails PDFDetails;
    private Uri showUri;
    private int pageNumber = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.pdf_viewer_layout, container, false);

        myInit1();


        return fragmentView;
    }

    private void myInit1() {
        delete_bottom_linear = getFragemtView().findViewById(R.id.delete_bottom_linear);
        download_bottom_linear = getFragemtView().findViewById(R.id.download_bottom_linear);
        print_bottom_linear = getFragemtView().findViewById(R.id.print_bottom_linear);
        edit_bottom_linear = getFragemtView().findViewById(R.id.edit_bottom_linear);


        download_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PDFDetails!=null)
                {
                    if(PDFDetails instanceof PrescriptionAPIData)
                    {
                        Utils.showToastMsg("PrescriptionAPIData");
                    }else if(PDFDetails instanceof HelathReportsData)
                    {
                        Utils.showToastMsg("HelathReportsData");
                    }
                    return;
                }
                if (PDFDetails != null) {
                    downloadTaskStart(PDFDetails.getDowloadzip());
                }
            }
        });
        print_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showUri != null) {
                    printPDF(showUri);

                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermission();
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );

            return;
        }
        callPDFViwer();
    }


    private void callPDFViwer() {
        if (PDFDetails == null || TextUtils.isEmpty(PDFDetails.getPrintpdf())) {

            showAlertDialog("Alert", "Invalid PDF details", false);
            return;
        }


        // String urlStr = "http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf";
        new DownloadPDF().execute(PDFDetails.getPrintpdf());
        // Uri uri= Utils.getURIfromURL(urlStr);


    }

    public void setPDFDetails(PDFDetails PDFDetails) {
        this.PDFDetails = PDFDetails;
    }


    private class DownloadPDF extends AsyncTask<String, Void, Uri> {

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
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPDFViwer();
            } else {
                getActivity().finish();
            }

        }
    }

    private void showPDFInScreen(Uri uri) {


        com.github.barteksc.pdfviewer.PDFView pdfView = getFragemtView().findViewById(R.id.pdfView);

        if (uri != null) {
            this.showUri = uri;
            pdfView.fromUri(uri)
                    //pdfView.fromAsset("sample1.pdf")
                    //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(pageNumber)
                    // allows to draw something on the current page, usually visible in the middle of the screen
                    //.onDraw(onDrawListener)
                    // allows to draw something on all pages, separately for every page. Called only for visible pages
                    //.onDrawAll(onDrawListener)
                    .onLoad(this) // called after document is loaded and starts to be rendered
                    .onPageChange(this)
                    // .onPageScroll(this)
                    // .onError(this)
                    .onPageError(this)
                    // .onRender(onRenderListener) // called after document is rendered for the first time
                    // called on single tap, return true if handled, false to toggle scroll handle visibility
                    // .onTap(this)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    //  .linkHandler(DefaultLinkHandler)
                    //.pageFitPolicy(FitPolicy.WIDTH)
                    .load();
        }
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        //Utils.showToastMsg("pageNumber " + pageCount);
        pageNumber = page;
        // setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e("PDFViewFragment", "Cannot load page " + page);
    }


    private void printPDF(Uri uri) {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            PrintPDF printPDF = new PrintPDF(uri);
            printPDF.printPreview();
        }else
        {
            Utils.showToastMsg(Utils.getStringFromResources(R.string.printing_not_supported_device_lbl));
        }

    }


    private void downloadTaskStart(final String downloadUrlStr) {

        if (TextUtils.isEmpty(downloadUrlStr)) {
            return;
        }
        DownloadTask downloadTask = new DownloadTask(downloadUrlStr, new DownloadTask.DownloadListener() {
            @Override
            public void download(String status) {
                switch (status) {
                    case DownloadTask.DownloadStatus.DOWNLOAD_STARTED:
                        Utils.showToastMsg("Download started");

                        break;

                    case DownloadTask.DownloadStatus.DOWNLOAD_COMPLETED:
                        openDownloadedFolder();
                        break;

                    case DownloadTask.DownloadStatus.DOWNLOAD_FAILED:
                        Utils.showToastMsg("Download failed");
                        showMyDialog("Alert", "Unable to download file,Please try again", new ApiErrorDialogInterface() {
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

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + "/" + Constants.APP_DIRECTORY_NAME);
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "Open Download Folder"));
    }


}
