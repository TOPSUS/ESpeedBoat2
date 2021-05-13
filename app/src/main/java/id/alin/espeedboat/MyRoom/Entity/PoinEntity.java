package id.alin.espeedboat.MyRoom.Entity;

public class PoinEntity {
    private long id_kapal;
    private String nama_kapal;
    private int total_poin;
    private String foto;

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
