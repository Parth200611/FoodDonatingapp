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
import com.mountreachsolution.sharebite.ACCEPTER.Adpter.AdpterHis;
import com.mountreachsolution.sharebite.DONOR.Adpter.AdpterHistory;
import com.mountreachsolution.sharebite.DONOR.POJO.POJOHistory;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class HistroryAcc extends Fragment {
    RecyclerView rvList;
    TextView tvNoRequest;
    String username;
    List<POJOHistory> pojoHistories;
    AdpterHis adpterHistroey;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_histrory_acc, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "Guest");
        rvList = view.findViewById(R.id.rvLsit);
        tvNoRequest = view.findViewById(R.id.tvNoRequest);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        pojoHistories=new ArrayList<>();

        getDta(username);
        return view;
    }
    private void getDta(String username) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username",username);
        client.post(urls.getHistroryAa,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getPostData");
                    if (jsonArray.length()==0){
                        rvList.setVisibility(View.GONE);
                        tvNoRequest.setVisibility(View.VISIBLE);
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        String username=jsonObject.getString("username");
                        String name=jsonObject.getString("name");
                        String address=jsonObject.getString("address");
                        String quantity=jsonObject.getString("quantity");
                        String details=jsonObject.getString("detail");
                        String image=jsonObject.getString("image");
                        String mobile=jsonObject.getString("mobilenno");
                        String acceptero=jsonObject.getString("acceptero");
                        String status=jsonObject.getString("status");
                        pojoHistories.add(new POJOHistory(id,username,name,mobile,address,details,image,acceptero,status,quantity));
                    }
                    adpterHistroey=new AdpterHis(pojoHistories,getActivity());
                    rvList.setAdapter(adpterHistroey);
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