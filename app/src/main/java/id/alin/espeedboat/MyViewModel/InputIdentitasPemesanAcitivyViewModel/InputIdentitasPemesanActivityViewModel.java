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
        try{
            return this.transaksiMutableLiveData;
        }catch (NullPointerException e){
            TransaksiData transaksiData = new TransaksiData();
            this.transaksiMutableLiveData.setValue(transaksiData);

            return this.transaksiMutableLiveData;
        }
    }

    public void setTransaksiMutableLiveData(TransaksiData transaksiData){
        this.transaksiMutableLiveData.setValue(transaksiData);
    }

    public LiveData<List<PenumpangData>> getListPenumpangLiveData(){
        try{
            return listPenumpangLivedata;
        }catch (NullPointerException e){
            List<PenumpangData> listPenumpangData = new LinkedList<>();
            this.listPenumpangLivedata.setValue(listPenumpangData);

            return this.listPenumpangLivedata;
        }
    }

    public void setListPenumpangLivedata(List<PenumpangData> penumpangDataList){
        this.listPenumpangLivedata.setValue(penumpangDataList);
    }
}
