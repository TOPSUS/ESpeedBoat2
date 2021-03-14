package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pelabuhan;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;

public class ServerResponsePelabuhanData {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorPelabuhanData error;
    private List<PelabuhanEntity> pelabuhan;

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

    public ServerResponseErrorPelabuhanData getError() {
        return error;
    }

    public void setError(ServerResponseErrorPelabuhanData error) {
        this.error = error;
    }

    public List<PelabuhanEntity> getPelabuhan() {
        return pelabuhan;
    }

    public void setPelabuhan(List<PelabuhanEntity> pelabuhan) {
        this.pelabuhan = pelabuhan;
    }
}

class ServerResponseErrorPelabuhanData{

}

