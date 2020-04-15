package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.DetailMovie;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.R;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.ResultsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.DetailMovie.EXTRA_MOVIE;

public class MovieUpcomingReceiver extends BroadcastReceiver {
    private static int mNotifId = 2000;
    private List<ResultsItem> MovieResults =  new ArrayList<>();
    private RequestQueue mRequestQueue;
    List<ResultsItem> mNotifList;

    public class GetMovieTask extends AsyncTask<String, Void, Void> {

        private Context context;

        public GetMovieTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... strings) {
            getData(context, strings[0]);
            return null;
        }
    }



    public void getData(final Context context, String url) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String today = dateFormat.format(date);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        ResultsItem movieItem = new ResultsItem();
                        movieItem.setTitle(data.getString("title"));
                        movieItem.setOverview(data.getString("overview"));
                        movieItem.setPosterPath(data.getString("poster_path"));
                        if (data.getString("release_date").equals(today)) {
                            mNotifList.add(movieItem);
                        }
                    }

                    String mDesc = context.getString(R.string.today_release);
                    ResultsItem mMovieResult = new ResultsItem();
                    sendNotification(context, context.getString(R.string.app_name), mDesc, mNotifId, mMovieResult, mNotifList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
//
    private void setReleaseAlarm(Context context) {
        MovieUpcomingReceiver.GetMovieTask getDataAsync = new MovieUpcomingReceiver.GetMovieTask(context);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String tanggal = dateFormat.format(date);
        getDataAsync.execute("https://api.themoviedb.org/3/discover/movie?api_key=5e0981d8c0cd0848bf351ca105a5974f&primary_release_date.gte=" + tanggal + "&primary_release_date.lte=" + tanggal);
    }



    private void sendNotification(Context context, String title, String mDesc, int id, ResultsItem mMovieResult, List<ResultsItem> MovieResults) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        int size = MovieResults.size();

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (ResultsItem movie : MovieResults) {

            inboxStyle.addLine(context.getString(R.string.today_release) + " " + movie.getTitle());
        }

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(mDesc)
                .setStyle(inboxStyle)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("11011",
                    "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            builder.setChannelId("11011");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(id, builder.build());
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, MovieUpcomingReceiver.class);
        return PendingIntent.getBroadcast(context, 1011, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }



    public void setAlarm(Context context, List<ResultsItem> mMovieResults) {
        int delay = 0;
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, MovieUpcomingReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay, pendingIntent);
            }
            mNotifId += 1;
        Toast.makeText(context, String.valueOf(R.string.up_notif_on), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onReceive(Context context, Intent intent) {

          mNotifList = new ArrayList<>();
          mRequestQueue = Volley.newRequestQueue(context);
          setReleaseAlarm(context);

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
        Toast.makeText(context, String.valueOf(R.string.up_notif_off), Toast.LENGTH_SHORT).show();
    }
}
