package lukeeey.drivejsy.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lukeeey.drivejsy.R;
import lukeeey.drivejsy.adapters.CarparksAdapter;
import lukeeey.drivejsy.models.carpark.Carpark;
import lukeeey.drivejsy.models.carpark.CarparkType;

public class CarparksFragment extends Fragment {
    private RequestQueue queue;
    private View view;
    private CarparksAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carparks, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.carparks_recycler_view);

        queue = Volley.newRequestQueue(getActivity());

        List<Carpark> list = new ArrayList<>();
        adapter = new CarparksAdapter(list);

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
        recyclerView.setLayoutManager(layoutManager);

        updateCarparkData();
        return view;
    }

    private void updateCarparkData() {
        final List<Carpark> list = new ArrayList<>();
        String url = "http://sojpublicdata.blob.core.windows.net/sojpublicdata/carpark-data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject root = response.getJSONObject("carparkData");
                    JSONArray carparkData = root.getJSONObject("Jersey").getJSONArray("carpark");

                    for(int i = 0; i < carparkData.length(); i++) {
                        JSONObject data = carparkData.getJSONObject(i);

                        Carpark carpark = new Carpark();
                        carpark.name = data.getString("name");
                        carpark.type = (data.getString("type") == "Long stay") ? CarparkType.LONG_STAY : CarparkType.SHORT_STAY;
                        carpark.isOpen = data.getBoolean("carparkOpen");
                        carpark.spaces = data.getInt("spaces");
                        carpark.unusableSpaces = data.getInt("numberOfUnusableSpaces");
                        carpark.spacesConsideredLow = data.getInt("numberOfSpacesConsideredLow");

                        adapter.addItem(carpark);
                    }

                    adapter.notifyDataSetChanged();

                    TextView lastUpdated = (TextView) view.findViewById(R.id.last_updated);
                    lastUpdated.setText(root.getString("Timestamp"));
                } catch(JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
}
