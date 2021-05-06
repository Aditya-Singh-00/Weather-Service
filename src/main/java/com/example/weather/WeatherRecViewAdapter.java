package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import java.util.List;

public class WeatherRecViewAdapter extends RecyclerView.Adapter<WeatherRecViewAdapter.ViewHolder> {

    private Context context;
    private List<WeatherReportModel> weatherReportModels;
    final String IMAGE_URL = "https://www.metaweather.com/static/img/weather";
    public static final String DEGREE = "\u00B0";

    public WeatherRecViewAdapter(Context context,List<WeatherReportModel> weatherReportModels) {
        this.context = context;
        this.weatherReportModels = weatherReportModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position == 0) {
            holder.date.setText("Today");
        }
        else if(position == 1) {
            holder.date.setText("Tomorrow");
        }
        else {
            holder.date.setText(weatherReportModels.get(position).getApplicable_date());
        }

        holder.weatherStateName.setText(weatherReportModels.get(position).getWeather_state_name());
        holder.temp.setText(String.valueOf((int)weatherReportModels.get(position).getThe_temp())+DEGREE);
        holder.minMaxTemp.setText(String.valueOf((int)weatherReportModels.get(position).getMin_temp())+DEGREE
                +"/"+String.valueOf((int)weatherReportModels.get(position).getMax_temp())+DEGREE);
        Glide.with(context).asBitmap().load(weatherReportModels.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return weatherReportModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parentCard;
        private TextView date,temp,weatherStateName,minMaxTemp;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentCard = itemView.findViewById(R.id.parentCard);
            imageView = itemView.findViewById(R.id.imageView);
            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);
            weatherStateName = itemView.findViewById(R.id.weatherStateName);
            minMaxTemp = itemView.findViewById(R.id.min_max_temp);

        }
    }
}
