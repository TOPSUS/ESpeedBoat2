package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.BeritaPelabuhan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;

public class ServerResponseBeritaPelabuhan implements Parcelable {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorBeritaPelabuhan error;
    private List<BeritaPelabuhanEntity> berita_pelabuhan;

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

    public ServerResponseErrorBeritaPelabuhan getError() {
        return error;
    }

    public void setError(ServerResponseErrorBeritaPelabuhan error) {
        this.error = error;
    }

    public List<BeritaPelabuhanEntity> getBerita_pelabuhan() {
        return berita_pelabuhan;
    }

    public void setBerita_pelabuhan(List<BeritaPelabuhanEntity> berita_pelabuhan) {
        this.berita_pelabuhan = berita_pelabuhan;
    }

    protected ServerResponseBeritaPelabuhan(Parcel in) {
        response_code = in.readString();
        status = in.readString();
        message = in.readString();
    }

    public static final Creator<ServerResponseBeritaPelabuhan> CREATOR = new Creator<ServerResponseBeritaPelabuhan>() {
        @Override
        public ServerResponseBeritaPelabuhan createFromParcel(Parcel in) {
            return new ServerResponseBeritaPelabuhan(in);
        }

        @Override
        public ServerResponseBeritaPelabuhan[] newArray(int size) {
            return new ServerResponseBeritaPelabuhan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(response_code);
        dest.writeString(status);
        dest.writeString(message);
    }
}

