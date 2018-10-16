package com.example.asus.katalogfilmku.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.asus.katalogfilmku.BuildConfig;
import com.example.asus.katalogfilmku.DetailActivity;
import com.example.asus.katalogfilmku.R;
import com.example.asus.katalogfilmku.model.FilmItems;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class GetReleaseJobService extends JobService {
    public static final String TAG = GetReleaseJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob() Executed");
        getCurrentMovie(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob() Executed");
        return true;
    }

    private void getCurrentMovie(final JobParameters job) {
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + BuildConfig.API_KEY + "&language=en-US";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final Date date = new Date();
        final String now = dateFormat.format(date);
        Log.e(TAG, "getCurrenMovie: " + url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);

                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject film = list.getJSONObject(i);
                        FilmItems mFilm = new FilmItems(film);
                        String title = mFilm.getTitle();
                        String message = getString(R.string.release_reminder) +" ";
                        int notifId = mFilm.getId();
                        if (mFilm.getRelease_date().equals(now)) {
                            showNotification(getApplicationContext(), title,
                                    message + title + " " +getString(R.string.release_today), notifId, mFilm);
                        }
                    }

                    jobFinished(job, false);
                } catch (Exception e) {
                    jobFinished(job, true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                jobFinished(job, true);
            }
        });
    }

    private void showNotification(Context context, String title, String message, int notifId, FilmItems mfilmItems) {
        Intent DataIntent = new Intent(context, DetailActivity.class);
        DataIntent.putExtra(DetailActivity.EXTRA_FILM,mfilmItems);
        DataIntent.putExtra(DetailActivity.KATA,mfilmItems.getTitle());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, DataIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_movie_creation_black_24dp)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
