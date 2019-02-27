package feri.com.myapplication;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_BukuPub extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private ArrayList<ModelBuku> array;
    private EditText search;
    private BukuPubAdapter bukuPubAdapter;
    private RequestQueue mqueue;
    //private static final String TAG1 = "BukuPub";

    public Fragment_BukuPub() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_listbukupublic, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_pub);
        bukuPubAdapter = new BukuPubAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllData();
        search = (EditText) v.findViewById(R.id.edit_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    getSearchData(search.getText().toString());
                } else {
                    getAllData();
                }
            }
        });
        return v;
    }

    private void getSearchData(String s) {
        ArrayList<ModelBuku> _array = new ArrayList<>();
        for (ModelBuku data : array) {
            if (data.getJudul().contains(s)) {
                _array.add(data);
            }
        }
        bukuPubAdapter.addItem(_array);
        recyclerView.setAdapter(bukuPubAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mqueue = Volley.newRequestQueue(getActivity());
        array = new ArrayList<>();

    }

    private void getAllData() {
        String jsonUrl = "https://raw.githubusercontent.com/benoitvallon/100-best-books/master/books.json";

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<ModelBuku> _array=new ArrayList<>();
                //Log.d(TAG1, response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonBuku = (JSONObject) response.get(i);
                        ModelBuku modelBuku = new ModelBuku();
                        modelBuku.setJudul(jsonBuku.getString("title"));
                        modelBuku.setPenulis(jsonBuku.getString("author"));
                        modelBuku.setBahasa(jsonBuku.getString("language"));
                        modelBuku.setTahun(jsonBuku.getInt("year"));
                        modelBuku.setJmlHalaman(jsonBuku.getInt("pages"));
                        modelBuku.setNegara(jsonBuku.getString("country"));
                        //Log.d("test1", jsonBuku.getString("title"));
                        _array.add(modelBuku);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (array.size()==0){
                    array=_array;
                }
                bukuPubAdapter.addItem(_array);
                recyclerView.setAdapter(bukuPubAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mqueue.add(req);
    }

}
