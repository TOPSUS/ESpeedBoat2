package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.BeritaEspeed;

import java.util.List;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.BeritaPelabuhan.ServerResponseErrorBeritaPelabuhan;
import id.alin.espeedboat.MyRoom.Entity.BeritaEspeedEntity;

public class ServerResponseBeritaEspeed {
    private String response_code;
    private String status;
    private String message;
    private Object error;
    private List<BeritaEspeedEntity> berita_espeed;

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

    public void setError(ServerResponseErrorBeritaPelabuhan error) {
        this.error = error;
    }

    public List<BeritaEspeedEntity> getBerita_espeed() {
        return berita_espeed;
    }

    public void setBerita_espeed(List<BeritaEspeedEntity> berita_espeed) {
        this.berita_espeed = berita_espeed;
    }
}
