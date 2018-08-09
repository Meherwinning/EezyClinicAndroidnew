package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/*import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;*/
import com.necistudio.vigerpdf.VigerPDF;
import com.necistudio.vigerpdf.adapter.VigerAdapter;
import com.necistudio.vigerpdf.manage.OnResultListener;
import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.APICore.PDFDetails;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.PDFViewActivity;
import com.vempower.eezyclinic.activities.UpdatePrescriptionReportActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.DeleteHealthRecordMapper;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.vempower.eezyclinic.activities.PDFViewActivity.MEDICAL_RECORDS_REFRESH_REQUEST_CODE;


/**
 * Created by satish on 6/12/17.
 */



public class PDFViewFragment extends AbstractFragment /*implements
        OnPageChangeListener,
        OnLoadCompleteListener,
        OnPageErrorListener*/ {

    private ViewPager viewPager;
    private ArrayList<Bitmap> itemData;
    private VigerAdapter adapter;
    private Button btnFromFile, btnFromNetwork,btnCancle;
    private VigerPDF vigerPDF;



    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final int PERMISSION_CODE = 42842;
    private LinearLayout delete_bottom_linear, download_bottom_linear,
            print_bottom_linear, edit_bottom_linear;


    private View fragmentView;
    private com.vempower.eezyclinic.APICore.PDFDetails PDFDetails;
    private Uri showUri;
    private int pageNumber = 1;
    private SuccussUpdateListener succussUpdateListener;

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
//DeleteHealthRecordMapper
        viewPager = (ViewPager) getFragemtView().findViewById(R.id.viewPager);
        vigerPDF = new VigerPDF(MyApplication.getCurrentActivityContext());
        itemData = new ArrayList<>();
        adapter = new VigerAdapter(MyApplication.getCurrentActivityContext(), itemData);
        viewPager.setAdapter(adapter);

        delete_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=null;
                if(PDFDetails!=null)
                {
                    if(PDFDetails instanceof PrescriptionAPIData)
                    {
                        PrescriptionAPIData prescriptionAPIData= (PrescriptionAPIData) PDFDetails;
                        id= prescriptionAPIData.getId();
                        //Utils.showToastMsg("PrescriptionAPIData - Edit");
                    }else if(PDFDetails instanceof HelathReportsData)
                    {
                        HelathReportsData reportsData= (HelathReportsData) PDFDetails;
                        id=reportsData.getId();
                        // Utils.showToastMsg("HelathReportsData -Edit");
                    }
                }

                if(TextUtils.isEmpty(id))
                {
                    showAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_pdf_details_lbl), false);
                    return;

                }


                final String myId=id;
                showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_record_lbl), "Yes", "No", new MyDialogInterface() {
                    @Override
                    public void onPossitiveClick() {
                        callDeleteRecordMapper(myId);
                    }

                    @Override
                    public void onNegetiveClick() {

                    }
                });

            }
        });

        download_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(PDFDetails!=null)
                {
                    if(PDFDetails instanceof PrescriptionAPIData)
                    {
                        Utils.showToastMsg("PrescriptionAPIData");
                    }else if(PDFDetails instanceof HelathReportsData)
                    {
                        Utils.showToastMsg("HelathReportsData");
                    }
                    return;
                }*/
                if (PDFDetails != null) {
                    downloadTaskStart(PDFDetails.getPrintpdf());
                }
            }
        });
        print_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PDFDetails != null) {

                new DownloadPDFAsURI(new DownloadPDFAsURIListener() {
                    @Override
                    public void onComplete(Uri uri) {
                        printPDF(uri);
                    }
                }).execute(PDFDetails.getPrintpdf());


                }
            }
        });

        edit_bottom_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=  new Intent(MyApplication.getCurrentActivityContext(),UpdatePrescriptionReportActivity.class);
                //((Activity) MyApplication.getCurrentActivityContext()).getIntent();
                intent.putExtra(ListenerKey.ObjectKey.PDF_DETAILS_OBJECT_KEY,new Messenger(new AbstractIBinder(){
                    @Override
                    protected IntentObjectListener getMyObject() {
                        return new IntentObjectListener(){

                            @Override
                            public Object getObject() {
                                return PDFDetails;
                            }
                        };
                    }
                }));

            /*    intent.putExtra(Constants.Pref.IS_FROM_UPDATE_PRESCRIPTION_KEY,true);
*/
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivityForResult(intent, MEDICAL_RECORDS_REFRESH_REQUEST_CODE);
                /*if(PDFDetails!=null)
                {
                    if(PDFDetails instanceof PrescriptionAPIData)
                    {
                        PrescriptionAPIData prescriptionAPIData= (PrescriptionAPIData) PDFDetails;
                        Utils.showToastMsg("PrescriptionAPIData - Edit");
                    }else if(PDFDetails instanceof HelathReportsData)
                    {
                        HelathReportsData reportsData= (HelathReportsData) PDFDetails;

                        Utils.showToastMsg("HelathReportsData -Edit");
                    }
                    return;
                }*/
            }
        });

    }

    private void fromNetwork(String endpoint) {
        itemData.clear();
        adapter.notifyDataSetChanged();
        vigerPDF.cancle();
        MyApplication.showTransparentDialog();
        vigerPDF.initFromNetwork(endpoint, new OnResultListener() {
            @Override
            public void resultData(Bitmap data) {
                MyApplication.hideTransaprentDialog();
                Log.e("data", "run");
                itemData.add(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void progressData(int progress) {
                Log.e("data", "" + progress);
            }

            @Override
            public void failed(Throwable t) {
                showAlertDialog("Alert","Download failed",true);
                MyApplication.hideTransaprentDialog();
            }

        });
    }

    private void callDeleteRecordMapper(String id) {
        DeleteHealthRecordMapper mapper= new DeleteHealthRecordMapper(id);
        mapper.setOnDeleteHealthRecordListener(new DeleteHealthRecordMapper.DeleteHealthRecordListener() {
            @Override
            public void deleteHealthRecord(AbstractResponse response, String errorMessage) {
                if(!isValidResponse(response,errorMessage,true,false))
                {
                    return;
                }
                if(succussUpdateListener!=null)
                {
                    succussUpdateListener.status(true);
                }
                Utils.showToastMsg(response.getStatusMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==MEDICAL_RECORDS_REFRESH_REQUEST_CODE)
            {

                if(succussUpdateListener!=null)
                {
                    succussUpdateListener.status(true);
                }
            }
        }
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

            showAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_pdf_details_lbl), false);
            return;
        }

        itemData.clear();
        adapter.notifyDataSetChanged();
        fromNetwork(PDFDetails.getPrintpdf());

        // String urlStr = "http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf";
        //new DownloadPDFAsURI().execute(PDFDetails.getPrintpdf());
        // Uri uri= Utils.getURIfromURL(urlStr);


    }

    public void setPDFDetails(PDFDetails PDFDetails) {
        this.PDFDetails = PDFDetails;
    }

    public interface SuccussUpdateListener
    {
        void status(boolean isSuccess);
    }

    public void setOnSuccuessUpdateListener(SuccussUpdateListener succussUpdateListener) {
        this.succussUpdateListener = succussUpdateListener;
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

    protected void showPDFInScreen(Uri uri) {


       /* com.github.barteksc.pdfviewer.PDFView pdfView = getFragemtView().findViewById(R.id.pdfView);

        if (uri != null) {
            this.showUri = uri;
           // DefaultScrollHandle handle= new DefaultScrollHandle(MyApplication.getCurrentActivityContext());
           // handle.setupLayout(pdfView);
            //handle.setScroll(0);
           // DefaultScrollHandle handle=
            //handle.setScroll(0);
            pdfView.fromUri(uri)
                    //pdfView.fromAsset("sample1.pdf")
                    //.pages(0, 2, 1, 3, 4, 5) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    //.defaultPage(0)

                    .defaultPage(0)
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
                    .scrollHandle(new DefaultScrollHandle((PDFViewActivity)MyApplication.getCurrentActivityContext()))
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(5)
                    //  .linkHandler(DefaultLinkHandler)
                    .pageFitPolicy(FitPolicy.BOTH)

                    .load();
        }*/
    }



    @Override
    View getFragemtView() {
        return fragmentView;
    }

  /*  @Override
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
    }*/





}
