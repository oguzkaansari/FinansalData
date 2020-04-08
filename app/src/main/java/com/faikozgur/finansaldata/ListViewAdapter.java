package com.faikozgur.finansaldata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    private FragmentManager fragmentManager;
    private List<CurrencyExchangeRateData> data;

    public ListViewAdapter(FragmentManager fragmentManager, List<CurrencyExchangeRateData> data) {
        this.fragmentManager = fragmentManager;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_data_view, parent, false);

        ViewHolder vh = new ViewHolder(v, fragmentManager);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fromCode.setText(data.get(position).getFromCode());
        holder.toCode.setText(data.get(position).getToCode());
        holder.exchangeRate.setText(String.format("%.5f", data.get(position).getExchangeRate()));
        holder.refreshTime.setText(data.get(position).getRefreshTime().toLocaleString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView toCode;
        public TextView fromCode;
        public TextView exchangeRate;
        public TextView refreshTime;
        private FragmentManager fragmentManager;

        public ViewHolder(View v, FragmentManager fragmentManager) {
            super(v);
            toCode = (TextView) v.findViewById(R.id.toCode);
            fromCode = (TextView) v.findViewById(R.id.fromCode);
            exchangeRate = (TextView) v.findViewById(R.id.exchangeRate);
            refreshTime = (TextView) v.findViewById(R.id.refreshTime);
            this.fragmentManager = fragmentManager;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Toast t = Toast.makeText(v.getContext(), toCode.getText() + " " + fromCode.getText() + ": " + exchangeRate.getText(), Toast.LENGTH_LONG);
            //t.show();
            BuySellDialogFragment islem = new BuySellDialogFragment();
            islem.show(fragmentManager, "islem");
        }
    }
}
