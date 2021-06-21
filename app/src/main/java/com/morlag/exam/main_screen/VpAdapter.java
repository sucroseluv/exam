package com.morlag.exam.main_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Header;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.RequestOptions;
import com.morlag.exam.R;
import com.morlag.exam.common.Movie;

import java.util.HashMap;
import java.util.Map;

public class VpAdapter extends RecyclerView.Adapter<VpAdapter.VpHolder> {
    Movie[] movies;
    Context mContext;
    public VpAdapter(Movie[] mvs,Context c){
        movies = mvs;
        mContext = c;
    }

    @NonNull
    @Override
    public VpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vp,parent,false);
        return new VpHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VpHolder holder, int position) {
        GlideUrl url = new GlideUrl("https://openweathermap.org/themes/openweathermap/assets/img/owm-clients-logos.png", new Headers() {
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> hm = new HashMap<>();
                hm.put("Content-Type","image/jpeg");
                return hm;
            }
        });
        Glide.with(mContext)
                .load("http://cinema.areas.su/up/images/"+movies[position].poster)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.empty)
                        .fitCenter())
                .into(holder.img);
        //holder.img.setImageResource(android.R.drawable.star_big_on);
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    class VpHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public VpHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.card).findViewById(R.id.image);
        }
    }
}
