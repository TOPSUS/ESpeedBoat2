package id.alin.espeedboat.MyAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import id.alin.espeedboat.MyRoom.Entity.PembelianEntitiy;
import id.alin.espeedboat.MyUnpaidDetailTransactionActivity;
import id.alin.espeedboat.R;
import id.alin.espeedboat.ReviewKapalDetailActivity;

public class ReviewKapalAdapter extends RecyclerView.Adapter<ReviewKapalAdapter.ViewHolder> {

    private Context context;
    List<PembelianEntitiy> pembelianEntity;

    public ReviewKapalAdapter(List<PembelianEntitiy> pembelianEntity, Context context) {
        this.pembelianEntity = pembelianEntity;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewKapalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_review_kapal, parent, false);
        ViewHolder viewHolder = new ReviewKapalAdapter.ViewHolder(view);
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
        holder.tvTanggal.setText("Rabu, 13 Maret 2100");
        holder.tvHarga.setText("IDR 50.000,-");
        holder.tvAsal.setText("Benoa");
        holder.tvTujuan.setText("Ketapang");
        holder.tvWaktuAsal.setText(" 13.30 WITA");
        holder.tvWaktuTujuan.setText("12.30 WITA");

        holder.tvKapal.setText(pembelianEntity.get(position).getNama_speedboat());
        holder.tvTanggal.setText(day + " " + month + " " + year);
        holder.tvHarga.setText(total_biaya_rupiah);
        holder.tvAsal.setText(pembelianEntity.get(position).getPelabuhan_asal_nama());
        holder.tvTujuan.setText(pembelianEntity.get(position).getPelabuhan_tujuan_nama());
        holder.tvWaktuAsal.setText(pembelianEntity.get(position).getWaktu_berangkat().substring(0, Math.min(pembelianEntity.get(position).getWaktu_berangkat().length(), 5)) + " WITA");
        holder.tvWaktuTujuan.setText(pembelianEntity.get(position).getWaktu_sampai().substring(0, Math.min(pembelianEntity.get(position).getWaktu_sampai().length(), 5)) + " WITA");
        int id_trans = (int) pembelianEntity.get(position).getId();
        if(pembelianEntity.get(position).getReview() == 0){
            holder.btnNilai.setVisibility(View.VISIBLE);
            holder.ratingBar.setVisibility(View.GONE);
            holder.btnNilai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReviewKapalDetailActivity.class);
                    intent.putExtra("id_trans", pembelianEntity.get(position).getId());
                    intent.putExtra("position", position);
                    intent.putExtra("state", "unreviewed");
                    context.startActivity(intent);
                    Toast.makeText(context, "Ini Ripiu", Toast.LENGTH_SHORT);
                }
            });
        }else{
            holder.btnNilai.setVisibility(View.GONE);
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.ratingBar.setRating(pembelianEntity.get(position).getReview());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReviewKapalDetailActivity.class);
                    intent.putExtra("id_trans", pembelianEntity.get(position).getId());
                    intent.putExtra("position", position);
                    intent.putExtra("state", "reviewed");
                    context.startActivity(intent);
                    Toast.makeText(context, "Ini Ripiu Tuntas", Toast.LENGTH_SHORT);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return pembelianEntity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTanggal, tvHarga, tvAsal, tvTujuan, tvWaktuAsal, tvWaktuTujuan, tvStatus, tvKapal;
        CardView cardView;
        RatingBar ratingBar;
        Button btnNilai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTanggal = itemView.findViewById(R.id.tvTanggalUnpaid);
            tvHarga = itemView.findViewById(R.id.tvHargaUnpaid);
            tvAsal = itemView.findViewById(R.id.tvAsalUnpaid);
            tvTujuan = itemView.findViewById(R.id.tvTujuanUnpaid);
            tvWaktuAsal = itemView.findViewById(R.id.tvWaktuAsalUnpaid);
            tvWaktuTujuan = itemView.findViewById(R.id.tvWaktuTujuanUnpaid);
            cardView = itemView.findViewById(R.id.cardViewUnpaidTransaction);
            tvKapal = itemView.findViewById(R.id.tvJenisKapalUnpaid);
            btnNilai = itemView.findViewById(R.id.btnNilaiKapal);
            ratingBar = itemView.findViewById(R.id.simpleRatingBarReview);
        }
    }

}
