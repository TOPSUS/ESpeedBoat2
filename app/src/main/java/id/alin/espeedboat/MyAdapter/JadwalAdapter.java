package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

import id.alin.espeedboat.InputIdentitasPemesanActivity;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyRoom.Entity.JadwalEntity;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
import id.alin.espeedboat.PemesananJadwalSpeedboatActivity;
import id.alin.espeedboat.R;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MyViewHolder> {
    private Context context;
    private List<JadwalEntity> jadwalEntities;

    private final String ZONA_WAKTU = " WITA ";

    // ATRIBUTE TIPE KAPAL
    private String tipe_kapal;

    public JadwalAdapter(Context context, List<JadwalEntity> jadwalEntities, String tipe_kapal) {
        this.context = context;
        this.jadwalEntities = jadwalEntities;
        this.tipe_kapal = tipe_kapal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jadwal,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // MENAMBAHKAN ZONA WAKTU
        String waktu_berangkat = this.jadwalEntities.get(position).getWaktu_berangkat()+ZONA_WAKTU;
        String waktu_sampai = this.jadwalEntities.get(position).getWaktu_sampai()+ZONA_WAKTU;

        holder.tvSpeedBoatName.setText(this.jadwalEntities.get(position).getNama_speedboat());
        holder.tvSpeedBoatDesc.setText(this.jadwalEntities.get(position).getDeskripsi_boat());
        holder.tvAsal.setText(this.jadwalEntities.get(position).getPelabuhan_asal_nama());
        holder.tvTujuan.setText(this.jadwalEntities.get(position).getPelabuhan_tujuan_nama());
        holder.tvJamBerangkat.setText(waktu_berangkat);
        holder.tvJamSampai.setText(waktu_sampai);
        holder.tvtipekapal.setText(this.tipe_kapal);

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

        if(jadwalEntities.get(position).getIsOrderable()){
            holder.tvtipekapal.setBackgroundResource(R.color.Blue_primary);
            holder.btnpilih.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tipe_kapal.matches(PemesananJadwalSpeedboatActivity.SPEEDBOAT)){
                        PemesananSpeedboatData pemesananSpeedboatData = MainActivity.mainActivityViewModel.getPemesananSpeedboatLiveData().getValue();
                        pemesananSpeedboatData.setJadwalEntity(jadwalEntities.get(position));
                        MainActivity.mainActivityViewModel.setPemesananSpeedboatData(pemesananSpeedboatData);
                    }else{
                        PemesananFeriData pemesananFeriData = MainActivity.mainActivityViewModel.getPemesananFeriLiveData().getValue();
                        pemesananFeriData.setJadwalEntity(jadwalEntities.get(position));
                        MainActivity.mainActivityViewModel.setPemesananFeriData(pemesananFeriData);
                    }

                    // MENJALANKAN INTENT DENGAN MENGIRIMKAN TIPE KAPALNYA FERI ATAU SPEEDBOAT
                    Intent intent = new Intent(context, InputIdentitasPemesanActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(PemesananJadwalSpeedboatActivity.TIPE_KAPAL,tipe_kapal);
                    context.startActivity(intent);
                }
            });
        }else{
            holder.tvtipekapal.setBackgroundResource(R.color.lightgrey);
            holder.itemView.setAlpha(.5f);
            String peringatan = "JADWAL TELAH LEWAT";
            holder.btnpilih.setEnabled(false);
            holder.btnpilih.setText(peringatan);
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
    }

    @Override
    public int getItemCount() {
        return jadwalEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSpeedBoatName, tvSpeedBoatDesc, tvAsal,tvJamBerangkat,tvJamSampai,tvTujuan,
                            tvHarga, tvkapasitasboat, tvtipekapal;

        private ExpandableLayout expandableLayout;
        private Button btnpilih;
        private CardView cardView;
        private View itemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
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
            this.tvtipekapal = itemView.findViewById(R.id.tipe_kapal);
        }
    }
}
