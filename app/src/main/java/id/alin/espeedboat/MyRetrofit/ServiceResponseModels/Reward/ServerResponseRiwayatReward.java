package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.RiwayatRewardEntity;

public class ServerResponseRiwayatReward {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorRiwayatReward error;
    private List<RiwayatRewardEntity> riwayat;

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

    public ServerResponseErrorRiwayatReward getError() {
        return error;
    }

    public void setError(ServerResponseErrorRiwayatReward error) {
        this.error = error;
    }

    public List<RiwayatRewardEntity> getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(List<RiwayatRewardEntity> riwayat) {
        this.riwayat = riwayat;
    }
}
class ServerResponseErrorRiwayatReward{

}
