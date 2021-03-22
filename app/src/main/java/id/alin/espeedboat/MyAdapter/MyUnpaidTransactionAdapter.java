package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyPointRewardActivity;
import id.alin.espeedboat.R;

public class MyUnpaidTransactionAdapter  extends RecyclerView.Adapter<MyUnpaidTransactionAdapter.ViewHolder> {

    private Context context;
    ArrayList<String> tanggal, harga, asal, tujuan, waktuasal, waktutujuan;

    public MyUnpaidTransactionAdapter(ArrayList<String> tanggal, ArrayList<String> harga, ArrayList<String> asal, ArrayList<String> tujuan, ArrayList<String> waktuasal, ArrayList<String> waktutujuan, Context context) {
        this.tanggal = tanggal;
        this.harga = harga;
        this.asal = asal;
        this.tujuan = tujuan;
        this.waktuasal = waktuasal;
        this.waktutujuan = waktutujuan;
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvTanggal.setText(tanggal.get(position));
        holder.tvHarga.setText(harga.get(position));
        holder.tvAsal.setText(asal.get(position));
        holder.tvTujuan.setText(tujuan.get(position));
        holder.tvWaktuAsal.setText(waktuasal.get(position));
        holder.tvWaktuTujuan.setText(waktutujuan.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Unpaid Trans", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tanggal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTanggal, tvHarga, tvAsal, tvTujuan, tvWaktuAsal, tvWaktuTujuan;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTanggal = itemView.findViewById(R.id.tvTanggalUnpaid);
            tvHarga = itemView.findViewById(R.id.tvHargaUnpaid);
            tvAsal = itemView.findViewById(R.id.tvAsalUnpaid);
            tvTujuan = itemView.findViewById(R.id.tvTujuanUnpaid);
            tvWaktuAsal = itemView.findViewById(R.id.tvWaktuAsalUnpaid);
            tvWaktuTujuan = itemView.findViewById(R.id.tvWaktuTujuanUnpaid);
            cardView = itemView.findViewById(R.id.cardViewUnpaidTransaction);
        }
    }

}

