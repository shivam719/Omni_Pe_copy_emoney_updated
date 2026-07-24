package com.roundpay.imagepicker;

import android.annotation.SuppressLint;
import android.content.Intent;

import java.io.File;

public interface ImagePickerContract {
    @SuppressWarnings("UnusedReturnValue")
    ImagePicker setWithImageCrop(int aspectRatioX, int aspectRatioY);

    ImagePicker setWithImageCrop();

    //    ImagePicker setWithIntentPickerTitle(String title);
//    ImagePicker setWithIntentPickerTitle(@StringRes int title);
    void choosePictureWithoutPermission(boolean includeCamera,boolean includeGallery);

    @SuppressLint("NewApi")
    void choosePicture(boolean includeCamera);

    void choosePicture(boolean includeCamera, boolean includeGallery);

    void openCamera();

    File getImageFile();

    void handlePermission(int requestCode, int[] grantResults);

    void handleActivityResult(int resultCode, int requestCode, Intent data);
}
