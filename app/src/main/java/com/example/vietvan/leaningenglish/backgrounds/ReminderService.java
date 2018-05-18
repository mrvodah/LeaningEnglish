package com.example.vietvan.leaningenglish.backgrounds;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.vietvan.leaningenglish.R;
import com.example.vietvan.leaningenglish.activities.MainActivity;

public class ReminderService extends Service {
    public ReminderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 
                0, 
                intent1, 
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_access_time_black_24dp)
                .setContentTitle("Time to study")
                .setContentText("Taps to open app")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        
        return START_NOT_STICKY;
    }
}
