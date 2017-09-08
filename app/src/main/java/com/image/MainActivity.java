package com.image;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Change drawable to Bitmap
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),
                R.drawable.c);

        //Function resize image parameter( original image Bitmap type, width of template, height of template)
        resizeImage(bitmap,2000,800);

    }

    public Bitmap resizeImage(Bitmap image, int width, int height) {
        Bitmap result = null;
        Matrix m = new Matrix();
        Bitmap reImage = null;
        result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas comboImage = new Canvas(result);
        comboImage.drawColor(Color.WHITE);
        if(image.getWidth() > width  || image.getHeight() > height) {
            m.setRectToRect(new RectF(0, 0, image.getWidth(), image.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
            reImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, true);
            comboImage.drawBitmap(reImage, (width/2)-(reImage.getWidth()/2), (height/2)-(reImage.getHeight()/2), null);
        }
        else {
            comboImage.drawBitmap(image, (width/2)-(image.getWidth()/2), (height/2)-(image.getHeight()/2), null);
        }
        String tmpImg = String.valueOf(System.currentTimeMillis()) + ".jpg";
        OutputStream os = null;
        try {
          os = new FileOutputStream("/storage/emulated/0/Pictures/" + tmpImg);
            result.compress(Bitmap.CompressFormat.PNG, 100, os);
        } catch(IOException e) {
          Log.e("combineImages", "problem combining images", e);
        }
            return result;
    }
}
