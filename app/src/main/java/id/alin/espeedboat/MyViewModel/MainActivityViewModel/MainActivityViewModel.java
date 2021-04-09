package id.alin.espeedboat.MyViewModel.MainActivityViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananFeriData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.PemesananSpeedboatData;
import id.alin.espeedboat.MyViewModel.MainActivityViewModel.ObjectData.ProfileData;

public class MainActivityViewModel extends ViewModel {

    /*
    * VARIABLE LIVE DATA PROFILE DATA
    * */
    private static MutableLiveData<ProfileData> profileDataMutableLiveData;

    /*
    * PROPERTI YANG DIGUNAKAN OLEH FRAGMENT PEMESANAN
    * */
    private static MutableLiveData<PemesananSpeedboatData> pemesananDataMutableLiveData;

    /*
    * PROPERTI YANG AKAN DIGUNAKAN UNTUK MENYIMPAN LIVE DATA DARI PEMESANAN FERI
    * */
    private static MutableLiveData<PemesananFeriData> pemesananFeriDataMutableLiveData;

    /*
    * METHOD YANG DIGUNAKAN UNTUK MENGAMBIL LIVE DATA BISA DIOBSERVER DARI
    * SEMUA FRAGMENT ATAU ACTIVITY
    *
    * SAAT DATA INI BERUBAH MAKA SEMUA FRAGMENT ATAU ACTIVITY YANG TELAH MENLAKUKAN OBSERVER AKAN
    * MENGIKUTI
    *
    * */
    public LiveData<ProfileData> getProfileLiveData(){
        try{
            return profileDataMutableLiveData;
        }catch(NullPointerException e){
            return null;
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

    /*DIGUNAKAN UNTUK MENGIRIM DATA PEMESANAN*/
    public LiveData<PemesananSpeedboatData> getPemesananSpeedboatLiveData(){
        try{
            return pemesananDataMutableLiveData;
        }catch (NullPointerException e){
            return null;
        }
    }

    /*PEMESANAN DATA SET VALUENYA DISINI*/
    public void setPemesananSpeedboatData(PemesananSpeedboatData data){
        if(MainActivityViewModel.pemesananDataMutableLiveData == null){
            pemesananDataMutableLiveData = new MutableLiveData<>();
        }

        pemesananDataMutableLiveData.setValue(data);
    }

    /*METHOD UNTUK MENDAPATKAN PEMESANAN FERI LIVE DATA*/
    public LiveData<PemesananFeriData> getPemesananFeriLiveData(){
        try{
            return pemesananFeriDataMutableLiveData;
        }catch (NullPointerException e){
            return null;
        }
    }

    /*METHOD UNTUK MEMASANGKAN PEMESANAN FERI LIVE DATA*/
    public void setPemesananFeriData(PemesananFeriData data){
        if(MainActivityViewModel.pemesananFeriDataMutableLiveData == null){
            pemesananFeriDataMutableLiveData = new MutableLiveData<>();
        }

        pemesananFeriDataMutableLiveData.setValue(data);
    }

}
