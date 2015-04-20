package com.tetragon.desto.model;

import com.tetragon.desto.util.DestoUtil;

public class SatisItem {

	private String idkey;
	private StokItemList stokItemList;
	private String satis_tarihi;
	private String satis_adedi="1";
	private Musteri musteri;
	
	private float toplamFiyat = 0;
	private float kkarti = 0;
	private float nakit = 0;
	private int taksitSayisi = 0;
	private float taksitTl = 0;
	private float sonTaksit = 0;
	private String taksitBasTar;
	private String taksitBitTar;
	private String satis_ayrinti;
	
	public SatisItem(StokItemList itemList){
		setIdkey(DestoUtil.generateId());
		setStokItemList(itemList);
		setSatis_tarihi(DestoUtil.getToday());
		setSatis_adedi("0");
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String getSatis_tarihi() {
		return satis_tarihi;
	}

	public void setSatis_tarihi(String satis_tarihi) {
		this.satis_tarihi = satis_tarihi;
	}

	public String getSatis_adedi() {
		return satis_adedi;
	}

	public void setSatis_adedi(String satis_adedi) {
		this.satis_adedi = satis_adedi;
	}

	public Musteri getMusteri() {
		return musteri;
	}

	public void setMusteri(Musteri musteri) {
		this.musteri = musteri;
	}

	public float getToplamFiyat() {
		return toplamFiyat;
	}

	public void setToplamFiyat(float toplamFiyat) {
		this.toplamFiyat = toplamFiyat;
	}

	public int getTaksitSayisi() {
		return taksitSayisi;
	}

	public void setTaksitSayisi(int taksitSayisi) {
		this.taksitSayisi = taksitSayisi;
	}

	public float getTaksitTl() {
		return taksitTl;
	}

	public void setTaksitTl(float taksitTl) {
		this.taksitTl = taksitTl;
	}

	public float getSonTaksit() {
		return sonTaksit;
	}

	public void setSonTaksit(float sonTaksit) {
		this.sonTaksit = sonTaksit;
	}

	public String getTaksitBasTar() {
		return taksitBasTar;
	}

	public void setTaksitBasTar(String taksitBasTar) {
		this.taksitBasTar = taksitBasTar;
	}

	public String getTaksitBitTar() {
		return taksitBitTar;
	}

	public void setTaksitBitTar(String taksitBitTar) {
		this.taksitBitTar = taksitBitTar;
	}

	public float getKkarti() {
		return kkarti;
	}

	public void setKkarti(float kkarti) {
		this.kkarti = kkarti;
	}

	public float getNakit() {
		return nakit;
	}

	public void setNakit(float nakit) {
		this.nakit = nakit;
	}

	public String getSatis_ayrinti() {
		return satis_ayrinti;
	}

	public void setSatis_ayrinti(String satis_ayrinti) {
		this.satis_ayrinti = satis_ayrinti;
	}

	public StokItemList getStokItemList() {
		return stokItemList;
	}

	public void setStokItemList(StokItemList stokItemList) {
		this.stokItemList = stokItemList;
	}
	
}