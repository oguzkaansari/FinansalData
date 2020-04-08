package com.faikozgur.finansaldata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView listeView;
    private List<CurrencyExchangeRateData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listeView = (RecyclerView) findViewById(R.id.listeView);
        listeView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listeView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        listeView.setLayoutManager(layoutManager);

        refreshData();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null)
            return networkInfo.isConnected();

        return false;
    }


    private void refreshData() {

        if (isNetworkConnected()) {

            data = new ArrayList<CurrencyExchangeRateData>();

            readExchangeRate("USD", "JPY");
            readExchangeRate("USD", "EUR");
            readExchangeRate("GBP", "EUR");
            readExchangeRate("GBP", "USD");

        } else {
            Toast.makeText(this, "Ağ bağlantısı yok", Toast.LENGTH_LONG).show();
        }
    }

    private void readExchangeRate(String fromCode, String toCode) {
        DataReader reader = DataReaders.getMockyDataReader(this);
        reader.setDataReadyListener(new DataReader.DataReadyListener() {
            @Override
            public void onDataReady(CurrencyExchangeRateData rateData) {
                data.add(rateData);
                ListViewAdapter listeViewAdapter = new ListViewAdapter(getSupportFragmentManager(), data);
                listeView.setAdapter(listeViewAdapter);
            }
        });
        reader.ReadData(fromCode, toCode);
    }
}
