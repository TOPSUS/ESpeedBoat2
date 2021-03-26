package id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData;

public class PenumpangData {
    private String nama_pemegang_ticket;
    private String type_id_card;
    private String no_id_card;
    private long harga;

    public PenumpangData() {
        this.harga = 0;
        this.type_id_card = "";
        this.nama_pemegang_ticket = "";
        this.no_id_card = "";
    }

    public String getNama_pemegang_ticket() {
        return nama_pemegang_ticket;
    }

    public void setNama_pemegang_ticket(String nama_pemegang_ticket) {
        this.nama_pemegang_ticket = nama_pemegang_ticket;
    }

    public String getType_id_card() {
        return type_id_card;
    }

    public void setType_id_card(String type_id_card) {
        this.type_id_card = type_id_card;
    }

    public String getNo_id_card() {
        return no_id_card;
    }

    public void setNo_id_card(String no_id_card) {
        this.no_id_card = no_id_card;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }
}
