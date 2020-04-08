package com.faikozgur.finansaldata;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class AlphavantageDataReader extends DataReader {
    private static final String TAG = "AlphavantageDataReader";

    private Context context;

    public AlphavantageDataReader(Context context) {
        this.context = context;
    }

    /*
     * Welcome to Alpha Vantage! Your API key is: 73A91YOMPTIV2ROI.
     * Please record this API key for future access to Alpha Vantage.
     */
    public void ReadData(String fromCode, String toCode) {

        RequestQueue queue = Volley.newRequestQueue(context);
        //http://www.mocky.io/v2/5e4e70452f0000a33016a768 USD-JPY
        String url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCode + "&to_currency=" + toCode + "&apikey=73A91YOMPTIV2ROI";

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
