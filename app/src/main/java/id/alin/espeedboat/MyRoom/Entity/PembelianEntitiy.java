package id.alin.espeedboat.MyRoom.Entity;

public class PembelianEntitiy {
    private long id;
    private long id_jadwal;
    private long id_user;
    private String tanggal;
    private long total_harga;
    private String status;
    private String pelabuhan_asal_nama;
    private String pelabuhan_tujuan_nama;
    private String nama_speedboat;
    private String waktu_berangkat;
    private String waktu_sampai;


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

    public String getPelabuhan_asal_nama() {
        return pelabuhan_asal_nama;
    }

    public void setPelabuhan_asal_nama(String pelabuhan_asal_nama) {
        this.pelabuhan_asal_nama = pelabuhan_asal_nama;
    }

    public String getPelabuhan_tujuan_nama() {
        return pelabuhan_tujuan_nama;
    }

    public void setPelabuhan_tujuan_nama(String pelabuhan_tujuan_name) {
        this.pelabuhan_tujuan_nama = pelabuhan_tujuan_nama;
    }

    public String getNama_speedboat() {
        return nama_speedboat;
    }

    public void setNama_speedboat(String nama_speedboat) {
        this.nama_speedboat = nama_speedboat;
    }

    public String getWaktu_berangkat() {
        return waktu_berangkat;
    }

    public void setWaktu_berangkat(String waktu_berangkat) {
        this.waktu_berangkat = waktu_berangkat;
    }

    public String getWaktu_sampai() {
        return waktu_sampai;
    }

    public void setWaktu_sampai(String waktu_sampai) {
        this.waktu_sampai = waktu_sampai;
    }
}
