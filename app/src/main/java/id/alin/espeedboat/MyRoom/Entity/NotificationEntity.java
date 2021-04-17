package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NotificationEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long id_server_notification;
    private String title;
    private String message;
    private short notification_by;
    private short status;
    private short type;
    private String created_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_server_notification() {
        return id_server_notification;
    }

    public void setId_server_notification(long id_server_notification) {
        this.id_server_notification = id_server_notification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public short getNotification_by() {
        return notification_by;
    }

    public void setNotification_by(short notification_by) {
        this.notification_by = notification_by;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
