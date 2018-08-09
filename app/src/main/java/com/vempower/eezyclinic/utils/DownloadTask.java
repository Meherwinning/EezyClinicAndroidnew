package com.vempower.eezyclinic.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.SystemClock.sleep;
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

        downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/')+1,downloadUrl.length());//downloadUrl);//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);


    }

    public void start()
    {
        //Start Downloading Task
        new DownloadingTask().execute();
    }

 /*   class DownloadMaterial extends AsyncTask<String, String, String> {

        CountDownTimer cdt;
        int id = i;
        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;

        @Override
        protected void onPreExecute() {
            *//**
             * Create custom Count Down Timer
             *//*
            cdt = new CountDownTimer(100 * 60 * 1000, 500) {
                public void onTick(long millisUntilFinished) {
                    mNotifyManager.notify(id, mBuilder.build());
                }

                public void onFinish() {
                    mNotifyManager.notify(id, mBuilder.build());
                }
            };
        }

        @Override
        protected String doInBackground(String... strings) {
            *//**
             * Start timer to update Notification
             * Set Progress to 20 after connection
             * Build Notification
             * Increment Progress
             * Download and Save file
             *//*
            try {
                mNotifyManager =
                        (NotificationManager) MyApplication.getCurrentActivityContext().getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(MyApplication.getCurrentActivityContext(),"Eezyclinic");
                mBuilder.setContentTitle("Downloading File")
                        .setContentText(file_name)
                        .setProgress(0, 100, false)
                        .setOngoing(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(Notification.PRIORITY_LOW);

                // Initialize Objects here
                publishProgress("5");
                mNotifyManager.notify(id, mBuilder.build());
                cdt.start();

                // Create connection here
                publishProgress("20");

                // Download file here
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) (20 + (total * 80 / fileLength)));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return "Failed";
            }
            return "Success";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            *//**
             * Update Download Progress
             *//*
            mBuilder.setContentInfo(values[0] + "%")
                    .setProgress(100, Integer.parseInt(values[0]), false);
        }

        @Override
        protected void onPostExecute(String s) {

            String title;
            if (s.equals("Success")) {
                title = "Downloaded";
            } else {
                title = "Error Occurred";
            }
            mBuilder.setContentTitle(title)
                    .setContentInfo("")
                    .setOngoing(false)
                    .setProgress(0, 0, false);
            cdt.onFinish();
            cdt.cancel();
        }
    }*/


    private class DownloadingTask extends AsyncTask<Void, String, Void> {

        File apkStorage = null;
        File outputFile = null;

        CountDownTimer cdt;
        int id = 634523;
        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // buttonText.setEnabled(false);
            if(downloadListener!=null)
            downloadListener.download(DOWNLOAD_STARTED);//Set Button Text when download started

            MyApplication.showTransparentDialog();


            /**
             * Create custom Count Down Timer
             */
            cdt = new CountDownTimer(100 * 60 * 1000, 500) {
                public void onTick(long millisUntilFinished) {
                    mNotifyManager.notify(id, mBuilder.build());
                }

                public void onFinish() {
                    mNotifyManager.notify(id, mBuilder.build());
                }
            };
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

            mBuilder.setContentTitle(downloadFileName)
                    .setContentInfo("")
                    .setOngoing(false)
                    .setProgress(0, 0, false);
            cdt.onFinish();
            cdt.cancel();


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
               // c .setRequestProperty("Accept-Encoding", "identity");
                c.setRequestProperty("Accept-Encoding", "identity");
                c.setRequestProperty("User-Agent", "Android");
                c.setRequestProperty("Connection", "close");
                //c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
               // c.setDoOutput(true);
                //c.connect();//connect the URL Connection
                c.setRequestMethod("GET");
                c.connect();

                int lenghtOfFile = c.getContentLength();

                final long length;
                String contentLength = c.getHeaderField("Content-Length");
                if (TextUtils.isEmpty(contentLength) || contentLength.equals("0") || contentLength
                        .equals("-1")) {
                    length = c.getContentLength();
                } else {
                    length = Long.parseLong(contentLength);
                }


               // int lenghtOfFile = url.getContentLength();
               //Long.parseLong(c.getHeaderField("Content-Length"));

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }

                mNotifyManager =
                        (NotificationManager) MyApplication.getCurrentActivityContext().getSystemService(Context.NOTIFICATION_SERVICE);
                mBuilder = new NotificationCompat.Builder(MyApplication.getCurrentActivityContext(),"Eezyclinic");
                mBuilder.setContentTitle("Downloading File")
                        .setContentText(downloadFileName)
                        .setProgress(0, 100, false)
                        .setOngoing(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(Notification.PRIORITY_LOW);

                // Initialize Objects here
                publishProgress("0");
                mNotifyManager.notify(id, mBuilder.build());
                cdt.start();


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
               // is.available()
                //int lenghtOfFile =  c.getContentLength();
                //long fileLength = c.getContentLengthLong();
                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                int total=0;
                while ((len1 = is.read(buffer)) != -1) {
                    total += len1;
                    Log.i("Download:","length:"+length);
                    publishProgress("" + ( ( (total * 100 / lenghtOfFile))));
                 //  Log.i("Download:","tot:"+total+" , len:"+fileLength);
                   if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
                   {
                       Log.i("Length:","File Length1 :"+c.getContentLengthLong());
                       //publishProgress("" + ( ( (total * 100 / c.getContentLengthLong()))));
                   }else
                   {
                       Log.i("Length:","File Length2 :"+(c.getContentLength()));
                       //publishProgress("" + ( ( (total * 100 / c.getContentLength()))));
                   }


                    fos.write(buffer, 0, len1);//Write new file
                    //Thread.sleep(2000);
                    //sleep(2000);
                }

                /*// Download file here
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) (20 + (total * 80 / fileLength)));
                    output.write(data, 0, count);
                }*/

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

        @Override
        protected void onProgressUpdate(String... values) {
            /**
             * Update Download Progress
             */
            Log.i("Download","Completed"+(values[0]+""));
            mBuilder.setContentInfo(values[0] + "%")
                    .setProgress(100, Integer.parseInt(values[0]), false);
        }


    }
    public interface DownloadListener
    {
        void download(String status);
    }
}
