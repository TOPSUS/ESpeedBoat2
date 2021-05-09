package id.alin.espeedboat.MyFireBase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationFragment;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
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
            // BUAT INSTANCE DATABASE ESPEEDBOAT
            DatabaeESpeedboat databaeESpeedboat = DatabaeESpeedboat.createDatabase(getBaseContext());

            // CHECK DULU APAKAH ID YANG DARI SERVER TELAH ADA DI DALAM SISTEM
            NotificationEntity checkNotification = databaeESpeedboat
                                                    .notificationDAO()
                                                        .getNotificationEntityById(Long.valueOf(remoteMessage.getData().get("id")));

            // KALAU TIDAK ADA MAKA BISA DISIMPAN
            if(checkNotification == null){
                // BUAT OBJECT NOTIFICATION ENTITY
                NotificationEntity notificationEntity = new NotificationEntity();
                notificationEntity.setId_server_notification(Long.parseLong(remoteMessage.getData().get("id")));
                notificationEntity.setTitle(remoteMessage.getData().get("title"));
                notificationEntity.setMessage(remoteMessage.getData().get("body"));
                notificationEntity.setType(Short.parseShort(remoteMessage.getData().get("type")));
                notificationEntity.setStatus(Short.parseShort(remoteMessage.getData().get("status")));
                notificationEntity.setCreated_at(remoteMessage.getData().get("created_at"));
                notificationEntity.setNotification_by(Short.parseShort(remoteMessage.getData().get("notification_by")));

                // SIMPAN KE DALAM ROOM BERUPA DATA BARU
                databaeESpeedboat.notificationDAO().insertNotification(notificationEntity);

                // MENAMBAHKAN DATA KE DALAM VIEW MODEL KALAU SUDAH ACTIVE
                if(NotificationFragment.notificationViewModel != null){
                    NotificationFragment.notificationViewModel.setNotificationData(
                            databaeESpeedboat.notificationDAO().getAllNotificationEntity()
                    );
                }
            }else{
                // APABILA NOTIFIKASI DENGAN ID YANG SAMA TELAH ADA MAKA NOTIFICATION AKAN DIUPDATE
                checkNotification.setTitle(remoteMessage.getData().get("title"));
                checkNotification.setMessage(remoteMessage.getData().get("body"));
                checkNotification.setType(Short.parseShort(remoteMessage.getData().get("type")));
                checkNotification.setStatus(Short.parseShort(remoteMessage.getData().get("status")));
                checkNotification.setCreated_at(remoteMessage.getData().get("created_at"));
                checkNotification.setNotification_by(Short.parseShort(remoteMessage.getData().get("notification_by")));

                // UPDATE KE DALAM ROOM BERUPA DATA BARU
                databaeESpeedboat.notificationDAO().updateNotification(checkNotification);

                // GET ALL NOTIFICATION FROM DATABASE

                // MENAMBAHKAN DATA KE DALAM VIEW MODEL KALAU SUDAH ACTIVE
                if(NotificationFragment.notificationViewModel != null){
                    NotificationFragment.notificationViewModel.setNotificationData(
                            databaeESpeedboat.notificationDAO().getAllNotificationEntity()
                    );
                }
            }


        }catch (Exception ignored){}

    }
}
