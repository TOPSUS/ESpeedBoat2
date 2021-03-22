package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyPointRewardActivity;
import id.alin.espeedboat.R;

public class MyPointHistoryAdapter extends RecyclerView.Adapter<MyPointHistoryAdapter.ViewHolder> {

    private Context context;
    ArrayList<String> speedboat, status, barang, jumlah;

    public MyPointHistoryAdapter(ArrayList<String> speedboat, ArrayList<String> status, ArrayList<String> barang, ArrayList<String> jumlah, Context context) {
        this.speedboat = speedboat;
        this.status = status;
        this.barang = barang;
        this.jumlah = jumlah;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_point_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(barang.get(position));
        holder.textView1.setText(jumlah.get(position) + " Barang");
        holder.textView2.setText(speedboat.get(position));
        holder.textView3.setText(status.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ini Cardnya Diklik", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ini Buttonnya Diklik", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return speedboat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textView1, textView2, textView3;
        CardView cardView;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivBarangPointHistory);
            textView = itemView.findViewById(R.id.tvNamaBarangPointHistory);
            textView1 = itemView.findViewById(R.id.tvTotalBarangPointHistory);
            textView2 = itemView.findViewById(R.id.tvSpeedboatPointHistory);
            textView3 = itemView.findViewById(R.id.tvStatusPointHistory);
            cardView = itemView.findViewById(R.id.cardViewPointHistory);
            btn = itemView.findViewById(R.id.btnPojokPointHistory);
        }
    }

}

