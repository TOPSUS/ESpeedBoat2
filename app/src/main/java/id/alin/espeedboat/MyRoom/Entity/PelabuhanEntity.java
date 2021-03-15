package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PelabuhanEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String kode_pelabuhan;
    private String nama_pelabuhan;
    private String lokasi_pelabuhan;
    private String alamat_kantor;
    private String latitude;
    private String longtitude;
    private String lama_beroperasi;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKode_pelabuhan() {
        return kode_pelabuhan;
    }

    public void setKode_pelabuhan(String kode_pelabuhan) {
        this.kode_pelabuhan = kode_pelabuhan;
    }

    public String getNama_pelabuhan() {
        return nama_pelabuhan;
    }

    public void setNama_pelabuhan(String nama_pelabuhan) {
        this.nama_pelabuhan = nama_pelabuhan;
    }

    public String getLokasi_pelabuhan() {
        return lokasi_pelabuhan;
    }

    public void setLokasi_pelabuhan(String lokasi_pelabuhan) {
        this.lokasi_pelabuhan = lokasi_pelabuhan;
    }

    public String getAlamat_kantor() {
        return alamat_kantor;
    }

    public void setAlamat_kantor(String alamat_kantor) {
        this.alamat_kantor = alamat_kantor;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLama_beroperasi() {
        return lama_beroperasi;
    }

    public void setLama_beroperasi(String lama_beroperasi) {
        this.lama_beroperasi = lama_beroperasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
