package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Jadwal;

import java.util.List;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseErrorProfileData;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;

public class ServerResponseJadwalData {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseJadwalErrorData error;
    private List<JadwalEntity> jadwal;

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

    public ServerResponseJadwalErrorData getError() {
        return error;
    }

    public void setError(ServerResponseJadwalErrorData error) {
        this.error = error;
    }

    public List<JadwalEntity> getJadwal() {
        return jadwal;
    }

    public void setJadwal(List<JadwalEntity> jadwal) {
        this.jadwal = jadwal;
    }
}


class ServerResponseJadwalErrorData{

}