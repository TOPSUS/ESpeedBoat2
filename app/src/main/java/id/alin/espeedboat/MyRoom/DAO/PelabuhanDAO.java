package id.alin.espeedboat.MyRoom.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;

@Dao
public interface PelabuhanDAO {
    @Insert
    Long insertDataPelabuhan(PelabuhanEntity pelabuhanEntity);

    @Query("SELECT * FROM PelabuhanEntity")
    List<PelabuhanEntity> getAllPelabuhan();

    @Query("DELETE FROM PelabuhanEntity")
    void truncatePelabuhanEntity();
}
