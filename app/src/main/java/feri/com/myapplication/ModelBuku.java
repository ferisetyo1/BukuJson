package feri.com.myapplication;

public class ModelBuku {
    private String judul;
    private String penulis;
    private int tahun;
    private int jmlHalaman;
    private String bahasa;
    private String negara;
    private String kategori;

    public ModelBuku() {
    }

    public ModelBuku(String judul, String penulis, int tahun, int jmlHalaman, String bahasa, String negara) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahun = tahun;
        this.jmlHalaman = jmlHalaman;
        this.bahasa = bahasa;
        this.negara = negara;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public int getJmlHalaman() {
        return jmlHalaman;
    }

    public void setJmlHalaman(int jmlHalaman) {
        this.jmlHalaman = jmlHalaman;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }
}
