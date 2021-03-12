package id.alin.espeedboat.MyViewModel.MainActivityViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ProfileData.ServerResponseProfileData;
import id.alin.espeedboat.MyRoom.Entity.BeritaPelabuhanEntity;

public class MainActivityViewModel extends ViewModel {

    /*
    * VARIABLE LIVE DATA PROFILE DATA
    * */
    private static MutableLiveData<ProfileData> profileDataMutableLiveData;

    /*
    * METHOD YANG DIGUNAKAN UNTUK MENGAMBIL LIVE DATA BISA DIOBSERVER DARI
    * SEMUA FRAGMENT ATAU ACTIVITY
    *
    * SAAT DATA INI BERUBAH MAKA SEMUA FRAGMENT ATAU ACTIVITY YANG TELAH MENLAKUKAN OBSERVER AKAN
    * MENGIKUTI
    *
    * */
    public LiveData<ProfileData> getProfileLiveData(){
        if(profileDataMutableLiveData == null){
            throw new NullPointerException();
        }
        else {
            return profileDataMutableLiveData;
        }
    }

    /*
    * METHOD YANG DIGUNAKAN UNTUK SET DATA PROFILE
    * */
    public void setProfileData(ProfileData data){

        /*INIT DULU PROFILE DATA*/
        if(profileDataMutableLiveData == null){
            profileDataMutableLiveData = new MutableLiveData<>();
        }

        profileDataMutableLiveData.setValue(data);
    }
}
