package com.vempower.eezyclinic.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;


import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vempower.eezyclinic.BuildConfig;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.EditProfileActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by Satishk on 6/1/2017.
 */

public abstract class ImageProcessFragment extends AbstractFragment {

    private final int REQUEST_IMAGE_CAPTURE = 1111;
    private final int REQUEST_IMAGE_GALLERY = 2222;

    public static final int CODE_WRITE_SETTINGS_PERMISSION = 8364;

    private File file;
    private Uri imageUri;
    private File croppedfile;


    protected void callGallery() {

        if(checkPermissions())
        {
            callGallery1();
        }else
        {
            ActivityCompat.requestPermissions((EditProfileActivity)MyApplication.getCurrentActivityContext(), new String[]{Manifest.permission.WRITE_SETTINGS,Manifest.permission.CAMERA}, CODE_WRITE_SETTINGS_PERMISSION);

        }

       // checkMyPermissions(false);

    }


    protected void callCamera() {
        if(checkPermissions())
        {
            callCamera1();
        }else
        {
            ActivityCompat.requestPermissions((EditProfileActivity)MyApplication.getCurrentActivityContext(), new String[]{Manifest.permission.WRITE_SETTINGS,Manifest.permission.CAMERA}, CODE_WRITE_SETTINGS_PERMISSION);

        }
        //checkMyPermissions(true);
    }



