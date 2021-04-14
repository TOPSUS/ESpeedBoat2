package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    ArrayList<NotificationEntity> notifikasi;

    public NotificationAdapter(ArrayList<NotificationEntity> notifikasi,Context context) {
        this.notifikasi = notifikasi;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_notification.setText(this.notifikasi.get(position).getTitle());
        holder.messange_notification.setText(this.notifikasi.get(position).getMessage());
        holder.time_notification.setText(this.notifikasi.get(position).getCreated_at());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ini Notifikasi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return notifikasi.size();
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
            constraintLayout = itemView.findViewById(R.id.conslayuser);
        }
    }

}
