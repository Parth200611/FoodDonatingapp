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
import com.mountreachsolution.sharebite.ACCEPTER.Adpter.AdpterRequest;
import com.mountreachsolution.sharebite.ACCEPTER.POJO.POJORequest;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class Request extends Fragment {
    RecyclerView rvList;
    TextView tvNoRequest;
    String username,name,address,image,RegisterNo,detail;
    List<POJORequest> pojoRequests;
    String UserType="Acceptor";
    AdpterRequest adpterRequest;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request, container, false);
        rvList = view.findViewById(R.id.rvLsit);
        tvNoRequest = view.findViewById(R.id.tvNoRequest);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        pojoRequests =new ArrayList<>();
        getData(UserType);
        return view;
    }
    private void getData(String userType) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("usertype",userType);
        client.post(urls.getAcceptor,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getProfildata");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        name = jsonObject.getString("name");

                        String mobileno = jsonObject.getString("mobile");
                        String gender = jsonObject.getString("dob");
                        String email = jsonObject.getString("email");
                        image = jsonObject.getString("image");
                        String usertype = jsonObject.getString("usertype");
                        RegisterNo = jsonObject.getString("registerno");
                        detail = jsonObject.getString("details");
                        address = jsonObject.getString("address");
                        username = jsonObject.getString("username");
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        String id = sharedPreferences.getString("username", "Guest");  // Default value "Guest" if not found


                        pojoRequests.add(new POJORequest(id,username,name,address,image,RegisterNo,detail));

                    }
                    adpterRequest=new AdpterRequest(pojoRequests,getActivity());
                    rvList.setAdapter(adpterRequest);
                }
                catch (JSONException e) {
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