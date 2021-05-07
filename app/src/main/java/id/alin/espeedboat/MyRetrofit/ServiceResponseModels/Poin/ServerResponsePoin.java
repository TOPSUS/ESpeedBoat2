package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Poin;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.PembelianEntitiy;
import id.alin.espeedboat.MyRoom.Entity.PoinEntity;

public class ServerResponsePoin {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorPoint error;
    private List<PoinEntity> pembelians;

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

    public ServerResponseErrorPoint getError() {
        return error;
    }

    public void setError(ServerResponseErrorPoint error) {
        this.error = error;
    }

    public List<PoinEntity> getPoin() {
        return pembelians;
    }

    public void setPoin(List<PoinEntity> pembelians) {
        this.pembelians = pembelians;
    }
}

class ServerResponseErrorPoint{

}
