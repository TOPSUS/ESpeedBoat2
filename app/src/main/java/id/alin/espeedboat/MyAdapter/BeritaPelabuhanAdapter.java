package id.alin.espeedboat.MyAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jsoup.Jsoup;

import java.util.List;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;
import id.alin.espeedboat.R;
import id.alin.espeedboat.WebviewActivity;

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

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String sanitizeHtmlBerita = Jsoup.parse(this.beritaPelabuhanEntities.get(position).getBerita()).text();
        holder.title.setText(this.beritaPelabuhanEntities.get(position).judul);
        holder.detail.setText(sanitizeHtmlBerita);
        holder.tanggal.setText(this.beritaPelabuhanEntities.get(position).tanggal);
        holder.beritaroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebviewActivity.class);
                String link = ApiClient.BASE_BERITA_PELABUHAN+ beritaPelabuhanEntities.get(position).id;
                intent.putExtra(WebviewActivity.LINK,link);
                context.startActivity(intent);
            }
        });
        try{
                StringBuilder url = new StringBuilder();
                url.append(ApiClient.BASE_IMAGE_BERITA_PELABUHAN);
                url.append(beritaPelabuhanEntities.get(position).foto);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.no_image_pelabuhan);

                Glide.with(this.context).load(url.toString()).apply(requestOptions).into(holder.ivBerita);
        }catch (NullPointerException e){
                Glide.with(this.context).load(R.drawable.no_image_pelabuhan).into(holder.ivBerita);
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
        public CardView beritaroot;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvItemBeritaPelabuhanTitle);
            this.detail = itemView.findViewById(R.id.tvItemBeritaPelabuhanDetail);
            this.tanggal = itemView.findViewById(R.id.tvItemBeritaPelabuhanTanggal);
            this.beritaroot = itemView.findViewById(R.id.beritaroot);
            this.ivBerita = itemView.findViewById(R.id.ivItemBeritaPelabuhan);
        }
    }

}
