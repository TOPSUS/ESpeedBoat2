package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyFragment.MainActivityFragment.PemesananChildFragment.FullscreenGolonganFragment;
import id.alin.espeedboat.MyRoom.Entity.GolonganEntitiy;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.R;

public class GolonganAdapter extends RecyclerView.Adapter<GolonganAdapter.MyViewHolder> {
    public List<GolonganEntitiy> golonganEntitiys;
    private Context context;
    private FullscreenGolonganFragment fullscreenGolonganFragment;

    public GolonganAdapter(List<GolonganEntitiy> golonganEntitiys, Context context, FullscreenGolonganFragment fullscreenGolonganFragment) {
        this.golonganEntitiys = golonganEntitiys;
        this.context = context;
        this.fullscreenGolonganFragment = fullscreenGolonganFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_golongan_kendaraan,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvtitlegolongan.setText(this.golonganEntitiys.get(position).getGolongan());
        holder.tvketerangangolongan.setText(this.golonganEntitiys.get(position).getKeterangan());

        String harga = "Rp. "+this.golonganEntitiys.get(position).getHarga();
        holder.tvharga.setText(harga);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                pemesananFeriData.setGologan_kendaraan(golonganEntitiys.get(position).getGolongan());
                pemesananFeriData.setId_golongan_kendaraan(golonganEntitiys.get(position).getId());
                pemesananFeriData.setKeterangan_golongan(golonganEntitiys.get(position).getKeterangan());
                pemesananFeriData.setHarga(Integer.parseInt(golonganEntitiys.get(position).getHarga()));
                pemesananFeriData.setNomor_kendaraan("");
                MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);

                GolonganAdapter.this.fullscreenGolonganFragment.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.golonganEntitiys.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvtitlegolongan, tvketerangangolongan,tvharga;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.card_root);
            this.tvtitlegolongan = itemView.findViewById(R.id.title);
            this.tvharga = itemView.findViewById(R.id.hargagolongan);
            this.tvketerangangolongan = itemView.findViewById(R.id.keterangan);
        }
    }
}
