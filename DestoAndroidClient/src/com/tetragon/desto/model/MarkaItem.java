package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;
import com.tetragon.desto.util.DestoUtil;

public class MarkaItem implements ItemInterface {

	private String idkey;
	private String marka;
	private String firma;
	private CloudEntity markaCE;
	public static MarkaItem MARKA_SEC = new MarkaItem("Marka yok");

	public MarkaItem(MarkaItemList itemList) {
		setIdkey(DestoUtil.generateId());
	}

	public MarkaItem(CloudEntity ce) {
		setMarkaCE(ce);

		if (markaCE.get(Marka.PROP_IDKEY) != null)
			setIdkey(markaCE.get(Marka.PROP_IDKEY).toString());

		if (markaCE.get(Marka.PROP_MARKA) != null)
			setMarka(markaCE.get(Marka.PROP_MARKA).toString());

		if (markaCE.get(Marka.PROP_FIRMA) != null)
			setFirma(markaCE.get(Marka.PROP_FIRMA).toString());
	}

	public MarkaItem() {
		CloudEntity markaCE = new CloudEntity(Marka.KIND_MARKA);
		setMarkaCE(markaCE);
		setIdkey(DestoUtil.generateId());
	}
	
	public void assign(MarkaItem item) {
		setMarka(item.getMarka());
		setFirma(item.getFirma());
		setMarkaCE(item.getMarkaCE());
	}
	
	public MarkaItem(String string) {
		setMarka(string);
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String pidkey) {
		this.idkey = pidkey;
		if (getMarkaCE()!=null)
			getMarkaCE().put(Marka.PROP_IDKEY, pidkey);
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String pmarka) {
		this.marka = pmarka;
		if (getMarkaCE()!=null)
			getMarkaCE().put(Marka.PROP_MARKA, pmarka);
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String pfirma) {
		this.firma = pfirma;
		if (getMarkaCE()!=null)
			getMarkaCE().put(Marka.PROP_FIRMA, pfirma);
	}

	public CloudEntity getMarkaCE() {
		return markaCE;
	}

	public void setMarkaCE(CloudEntity markaCE) {
		this.markaCE = markaCE;
	}

	public String toString() {
		return getMarka();
	}

}