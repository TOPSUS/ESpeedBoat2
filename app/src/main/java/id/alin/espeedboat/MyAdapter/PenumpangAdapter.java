package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.alin.espeedboat.R;

public class PenumpangAdapter extends RecyclerView.Adapter<PenumpangAdapter.MyViewHolder> {
    private Context context;
    private List<String> pemesans;

    public PenumpangAdapter(Context context, List<String> pemesans) {
        this.context = context;
        this.pemesans = pemesans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_penumpang,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNama.setText(this.pemesans.get(position));
    }

    @Override
    public int getItemCount() {
        return this.pemesans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.penumpang);
        }
    }
}
