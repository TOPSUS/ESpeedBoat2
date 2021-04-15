package id.alin.espeedboat.MyFireBase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationFragment;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.MyViewModel.NotificationViewModel.NotificationViewModel;
import id.alin.espeedboat.MyViewModel.NotificationViewModel.NotificationViewModelFactory;
import id.alin.espeedboat.R;
import id.alin.espeedboat.SplashScreenActivity;

public class MyFireBaseCloudMessaging extends FirebaseMessagingService {
    public static final String channelID = "default";
    public static final String BROADCAST = "ALL";
    private String TAG = "apekaden";


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // MENAMPILKAN NOTIFICATION
        showNotification(remoteMessage);

        // MELAKUKAN THREADING UNTUK MENYIMPAN NOTIFICATION
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                safeNotification(remoteMessage);
            }
        });
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG,s);
    }

    private void showNotification(RemoteMessage remoteMessage){
        Intent intent = new Intent(this, SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelID,"default",NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        manager.notify(0,builder.build());
    }

    private void safeNotification(RemoteMessage remoteMessage){
        try {
            // CHECK DULU APAKAH ID YANG DARI SERVER TELAH ADA DI DALAM SISTEM
            NotificationEntity checkNotification = DatabaeESpeedboat.createDatabase(getBaseContext())
                                                    .notificationDAO()
                                                        .getNotificationEntityById(Long.valueOf(remoteMessage.getData().get("id")));

            // KALAU TIDAK ADA MAKA BISA DISIMPAN
            if(checkNotification == null){
                // SIMPAN KE DALAM ROOM
                NotificationEntity notificationEntity = new NotificationEntity();
                notificationEntity.setId_server_notification(Long.parseLong(remoteMessage.getData().get("id")));
                notificationEntity.setTitle(remoteMessage.getData().get("title"));
                notificationEntity.setMessage(remoteMessage.getData().get("body"));
                notificationEntity.setStatus(remoteMessage.getData().get("status"));
                notificationEntity.setType(Short.parseShort(remoteMessage.getData().get("type")));
                notificationEntity.setCreated_at(remoteMessage.getData().get("created_at"));
                notificationEntity.setNotification_by(remoteMessage.getData().get("notification_by"));
                DatabaeESpeedboat.createDatabase(getBaseContext()).notificationDAO().insertNotification(notificationEntity);

                // MENAMBAHKAN DATA KE DALAM VIEW MODEL KALAU SUDAH ACTIVE
                if(NotificationFragment.notificationViewModel != null){
                    NotificationFragment.notificationViewModel.addSingleNotificationData(notificationEntity);
                }
            }

        }catch (NullPointerException ignored){}

    }
}
