package id.alin.espeedboat.MyAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import id.alin.espeedboat.MyFragment.MainActivityFragment.NotificationFragment;
import id.alin.espeedboat.MyRoom.Database.DatabaeESpeedboat;
import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;
import id.alin.espeedboat.R;

public class NotificationHistoryAdapter extends RecyclerView.Adapter<NotificationHistoryAdapter.ViewHolder> {
    private Context context;
    public List<NotificationEntity> notificationentities;
    private DatabaeESpeedboat databaeESpeedboat;

    // PRIVATE STATUS DARI NOTIFICATION
    private static final short NORMAL = 0;
    private static final short SUCCESS = 1;
    private static final short WARNING = 2;
    private static final short DANGER = 3;
    private static final short SYSTEM_INFORMATION = 4;

    public NotificationHistoryAdapter(List<NotificationEntity> notifikasi, Context context) {
        this.notificationentities = notifikasi;
        this.context = context;
        this.databaeESpeedboat = DatabaeESpeedboat.createDatabase(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_history, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_notification.setText(this.notificationentities.get(position).getTitle());
        holder.messange_notification.setText(this.notificationentities.get(position).getMessage());
        holder.time_notification.setText(this.notificationentities.get(position).getCreated_at());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationEntity notificationEntity = notificationentities.get(position);
                notificationEntity.setStatus((short) 2);
                databaeESpeedboat.notificationDAO().updateNotification(notificationEntity);

                if(NotificationFragment.notificationViewModel != null){
                    Log.d("notifikasi_test","masuk");
                    NotificationFragment.notificationViewModel.setNotificationData(databaeESpeedboat.notificationDAO().getAllNotificationEntity());;
                }

                holder.expandableLayout.collapse();
            }
        });

        holder.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationEntity notificationEntity = notificationentities.get(position);
                notificationEntity.setStatus((short) 0);
                databaeESpeedboat.notificationDAO().updateNotification(notificationEntity);

                if(NotificationFragment.notificationViewModel != null){
                    Log.d("notifikasi_test","masuk");
                    NotificationFragment.notificationViewModel.setNotificationData(databaeESpeedboat.notificationDAO().getAllNotificationEntity());;
                }

                holder.expandableLayout.collapse();
            }
        });

        holder.framelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableLayout.toggle();
            }
        });

        // SET ICON BERDASARKAN STATUS
        if(this.notificationentities.get(position).getType() == SUCCESS){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.checked));
            holder.framelayout.setBackgroundColor(Color.parseColor("#e0facf"));
        }else if(this.notificationentities.get(position).getType() == WARNING){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.warning));
            holder.framelayout.setBackgroundColor(Color.parseColor("#f7efd0"));
        }else if(this.notificationentities.get(position).getType() == DANGER){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.warn));
            holder.framelayout.setBackgroundColor(Color.parseColor("#ffd6d4"));
        }else if(this.notificationentities.get(position).getType() == SYSTEM_INFORMATION){
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.exchange));
            holder.framelayout.setBackgroundColor(Color.parseColor("#e6f4f7"));
        }else{
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.ic_notification));
            holder.framelayout.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return notificationentities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnDelete,btnRestore;
        ImageView imageView;
        TextView title_notification, messange_notification, time_notification;
        FrameLayout framelayout;
        ExpandableLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.notification_expanded);
            imageView = itemView.findViewById(R.id.iconNotification);
            title_notification = itemView.findViewById(R.id.namaNotifikasi);
            messange_notification = itemView.findViewById(R.id.messagenotifikasi);
            time_notification = itemView.findViewById(R.id.waktuNotifikasi);
            framelayout = itemView.findViewById(R.id.notificationroot);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnRestore = itemView.findViewById(R.id.btnRestore);
        }
    }

}
