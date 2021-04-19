package id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData;

import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;
import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;

public class PemesananSpeedboatData {
    private String asal;
    private String tujuan;
    private long id_asal;
    private String tanggal;
    private long id_tujuan;
    private String tanggal_variable;
    private String jumlah_penumpang;
    private String tipe_jasa;
    private String gologan_kendaraan;
    private long id_golongan_kendaraan;
    private JadwalEntity jadwalEntity;

    public PemesananSpeedboatData() {
        this.asal = "";
        this.tujuan = "";
        this.id_asal = 0;
        this.tanggal = "";
        this.id_tujuan = 0;
        this.tanggal_variable = "";
        this.jumlah_penumpang = "";
        this.tipe_jasa = "";
        this.gologan_kendaraan = "";
        this.id_golongan_kendaraan = 0;
        this.jadwalEntity = new JadwalEntity();
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJumlah_penumpang() {
        return jumlah_penumpang;
    }

    public void setJumlah_penumpang(String jumlah_penumpang) {
        this.jumlah_penumpang = jumlah_penumpang;
    }

    public String getTanggal_variable() {
        return tanggal_variable;
    }

    public void setTanggal_variable(String tanggal_variable) {
        this.tanggal_variable = tanggal_variable;
    }

    public long getId_asal() {
        return id_asal;
    }

    public void setId_asal(long id_asal) {
        this.id_asal = id_asal;
    }

    public long getId_tujuan() {
        return id_tujuan;
    }

    public void setId_tujuan(long id_tujuan) {
        this.id_tujuan = id_tujuan;
    }

    public JadwalEntity getJadwalEntity() {
        return jadwalEntity;
    }

    public void setJadwalEntity(JadwalEntity jadwalEntity) {
        this.jadwalEntity = jadwalEntity;
    }

    public String getTipe_jasa() {
        return tipe_jasa;
    }

    public void setTipe_jasa(String tipe_jasa) {
        this.tipe_jasa = tipe_jasa;
    }

    public String getGologan_kendaraan() {
        return gologan_kendaraan;
    }

    public void setGologan_kendaraan(String gologan_kendaraan) {
        this.gologan_kendaraan = gologan_kendaraan;
    }

    public long getId_golongan_kendaraan() {
        return id_golongan_kendaraan;
    }

    public void setId_golongan_kendaraan(long id_golongan_kendaraan) {
        this.id_golongan_kendaraan = id_golongan_kendaraan;
    }
}
