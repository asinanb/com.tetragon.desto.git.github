package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.util.DestoUtil;

public class StokItem {

	private String idkey;
	private ModelItem modelItem;
	private Stok_yeri stok_yeri;
	private int adet;
	private boolean selected=false;
	private int satisAdet=1;
	private CloudEntity stokCE;

	public StokItem(CloudEntity ce) {
		setStokCE(ce);
		
		if (stokCE.get(Stok.PROP_IDKEY)!=null)
			setIdkey(stokCE.get(Stok.PROP_IDKEY).toString());
		
		String pModelid="";
		Object object=stokCE.get(Stok.PROP_MODEL_ID);
		if (object!=null){
			pModelid=object.toString();
		}
		
		ModelItem pmodel=DbObjects.getModelItemList().findModelItemByIdkey(pModelid);
		if (pmodel!=null)
			setModelItem(pmodel);
		else
			setModelItem(ModelItem.MODEL_SEC);
		
		String pStok_yeriid="";
		object=stokCE.get(Stok.PROP_STOKYERI);
		if (object!=null){
			pStok_yeriid=object.toString();
		}
		
		Stok_yeri pstok_yeri=DbObjects.getStok_yeriList().findStok_yeriByIdkey(pStok_yeriid);
		if (pstok_yeri!=null)
			setStok_yeri(pstok_yeri);
		else
			setStok_yeri(Stok_yeri.STOK_YERI_SEC);
		
		if (stokCE.get(Stok.PROP_ADET)!=null)
			setAdet(DestoUtil.stringToInt(stokCE.get(Stok.PROP_ADET).toString()));
	}

	public StokItem() {
		CloudEntity ce = new CloudEntity(Stok.KIND_STOK);
		setStokCE(ce);
		setIdkey(DestoUtil.generateId());
		
	}

	public void assign(StokItem item) {
		setAdet(item.getAdet());
		setModelItem(item.getModelItem());
		setSatisAdet(item.getSatisAdet());
		setSelected(item.isSelected());
		setStok_yeri(item.getStok_yeri());
		setStokCE(item.getStokCE());
	}
	
	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
		getStokCE().put(Stok.PROP_IDKEY, idkey);
	}

	public ModelItem getModelItem() {
		return modelItem;
	}

	public void setModelItem(ModelItem model) {
		this.modelItem = model;
		getStokCE().put(Stok.PROP_MODEL_ID, model.getIdkey());
	}

	public Stok_yeri getStok_yeri() {
		return stok_yeri;
	}

	public void setStok_yeri(Stok_yeri stok_yeri) {
		this.stok_yeri = stok_yeri;
		getStokCE().put(Stok.PROP_STOKYERI, stok_yeri.getIdkey());
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getSatisAdet() {
		return satisAdet;
	}

	public void setSatisAdet(int satisAdet) {
		this.satisAdet = satisAdet;
	}

	public int getAdet() {
		return adet;
	}

	public void setAdet(int adet) {
		this.adet = adet;
		getStokCE().put(Stok.PROP_ADET, adet);
	}

	public CloudEntity getStokCE() {
		return stokCE;
	}

	public void setStokCE(CloudEntity stok) {
		this.stokCE = stok;
	}
	
	public String toString() {
		return getModelItem().getModel();
	}
}