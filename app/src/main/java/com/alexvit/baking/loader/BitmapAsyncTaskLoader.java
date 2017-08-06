package com.alexvit.baking.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aleksandrs Vitjukovs on 8/6/2017.
 */

public class BitmapAsyncTaskLoader extends AsyncTaskLoader<Bitmap> {

    private String mUrl;
    private HttpURLConnection mConnection;
    private Bitmap mBitmap = null;

    public BitmapAsyncTaskLoader(Context context, String url) {
        super(context);

        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mBitmap != null) deliverResult(mBitmap);
        else forceLoad();
    }

    @Override
    public Bitmap loadInBackground() {
        Bitmap bitmap = null;
        try {
            URL url = new URL(mUrl);
            mConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(mConnection.getInputStream());
            bitmap = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mConnection != null) mConnection.disconnect();
        }
        return bitmap;
    }

    @Override
    public void deliverResult(Bitmap bitmap) {
        if (bitmap != null) mBitmap = bitmap;
        super.deliverResult(bitmap);
    }
}
