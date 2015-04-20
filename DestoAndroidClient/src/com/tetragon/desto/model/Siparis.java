package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;

public class Siparis extends CloudEntity {

	/**
	 * Siparis Tablosu
	 * Kolon adlarý
	 */
	public static final String KIND_SIPARIS = "Siparis";
	public static final String LABEL_SIPARIS = "Siparis";
	public static final String PROP_IDKEY = "idkey";
	public static final String PROP_ETIKET = "etiket";
	public static final String PROP_MARKA_ID = "marka";
	public static final String PROP_SIPARIS_TARIHI = "siparisTarihi";
	public static final String PROP_SIPARIS_DURUMU = "siparisDurumu";
	public static final Siparis SIPARIS_SEC = new Siparis("Siparis yok");
	public static final int SIPARIS_ADET_MAX = 100;

	private String idkey;
	private String etiket;
	private MarkaItem markaItem;
	private String siparisTarihi;
	private String siparisDurumu;

	
	public Siparis(String pEtiket) {
		super(KIND_SIPARIS);
		setEtiket(pEtiket);
	}

	public Siparis(String kindName, String idkey, String etiket, MarkaItem marka,
			String siparisTarihi, String siparisDurumu) {
		super(kindName);
		this.idkey = idkey;
		this.etiket = etiket;
		this.markaItem = marka;
		this.siparisTarihi = siparisTarihi;
		this.siparisDurumu = siparisDurumu;
	}
	
	public Siparis(String kindName,CloudEntity result) {
		super(kindName);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
		String pidkey="";
		Object object=result.get(Siparis.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		String pEtiket="";
		object=result.get(Siparis.PROP_ETIKET);
		if (object!=null){
			pEtiket=object.toString();
		}
		String pMarka="";
		object=result.get(Siparis.PROP_MARKA_ID);
		if (object!=null){
			pMarka=object.toString();
		}
		String pSiparisTarihi="";
		object=result.get(Siparis.PROP_SIPARIS_TARIHI);
		if (object!=null){
			pSiparisTarihi=object.toString();
		}
		String pSiparisDurumu="";
		object=result.get(Siparis.PROP_SIPARIS_DURUMU);
		if (object!=null){
			pSiparisDurumu=object.toString();
		}
		
		put(Siparis.PROP_IDKEY, pidkey);
		put(Siparis.PROP_ETIKET, pEtiket);
		put(Siparis.PROP_MARKA_ID, pMarka);
		put(Siparis.PROP_SIPARIS_TARIHI, pSiparisTarihi);
		put(Siparis.PROP_SIPARIS_DURUMU, pSiparisDurumu);
		
		setIdkey(pidkey);
		setEtiket(pEtiket);
		setSiparisTarihi(pSiparisTarihi);
		setSiparisDurumu(pSiparisDurumu);
		
		MarkaItem mrk=DbObjects.getMarkaItemList().findMarkaByIdkey(pMarka);
		if (mrk!=null)
			setMarkaItem(mrk);
		else
			setMarkaItem(MarkaItem.MARKA_SEC);
		
	}
	
	public String getIdkey() {
		return idkey;
	}
	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}
	public String getEtiket() {
		return etiket;
	}
	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}
	public MarkaItem getMarkaItem() {
		return markaItem;
	}
	public void setMarkaItem(MarkaItem marka) {
		this.markaItem = marka;
	}
	public String getSiparisTarihi() {
		return siparisTarihi;
	}
	public void setSiparisTarihi(String siparisTarihi) {
		this.siparisTarihi = siparisTarihi;
	}
	public String getSiparisDurumu() {
		return siparisDurumu;
	}
	public void setSiparisDurumu(String siparisDurumu) {
		this.siparisDurumu = siparisDurumu;
	}
	
	@Override
	public String toString() {
		return etiket;
	}
}