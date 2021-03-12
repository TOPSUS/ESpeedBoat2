package id.alin.espeedboat.MyRoom.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;

@Dao
public interface BeritaPelabuhanDAO {

    @Insert
    Long insertBeritaPelabuhan(BeritaPelabuhanEntity entity);

    @Query("SELECT * FROM BeritaPelabuhanEntity")
    List<BeritaPelabuhanEntity> getAllBeritaPelabuhanEntity();

}
