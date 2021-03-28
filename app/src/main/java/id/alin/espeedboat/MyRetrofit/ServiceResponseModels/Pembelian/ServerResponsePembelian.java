package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian;

import java.util.List;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian.ServerResponseErrorPembelian;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;
import id.alin.espeedboat.MyRoom.Entity.PembelianEntitiy;

public class ServerResponsePembelian {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorPembelian error;
    private List<PembelianEntitiy> pembelian;

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

    public ServerResponseErrorPembelian getError() {
        return error;
    }

    public void setError(ServerResponseErrorPembelian error) {
        this.error = error;
    }

    public List<PembelianEntitiy> getPembelian() {
        return pembelian;
    }

    public void setPembelian(List<PembelianEntitiy> pembelian) {
        this.pembelian = pembelian;
    }
}

class ServerResponseErrorPembelian{

}
