package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;

public class Siparis_ayrinti extends CloudEntity {

	/**
	 * Siparis Ayrýntý Tablosu Kolon adlarý
	 */
	public static final String KIND_SIPARISAYRINTI = "Siparis_ayrinti";
	public static final String LABEL_SIPARISAYRINTI = "Siparis Ayrýntýlarý";

	
	public static final String PROP_IDKEY = "idkey";
	public static final String PROP_SIPARIS_ID = "siparis";
	public static final String PROP_MODEL_ID = "model";
	public static final String PROP_SIPARIS_ADEDI = "siparis_adedi";
	public static final String PROP_GELEN_ADET = "gelen_adet";
	public static final String PROP_SIPARISDURUMU = "siparis_durumu";
	
	/*
	 * Siparis durumu
	 * 			Hazýrlandý
	 * 			Firmada
	 * 			Kargoda
	 * 			Depoya girdi
	 * 			
	 */
	private String idkey;
	private Siparis siparis;
	private ModelItem modelItem;
	private String siparis_adedi;
	private String gelen_adet;
	
	public Siparis_ayrinti(String pSiparis_ayrinti) {
		super(KIND_SIPARISAYRINTI);
		// TODO Auto-generated constructor stub
	}

	public Siparis_ayrinti(String kindName,CloudEntity result) {
		super(kindName);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
		String pidkey="";
		Object object=result.get(Siparis_ayrinti.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		String pSiparis="";
		object=result.get(Siparis_ayrinti.PROP_SIPARIS_ID);
		if (object!=null){
			pSiparis=object.toString();
		}
		String pModel="";
		object=result.get(Siparis_ayrinti.PROP_MODEL_ID);
		if (object!=null){
			pModel=object.toString();
		}
		String pSiparisAdedi="0";
		object=result.get(Siparis_ayrinti.PROP_SIPARIS_ADEDI);
		if (object!=null){
			pSiparisAdedi=object.toString();
		}

		String pgelenAdet="0";
		object=result.get(Siparis_ayrinti.PROP_GELEN_ADET);
		if (object!=null){
			pgelenAdet=object.toString();
		}

		put(Siparis_ayrinti.PROP_IDKEY, pidkey);
		put(Siparis_ayrinti.PROP_SIPARIS_ID, pSiparis);
		put(Siparis_ayrinti.PROP_MODEL_ID, pModel);
		put(Siparis_ayrinti.PROP_SIPARIS_ADEDI, pSiparisAdedi);
		put(Siparis_ayrinti.PROP_GELEN_ADET, pgelenAdet);
		
		setIdkey(pidkey);
		setSiparis_adedi(pSiparisAdedi);
		setGelen_adet(pgelenAdet);
		
		ModelItem mdl=DbObjects.getModelItemList().findModelItemByIdkey(pModel);
		
		if (mdl!=null)
			setModelItem(mdl);
		else
			setModelItem(ModelItem.MODEL_SEC);
		
		Siparis spr=DbObjects.getSiparisList().findSiparisByIdkey(pSiparis);
		
		if (spr!=null)
			setSiparis(spr);
		else
			setSiparis(Siparis.SIPARIS_SEC);
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public ModelItem getModelItem() {
		return modelItem;
	}

	public void setModelItem(ModelItem model) {
		this.modelItem = model;
	}

	public Siparis getSiparis() {
		return siparis;
	}

	public void setSiparis(Siparis siparis) {
		this.siparis = siparis;
	}

	public String getSiparis_adedi() {
		return siparis_adedi;
	}

	public void setSiparis_adedi(String siparis_adedi) {
		this.siparis_adedi = siparis_adedi;
	}

	public String getGelen_adet() {
		return gelen_adet;
	}

	public void setGelen_adet(String gelen_adet) {
		this.gelen_adet = gelen_adet;
	}
	

}