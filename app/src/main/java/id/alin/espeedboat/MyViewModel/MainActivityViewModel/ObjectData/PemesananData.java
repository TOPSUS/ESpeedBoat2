package id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData;

public class PemesananData {
    private String asal;
    private String tujuan;
    private String tanggal;
    private String tanggal_variable;
    private String jumlah_penumpang;

    public PemesananData() {
        this.asal = "";
        this.tujuan = "";
        this.tanggal = "";
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
}
