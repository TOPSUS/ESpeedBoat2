package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponseErrorProfileData {
    @SerializedName("nama")
    @Expose
    private List<String> nama;

    @SerializedName("alamat")
    @Expose
    private List<String> alamat;

    @SerializedName("jeniskelamin")
    @Expose
    private List<String> jeniskelamin;


    @SerializedName("nohp")
    @Expose
    private List<String> nohp;


    @SerializedName("email")
    @Expose
    private List<String> email;


    @SerializedName("foto")
    @Expose
    private List<String> foto;

    @SerializedName("password")
    @Expose
    private List<String> password;

    @SerializedName("c_password")
    @Expose
    private List<String> c_password;

    @SerializedName("imageprofile")
    @Expose
    private List<String> imageprofile;

    public List<String> getNama() {
        return nama;
    }

    public void setNama(List<String> nama) {
        this.nama = nama;
    }

    public List<String> getAlamat() {
        return alamat;
    }

    public void setAlamat(List<String> alamat) {
        this.alamat = alamat;
    }

    public List<String> getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(List<String> jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public List<String> getNohp() {
        return nohp;
    }

    public void setNohp(List<String> nohp) {
        this.nohp = nohp;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getFoto() {
        return foto;
    }

    public void setFoto(List<String> foto) {
        this.foto = foto;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }

    public List<String> getC_password() {
        return c_password;
    }

    public void setC_password(List<String> c_password) {
        this.c_password = c_password;
    }

    public List<String> getImageprofile() {
        return imageprofile;
    }

    public void setImageprofile(List<String> imageprofile) {
        this.imageprofile = imageprofile;
    }

    public String parseErrorAll(){
        StringBuilder error = new StringBuilder();

        if(this.nama != null){
            error.append(this.nama.get(0));
            error.append("\n");
        }

        if(this.alamat != null){
            error.append(this.alamat.get(0));
            error.append("\n");
        }

        if(this.c_password != null){
            error.append(this.c_password.get(0));
            error.append("\n");
        }

        if(this.email != null){
            error.append(this.email.get(0));
            error.append("\n");
        }

        if(this.foto != null){
            error.append(this.foto.get(0));
            error.append("\n");
        }

        if(this.jeniskelamin != null){
            error.append(this.jeniskelamin.get(0));
            error.append("\n");
        }

        if(this.nohp != null){
            error.append(this.nohp.get(0));
            error.append("\n");
        }

        if(this.password != null){
            error.append(this.password.get(0));
            error.append("\n");
        }

        if(this.imageprofile != null){
            error.append(this.imageprofile.get(0));
            error.append("\n");
        }

        return error.toString();
    }
}
