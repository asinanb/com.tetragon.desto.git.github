package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;

public class Konum extends CloudEntity {
	/**
	 * Kullanici Giris Tablosu Kolon adlarý
	 */
	public static final String KIND_KONUM = "Konum";
	public static final String LABEL_KONUM = "Konum";

	public static final String PROP_IDKEY = "idkey";

	public static final String PROP_KONUM = "konum";
	
	public static final Konum KONUM_SEC = new Konum("0","Grup yok");

	private String idkey;
	private String konum;

	public Konum(String pidkey, String pkonum) {
		super(KIND_KONUM);
		setIdkey(pidkey);
		setKonum(pkonum);
	}

	public Konum (String kindName,CloudEntity result) {
		super(kindName);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
		String pidkey="";
		Object object=result.get(Konum.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		
		String pKonum="";
		object=result.get(Konum.PROP_KONUM);
		if (object!=null){
			pKonum=object.toString();
		}
		
		put(Konum.PROP_IDKEY, pidkey);
		put(Konum.PROP_KONUM, pKonum);
		
		setIdkey(pidkey);
		setKonum(pKonum);
		
	}
	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String getKonum() {
		return konum;
	}

	public void setKonum(String konum) {
		this.konum = konum;
	}

	public String toString() {
		return konum;
	}
}