package feri.com.myapplication;

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
import android.widget.TextView;

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

public class Fragment_BukuLoc extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private EditText search;
    private TextView kategori;
    private BukuLocAdapter bukuLocAdapter;
    private ArrayList<ModelBuku> array;
    private RequestQueue mqueue;

    public Fragment_BukuLoc() {
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
        v = inflater.inflate(R.layout.fragment_listbukulocal,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_loc);
        bukuLocAdapter = new BukuLocAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getAllData();
        kategori =(TextView) v.findViewById(R.id.kategori);
        getKategoridata();
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
        bukuLocAdapter.addItem(_array);
        recyclerView.setAdapter(bukuLocAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mqueue = Volley.newRequestQueue(getActivity());
        array = new ArrayList<>();

    }

    private void getKategoridata() {
        String jsonUrl = "http://192.168.100.9:3000/kategori";

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String data ="";
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonkategori = (JSONObject) response.get(i);
                        data += jsonkategori.getString("nama")+" ("+jsonkategori.getInt("jumlah")+") | ";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                kategori.setText(data);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mqueue.add(req);
    }

    private void getAllData() {
        String jsonUrl = "http://192.168.100.9:3000/book";

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<ModelBuku> _array=new ArrayList<>();
//                Log.d("BukuLoc", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonBuku = (JSONObject) response.get(i);
                        ModelBuku modelBuku = new ModelBuku();
                        modelBuku.setJudul(jsonBuku.getString("judul"));
                        modelBuku.setPenulis(jsonBuku.getString("penulis"));
                        modelBuku.setBahasa(jsonBuku.getString("bahasa"));
                        modelBuku.setTahun(jsonBuku.getInt("tahun"));
                        modelBuku.setJmlHalaman(jsonBuku.getInt("halaman"));
                        modelBuku.setNegara(jsonBuku.getString("negara"));
                        modelBuku.setKategori(jsonBuku.getString("kategori"));
//                        Log.d("test1", jsonBuku.getString("judul"));
                        _array.add(modelBuku);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (array.size()==0){
                    array=_array;
                }
                bukuLocAdapter.addItem(_array);
                recyclerView.setAdapter(bukuLocAdapter);
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
