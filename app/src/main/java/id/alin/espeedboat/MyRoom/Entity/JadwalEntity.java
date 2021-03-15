package id.alin.espeedboat.MyRoom.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class JadwalEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long id_speedboat;
    private long id_asal_pelabuhan;
    private long id_tujuan_pelabuhan;
    private String waktu_sampai;
    private String waktu_berangkat;
    private String harga;
    private String pelabuhan_asal_nama;
    private String pelabuhan_asal_kode;
    private String pelabuhan_tujuan_nama;
    private String pelabuhan_tujuan_kode;
    private String nama_speedboat;
    private long kapasitas;
    private long pemesanan_saat_ini;
    private long sisa;
    private String deskripsi_boat;
    private String foto_boat;
    private String contact_service;
    private String tanggal_beroperasi;

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

    public long getId_asal_pelabuhan() {
        return id_asal_pelabuhan;
    }

    public void setId_asal_pelabuhan(long id_asal_pelabuhan) {
        this.id_asal_pelabuhan = id_asal_pelabuhan;
    }

    public long getId_tujuan_pelabuhan() {
        return id_tujuan_pelabuhan;
    }

    public void setId_tujuan_pelabuhan(long id_tujuan_pelabuhan) {
        this.id_tujuan_pelabuhan = id_tujuan_pelabuhan;
    }

    public String getWaktu_sampai() {
        return waktu_sampai;
    }

    public void setWaktu_sampai(String waktu_sampai) {
        this.waktu_sampai = waktu_sampai;
    }

    public String getWaktu_berangkat() {
        return waktu_berangkat;
    }

    public void setWaktu_berangkat(String waktu_berangkat) {
        this.waktu_berangkat = waktu_berangkat;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getPelabuhan_asal_nama() {
        return pelabuhan_asal_nama;
    }

    public void setPelabuhan_asal_nama(String pelabuhan_asal_nama) {
        this.pelabuhan_asal_nama = pelabuhan_asal_nama;
    }

    public String getPelabuhan_asal_kode() {
        return pelabuhan_asal_kode;
    }

    public void setPelabuhan_asal_kode(String pelabuhan_asal_kode) {
        this.pelabuhan_asal_kode = pelabuhan_asal_kode;
    }

    public String getPelabuhan_tujuan_nama() {
        return pelabuhan_tujuan_nama;
    }

    public void setPelabuhan_tujuan_nama(String pelabuhan_tujuan_nama) {
        this.pelabuhan_tujuan_nama = pelabuhan_tujuan_nama;
    }

    public String getPelabuhan_tujuan_kode() {
        return pelabuhan_tujuan_kode;
    }

    public void setPelabuhan_tujuan_kode(String pelabuhan_tujuan_kode) {
        this.pelabuhan_tujuan_kode = pelabuhan_tujuan_kode;
    }

    public String getNama_speedboat() {
        return nama_speedboat;
    }

    public void setNama_speedboat(String nama_speedboat) {
        this.nama_speedboat = nama_speedboat;
    }

    public long getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(long kapasitas) {
        this.kapasitas = kapasitas;
    }

    public long getPemesanan_saat_ini() {
        return pemesanan_saat_ini;
    }

    public void setPemesanan_saat_ini(long pemesanan_saat_ini) {
        this.pemesanan_saat_ini = pemesanan_saat_ini;
    }

    public long getSisa() {
        return sisa;
    }

    public void setSisa(long sisa) {
        this.sisa = sisa;
    }

    public String getDeskripsi_boat() {
        return deskripsi_boat;
    }

    public void setDeskripsi_boat(String deskripsi_boat) {
        this.deskripsi_boat = deskripsi_boat;
    }

    public String getFoto_boat() {
        return foto_boat;
    }

    public void setFoto_boat(String foto_boat) {
        this.foto_boat = foto_boat;
    }

    public String getContact_service() {
        return contact_service;
    }

    public void setContact_service(String contact_service) {
        this.contact_service = contact_service;
    }

    public String getTanggal_beroperasi() {
        return tanggal_beroperasi;
    }

    public void setTanggal_beroperasi(String tanggal_beroperasi) {
        this.tanggal_beroperasi = tanggal_beroperasi;
    }
}
