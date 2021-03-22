package id.alin.espeedboat.MyRoom.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;

@Dao
public interface JadwalDAO {

    @Insert
    Long insertJadwalEntity(JadwalEntity entity);

    @Query("DELETE FROM JadwalEntity")
    void truncateJadwalEntity();

}
