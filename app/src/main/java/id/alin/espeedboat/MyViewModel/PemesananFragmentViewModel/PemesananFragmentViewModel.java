package id.alin.espeedboat.MyViewModel.PemesananFragmentViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;

public class PemesananFragmentViewModel extends ViewModel {

    /*LIST PELABUHAN*/
    private MutableLiveData<List<PelabuhanEntity>> pelabuhanentitieslivedata;

    /*GET PELABUHAN*/
    public LiveData<List<PelabuhanEntity>> getPelabuhanEntityLiveData(){
        try{
            return pelabuhanentitieslivedata;
        }catch (NullPointerException e){
            return null;
        }
    }

    /*SET PELABUHAN*/
    public void setPelabuhanLiveData(List<PelabuhanEntity> pelabuhanentitieslivedata){
        if(this.pelabuhanentitieslivedata == null){
            this.pelabuhanentitieslivedata = new MutableLiveData<>();
        }

        this.pelabuhanentitieslivedata.setValue(pelabuhanentitieslivedata);
    }

}
