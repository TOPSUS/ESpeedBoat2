package id.alin.espeedboat.MyViewModel.MainActivityViewModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeritaPelabuhanData {

    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("id_pelabuhan")
    @Expose
    public long id_pelabuhan;

    @SerializedName("id_user")
    @Expose
    public long id_user;

    @SerializedName("judul")
    @Expose
    public String judul;

    @SerializedName("berita")
    @Expose
    public String berita;

    @SerializedName("foto")
    @Expose
    public String foto;

    @SerializedName("tanggal")
    @Expose
    public String tanggal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_pelabuhan() {
        return id_pelabuhan;
    }

    public void setId_pelabuhan(long id_pelabuhan) {
        this.id_pelabuhan = id_pelabuhan;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getBerita() {
        return berita;
    }

    public void setBerita(String berita) {
        this.berita = berita;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
