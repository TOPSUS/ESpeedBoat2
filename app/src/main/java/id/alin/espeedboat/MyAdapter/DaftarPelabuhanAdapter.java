package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.alin.espeedboat.R;

public class DaftarPelabuhanAdapter extends RecyclerView.Adapter<DaftarPelabuhanAdapter.MyViewHolder> {
    private ArrayList<String> data;
    private Context context;

    public DaftarPelabuhanAdapter(ArrayList<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_pelabuhan_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.data.setText(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView data;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.data = itemView.findViewById(R.id.tvDaftarPelabuhanTitle);
        }
    }
}
