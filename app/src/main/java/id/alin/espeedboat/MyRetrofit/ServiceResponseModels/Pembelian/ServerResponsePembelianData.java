package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Pembelian;

import id.alin.espeedboat.MyRoom.Entity.PembelianEntity;

public class ServerResponsePembelianData {
    private String response_code;
    private String status;
    private String message;
    private Object error;
    private PembelianEntity pembelian;

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

    public PembelianEntity getPembelian() {
        return pembelian;
    }

    public void setPembelian(PembelianEntity pembelian) {
        this.pembelian = pembelian;
    }
}
