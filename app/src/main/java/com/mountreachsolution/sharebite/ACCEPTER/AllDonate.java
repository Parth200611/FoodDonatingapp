package com.mountreachsolution.sharebite.ACCEPTER;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.sharebite.ACCEPTER.Adpter.AdpterPost;
import com.mountreachsolution.sharebite.ACCEPTER.POJO.POJOPOSt;
import com.mountreachsolution.sharebite.R;
import com.mountreachsolution.sharebite.urls;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;

public class AllDonate extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private TextView noPostTextView;
    private List<POJOPOSt> pojopoSts;
    private AdpterPost adpterPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_donate, container, false);

        searchView = rootView.findViewById(R.id.search_view);
        recyclerView = rootView.findViewById(R.id.rvlist);
        noPostTextView = rootView.findViewById(R.id.tvNoPost);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pojopoSts = new ArrayList<>();

        // Set up SearchView to filter data by address
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Don't perform action on query submit
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText); // Filter data as text changes
                return false;
            }
        });

        // Call to get the data when the fragment is created
        getData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the data every time the fragment is resumed
        getData();
    }

    private void getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.post(urls.getPost, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("getPost");

                    pojopoSts.clear(); // Clear the old data

                    if (jsonArray.length() == 0) {
                        // No posts available
                        recyclerView.setVisibility(View.GONE);
                        noPostTextView.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String username = jsonObject.getString("username");
                            String address = jsonObject.getString("address");
                            String detail = jsonObject.getString("details");
                            String quantity = jsonObject.getString("quantity");
                            String image = jsonObject.getString("image");

                            pojopoSts.add(new POJOPOSt(id, username, address, detail, quantity, image));
                        }

                        // Set the adapter
                        adpterPost = new AdpterPost(pojopoSts, getActivity());
                        recyclerView.setAdapter(adpterPost);

                        recyclerView.setVisibility(View.VISIBLE);
                        noPostTextView.setVisibility(View.GONE); // Hide the "No posts" text when data is available
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void filterData(String query) {
        // Filter posts based on the query
        List<POJOPOSt> filteredList = new ArrayList<>();
        for (POJOPOSt post : pojopoSts) {
            if (post.getAddress().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(post);
            }
        }

        // Update the adapter with the filtered list
        adpterPost = new AdpterPost(filteredList, getActivity());
        recyclerView.setAdapter(adpterPost);

        if (filteredList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noPostTextView.setVisibility(View.VISIBLE); // Show "No posts" if no results match
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noPostTextView.setVisibility(View.GONE); // Hide "No posts" if there are results
        }
    }
}
