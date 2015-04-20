package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.tetragon.desto.util.DestoUtil;

public class UrungrubuItem {

	private String idkey;
	private String urungrubu;
	private CloudEntity urungrubuCE;
	public static final UrungrubuItem URUNGRUBU_SEC = new UrungrubuItem(
			"Ürün Grubu yok");
	
	public UrungrubuItem(CloudEntity ce) {
		setUrungrubuCE(ce);
		
		if (urungrubuCE.get(Urungrubu.PROP_IDKEY)!=null)
			setIdkey(urungrubuCE.get(Urungrubu.PROP_IDKEY).toString());
		if (urungrubuCE.get(Urungrubu.PROP_URUNGRUBU)!=null)
			setUrungrubu(urungrubuCE.get(Urungrubu.PROP_URUNGRUBU).toString());
	}

	public UrungrubuItem() {
		CloudEntity ce = new CloudEntity(Urungrubu.KIND_URUNGRUBU);
		setUrungrubuCE(ce);
		setIdkey(DestoUtil.generateId());
	}
	
	public void assign(UrungrubuItem item) {
		setUrungrubu(item.getUrungrubu());
		setUrungrubuCE(item.getUrungrubuCE());
	}
	
	public UrungrubuItem(String string) {
		setUrungrubu(string);
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String pidkey) {
		this.idkey = pidkey;
		if(getUrungrubuCE()!=null)
			getUrungrubuCE().put(Urungrubu.PROP_IDKEY, pidkey);
	}


	public String getUrungrubu() {
		return urungrubu;
	}

	public void setUrungrubu(String urungrubu) {
		this.urungrubu = urungrubu;
		if(getUrungrubuCE()!=null)
			getUrungrubuCE().put(Urungrubu.PROP_URUNGRUBU, urungrubu);
	}
	public CloudEntity getUrungrubuCE() {
		return urungrubuCE;
	}

	public void setUrungrubuCE(CloudEntity urungrubuCE) {
		this.urungrubuCE = urungrubuCE;
	}
	public String toString(){
		return getUrungrubu();
	}
}