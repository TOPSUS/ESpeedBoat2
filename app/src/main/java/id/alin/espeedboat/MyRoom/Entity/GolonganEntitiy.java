package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GolonganEntitiy {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long id_pelabuhan;
    private String golongan;
    private String keterangan;
    private String harga;
    private String created_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_pelabuhan() {
        return id_pelabuhan;
    }

    public void setId_pelabuhan(long id_pelabuhan) {
        this.id_pelabuhan = id_pelabuhan;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
