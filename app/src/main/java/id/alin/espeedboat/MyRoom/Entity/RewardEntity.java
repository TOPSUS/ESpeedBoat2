package id.alin.espeedboat.MyRoom.Entity;

public class RewardEntity {
    private long id;
    private long id_speedboat;
    private String reward;
    private String berlaku;
    private int minimal_point;
    private String foto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_speedboat() {
        return id_speedboat;
    }

    public void setId_speedboat(long id_speedboat) {
        this.id_speedboat = id_speedboat;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getBerlaku() {
        return berlaku;
    }

    public void setBerlaku(String berlaku) {
        this.berlaku = berlaku;
    }

    public int getMinimal_point() {
        return minimal_point;
    }

    public void setMinimal_point(int minimal_point) {
        this.minimal_point = minimal_point;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
