package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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


        int harga = Integer.valueOf(this.jadwalEntities.get(position).getHarga());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String harga_rupiah = kursIndonesia.format(harga);

        String html = "Rp <font color='red'>"+harga_rupiah+"</font>/org";

        holder.tvHarga.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));
    }

    @Override
    public int getItemCount() {
        return jadwalEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSpeedBoatName, tvSpeedBoatDesc, tvAsal,tvJamBerangkat,tvJamSampai,tvTujuan,
                            tvHarga;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvSpeedBoatName = itemView.findViewById(R.id.tvItemJadwalNamaSpeedBoat);
            this.tvSpeedBoatDesc = itemView.findViewById(R.id.tvItemJadwalDescSpeedBoat);
            this.tvAsal = itemView.findViewById(R.id.tvItemJadwalAsal);
            this.tvJamBerangkat = itemView.findViewById(R.id.tvItemKberangkatan);
            this.tvJamSampai = itemView.findViewById(R.id.tvItemJadwalSampai);
            this.tvTujuan = itemView.findViewById(R.id.tvItemJAdwalTujuan);
            this.tvHarga = itemView.findViewById(R.id.tvItemJadwalHarga);
        }
    }
}
