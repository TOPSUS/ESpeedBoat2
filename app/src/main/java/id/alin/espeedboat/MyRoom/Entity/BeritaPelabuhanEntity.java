package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BeritaPelabuhanEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public long id_pelabuhan;
    public long id_user;
    public String judul;
    public String berita;
    public String foto;
    public String tanggal;

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

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getBerita() {
        return berita;
    }

    public void setBerita(String berita) {
        this.berita = berita;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
