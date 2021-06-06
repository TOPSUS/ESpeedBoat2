package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.MaxJumlahPenumpang;

import java.util.List;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;

public class ServerResponseMaxJumlahPenumpang {
    private String response_code;
    private String status;
    private String message;
    private Object error;
    private int maximal_penumpang;

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

    public int getMaximal_penumpang() {
        return maximal_penumpang;
    }

    public void setMaximal_penumpang(int maximal_penumpang) {
        this.maximal_penumpang = maximal_penumpang;
    }
}
