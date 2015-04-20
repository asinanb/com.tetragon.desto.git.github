package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;

public class Kullanici extends CloudEntity {

	/**
	 * Kullanici Giris Tablosu Kolon adlarý
	 */
	public static final String KIND_KULLANICI = "Kullanici";
	public static final String LABEL_KULLANICI = "Kullanici";

	
	public static final String PROP_IDKEY = "idkey";
	
	public static final String PROP_KULLANICIADI = "kullanici_adi";
	
	public static final String PROP_AD = "ad";
	
	public static final String PROP_SOYAD = "soyad";
	
	public static final String PROP_GRUP_ID = "grup";
	
	public static final String PROP_KONUM_ID = "konum";
	
	private String idkey;
	private String kullanici_adi;
	private String ad;
	private String soyad;
	private Kullanici_grubu grup;
	private Konum konum;
	
	public Kullanici() {
		super(KIND_KULLANICI);
	}
	
	public Kullanici(String kindName,CloudEntity result) {
		super(kindName);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
		String pidkey="";
		Object object=result.get(Kullanici.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		
		String pkullaniciAdi="";
		object=result.get(Kullanici.PROP_KULLANICIADI);
		if (object!=null){
			pkullaniciAdi=object.toString();
		}
		
		String pAd="";
		object=result.get(Kullanici.PROP_AD);
		if (object!=null){
			pAd=object.toString();
		}
		
		String pSoyad="";
		object=result.get(Kullanici.PROP_SOYAD);
		if (object!=null){
			pSoyad=object.toString();
		}

		String pGrup="";
		object=result.get(Kullanici.PROP_GRUP_ID);
		if (object!=null){
			pGrup=object.toString();
		}
		
		String pKonum="";
		object=result.get(Kullanici.PROP_KONUM_ID);
		if (object!=null){
			pKonum=object.toString();
		}

		put(Kullanici.PROP_IDKEY, pidkey);
		put(Kullanici.PROP_KULLANICIADI, pkullaniciAdi);
		put(Kullanici.PROP_AD, pAd);
		put(Kullanici.PROP_SOYAD, pSoyad);
		put(Kullanici.PROP_GRUP_ID, pGrup);
		put(Kullanici.PROP_KONUM_ID, pKonum);
		
		setIdkey(pidkey);
		setKullanici_adi(pkullaniciAdi);
		setAd(pAd);
		setSoyad(pSoyad);

		Kullanici_grubu kgr=DbObjects.getKullanici_grubuList().findKullanici_grubuByIdkey(pGrup);
		
		if (kgr!=null)
			setGrup(kgr);
		else
			setGrup(Kullanici_grubu.GRUP_SEC);
		
		Konum knm=DbObjects.getKonumList().findKonumByIdkey(pKonum);
		
		if (knm!=null)
			setKonum(knm);
		else
			setKonum(Konum.KONUM_SEC);
		
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String getKullanici_adi() {
		return kullanici_adi;
	}

	public void setKullanici_adi(String kullanici_adi) {
		this.kullanici_adi = kullanici_adi;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public Kullanici_grubu getGrup() {
		return grup;
	}

	public void setGrup(Kullanici_grubu grup) {
		this.grup = grup;
	}

	public Konum getKonum() {
		return konum;
	}

	public void setKonum(Konum konum) {
		this.konum = konum;
	}

	public String toString() {
		return kullanici_adi;
	}
}