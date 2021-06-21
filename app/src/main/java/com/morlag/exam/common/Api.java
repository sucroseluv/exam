package com.morlag.exam.common;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Api {

    public void register(final Map<String,String> req, final Context c, final FragmentManager fm, final Callback<String> cb){
        StringRequest r = new StringRequest(Request.Method.POST,
                "http://cinema.areas.su/auth/register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cb.run(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new MessageDialog("Проблемы при регистрации", c,"Ошибка "+(error.networkResponse.statusCode)).show(fm, "tag");
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return req;
            }
        };
        Volley.newRequestQueue(c).add(r);
    }
    public void login(final Map<String,String> req, final Context c, final FragmentManager fm, final Callback<String> cb){
        StringRequest r = new StringRequest(Request.Method.POST,
                "http://cinema.areas.su/auth/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cb.run(new Gson().fromJson(response, Token.class).token);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new MessageDialog("Проблема аутентификации",c,"Ошибка " + (error.networkResponse.statusCode)).show(fm,"tag");
                    }
                }

        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return req;
            }
        };
        Volley.newRequestQueue(c).add(r);
    }

    public void getNewMovies(final Context c, final FragmentManager fm, final Callback<Movie[]> cb){
        StringRequest r = new StringRequest(Request.Method.GET,
                "http://cinema.areas.su/movies?filter=new",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cb.run(new Gson().fromJson(response, Movie[].class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new MessageDialog("Проблема при запросе",c,"Ошибка " + (error.networkResponse.statusCode)).show(fm,"tag");
                    }
                }

        );
        Volley.newRequestQueue(c).add(r);
    }

    public void getUser(final Context c, final FragmentManager fm, final Callback<User> cb){
        StringRequest r = new StringRequest(Request.Method.GET,
                "http://cinema.areas.su/user",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cb.run(new Gson().fromJson(response, User.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new MessageDialog("Проблема при запросе",c,"Ошибка " + (error.networkResponse.statusCode)).show(fm,"tag");
                    }
                }

        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> s = new HashMap<>();
                s.put("Authorization:", "Bearer "+c.getSharedPreferences("prefs",Context.MODE_PRIVATE).getString("token",""));
                return s;
            }
        };
        Volley.newRequestQueue(c).add(r);
    }
}


