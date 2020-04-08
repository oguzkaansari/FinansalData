package com.faikozgur.finansaldata;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataReaders {

    public static DataReader getAlphavantageDataReader(Context context) {
        return new AlphavantageDataReader(context);
    }

    public static DataReader getMockyDataReader(Context context) {
        return new MockyDataReader(context);
    }

    public static DataReader getTestReader() {
        return new TestDataReader();
    }

}