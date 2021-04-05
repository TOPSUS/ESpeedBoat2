package id.alin.espeedboat.MyAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.StringUtil;
import id.alin.espeedboat.MyPointRewardActivity;
import id.alin.espeedboat.MyRoom.Entity.PembelianEntitiy;
import id.alin.espeedboat.MyUnpaidDetailTransactionActivity;
import id.alin.espeedboat.R;

public class MyUnpaidTransactionAdapter  extends RecyclerView.Adapter<MyUnpaidTransactionAdapter.ViewHolder> {

    private Context context;
    List<PembelianEntitiy> pembelianEntity;

    public MyUnpaidTransactionAdapter(List<PembelianEntitiy> pembelianEntity, Context context) {
        this.pembelianEntity = pembelianEntity;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_unpaid_transaction, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //FORMAT MATA UANG INDO
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String total_biaya_rupiah = "IDR " + kursIndonesia.format(pembelianEntity.get(position).getTotal_harga());

        //FORMAT TANGGAL
        String tanggal[] = pembelianEntity.get(position).getTanggal().split("-", 3);
        String day = tanggal[2];
        String month = tanggal[1];
        String year = tanggal[0];

        if(month.equals("01")){
            month = "Januari";
        }else if(month.equals("02")) {
            month = "Februari";
        }else if(month.equals("03")){
            month = "Maret";
        }else if(month.equals("04")){
            month = "April";
        }else if(month.equals("05")){
            month = "Mei";
        }else if(month.equals("06")){
            month = "Juni";
        }else if(month.equals("07")){
            month = "Juli";
        }else if(month.equals("08")){
            month = "Agustus";
        }else if(month.equals("09")){
            month = "September";
        }else if(month.equals("10")){
            month = "Oktober";
        }else if(month.equals("11")){
            month = "November";
        }else if(month.equals("12")){
            month = "Desember";
        }

        //SET DATA TEXTVIEW LAYOUT
        holder.tvKapal.setText(pembelianEntity.get(position).getNama_speedboat());
        holder.tvTanggal.setText(day + " " + month + " " + year);
        holder.tvHarga.setText(total_biaya_rupiah);
        holder.tvAsal.setText(pembelianEntity.get(position).getPelabuhan_asal_nama());
        holder.tvTujuan.setText(pembelianEntity.get(position).getPelabuhan_tujuan_nama());
        holder.tvWaktuAsal.setText(pembelianEntity.get(position).getWaktu_berangkat().substring(0, Math.min(pembelianEntity.get(position).getWaktu_berangkat().length(), 5)) + " WITA");
        holder.tvWaktuTujuan.setText(pembelianEntity.get(position).getWaktu_sampai().substring(0, Math.min(pembelianEntity.get(position).getWaktu_sampai().length(), 5)) + " WITA");
        if (pembelianEntity.get(position).getStatus().equals("menunggu pembayaran")){
            holder.tvStatus.setText("Belum Dibayar");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.Danger_Red));
        }else if(pembelianEntity.get(position).getStatus().equals("menunggu konfirmasi")){
            holder.tvStatus.setText("Menunggu Konfirmasi");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.Warning_Orange));
        }else if(pembelianEntity.get(position).getStatus().equals("terkonfirmasi")){
            holder.tvStatus.setText("Terkonfirmasi");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.Safety_Green));
        }else if(pembelianEntity.get(position).getStatus().equals("digunakan")){
            holder.tvStatus.setText("Selesai");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.Safety_Green));
        }else if(pembelianEntity.get(position).getStatus().equals("dibatalkan")){
            holder.tvStatus.setText("Dibatalkan");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.Danger_Red));
        }else if(pembelianEntity.get(position).getStatus().equals("expired")){
            holder.tvStatus.setText("Kadaluwarsa");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.Danger_Red));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id_trans = (int) pembelianEntity.get(position).getId();
                Intent intent = new Intent(context, MyUnpaidDetailTransactionActivity.class);
                intent.putExtra("id_trans", pembelianEntity.get(position).getId());
                intent.putExtra("position", position);
                Log.d("jejeje", String.valueOf(intent.getIntExtra("id_trans", 0)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pembelianEntity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTanggal, tvHarga, tvAsal, tvTujuan, tvWaktuAsal, tvWaktuTujuan, tvStatus, tvKapal;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTanggal = itemView.findViewById(R.id.tvTanggalUnpaid);
            tvHarga = itemView.findViewById(R.id.tvHargaUnpaid);
            tvAsal = itemView.findViewById(R.id.tvAsalUnpaid);
            tvTujuan = itemView.findViewById(R.id.tvTujuanUnpaid);
            tvWaktuAsal = itemView.findViewById(R.id.tvWaktuAsalUnpaid);
            tvWaktuTujuan = itemView.findViewById(R.id.tvWaktuTujuanUnpaid);
            tvStatus = itemView.findViewById(R.id.tvStatusBayar);
            cardView = itemView.findViewById(R.id.cardViewUnpaidTransaction);
            tvKapal = itemView.findViewById(R.id.tvJenisKapalUnpaid);
        }
    }

}

