package com.centerstage.limelight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * Created by Smitesh on 7/8/2015.
 * General Utilities.
 */
public class Utils {

    // Converts text to a bitmap drawable that can placed in an ImageView
    public static BitmapDrawable textAsBitmapDrawable(Context context, String text, float textSize, int textColor, int imageWidth, int imageHeight) {
        TextPaint paint = new TextPaint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setFakeBoldText(true);

        Bitmap image;
        if (imageWidth > 0 && imageHeight > 0) {
            image = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888);
        } else {
            return null;
        }

        Canvas canvas = new Canvas(image);
        StaticLayout textLayout = new StaticLayout(text, paint, imageWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(0, imageHeight/2.5f);
        textLayout.draw(canvas);
        canvas.restore();

        return new BitmapDrawable(context.getResources(), image);
    }

    // Checks whether an internet connection is available
    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
