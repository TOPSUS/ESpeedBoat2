package id.alin.espeedboat.MyRetrofit.ServiceResponseModels;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class ProfileData implements Parcelable {
    private String response_code;
    private String status;
    private String message;
    private Object error;
    private String token;
    private String user_id;
    private String name;
    private String alamat;
    private String chat_id;
    private String pin;
    private String email;
    private String nohp;
    private String jeniskelamin;

    protected ProfileData(Parcel in) {
        response_code = in.readString();
        status = in.readString();
        message = in.readString();
        token = in.readString();
        user_id = in.readString();
        name = in.readString();
        alamat = in.readString();
        chat_id = in.readString();
        pin = in.readString();
        email = in.readString();
        nohp = in.readString();
        jeniskelamin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(response_code);
        dest.writeString(status);
        dest.writeString(message);
        dest.writeString(token);
        dest.writeString(user_id);
        dest.writeString(name);
        dest.writeString(alamat);
        dest.writeString(chat_id);
        dest.writeString(pin);
        dest.writeString(email);
        dest.writeString(nohp);
        dest.writeString(jeniskelamin);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

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
}

