package id.alin.espeedboat.MyRoom.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;

@Dao
public interface NotificationDAO {
    @Insert
    Long insertNotification(NotificationEntity entity);

    @Query("SELECT * FROM NotificationEntity WHERE id_server_notification = :id")
    NotificationEntity getNotificationEntityById(long id);

    // MENGAMBIL SEMUA DATA NOTIFIKASI YANG BARU DAN BELUM DIHAPUS
    @Query("SELECT * FROM NotificationEntity ORDER BY id_server_notification DESC")
    List<NotificationEntity> getAllNotificationEntity();

    // UPDATE DATA NOTIFIKASI
    @Update
    void updateNotification(NotificationEntity... notificationEntities);

    // TRUNCATE NOTIFICATION
    @Query("DELETE FROM NotificationEntity")
    void truncateNotification();
}
