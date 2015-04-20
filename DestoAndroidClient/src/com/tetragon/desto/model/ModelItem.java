package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.util.DestoUtil;

public class ModelItem {

	private String idkey;
	private String model;
	private MarkaItem markaItem;
	private UrungrubuItem urungrubuItem;
	private Urun_tipItem urun_tipItem;
	private String listeFiyati;

	private CloudEntity modelCE;
	public static final ModelItem MODEL_SEC = new ModelItem ("Model yok");
	
	public ModelItem() {
		CloudEntity ce = new CloudEntity(Model.KIND_MODEL);
		setModelCE(ce);
		setIdkey(DestoUtil.generateId());
	}
	
	public ModelItem(CloudEntity ce) {
		setModelCE(ce);

		if (modelCE.get(Model.PROP_IDKEY) != null)
			setIdkey(modelCE.get(Model.PROP_IDKEY).toString());

		if (modelCE.get(Model.PROP_MODEL) != null)
			setModel(modelCE.get(Model.PROP_MODEL).toString());
		
		if (modelCE.get(Model.PROP_FIYAT) != null)
			setListeFiyati(modelCE.get(Model.PROP_FIYAT).toString());

		String pMarkaId = "";
		Object object = modelCE.get(Model.PROP_MARKA_ID);
		if (object != null) {
			pMarkaId = object.toString();
		}
		MarkaItem pMarkaItem = DbObjects.getMarkaItemList().findMarkaByIdkey(
				pMarkaId);
		if (pMarkaItem != null)
			setMarkaItem(pMarkaItem);
		else
			setMarkaItem(MarkaItem.MARKA_SEC);

		String pUrungrubuId = "";
		object = modelCE.get(Model.PROP_URUNGRUP_ID);
		if (object != null) {
			pUrungrubuId = object.toString();
		}
		UrungrubuItem pUrungrubuItem = DbObjects.getUrungrubuItemList()
				.findUrungrubuItemByIdkey(pUrungrubuId);
		if (pUrungrubuItem != null)
			setUrungrubuItem(pUrungrubuItem);
		else
			setUrungrubuItem(UrungrubuItem.URUNGRUBU_SEC);

		String pUrun_tipId = "";
		object = modelCE.get(Model.PROP_URUN_TIP_ID);
		if (object != null) {
			pUrun_tipId = object.toString();
		}
		Urun_tipItem pUrun_tipItem = DbObjects.getUrunTipItemList()
				.findUrun_tipByIdkey(pUrun_tipId);
		if (pUrun_tipItem != null)
			setUrun_tipItem(pUrun_tipItem);
		else
			setUrun_tipItem(Urun_tipItem.URUNTIP_SEC);

	}

	public void assign(ModelItem item) {
		setMarkaItem(item.getMarkaItem());
		setModel(item.getModel());
		setUrun_tipItem(item.getUrun_tipItem());
		setUrungrubuItem(item.getUrungrubuItem());
		setListeFiyati(item.getListeFiyati());
		setModelCE(item.getModelCE());
	}

	public ModelItem(String string) {
		setModel(string);
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
		if (getModelCE()!=null)
			getModelCE().put(Model.PROP_IDKEY, idkey);
	}

	public MarkaItem getMarkaItem() {
		return markaItem;
	}

	public void setMarkaItem(MarkaItem markaItem) {
		this.markaItem = markaItem;
		if (getModelCE()!=null)
			getModelCE().put(Model.PROP_MARKA_ID, markaItem.getIdkey());
	}

	public UrungrubuItem getUrungrubuItem() {
		return urungrubuItem;
	}

	public void setUrungrubuItem(UrungrubuItem urungrubuItem) {
		this.urungrubuItem = urungrubuItem;
		if (getModelCE()!=null)
			getModelCE().put(Model.PROP_URUNGRUP_ID, urungrubuItem.getIdkey());
	}

	public Urun_tipItem getUrun_tipItem() {
		return urun_tipItem;
	}

	public void setUrun_tipItem(Urun_tipItem urun_tipItem) {
		this.urun_tipItem = urun_tipItem;
		if (getModelCE()!=null)
			getModelCE().put(Model.PROP_URUN_TIP_ID, urun_tipItem.getIdkey());
	}

	public CloudEntity getModelCE() {
		return modelCE;
	}

	public void setModelCE(CloudEntity modelCE) {
		this.modelCE = modelCE;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
		if (getModelCE()!=null)
			getModelCE().put(Model.PROP_MODEL, model);
	}

	public String getListeFiyati() {
		return listeFiyati;
	}

	public void setListeFiyati(String listeFiyati) {
		this.listeFiyati = listeFiyati;
		if (getModelCE()!=null)
			getModelCE().put(Model.PROP_FIYAT, listeFiyati);
	}

	public String toString() {
		return getModel();
	}
}