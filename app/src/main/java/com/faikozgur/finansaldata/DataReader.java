package com.faikozgur.finansaldata;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class DataReader {

    private DataReadyListener dataReadyListener;

    public  DataReader(){

    }

    public void setDataReadyListener(DataReadyListener dataReadyListener) {
        this.dataReadyListener = dataReadyListener;
    }

    public abstract void ReadData(String fromCode, String toCode);

    protected void dataReady(CurrencyExchangeRateData rateData){
        if(dataReadyListener!=null)
            dataReadyListener.onDataReady(rateData);
    }

    public interface DataReadyListener{
        void onDataReady(CurrencyExchangeRateData data);
    }
}
