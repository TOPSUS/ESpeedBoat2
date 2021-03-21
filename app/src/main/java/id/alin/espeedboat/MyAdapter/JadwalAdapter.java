package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import id.alin.espeedboat.InputIdentitasPemesanActivity;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;
import id.alin.espeedboat.R;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MyViewHolder> {
    private Context context;
    private List<JadwalEntity> jadwalEntities;

    public JadwalAdapter(Context context, List<JadwalEntity> jadwalEntities) {
        this.context = context;
        this.jadwalEntities = jadwalEntities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jadwal,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSpeedBoatName.setText(this.jadwalEntities.get(position).getNama_speedboat());
        holder.tvSpeedBoatDesc.setText(this.jadwalEntities.get(position).getDeskripsi_boat());
        holder.tvAsal.setText(this.jadwalEntities.get(position).getPelabuhan_asal_nama());
        holder.tvTujuan.setText(this.jadwalEntities.get(position).getPelabuhan_tujuan_nama());
        holder.tvJamBerangkat.setText(this.jadwalEntities.get(position).getWaktu_berangkat());
        holder.tvJamSampai.setText(this.jadwalEntities.get(position).getWaktu_sampai());

        String kapasitas;

        if(jadwalEntities.get(position).getSisa() < 10){
            Log.d("ALIN DEBUG","MASUK SISA");
            kapasitas ="<font color='red'>" +
                    jadwalEntities.get(position).getKapasitas() +
                    " / " + jadwalEntities.get(position).getPemesanan_saat_ini() +
                    "</font>";
            holder.tvkapasitasboat.setText(HtmlCompat.fromHtml(kapasitas, HtmlCompat.FROM_HTML_MODE_LEGACY));
        }else{
            kapasitas =jadwalEntities.get(position).getKapasitas() +
                    " / " +
                    jadwalEntities.get(position).getPemesanan_saat_ini();
            holder.tvkapasitasboat.setText(kapasitas);
        }



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableLayout.toggle();
            }
        });

        int harga = Integer.parseInt(this.jadwalEntities.get(position).getHarga());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String harga_rupiah = kursIndonesia.format(harga);

        String html = "Rp <font color='red'>"+harga_rupiah+"</font>/org";

        holder.tvHarga.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        holder.btnpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InputIdentitasPemesanActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jadwalEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSpeedBoatName, tvSpeedBoatDesc, tvAsal,tvJamBerangkat,tvJamSampai,tvTujuan,
                            tvHarga, tvkapasitasboat;

        private ExpandableLayout expandableLayout;
        private Button btnpilih;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvSpeedBoatName = itemView.findViewById(R.id.tvItemJadwalNamaSpeedBoat);
            this.tvSpeedBoatDesc = itemView.findViewById(R.id.tvItemJadwalDescSpeedBoat);
            this.tvAsal = itemView.findViewById(R.id.tvItemJadwalAsal);
            this.tvJamBerangkat = itemView.findViewById(R.id.tvItemKberangkatan);
            this.tvJamSampai = itemView.findViewById(R.id.tvItemJadwalSampai);
            this.tvTujuan = itemView.findViewById(R.id.tvItemJAdwalTujuan);
            this.tvHarga = itemView.findViewById(R.id.tvItemJadwalHarga);
            this.expandableLayout = itemView.findViewById(R.id.expandLayout);
            this.btnpilih = itemView.findViewById(R.id.btnpilih);
            this.tvkapasitasboat = itemView.findViewById(R.id.tvKapasitasBoat);
            this.cardView = itemView.findViewById(R.id.cardviewroot);
        }
    }
}
