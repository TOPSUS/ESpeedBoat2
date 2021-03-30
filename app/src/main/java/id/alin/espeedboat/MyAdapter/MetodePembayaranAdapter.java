package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.alin.espeedboat.InputIdentitasPemesanActivity;
import id.alin.espeedboat.MetodePembayaranActivity;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRoom.Entity.MetodePembayaranEntity;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.TransaksiData;
import id.alin.espeedboat.R;

public class MetodePembayaranAdapter extends RecyclerView.Adapter<MetodePembayaranAdapter.MyViewHolder> {
    private Context context;
    public List<MetodePembayaranEntity> metodePembayaranEntities;

    public MetodePembayaranAdapter(Context context, List<MetodePembayaranEntity> metodePembayaranEntities) {
        this.context = context;
        this.metodePembayaranEntities = metodePembayaranEntities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_metode_pembayaran,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvnamametode.setText(metodePembayaranEntities.get(position).getNama_metode());
        holder.tvdeskirpsi.setText(metodePembayaranEntities.get(position).getDeskripsi_metode());

        /*MENENTUKAN LIMITS PEMBAYARAN*/
        long hours = this.metodePembayaranEntities.get(position).getPayment_limit()/3600;

        holder.tvbataswaktu.setText(String.valueOf(hours+" Jam"));

        try{
            StringBuilder url_logo_bank = new StringBuilder();
            url_logo_bank.append(ApiClient.BASE_LOGO_METODE_PEMBAYARAN);
            url_logo_bank.append(metodePembayaranEntities.get(position).getLogo_metode());
            Glide.with(context).load(url_logo_bank.toString()).placeholder(R.drawable.wallet).into(holder.ivlogometode);
        }catch (Exception ignored){

        }

        /*MEMBUAT EVENT CLICK*/
        holder.cardMetode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SAVE KE LIVE DATA*/
                TransaksiData transaksiData = InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.getTransaksiLiveData().getValue();
                transaksiData.setMetode_pembayaran(metodePembayaranEntities.get(position).getNama_metode());
                transaksiData.setId_metode_pembayaran(metodePembayaranEntities.get(position).getId());
                InputIdentitasPemesanActivity.inputIdentitasPemesanActivityViewModel.setTransaksiMutableLiveData(transaksiData);

                ((MetodePembayaranActivity) context).showModalPersetujuanMetodePembayaran();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.metodePembayaranEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvnamametode, tvdeskirpsi, tvbataswaktu;
        ImageView ivlogometode;
        CardView cardMetode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvbataswaktu = itemView.findViewById(R.id.tvbataswaktu);
            this.ivlogometode = itemView.findViewById(R.id.ivlogo);
            this.tvnamametode = itemView.findViewById(R.id.tvnamametode);
            this.tvdeskirpsi = itemView.findViewById(R.id.tvpenjelasanmetode);
            this.cardMetode = itemView.findViewById(R.id.cardViewMotedePembayaran);
        }
    }
}
