package com.vempower.eezyclinic.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.vempower.eezyclinic.application.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.vempower.eezyclinic.utils.DownloadTask.DownloadStatus.DOWNLOAD_COMPLETED;
import static com.vempower.eezyclinic.utils.DownloadTask.DownloadStatus.DOWNLOAD_FAILED;
import static com.vempower.eezyclinic.utils.DownloadTask.DownloadStatus.DOWNLOAD_STARTED;

/**
 * Created by Sathish on 17/1/18.
 */
public class DownloadTask {

    private static final String TAG = "Download Task";
   // private Context context;
   // private Button buttonText;
    private String downloadUrl = "", downloadFileName = "";

    private DownloadListener downloadListener;

    public  interface DownloadStatus
    {
        String DOWNLOAD_STARTED="downloadStarted";
        String DOWNLOAD_COMPLETED="downloadCompleted";
        String DOWNLOAD_FAILED="downloadFailed";
        //String DOWNLOAD_AGAIN="downloadAgain";
    }

    public DownloadTask(String downloadUrl,DownloadListener downloadListener) {
       // this.context = context;
        this.downloadListener = downloadListener;
        this.downloadUrl = downloadUrl;

        downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'),downloadUrl.length());//downloadUrl);//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);


    }

    public void start()
    {
        //Start Downloading Task
        new DownloadingTask().execute();
    }


    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // buttonText.setEnabled(false);
            if(downloadListener!=null)
            downloadListener.download(DOWNLOAD_STARTED);//Set Button Text when download started

            MyApplication.showTransparentDialog();
        }

        @Override
        protected void onPostExecute(Void result) {
            MyApplication.hideTransaprentDialog();
            try {
                if (outputFile != null) {
                   // buttonText.setEnabled(true);
                    if(downloadListener!=null)
                    downloadListener.download(DOWNLOAD_COMPLETED);//If Download completed then change button text
                } else {
                    if(downloadListener!=null)
                    downloadListener.download(DOWNLOAD_FAILED);//If download failed change button text
                    /*new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //buttonText.setEnabled(true);
                            if(downloadListener!=null)
                            downloadListener.download(DOWNLOAD_AGAIN);//Change button text again after 3sec
                        }
                    }, 3000);*/

                    Log.e(TAG, DOWNLOAD_FAILED);

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs
                if(downloadListener!=null)
                downloadListener.download(DOWNLOAD_FAILED);
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // buttonText.setEnabled(true);
                        if(downloadListener!=null)
                        downloadListener.download(DOWNLOAD_AGAIN);
                    }
                }, 3000);*/
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }


                //Get File if SD card is present
               // if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(
                            Environment.getExternalStorageDirectory() + "/"
                                    + Constants.APP_DIRECTORY_NAME);
                /*} else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
*/
                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }

            return null;
        }


    }
    public interface DownloadListener
    {
        void download(String status);
    }
}
