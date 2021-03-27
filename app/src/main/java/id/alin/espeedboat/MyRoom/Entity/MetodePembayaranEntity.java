package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MetodePembayaranEntity {

    @PrimaryKey
    private long id;
    private String metode;
    private String nama_metode;
    private String deskripsi_metode;
    private String nomor_rekening;
    private String logo_metode;
    private long payment_limit;
    private String created_at;
    private String updated_at;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public String getNama_metode() {
        return nama_metode;
    }

    public void setNama_metode(String nama_metode) {
        this.nama_metode = nama_metode;
    }

    public String getNomor_rekening() {
        return nomor_rekening;
    }

    public void setNomor_rekening(String nomor_rekening) {
        this.nomor_rekening = nomor_rekening;
    }

    public String getLogo_metode() {
        return logo_metode;
    }

    public void setLogo_metode(String logo_metode) {
        this.logo_metode = logo_metode;
    }

    public long getPayment_limit() {
        return payment_limit;
    }

    public void setPayment_limit(long payment_limit) {
        this.payment_limit = payment_limit;
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

    public String getDeskripsi_metode() {
        return deskripsi_metode;
    }

    public void setDeskripsi_metode(String deskripsi_metode) {
        this.deskripsi_metode = deskripsi_metode;
    }
}
