package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.alin.espeedboat.MyFragment.MainActivityFragment.HomeFragment.updateBeritaPelabuhan;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;
import id.alin.espeedboat.R;

public class BeritaPelabuhanAdapter extends RecyclerView.Adapter<BeritaPelabuhanAdapter.MyViewHolder>{
    public List<BeritaPelabuhanEntity> beritaPelabuhanEntities;
    public Context context;

    public BeritaPelabuhanAdapter(List<BeritaPelabuhanEntity> beritaPelabuhanEntities, Context context) {
        this.beritaPelabuhanEntities = beritaPelabuhanEntities;
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
        holder.title.setText(this.beritaPelabuhanEntities.get(position).judul);
        holder.detail.setText(this.beritaPelabuhanEntities.get(position).berita);
        holder.tanggal.setText(this.beritaPelabuhanEntities.get(position).tanggal);

        try{
                StringBuilder url = new StringBuilder();
                url.append(ApiClient.BASE_IMAGE_BERITA_PELABUHAN);
                url.append(beritaPelabuhanEntities.get(position).foto);

                Glide.with(this.context).load(url.toString()).into(holder.ivBerita);

        }catch (NullPointerException e){
            Glide.with(this.context).load(R.drawable.berita).into(holder.ivBerita);
        }

    }

    @Override
    public int getItemCount() {
        return this.beritaPelabuhanEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView detail;
        public TextView tanggal;
        public ImageView ivBerita;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvItemBeritaPelabuhanTitle);
            this.detail = itemView.findViewById(R.id.tvItemBeritaPelabuhanDetail);
            this.tanggal = itemView.findViewById(R.id.tvItemBeritaPelabuhanTanggal);

            this.ivBerita = itemView.findViewById(R.id.ivItemBeritaPelabuhan);
        }
    }

}
