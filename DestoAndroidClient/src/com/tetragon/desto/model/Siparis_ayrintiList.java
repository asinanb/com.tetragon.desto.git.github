package com.tetragon.desto.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

import com.google.cloud.backend.core.DbObjects;

public class Siparis_ayrintiList extends ArrayList<Siparis_ayrinti> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 309034410956296630L;

	public Siparis_ayrintiList getSiparisList() {
		return this;
	}

	public Siparis_ayrinti findSiparisAyrintiByIdkey(String idkey) {
		Siparis_ayrinti found = null;
		for (int i = 0; i < size(); i++) {
			Siparis_ayrinti siparisAyrinti = (Siparis_ayrinti) get(i);
			if (siparisAyrinti.getIdkey().equals(idkey)) {
				found = siparisAyrinti;
				break;
			}

		}
		return found;
	}

	public void setSiparis_ayrinti(Siparis_ayrinti result) {
		Siparis_ayrinti siparisAyrinti = findSiparisAyrintiByIdkey(result
				.getIdkey());

		String pSiparis = "";
		Object object = result.get(Siparis_ayrinti.PROP_SIPARIS_ID);
		if (object != null) {
			pSiparis = object.toString();
		}
		String pModel = "";
		object = result.get(Siparis_ayrinti.PROP_MODEL_ID);
		if (object != null) {
			pModel = object.toString();
		}
		String pAdet = "";
		object = result.get(Siparis_ayrinti.PROP_SIPARIS_ADEDI);
		if (object != null) {
			pAdet = object.toString();
		}

		Siparis spr = DbObjects.getSiparisList().findSiparisByIdkey(pSiparis);

		if (spr != null)
			siparisAyrinti.setSiparis(spr);
		else
			siparisAyrinti.setSiparis(Siparis.SIPARIS_SEC);

		ModelItem mdl = DbObjects.getModelItemList().findModelItemByIdkey(pModel);

		if (mdl != null)
			siparisAyrinti.setModelItem(mdl);
		else
			siparisAyrinti.setModelItem(ModelItem.MODEL_SEC);

		siparisAyrinti.setSiparis_adedi(pAdet);

		set(indexOf(siparisAyrinti), siparisAyrinti);
	}
	public Siparis_ayrintiList sort(){
		Collections.sort(getSiparisList(),new AdetComparator());
		return this;
		
	}

	class AdetComparator implements Comparator<Siparis_ayrinti>
	{

		@Override
		public int compare(Siparis_ayrinti lhs, Siparis_ayrinti rhs) {
			int s1=Integer.valueOf(lhs.getSiparis_adedi());
			int s2=Integer.valueOf(lhs.getGelen_adet());
			int g1=Integer.valueOf(rhs.getSiparis_adedi());
			int g2=Integer.valueOf(rhs.getGelen_adet());

			int ss=s1-s2;
			int gg=g1-g2;

			if (ss > gg)
	            return -1;
	        else if (ss < gg)
	            return 1;
	        else
	            return 0;
		}

	}
}
