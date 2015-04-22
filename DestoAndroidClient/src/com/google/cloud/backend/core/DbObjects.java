package com.google.cloud.backend.core;

import java.util.List;

import com.tetragon.desto.model.Konum;
import com.tetragon.desto.model.KonumList;
import com.tetragon.desto.model.Kullanici;
import com.tetragon.desto.model.KullaniciList;
import com.tetragon.desto.model.Kullanici_grubu;
import com.tetragon.desto.model.Kullanici_grubuList;
import com.tetragon.desto.model.MarkaItem;
import com.tetragon.desto.model.MarkaItemList;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.ModelItemList;
import com.tetragon.desto.model.Musteri;
import com.tetragon.desto.model.MusteriList;
import com.tetragon.desto.model.SatisItem;
import com.tetragon.desto.model.Siparis;
import com.tetragon.desto.model.SiparisList;
import com.tetragon.desto.model.Siparis_ayrinti;
import com.tetragon.desto.model.Siparis_ayrintiList;
import com.tetragon.desto.model.Stok;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.model.Stok_yeri;
import com.tetragon.desto.model.Stok_yeriList;
import com.tetragon.desto.model.Urun_tipItem;
import com.tetragon.desto.model.Urun_tipItemList;
import com.tetragon.desto.model.UrungrubuItem;
import com.tetragon.desto.model.UrungrubuItemList;
import com.tetragon.desto.util.DestoUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DbObjects extends CloudBackendAsync {
	private static MarkaItemList markaItemList = new MarkaItemList();
	private static UrungrubuItemList urungrubuItemList = new UrungrubuItemList();
	private static Urun_tipItemList urunTipItemList = new Urun_tipItemList();
	private static ModelItemList modelItemList = new ModelItemList();
	private static SiparisList siparisList = new SiparisList();
	private static Siparis_ayrintiList siparisAyrintiList = new Siparis_ayrintiList();
	private static StokItemList stokItemList = new StokItemList();
	private static Stok_yeriList stok_yeriList = new Stok_yeriList();
	private static Kullanici_grubuList kullanici_grubuList = new Kullanici_grubuList();
	private static KonumList konumList = new KonumList();
	private static KullaniciList kullaniciList = new KullaniciList();
	private static StokItemList selectedStokList = new StokItemList();
	private static SatisItem satisItem;
	private static MusteriList musteriList;

	private static Context context;

	public DbObjects(Context context) {
		super(context);
		setContext(context);
	}

	public static Siparis_ayrintiList getSiparisAyrintiList(Siparis ce) {
		Siparis_ayrintiList allList = getSiparisAyrintiList();
		Siparis_ayrintiList filteredList = new Siparis_ayrintiList();

		for (Siparis_ayrinti entity : allList) {
			if ((entity.get(Siparis_ayrinti.PROP_SIPARIS_ID)).equals(ce
					.get(Siparis.PROP_IDKEY)))
				filteredList.add(entity);
		}
		return filteredList;
	}

	public MarkaItemList createMarkaItemList(List<CloudEntity> results) {
		MarkaItemList list = new MarkaItemList();
		for (CloudEntity ce : results) {
			MarkaItem item = new MarkaItem((CloudEntity) ce);
			list.add(item);
		}
		return list;
	}

	public UrungrubuItemList createUrungrubuItemList(List<CloudEntity> results) {
		UrungrubuItemList list = new UrungrubuItemList();
		for (CloudEntity ce : results) {
			UrungrubuItem item = new UrungrubuItem((CloudEntity) ce);
			list.add(item);
		}
		return list;
	}

	public Urun_tipItemList createUrunTipItemList(List<CloudEntity> results) {
		Urun_tipItemList list = new Urun_tipItemList();
		for (CloudEntity ce : results) {
			Urun_tipItem item= new Urun_tipItem((CloudEntity) ce);
			list.add(item);
		}
		return list;
	}

	public ModelItemList createModelItemList(List<CloudEntity> results) {
		ModelItemList list = new ModelItemList();
		for (CloudEntity ce : results) {
			ModelItem item = new ModelItem((CloudEntity) ce);
			list.add(item);
		}
		return list;
	}

	public SiparisList createSiparisList(List<CloudEntity> results) {
		SiparisList list = new SiparisList();
		for (CloudEntity ce : results) {
			Siparis siparis = new Siparis(ce.getKindName(), ce);
			list.add(siparis);
		}
		return list;
	}

	public Siparis_ayrintiList createSiparisAyrintiList(
			List<CloudEntity> results) {
		Siparis_ayrintiList list = new Siparis_ayrintiList();
		for (CloudEntity ce : results) {
			Siparis_ayrinti siparis_ayrinti = new Siparis_ayrinti(
					ce.getKindName(), ce);
			list.add(siparis_ayrinti);
		}
		return list;
	}

	public StokItemList createStokItemList(List<CloudEntity> results) {
		StokItemList list = new StokItemList();
		for (CloudEntity stok : results) {
			String adet=stok.get(Stok.PROP_ADET).toString();
			if ((adet!=null)&&(!adet.isEmpty()&&(DestoUtil.stringToInt(adet)>0))){
				StokItem stokItem = new StokItem((CloudEntity) stok);
				list.add(stokItem);
			}
			
		}
		return list;
	}
	
	public Stok_yeriList createStok_yeriList(List<CloudEntity> results) {
		Stok_yeriList list = new Stok_yeriList();
		for (CloudEntity ce : results) {
			Stok_yeri stok_yeri = new Stok_yeri(ce.getKindName(), ce);
			list.add(stok_yeri);
		}
		return list;
	}

	public Kullanici_grubuList createKullanici_grubuList(
			List<CloudEntity> results) {
		Kullanici_grubuList list = new Kullanici_grubuList();
		for (CloudEntity ce : results) {
			Kullanici_grubu kullanici_grubu = new Kullanici_grubu(
					ce.getKindName(), ce);
			list.add(kullanici_grubu);
		}
		return list;
	}

	public KonumList createKonumList(List<CloudEntity> results) {
		KonumList list = new KonumList();
		for (CloudEntity ce : results) {
			Konum konum = new Konum(ce.getKindName(), ce);
			list.add(konum);
		}
		return list;
	}

	public KullaniciList createKullaniciList(List<CloudEntity> results) {
		KullaniciList list = new KullaniciList();
		for (CloudEntity ce : results) {
			Kullanici kullanici = new Kullanici(ce.getKindName(), ce);
			list.add(kullanici);
		}
		return list;
	}

	public static SatisItem createSatisItem(StokItemList results) {
		SatisItem satisItem = new SatisItem(results);
		return satisItem;
	}
	
	public MusteriList createMusteriList(List<CloudEntity> results) {
		MusteriList list = new MusteriList();
		for (CloudEntity ce : results) {
			Musteri musteri= new Musteri(ce.getKindName(), ce);
			list.add(musteri);
		}
		return list;
	}
	
	// Connection
	/**
	 * Checks if the device has Internet connection.
	 * 
	 * @return <code>true</code> if the phone is connected to the Internet.
	 */
	public static boolean hasConnection() {
		ConnectivityManager cm = (ConnectivityManager) getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}

	// getter setter
	public static MarkaItemList getMarkaItemList() {
		return markaItemList;
	}

	public static void setMarkaItemList(MarkaItemList markaList) {
		DbObjects.markaItemList = markaList;
	}

	public static SiparisList getSiparisList() {
		return siparisList;
	}

	public static void setSiparisList(SiparisList siparisList) {
		DbObjects.siparisList = siparisList;
	}

	public static Siparis_ayrintiList getSiparisAyrintiList() {
		return siparisAyrintiList;
	}

	public static void setSiparisAyrintiList(
			Siparis_ayrintiList siparisAyrintiList) {
		DbObjects.siparisAyrintiList = siparisAyrintiList;
	}

	public static Kullanici_grubuList getKullanici_grubuList() {
		return kullanici_grubuList;
	}

	public static void setKullanici_grubuList(
			Kullanici_grubuList kullanici_grubuList) {
		DbObjects.kullanici_grubuList = kullanici_grubuList;
	}

	public static KonumList getKonumList() {
		return konumList;
	}

	public static void setKonumList(KonumList konumList) {
		DbObjects.konumList = konumList;
	}

	public static KullaniciList getKullaniciList() {
		return kullaniciList;
	}

	public static void setKullaniciList(KullaniciList kullaniciList) {
		DbObjects.kullaniciList = kullaniciList;
	}

	public static Stok_yeriList getStok_yeriList() {
		return stok_yeriList;
	}

	public static void setStok_yeriList(Stok_yeriList stok_yeriList) {
		DbObjects.stok_yeriList = stok_yeriList;
	}

	public static Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public static StokItemList getSelectedStokList() {
		return selectedStokList;
	}

	public static void setSelectedStokList(StokItemList selectedStokList) {
		DbObjects.selectedStokList = selectedStokList;
	}

	public static MusteriList getMusteriList() {
		return musteriList;
	}

	public static void setMusteriList(MusteriList musteriList) {
		DbObjects.musteriList = musteriList;
	}

	public static SatisItem getSatisItem() {
		return satisItem;
	}

	public static void setSatisItem(SatisItem satisItem) {
		DbObjects.satisItem = satisItem;
	}

	public static StokItemList getStokItemList() {
		return stokItemList;
	}

	public static void setStokItemList(StokItemList stokItemList) {
		DbObjects.stokItemList = stokItemList;
	}

	public static UrungrubuItemList getUrungrubuItemList() {
		return urungrubuItemList;
	}

	public static void setUrungrubuItemList(UrungrubuItemList urungrubuItemList) {
		DbObjects.urungrubuItemList = urungrubuItemList;
	}

	public static Urun_tipItemList getUrunTipItemList() {
		return urunTipItemList;
	}

	public static void setUrunTipItemList(Urun_tipItemList urunTipItemList) {
		DbObjects.urunTipItemList = urunTipItemList;
	}

	public static ModelItemList getModelItemList() {
		return modelItemList;
	}

	public static void setModelItemList(ModelItemList modelItemList) {
		DbObjects.modelItemList = modelItemList;
	}

}
