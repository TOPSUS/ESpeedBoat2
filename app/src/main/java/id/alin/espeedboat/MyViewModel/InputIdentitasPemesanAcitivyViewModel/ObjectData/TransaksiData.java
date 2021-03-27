package id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData;

public class TransaksiData {

    private long id_jadwal;
    private long id_user;
    private long id_metode_pembayaran;
    private long total_biaya;
    private String bukti;
    private String tanggal;
    private String metode_pembayaran;

    public TransaksiData() {
        this.id_jadwal = 0;
        this.id_user = 0;
        this.bukti = "";
        this.tanggal = "";
        this.total_biaya = 0;
        this.metode_pembayaran = "";
        this.id_metode_pembayaran = 0;
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

    public long getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(long total_biaya) {
        this.total_biaya = total_biaya;
    }

    public long getId_metode_pembayaran() {
        return id_metode_pembayaran;
    }

    public void setId_metode_pembayaran(long id_metode_pembayaran) {
        this.id_metode_pembayaran = id_metode_pembayaran;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }
}
