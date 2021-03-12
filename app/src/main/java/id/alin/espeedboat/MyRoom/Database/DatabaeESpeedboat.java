package id.alin.espeedboat.MyRoom.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.alin.espeedboat.MyRoom.DAO.BeritaPelabuhanDAO;
import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;

@Database(entities = {BeritaPelabuhanEntity.class},version = 2,exportSchema = false)
public abstract class DatabaeESpeedboat extends RoomDatabase {
    public abstract BeritaPelabuhanDAO beritaPelabuhanDAO();

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