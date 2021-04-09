package id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData;

import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;

public class PemesananFeriData {
    private String asal;
    private long id_asal;
    private String tujuan;
    private long id_tujuan;
    private String tanggal;
    private String tanggal_variable;
    private String tipe_jasa;
    private String gologan_kendaraan;
    private long id_golongan_kendaraan;
    private long jumlah_penumpang;
    private JadwalEntity jadwalEntity;

    public PemesananFeriData() {
        this.asal = "";
        this.id_asal = 0;
        this.tujuan = "";
        this.id_tujuan = 0;
        this.tanggal_variable = "";
        this.jumlah_penumpang = 0;
        this.jadwalEntity = new JadwalEntity();
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public long getId_asal() {
        return id_asal;
    }

    public void setId_asal(long id_asal) {
        this.id_asal = id_asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public long getId_tujuan() {
        return id_tujuan;
    }

    public void setId_tujuan(long id_tujuan) {
        this.id_tujuan = id_tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal_variable() {
        return tanggal_variable;
    }

    public void setTanggal_variable(String tanggal_variable) {
        this.tanggal_variable = tanggal_variable;
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

    public long getJumlah_penumpang() {
        return jumlah_penumpang;
    }

    public void setJumlah_penumpang(long jumlah_penumpang) {
        this.jumlah_penumpang = jumlah_penumpang;
    }

    public JadwalEntity getJadwalEntity() {
        return jadwalEntity;
    }

    public void setJadwalEntity(JadwalEntity jadwalEntity) {
        this.jadwalEntity = jadwalEntity;
    }
}
