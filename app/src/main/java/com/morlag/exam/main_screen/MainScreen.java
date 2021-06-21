package com.morlag.exam.main_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.morlag.exam.R;
import com.morlag.exam.common.Api;
import com.morlag.exam.common.Callback;
import com.morlag.exam.common.Movie;


public class MainScreen extends Fragment {

    ViewPager2 vp;
    TextView name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment,container,false);
        vp = v.findViewById(R.id.vp);
        name = v.findViewById(R.id.name);

        new Api().getNewMovies(getActivity(), getActivity().getSupportFragmentManager(), new Callback<Movie[]>() {
            @Override
            public void run(final Movie[] data) {
                Toast.makeText(getActivity(),data!=null?String.valueOf(data.length):"null",Toast.LENGTH_SHORT).show();
                VpAdapter ad = new VpAdapter(data,getActivity());
                vp.setAdapter(ad);
                vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        name.setText(data[position].name);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        super.onPageScrollStateChanged(state);
                    }
                });
            }
        });
        return v;
    }


}
