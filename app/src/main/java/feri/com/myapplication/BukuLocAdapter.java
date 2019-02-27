package feri.com.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BukuLocAdapter extends RecyclerView.Adapter<BukuLocAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<ModelBuku> modelBuku;
    private LayoutInflater mInflater;

    public BukuLocAdapter(Context context) {
        this.context = context;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public BukuLocAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_buku, viewGroup, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BukuLocAdapter.CustomViewHolder customViewHolder, int position) {
        final String judul = modelBuku.get(position).getJudul();
        final String penulis = modelBuku.get(position).getPenulis();
        final int tahun = modelBuku.get(position).getTahun();
        final int jmlHalaman = modelBuku.get(position).getJmlHalaman();
        final String bahasa = modelBuku.get(position).getBahasa();
        final String negara = modelBuku.get(position).getNegara();
        final String kategori=modelBuku.get(position).getKategori();
        String textJson =
                "Judul\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t= "+judul+"\n"
                + "Penulis\t\t\t\t\t\t\t\t\t\t\t\t\t= "+penulis+"\n"
                + "Tahun\t\t\t\t\t\t\t\t\t\t\t\t\t\t= "+tahun+"\n"
                + "Negara\t\t\t\t\t\t\t\t\t\t\t\t\t= "+negara+"\n"
                + "Bahasa\t\t\t\t\t\t\t\t\t\t\t\t\t= "+bahasa+"\n"
                + "Jumlah Halaman\t= "+jmlHalaman+"\n"
                + "kategori\t\t\t\t\t\t\t\t\t\t\t\t= "+kategori;

        customViewHolder.textjson.setText(textJson);
        //Log.d("BukuPub",textJson);
    }

    @Override
    public int getItemCount() {
        return modelBuku.size();
    }

    public void addItem(ArrayList<ModelBuku> mData) {
        this.modelBuku = mData;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView textjson;
        private CardView cv;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textjson = (TextView) itemView.findViewById(R.id.textjson);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }
    }
}
