package id.alin.espeedboat.MyAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    public List<NotificationEntity> notificationentities;

    // PRIVATE STATUS DARI NOTIFICATION
    private static final short NORMAL = 0;
    private static final short SUCCESS = 1;
    private static final short WARNING = 2;
    private static final short DANGER = 3;
    private static final short SYSTEM_INFORMATION = 4;

    public NotificationAdapter(List<NotificationEntity> notifikasi,Context context) {
        this.notificationentities = notifikasi;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_notification.setText(this.notificationentities.get(position).getTitle());
        holder.messange_notification.setText(this.notificationentities.get(position).getMessage());
        holder.time_notification.setText(this.notificationentities.get(position).getCreated_at());

        // SET ICON BERDASARKAN STATUS
        if(this.notificationentities.get(position).getType() == SUCCESS){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.checked));
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#e0facf"));
        }else if(this.notificationentities.get(position).getType() == WARNING){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.warning));
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#f7efd0"));
        }else if(this.notificationentities.get(position).getType() == DANGER){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.warn));
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#ffd6d4"));
        }else if(this.notificationentities.get(position).getType() == SYSTEM_INFORMATION){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.exchange));
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#e6f4f7"));
        }else{
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_notification));
        }


    }

    @Override
    public int getItemCount() {
        return notificationentities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title_notification, messange_notification, time_notification;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iconNotification);
            title_notification = itemView.findViewById(R.id.namaNotifikasi);
            messange_notification = itemView.findViewById(R.id.messagenotifikasi);
            time_notification = itemView.findViewById(R.id.waktuNotifikasi);
            constraintLayout = itemView.findViewById(R.id.notificationroot);
        }
    }

}
