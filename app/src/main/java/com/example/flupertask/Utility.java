package com.example.flupertask;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Utility {

    public Utility() {}

    public byte[] convertImageToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return  stream.toByteArray();
    }
}
