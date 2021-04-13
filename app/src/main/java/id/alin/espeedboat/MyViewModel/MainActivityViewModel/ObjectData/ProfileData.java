package id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileData implements Parcelable {
    private String user_id;
    private String token;
    private String name;
    private String alamat;
    private String chat_id;
    private String pin;
    private String email;
    private String foto;
    private String nohp;
    private String jeniskelamin;
    private String fcm_token;

    public ProfileData(String user_id, String token, String name, String alamat,
                       String chat_id, String pin, String email, String foto,
                       String nohp, String jeniskelamin, String fcm_token) {
        this.user_id = user_id;
        this.token = token;
        this.name = name;
        this.alamat = alamat;
        this.chat_id = chat_id;
        this.pin = pin;
        this.email = email;
        this.foto = foto;
        this.nohp = nohp;
        this.jeniskelamin = jeniskelamin;
        this.fcm_token = fcm_token;
    }

    public static final Creator<ProfileData> CREATOR = new Creator<ProfileData>() {
        @Override
        public ProfileData createFromParcel(Parcel in) {
            return new ProfileData(in);
        }

        @Override
        public ProfileData[] newArray(int size) {
            return new ProfileData[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(token);
        dest.writeString(name);
        dest.writeString(alamat);
        dest.writeString(chat_id);
        dest.writeString(pin);
        dest.writeString(email);
        dest.writeString(foto);
        dest.writeString(nohp);
        dest.writeString(jeniskelamin);
    }

    protected ProfileData(Parcel in) {
        user_id = in.readString();
        token = in.readString();
        name = in.readString();
        alamat = in.readString();
        chat_id = in.readString();
        pin = in.readString();
        email = in.readString();
        foto = in.readString();
        nohp = in.readString();
        jeniskelamin = in.readString();
    }

}
