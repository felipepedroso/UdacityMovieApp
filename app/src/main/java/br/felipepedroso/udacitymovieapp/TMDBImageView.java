package br.felipepedroso.udacitymovieapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by FelipeAugusto on 22/11/2015.
 */
public class TMDBImageView extends ImageView {

    private ProgressBar progressBar;

    public TMDBImageView(Context context) {
        super(context);
    }

    public TMDBImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TMDBImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setImageUrl(String url) {
        TMDBImageLoader task = new TMDBImageLoader();
        task.execute(url);
    }

    private class TMDBImageLoader extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            if (params == null || params.length < 1) {
                return null;
            }

            String imageUrl = params[0];
            try {
                URLConnection connection = (new URL(imageUrl)).openConnection();
                int fileLength = connection.getContentLength();

                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);

                byte[] buffer = new byte[1024];
                int length;
                long total = 0;

                while ((length = is.read(buffer)) != -1) {
                    total += length;
                    publishProgress(new Integer((int) total * 100 / fileLength));
                    baos.write(buffer, 0, length);
                }

                byte[] imageData = baos.toByteArray();
                return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (progressBar != null && values != null && values.length > 0) {
                progressBar.setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                setImageBitmap(bitmap);
                setVisibility(VISIBLE);

                if (progressBar != null) {
                    progressBar.setVisibility(GONE);
                }
            }
        }
    }
}
