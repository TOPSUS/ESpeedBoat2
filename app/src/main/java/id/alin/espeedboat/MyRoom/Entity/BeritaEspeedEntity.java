package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BeritaEspeedEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public long id_speedboat;
    public long id_user;
    public String judul;
    public String berita;
    public String tanggal;
    public String foto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_speedboat() {
        return id_speedboat;
    }

    public void setId_speedboat(long id_speedboat) {
        this.id_speedboat = id_speedboat;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
