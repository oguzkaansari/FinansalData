package com.faikozgur.finansaldata;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;


public class MockyDataReader extends DataReader {
    private static final String TAG = "MockyDataReader";
    private static Hashtable<String, String> urls = new Hashtable<>();

    private Context context;

    static {
        urls.put("USDJPY", "http://www.mocky.io/v2/5e4e70452f0000a33016a768");
        urls.put("USDEUR", "http://www.mocky.io/v2/5e88ebfd3100006000d39b41");
    }

    public MockyDataReader(Context context) {
        this.context = context;
    }

    @Override
    public void ReadData(String fromCode, String toCode) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = urls.get(fromCode + toCode);
        if (url == null)
            return;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        CurrencyExchangeRateData parsed = parseData(response);
                        if (parsed != null) {
                            dataReady(parsed);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Finansal veriler okunurken hata oluştu:" + error.getMessage(), error.getCause());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private CurrencyExchangeRateData parseData(JSONObject response) {

        try {
            CurrencyExchangeRateData data = new CurrencyExchangeRateData();

            JSONObject rate = response.getJSONObject("Realtime Currency Exchange Rate");
            String fromCode = rate.getString("1. From_Currency Code");
            String toCode = rate.getString("3. To_Currency Code");
            String exchangeRate = rate.getString("5. Exchange Rate");
            String dateStr = rate.getString("6. Last Refreshed");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date refreshTime = sdf.parse(dateStr);
            data.setRefreshTime(refreshTime);
            data.setFromCode(fromCode);
            data.setToCode(toCode);
            data.setExchangeRate(new BigDecimal(exchangeRate));
            return data;

        } catch (JSONException | ParseException e) {
            Log.e(TAG, "Finansal veriler işlenirken hata oluştu", e);
        }
        return null;
    }
}
