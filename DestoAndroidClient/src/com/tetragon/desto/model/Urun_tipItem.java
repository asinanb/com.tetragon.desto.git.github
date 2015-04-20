package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.util.DestoUtil;

public class Urun_tipItem {

	private String idkey;
	private String urun_tip;
    private UrungrubuItem urungrubuItem;
    private CloudEntity urun_tipCE;
    public static final Urun_tipItem URUNTIP_SEC = new Urun_tipItem("Ürün Tipi yok");;

    public Urun_tipItem(MarkaItemList itemList){
		setIdkey(DestoUtil.generateId());
	}
    
    public Urun_tipItem() {
		CloudEntity ce = new CloudEntity(Urun_tip.KIND_URUN_TIP);
		setUrun_tipCE(ce);
		setIdkey(DestoUtil.generateId());
	}
  
    public Urun_tipItem(CloudEntity ce) {
		setUrun_tipCE(ce);
		
		if (urun_tipCE.get(Urun_tip.PROP_IDKEY)!=null)
			setIdkey(urun_tipCE.get(Urun_tip.PROP_IDKEY).toString());
		
		if (urun_tipCE.get(Urun_tip.PROP_URUN_TIP)!=null)
			setUrun_tip(urun_tipCE.get(Urun_tip.PROP_URUN_TIP).toString());
		
		String pUrungrubuId="";
		Object object=urun_tipCE.get(Urun_tip.PROP_URUNGRUP_ID);
		if (object!=null){
			pUrungrubuId=object.toString();
		}
		UrungrubuItem pUrungrubuItem=DbObjects.getUrungrubuItemList().findUrungrubuItemByIdkey(pUrungrubuId);
		if (pUrungrubuItem!=null)
			setUrungrubuItem(pUrungrubuItem);
		else
			setUrungrubuItem(UrungrubuItem.URUNGRUBU_SEC);
		
	}

    public void assign(Urun_tipItem item) {
		setUrun_tip(item.getUrun_tip());
		setUrungrubuItem(item.getUrungrubuItem());
		setUrun_tipCE(item.getUrun_tipCE());
	}
    
	public Urun_tipItem(String string) {
		setUrun_tip(string);
	}
	
    public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String pidkey) {
		this.idkey = pidkey;
		if(getUrun_tipCE()!=null)
			getUrun_tipCE().put(Urun_tip.PROP_IDKEY, pidkey);
	}

	public String getUrun_tip() {
		return urun_tip;
	}

	public void setUrun_tip(String purun_tip) {
		this.urun_tip = purun_tip;
		if(getUrun_tipCE()!=null)
			getUrun_tipCE().put(Urun_tip.PROP_URUN_TIP, purun_tip);
	}

	public CloudEntity getUrun_tipCE() {
		return urun_tipCE;
	}

	public void setUrun_tipCE(CloudEntity urun_tipCE) {
		this.urun_tipCE = urun_tipCE;
	}

	public UrungrubuItem getUrungrubuItem() {
		return urungrubuItem;
	}

	public void setUrungrubuItem(UrungrubuItem purungrubuItem) {
		this.urungrubuItem = purungrubuItem;
		if(getUrun_tipCE()!=null)
			getUrun_tipCE().put(Urun_tip.PROP_URUNGRUP_ID, purungrubuItem.getIdkey());
	}
	
	public String toString(){
		return getUrun_tip();
	}

}