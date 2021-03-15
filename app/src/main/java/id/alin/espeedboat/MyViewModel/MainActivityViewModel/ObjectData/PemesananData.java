package id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData;

public class PemesananData {
    private String asal;
    private String tujuan;
    private long id_asal;
    private String tanggal;
    private long id_tujuan;
    private String tanggal_variable;
    private String jumlah_penumpang;

    public PemesananData() {
        this.asal = "";
        this.tujuan = "";
        this.id_asal = 0;
        this.tanggal = "";
        this.id_tujuan = 0;
        this.tanggal_variable = "";
        this.jumlah_penumpang = "";
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
}
