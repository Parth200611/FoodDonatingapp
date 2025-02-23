package com.mountreachsolution.sharebite.DONOR;

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
import com.mountreachsolution.sharebite.DONOR.Adpter.AdpterConfirm;
import com.mountreachsolution.sharebite.DONOR.POJO.POJOAccepetRequest;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ResponceDonor extends Fragment {
     RecyclerView rvList;
     TextView tvNoRequest;
     List<POJOAccepetRequest>pojoAccepetRequests;
     String uswername;
     AdpterConfirm adpterConfirm;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_responce_donor, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        uswername = sharedPreferences.getString("username", "Guest");
        rvList = view.findViewById(R.id.rvLsit);
        tvNoRequest = view.findViewById(R.id.tvNoRequest);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        pojoAccepetRequests=new ArrayList<>();
        getdata(uswername);


        return view;
    }

    private void getdata(String uswername) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("Dousername",uswername);
        client.post(urls.confirRequest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getData");
                    if (jsonArray.length()==0){
                        rvList.setVisibility(View.GONE);
                        tvNoRequest.setVisibility(View.VISIBLE);
                    }for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        String Dusername=jsonObject.getString("Dousername");
                        String Ausername=jsonObject.getString("accusername");
                        String status=jsonObject.getString("status");
                        pojoAccepetRequests.add(new POJOAccepetRequest(id,Dusername,Ausername,status));
                    }
                    adpterConfirm=new AdpterConfirm(pojoAccepetRequests,getActivity());
                    rvList.setAdapter(adpterConfirm);

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