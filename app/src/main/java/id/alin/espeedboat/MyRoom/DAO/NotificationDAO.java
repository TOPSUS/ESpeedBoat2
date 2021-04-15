package id.alin.espeedboat.MyRoom.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;

@Dao
public interface NotificationDAO {
    @Insert
    Long insertNotification(NotificationEntity entity);

    @Query("SELECT * FROM NotificationEntity WHERE id_server_notification = :id")
    NotificationEntity getNotificationEntityById(long id);

    @Query("SELECT * FROM NotificationEntity")
    List<NotificationEntity> getAllNotificationEntity();
}