    protected void callGallery1() {

        if (!checkPermissions()) {
            Utils.showToastMsg("Please try again");
            return;
        }

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,
                REQUEST_IMAGE_GALLERY);
    }

    protected  void checkMyPermissions(boolean isFromCamera)
    {
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(MyApplication.getCurrentActivityContext());
        } else {
            permission = ContextCompat.checkSelfPermission(MyApplication.getCurrentActivityContext(), Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            if(checkPermissions())
            {
                if(isFromCamera)
                {
                    callCamera1();
                }else
                {
                    callGallery1();
                }
            }
        }  else {
           /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, CODE_WRITE_SETTINGS_PERMISSION);

            } else {*/
            //ActivityCompat.requestPermissions(this, arrPerm.toArray(permissions), PERMISSION_REQUEST_CODE);

            ActivityCompat.requestPermissions((EditProfileActivity)MyApplication.getCurrentActivityContext(), new String[]{Manifest.permission.WRITE_SETTINGS}, CODE_WRITE_SETTINGS_PERMISSION);
           // ActivityCompat.requestPermissions();
            //}
        }
    }


    private void callCamera1() {
        if (!checkPermissions()) {
            Utils.showToastMsg("Please try again");
            return;
        }
        if (isSDCARDMounted()) {
                        /*
                         * create an instance of intent pass action
						 * android.media.action.IMAGE_CAPTURE as argument to
						 * launch camera
						 */
            Intent intent = new Intent(
                    "android.media.action.IMAGE_CAPTURE");
                        /* create instance of File with name img.jpg */
            file = new File(createAppFolder(),
                    System.currentTimeMillis() + ".jpg");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Uri fileUri=null;
            //https://inthecheesefactory.com/blog/how-to-share-access-to-file-with-fileprovider-on-android-nougat/en
            if (Utils.hasAbovMarshmallowe()) {

                fileUri= FileProvider.getUriForFile(MyApplication.getCurrentActivityContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        file);

            }else
            {
                fileUri= Uri.fromFile(file);

            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    // Uri.fromFile(file));
                    //     FileProvider.getUriForFile(MyApplication.getCurrentActivityContext(), MyApplication.getCurrentActivityContext().getApplicationContext().getPackageName() + ".provider", file));
                    fileUri);


            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        /*
                         * start activity for result pass intent as argument and
						 * request code
						 */
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

        } else {
            Utils.showToastMsg(R.string.you_need_to_insert_sd_card);
        }
    }


    private boolean isSDCARDMounted() {

        if (!(ContextCompat.checkSelfPermission(MyApplication.getCurrentActivityContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)) {
            return false;
        }
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(!checkPermissions())
        {
            return;
        }

//        if (resultCode != RESULT_OK) {
//            if (file != null && file.exists()) {
//                file.delete();
//            }
//            file = null;
//            return;
//
//        }

        // Log.d(TAG, "onActivity: " + requestCode + " : " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            computeImageFromActivityResult(requestCode, resultCode, data);
        } else {
            Log.v("", "OnActivityResult: " + resultCode);
        }
    }

    private void computeImageFromActivityResult(int requestCode,
                                                int resultCode, Intent data) {

        switch (requestCode) {

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (!croppedfile.exists()) {
                    try {
                        croppedfile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (resultCode == Activity.RESULT_OK) {
                    // setImage(FileProvider.getUriForFile(MyApplication.getCurrentActivityContext(), MyApplication.getCurrentActivityContext().getApplicationContext().getPackageName() + ".provider", croppedfile));
                    //setImage(Uri.fromFile((croppedfile)));
                    setImage(croppedfile);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Utils.showToastMsg(R.string.image_crop_error);
                }

                break;

            case REQUEST_IMAGE_GALLERY:

                Uri selectedImage = data.getData();
                // doCrop(selectedImage);
                cropCapturedImage(selectedImage);

                Log.v("", "selected Image: " + selectedImage);
                // callCropActivity(getPath(selectedImage), true);

                break;

            case REQUEST_IMAGE_CAPTURE:

                if (file == null || !file.exists()) {
                    return;
                }
                // create instance of File with same name we created before to get
                // image from storage
            /*
             * File file = new File(Environment.getExternalStorageDirectory() +
			 * File.separator + "img.jpg");
			 */// Crop the captured image using an other intent
                try {
                /* the user's device may not support cropping */
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //      cropCapturedImage(FileProvider.getUriForFile(MyApplication.getCurrentActivityContext(), MyApplication.getCurrentActivityContext().getApplicationContext().getPackageName() + ".provider", file));
                    cropCapturedImage(Uri.fromFile(file));
                } catch (ActivityNotFoundException aNFE) {
                    // display an error message if user device doesn't support
                    Utils.showToastMsg(R.string.sorry_your_device_doesnot_support_the_crop_action);
                   /* String errorMessage = getStringFromResouces(R.string.sorry_your_device_doesnot_support_the_crop_action);
                    Toast toast = Toast.makeText(this, errorMessage,
                            Toast.LENGTH_SHORT);
                    toast.show();*/
                }
                break;
        }

    }

    private void cropCapturedImage(Uri imageUri) {
        croppedfile = new File(createAppFolder(), System.currentTimeMillis() + ".jpg");
        if (!croppedfile.exists())
            try {
                croppedfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setOutputUri(Uri.fromFile(croppedfile))
                .start(getContext(), this);
                //    .setOutputUri(FileProvider.getUriForFile(MyApplication.getCurrentActivityContext(), MyApplication.getCurrentActivityContext().getApplicationContext().getPackageName() + ".provider", croppedfile))
                //.start((EditProfileActivity)MyApplication.getCurrentActivityContext());


       /* if (MyApplication.getCurrentActivityContext() instanceof CreateActionActivity) {
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    //.setAspectRatio(700, 332)
                    .setOutputUri(Uri.fromFile(croppedfile))
                    //    .setOutputUri(FileProvider.getUriForFile(MyApplication.getCurrentActivityContext(), MyApplication.getCurrentActivityContext().getApplicationContext().getPackageName() + ".provider", croppedfile))
                    .start(this);
        } else
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setOutputUri(Uri.fromFile(croppedfile))
                    //    .setOutputUri(FileProvider.getUriForFile(MyApplication.getCurrentActivityContext(), MyApplication.getCurrentActivityContext().getApplicationContext().getPackageName() + ".provider", croppedfile))
                    .start(this);*/

// for fragment (DO NOT use `getActivity()`)
        /*CropImage.activity(imageUri)
                .start(MyApplication.getCurrentActivityContext(), this);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //deleteImageFromSdCard();
    }

    private void deleteImageFromSdCard() {
        if (this.file != null && this.file.exists()) {
            file.delete();
            file = null;
        }
        if (this.croppedfile != null && this.croppedfile.exists()) {
            croppedfile.delete();
            croppedfile = null;
        }
    }

   /* protected File getImageFile()
    {
        if(imageUri==null)
        {
            return null;
        }
        return new File(imageUri.toString());
    }
*/

    protected Uri getImageUri() {
        return imageUri;
    }

    //protected abstract void setImage(Uri imageUri);
    protected abstract void setImage(File file);

    private File createAppFolder() {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + Constants.RECORD_FOLDER_NAME);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }
        return folder;
    }



  /*  @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkPermissions();
    }*/

    private boolean checkPermissions() {
        boolean isPermission=true;
        if (ActivityCompat.checkSelfPermission(MyApplication.getCurrentActivityContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Log.v("Permission", Manifest.permission.WRITE_EXTERNAL_STORAGE + "  Permission is revoked");
            //ActivityCompat.requestPermissions(this, new String[]{per}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions((AbstractActivity) MyApplication.getCurrentActivityContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_SETTINGS_PERMISSION);

            isPermission= false;
        }
       // else
        if (ActivityCompat.checkSelfPermission(MyApplication.getCurrentActivityContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            Log.v("Permission", Manifest.permission.CAMERA + "  Permission is revoked");
            //ActivityCompat.requestPermissions(this, new String[]{per}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions((AbstractActivity) MyApplication.getCurrentActivityContext(), new String[]{Manifest.permission.CAMERA}, CODE_WRITE_SETTINGS_PERMISSION);

            isPermission= false;
        }
        if(!isPermission)
        {
            return isPermission;
        }
        /*else
        {
            showAlertDialog(Utils.getStringFromResources(R.string.alert_lbl),Utils.getStringFromResources(R.string.write_on_sd_card_permission_required_msg),true);
        }*/
        /*if (ActivityCompat.checkSelfPermission(MyApplication.getCurrentActivityContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            Log.v("Permission", Manifest.permission.RECORD_AUDIO+"  Permission is revoked");
            //ActivityCompat.requestPermissions(this, new String[]{per}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions((AbstractActivity)MyApplication.getCurrentActivityContext(), new String[]{Manifest.permission.RECORD_AUDIO}, CODE_WRITE_SETTINGS_PERMISSION);
            return;
        }*//*else
        {
            showAlertDialog(Utils.getStringFromResources(R.string.alert_lbl),Utils.getStringFromResources(R.string.record_audio_permission_required_msg),true);

        }*/
        return true;
    }



}