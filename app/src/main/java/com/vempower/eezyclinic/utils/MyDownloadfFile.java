package com.vempower.eezyclinic.utils;

/**
 * Created by satish on 2/1/18.
 */


import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyDownloadfFile {

    TextView tv_loading;
  //  String dest_file_path = "test.pdf";
    int downloadedSize = 0, totalsize;
   // String download_file_url = "http://ilabs.uw.edu/sites/default/files/sample_0.pdf";
    float per = 0;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_loading = new TextView(this);
        setContentView(tv_loading);
        tv_loading.setGravity(Gravity.CENTER);
        tv_loading.setTypeface(null, Typeface.BOLD);
        downloadAndOpenPDF();
    }*/

    void downloadAndOpenPDF() {
        new Thread(new Runnable() {
            public void run() {
               // Uri path = Uri.fromFile(downloadFile(download_file_url));
               /* try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException e) {
                    tv_loading
                            .setError("PDF Reader application is not installed in your device");
                }*/
            }
        }).start();

    }

   public  File downloadFile(String dwnload_file_path) {

        if(TextUtils.isEmpty(dwnload_file_path))
        {
            return null;
        }
        String fileName=dwnload_file_path.substring(dwnload_file_path.lastIndexOf('/'),dwnload_file_path.length());
        File file = null;
        try {

            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            // connect
            urlConnection.connect();

            // set the path where we want to save the file
            File SDCardRoot1 = Environment.getExternalStorageDirectory();

            //Create directory
            File myDirectory= new File(SDCardRoot1+"/Eezyclinic");
            if(!myDirectory.exists())
            {
                if(!myDirectory.mkdir())
                {
                    Utils.showToastMsg("Unable to create directory");
                    return null;
                }
            }

            // create a new file, to save the downloaded file
            file = new File(myDirectory, fileName);

            FileOutputStream fileOutput = new FileOutputStream(file);

            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file which we are
            // downloading
            totalsize = urlConnection.getContentLength();
            setText("Starting PDF download...");

            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
                setText("Total PDF File size  : "
                        + (totalsize / 1024)
                        + " KB\n\nDownloading PDF " + (int) per
                        + "% complete");
            }
            // close the output stream when complete //
            fileOutput.close();
            setText("Download Complete. Open PDF Application installed in the device.");

        } catch (final MalformedURLException e) {
            setTextError("Some error occured. Press back and try again1.",
                    Color.RED);
        } catch (final IOException e) {
            setTextError("Some error occured. Press back and try again2.",
                    Color.RED);
        } catch (final Exception e) {
            setTextError(
                    "Failed to download image. Please check your internet connection.",
                    Color.RED);
        }
        return file;
    }

    void setTextError(final String message, final int color) {

        Log.i("Print_message",message);
       /* runOnUiThread(new Runnable() {
            public void run() {
                tv_loading.setTextColor(color);
                tv_loading.setText(message);
            }
        });*/

    }

    void setText(final String txt) {
        Log.i("Print_txt",txt);
        /*runOnUiThread(new Runnable() {
            public void run() {
                tv_loading.setText(txt);
            }
        });
*/
    }

}
