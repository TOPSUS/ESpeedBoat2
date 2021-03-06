package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.DetailPembelian;

import java.util.ArrayList;
import java.util.List;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponsePembelian;
import id.alin.espeedboat.MyRoom.Entity.PembelianEntitiy;
import id.alin.espeedboat.MyRoom.Entity.PenumpangDetailEntity;

public class ServerResponseDetailPembelian {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorDetailPembelian error;
    private String kapal;
    private String tanggal;
    private long harga;
    private String pelabuhan_asal;
    private String pelabuhan_tujuan;
    private String waktu_berangkat;
    private String waktu_sampai;
    private String status_transaksi;
    private long sisa_waktu;
    private String nama_pemesan;
    private String email_pemesan;
    private String telepon_pemesan;
    private String tiket;
    private String bukti;
    private String metode_pembayaran;
    private String rekening;
    private String logo_metode;
    private String nomor_polisi;
    private String golongan;
    private long harga_golongan;
    private long rating;
    private String review;
    private int persenan_refund;
    private String tanggal_refund;
    private String rekening_refund;
    private long jumlah_refund;
    private String status_refund;
    private int isrefund;
    private List<PenumpangDetailEntity> penumpang;

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServerResponseErrorDetailPembelian getError() {
        return error;
    }

    public void setError(ServerResponseErrorDetailPembelian error) {
        this.error = error;
    }

    public String getKapal() {
        return kapal;
    }

    public void setKapal(String kapal) {
        this.kapal = kapal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public String getPelabuhan_asal() {
        return pelabuhan_asal;
    }

    public void setPelabuhan_asal(String pelabuhan_asal) {
        this.pelabuhan_asal = pelabuhan_asal;
    }

    public String getPelabuhan_tujuan() {
        return pelabuhan_tujuan;
    }

    public void setPelabuhan_tujuan(String pelabuhan_tujuan) {
        this.pelabuhan_tujuan = pelabuhan_tujuan;
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

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public long getSisa_waktu() {
        return sisa_waktu;
    }

    public void setSisa_waktu(long sisa_waktu) {
        this.sisa_waktu = sisa_waktu;
    }

    public String getNama_pemesan() {
        return nama_pemesan;
    }

    public void setNama_pemesan(String nama_pemesan) {
        this.nama_pemesan = nama_pemesan;
    }

    public String getEmail_pemesan() {
        return email_pemesan;
    }

    public void setEmail_pemesan(String email_pemesan) {
        this.email_pemesan = email_pemesan;
    }

    public String getTelepon_pemesan() {
        return telepon_pemesan;
    }

    public void setTelepon_pemesan(String telepon_pemesan) {
        this.telepon_pemesan = telepon_pemesan;
    }

    public String getTiket() {
        return tiket;
    }

    public void setTiket(String tiket) {
        this.tiket = tiket;
    }

    public String getBukti() {
        return bukti;
    }

    public void setBukti(String bukti) {
        this.bukti = bukti;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getLogo_metode() {
        return logo_metode;
    }

    public void setLogo_metode(String logo_metode) {
        this.logo_metode = logo_metode;
    }

    public String getNomor_polisi() {
        return nomor_polisi;
    }

    public void setNomor_polisi(String nomor_polisi) {
        this.nomor_polisi = nomor_polisi;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public long getHarga_golongan() {
        return harga_golongan;
    }

    public void setHarga_golongan(long harga_golongan) {
        this.harga_golongan = harga_golongan;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getPersenan_refund() {
        return persenan_refund;
    }

    public void setPersenan_refund(int persenan_refund) {
        this.persenan_refund = persenan_refund;
    }

    public String getTanggal_refund() {
        return tanggal_refund;
    }

    public void setTanggal_refund(String tanggal_refund) {
        this.tanggal_refund = tanggal_refund;
    }

    public String getRekening_refund() {
        return rekening_refund;
    }

    public void setRekening_refund(String rekening_refund) {
        this.rekening_refund = rekening_refund;
    }

    public long getJumlah_refund() {
        return jumlah_refund;
    }

    public void setJumlah_refund(long jumlah_refund) {
        this.jumlah_refund = jumlah_refund;
    }

    public String getStatus_refund() {
        return status_refund;
    }

    public void setStatus_refund(String status_refund) {
        this.status_refund = status_refund;
    }

    public int getIsrefund() {
        return isrefund;
    }

    public void setIsrefund(int isrefund) {
        this.isrefund = isrefund;
    }

    public List<PenumpangDetailEntity> getPenumpang() {
        return penumpang;
    }

    public void setPenumpang(List<PenumpangDetailEntity> penumpang) {
        this.penumpang = penumpang;
    }
}

class ServerResponseErrorDetailPembelian{

}
