package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.tetragon.desto.util.DestoUtil;

public class Stok_yeri extends CloudEntity {

	/**
	 * Stok Giris Tablosu Kolon adlarý
	 */
	public static final String KIND_STOK_YERI = "Stok_yeri";
	public static final String LABEL_STOK_YERI = "Stok Yeri";

	
	public static final String PROP_IDKEY = "idkey";
	
	public static final String PROP_STOKYERI = "stok_yeri";
	public static final Stok_yeri STOK_YERI_SEC = new Stok_yeri("Stok yeri yok");
	
	private String idkey;
	private String stok_yeri;
	
	public Stok_yeri (String pStokYeri) {
		super(KIND_STOK_YERI);
		setStok_yeri(pStokYeri);
	}
	
	public Stok_yeri (String kindname,String pStokYeri) {
		super(kindname);
		setIdkey(DestoUtil.generateId());
		setStok_yeri(pStokYeri);
	}

	public Stok_yeri(String kindName,CloudEntity result) {
		super(kindName);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
		String pidkey="";
		Object object=result.get(Stok_yeri.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		
		String pStokyeri="";
		object=result.get(Stok_yeri.PROP_STOKYERI);
		if (object!=null){
			pStokyeri=object.toString();
		}
		
		put(Stok_yeri.PROP_STOKYERI, pStokyeri);
		put(Stok_yeri.PROP_IDKEY, String.valueOf(pidkey));
		
		setIdkey(pidkey);
		setStok_yeri(pStokyeri);
		
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String getStok_yeri() {
		return stok_yeri;
	}

	public void setStok_yeri(String stok_yeri) {
		this.stok_yeri = stok_yeri;
	}

	@Override
	public String toString() {
		return stok_yeri;
	}

}