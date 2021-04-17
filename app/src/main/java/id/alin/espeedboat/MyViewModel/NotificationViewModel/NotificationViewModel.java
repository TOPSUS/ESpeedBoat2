package id.alin.espeedboat.MyViewModel.NotificationViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.NotificationEntity;

public class NotificationViewModel extends ViewModel {

    // ATRIBUTE LIVE DATA DARI NOTIFICATION
    private static MutableLiveData<List<NotificationEntity>> notificationEntityMutableLiveData;

    // METHOD UNTUK SET NOTIFICATION
    public void setNotificationData(List<NotificationEntity> notificationEntities){
        if(notificationEntityMutableLiveData == null) {
            notificationEntityMutableLiveData = new MutableLiveData<>();
        }

        notificationEntityMutableLiveData.setValue(notificationEntities);
    }


    // METHOD UNTUK MENAMBAHKAN SINGLE DATA KE DALAM NOTIFICATION VIEW MODEL
    public void addSingleNotificationData(NotificationEntity notificationEntity){
        if(notificationEntityMutableLiveData == null) {
            notificationEntityMutableLiveData = new MutableLiveData<>();
        }

        // MENAMBAHKAN SINGLE DATA
        List<NotificationEntity> notificationEntities = notificationEntityMutableLiveData.getValue();
        notificationEntities.add(0,notificationEntity);
        notificationEntityMutableLiveData.setValue(notificationEntities);
    }

    // METHOD UNTUK GET NOTIFICATION
    public LiveData<List<NotificationEntity>> getAllNotification(){
        try{
            return notificationEntityMutableLiveData;
        }catch (NullPointerException e){
            return null;
        }
    }

}
