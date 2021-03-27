package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.MetodePembayaran;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.MetodePembayaranEntity;

public class ServerResponseMetodePembayaranData {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseMetodePembayaranErrorData error;
    private List<MetodePembayaranEntity> metode_pembayaran;

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

    public ServerResponseMetodePembayaranErrorData getError() {
        return error;
    }

    public void setError(ServerResponseMetodePembayaranErrorData error) {
        this.error = error;
    }

    public List<MetodePembayaranEntity> getMetodePembayaranEntities() {
        return metode_pembayaran;
    }

    public void setMetodePembayaranEntities(List<MetodePembayaranEntity> metodePembayaranEntities) {
        this.metode_pembayaran = metodePembayaranEntities;
    }
}

class ServerResponseMetodePembayaranErrorData{

}
