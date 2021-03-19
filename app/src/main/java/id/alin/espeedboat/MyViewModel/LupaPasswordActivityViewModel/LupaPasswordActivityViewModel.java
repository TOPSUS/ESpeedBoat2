package id.alin.espeedboat.MyViewModel.LupaPasswordActivityViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.alin.espeedboat.MyViewModel.LupaPasswordActivityViewModel.ObjectData.LupaPasswordData;

public class LupaPasswordActivityViewModel extends ViewModel {
    private MutableLiveData<LupaPasswordData> lupaPasswordDataMutableLiveData;

    public LupaPasswordActivityViewModel() {
        LupaPasswordData lupaPasswordData = new LupaPasswordData();
        lupaPasswordData.setEmail("");
        lupaPasswordData.setKode_verifikasi_email("");

        this.lupaPasswordDataMutableLiveData = new MutableLiveData<>();

        this.lupaPasswordDataMutableLiveData.setValue(lupaPasswordData);
    }

    public void setLupaPasswordDataMutableLiveData(LupaPasswordData lupaPasswordData){

        this.lupaPasswordDataMutableLiveData.setValue(lupaPasswordData);

    }

    public MutableLiveData<LupaPasswordData> getLupaPasswordDataMutableLiveData() {

        return lupaPasswordDataMutableLiveData;

    }
}
