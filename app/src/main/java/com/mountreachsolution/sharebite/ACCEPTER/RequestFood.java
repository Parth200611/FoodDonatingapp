package com.mountreachsolution.sharebite.ACCEPTER;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.sharebite.ACCEPTER.Adpter.AdpterFRequest;
import com.mountreachsolution.sharebite.ACCEPTER.Adpter.AdpterRequest;
import com.mountreachsolution.sharebite.ACCEPTER.POJO.POJOGetRequest;
import com.mountreachsolution.sharebite.ACCEPTER.POJO.POJORequest;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class RequestFood extends Fragment {
    RecyclerView rvList;
    TextView tvNoRequest;
    String id,DUsername,Ausername;
    List<POJOGetRequest>pojoGetRequests;
    String username;
    AdpterFRequest adpterFRequest;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_food, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "Guest");

        rvList = view.findViewById(R.id.rvLsit);
        tvNoRequest = view.findViewById(R.id.tvNoRequest);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        pojoGetRequests=new ArrayList<>();
        getData(username);
        


        return view;
    }

    private void getData(String username) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("accusername",username);
        client.post(urls.getRequest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getRequest");
                    if (jsonArray.length()==0){
                        rvList.setVisibility(View.GONE);
                        tvNoRequest.setVisibility(View.VISIBLE);
                    }for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id=jsonObject.getString("id");
                        DUsername=jsonObject.getString("Dousername");
                        Ausername=jsonObject.getString("accusername");
                        pojoGetRequests.add(new POJOGetRequest(id,DUsername,Ausername));
                    }
                    adpterFRequest=new AdpterFRequest(pojoGetRequests,getActivity());
                    rvList.setAdapter(adpterFRequest);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


}