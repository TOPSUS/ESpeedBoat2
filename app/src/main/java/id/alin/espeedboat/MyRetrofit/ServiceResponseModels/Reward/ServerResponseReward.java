package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward;


import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.RewardEntity;

public class ServerResponseReward {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorReward error;
    private long id_kapal;
    private String nama_kapal;
    private int total_poin;
    List<RewardEntity> rewards;

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

    public ServerResponseErrorReward getError() {
        return error;
    }

    public void setError(ServerResponseErrorReward error) {
        this.error = error;
    }

    public long getId_kapal() {
        return id_kapal;
    }

    public void setId_kapal(long id_kapal) {
        this.id_kapal = id_kapal;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }

    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
    }

    public int getTotal_poin() {
        return total_poin;
    }

    public void setTotal_poin(int total_poin) {
        this.total_poin = total_poin;
    }

    public List<RewardEntity> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardEntity> rewards) {
        this.rewards = rewards;
    }
}

class ServerResponseErrorReward{

}
