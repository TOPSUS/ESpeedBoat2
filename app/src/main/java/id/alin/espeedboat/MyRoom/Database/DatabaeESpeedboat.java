package id.alin.espeedboat.MyRoom.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.alin.espeedboat.MyRoom.DAO.BeritaEspeedDAO;
import id.alin.espeedboat.MyRoom.DAO.BeritaPelabuhanDAO;
import id.alin.espeedboat.MyRoom.DAO.JadwalDAO;
import id.alin.espeedboat.MyRoom.DAO.NotificationDAO;
import id.alin.espeedboat.MyRoom.DAO.PelabuhanDAO;
import id.alin.espeedboat.MyRoom.Entity.BeritaEspeedEntity;
import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;
import id.alin.espeedboat.MyRoom.Entity.MetodePembayaranEntity;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;

@Database(entities = {
        BeritaPelabuhanEntity.class,
        BeritaEspeedEntity.class,
        PelabuhanEntity.class,
        JadwalEntity.class,
        MetodePembayaranEntity.class,
        NotificationEntity.class
},version = 38,exportSchema = false)
public abstract class DatabaeESpeedboat extends RoomDatabase {
    public abstract BeritaPelabuhanDAO beritaPelabuhanDAO();
    public abstract BeritaEspeedDAO beritaEspeedDAO();
    public abstract PelabuhanDAO pelabuhanDAO();
    public abstract JadwalDAO jadwalDAO();
    public abstract NotificationDAO notificationDAO();

    private static DatabaeESpeedboat INSTANCE;

    public static DatabaeESpeedboat createDatabase(Context context){
        synchronized (DatabaeESpeedboat.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,DatabaeESpeedboat.class,"db_espeedboat")
                                    .allowMainThreadQueries()
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
        }
        return INSTANCE;
    }
}
