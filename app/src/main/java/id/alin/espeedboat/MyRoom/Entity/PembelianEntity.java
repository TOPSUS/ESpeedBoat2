package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PembelianEntity {

    @PrimaryKey
    private long id;
    private long id_jadwal;
    private long id_user;
    private long id_metode_pembayaran;
    private String bukti;
    private String tanggal;
    private long total_harga;
    private String status;
    private String created_at;
    private String updated_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(long id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_metode_pembayaran() {
        return id_metode_pembayaran;
    }

    public void setId_metode_pembayaran(long id_metode_pembayaran) {
        this.id_metode_pembayaran = id_metode_pembayaran;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public long getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
