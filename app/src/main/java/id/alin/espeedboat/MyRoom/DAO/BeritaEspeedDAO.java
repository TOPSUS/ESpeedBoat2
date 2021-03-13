package id.alin.espeedboat.MyRoom.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.BeritaEspeedEntity;
import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;

@Dao
public interface BeritaEspeedDAO {
    @Insert
    Long insertBeritaEspeed(BeritaPelabuhanEntity entity);

    @Query("SELECT * FROM BeritaEspeedEntity")
    List<BeritaEspeedEntity> getAllBeritaEspeed();

    @Query("DELETE FROM BeritaPelabuhanEntity")
    void truncateBeritaEspeed();
}

