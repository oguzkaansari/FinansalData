package com.faikozgur.finansaldata;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class TestDataReader extends DataReader {
    private static Random random = new Random(System.currentTimeMillis());

    public TestDataReader() {
        super();
    }

    @Override
    public void ReadData(String fromCode, String toCode) {
        CurrencyExchangeRateData rateData = new CurrencyExchangeRateData();
        rateData.setFromCode(fromCode);
        rateData.setToCode(toCode);
        rateData.setExchangeRate(new BigDecimal((random.nextFloat())));
        dataReady(rateData);
    }
}
