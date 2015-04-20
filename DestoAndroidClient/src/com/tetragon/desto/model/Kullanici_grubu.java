package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;

public class Kullanici_grubu extends CloudEntity {
	/**
	 * Kullanici_grubu Giris Tablosu Kolon adlarý
	 */
	public static final String KIND_KULLANICIGRUBU = "Kullanici_grubu";
	public static final String LABEL_KULLANICIGRUBU = "Kullanici Grubu";

	public static final String PROP_IDKEY = "idkey";

	public static final String PROP_GRUP = "grup";
	
	public static final Kullanici_grubu GRUP_SEC = new Kullanici_grubu ("0","Grup yok");

	private String idkey;
	private String grup;
	
	public Kullanici_grubu() {
		super(KIND_KULLANICIGRUBU);
	}

	public Kullanici_grubu(String pidkey, String pgrup) {
		super(KIND_KULLANICIGRUBU);
		setIdkey(pidkey);
		setGrup(pgrup);
	}
	
	public Kullanici_grubu(String kindName,CloudEntity result) {
		super(kindName);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
		String pidkey="";
		Object object=result.get(Kullanici_grubu.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		
		String pGrup="";
		object=result.get(Kullanici_grubu.PROP_GRUP);
		if (object!=null){
			pGrup=object.toString();
		}
		
		put(Kullanici_grubu.PROP_IDKEY, pidkey);
		put(Kullanici_grubu.PROP_GRUP, pGrup);
		
		setIdkey(pidkey);
		setGrup(pGrup);
		
	}
	
	public String getGrup() {
		return grup;
	}

	public void setGrup(String grup) {
		this.grup = grup;
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String toString() {
		return grup;
	}
}