package id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.TransaksiData;

public class InputIdentitasPemesanActivityViewModel extends ViewModel {
    private MutableLiveData<TransaksiData> transaksiMutableLiveData;
    private MutableLiveData<List<PenumpangData>> listPenumpangLivedata;

    public LiveData<TransaksiData> getTransaksiLiveData(){
        if(this.transaksiMutableLiveData == null){
            this.transaksiMutableLiveData = new MutableLiveData<>();
            TransaksiData transaksiData = new TransaksiData();
            this.transaksiMutableLiveData.setValue(transaksiData);

            return this.transaksiMutableLiveData;
        }else {
            return this.transaksiMutableLiveData;
        }
    }

    public void setTransaksiMutableLiveData(TransaksiData transaksiData){
        this.transaksiMutableLiveData.setValue(transaksiData);
    }

    public LiveData<List<PenumpangData>> getListPenumpangLiveData(){
        if(this.listPenumpangLivedata == null){
            this.listPenumpangLivedata = new MutableLiveData<>();
            List<PenumpangData> penumpangDataList = new LinkedList<>();
            this.listPenumpangLivedata.setValue(penumpangDataList);

            return this.listPenumpangLivedata;
        }else{
            return this.listPenumpangLivedata;
        }
    }

    public void setListPenumpangLivedata(List<PenumpangData> penumpangDataList){
        this.listPenumpangLivedata.setValue(penumpangDataList);
    }
}
