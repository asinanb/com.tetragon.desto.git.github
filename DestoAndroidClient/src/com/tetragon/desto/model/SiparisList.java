package com.tetragon.desto.model;

import java.util.ArrayList;

import com.google.cloud.backend.core.DbObjects;

public class SiparisList extends ArrayList<Siparis> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 309034410956296630L;

	public SiparisList getSiparisList() {
		return this;
	}

	public Siparis findSiparisByIdkey(String idkey) {
		Siparis found = null;
		for (int i = 0; i < size(); i++) {
			Siparis siparis = (Siparis) get(i);
			if (siparis.getIdkey().equals(idkey)) {
				found = siparis;
				break;
			}

		}
		return found;
	}

	public void setSiparis(Siparis result) {
		
		Siparis siparis = findSiparisByIdkey(result.getIdkey());
		
		String pidkey="";
		Object object=result.get(Siparis.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		
		String pEtiket="";
		object=result.get(Siparis.PROP_ETIKET);
		if (object!=null){
			pEtiket=object.toString();
		}
		String pMarkaId="";
		object=result.get(Siparis.PROP_MARKA_ID);
		if (object!=null){
			pMarkaId=object.toString();
		}
		String pSiparisTarihi="";
		object=result.get(Siparis.PROP_SIPARIS_TARIHI);
		if (object!=null){
			pSiparisTarihi=object.toString();
		}
		String pSiparisDurumu="";
		object=result.get(Siparis.PROP_SIPARIS_DURUMU);
		if (object!=null){
			pSiparisDurumu=object.toString();
		}
		siparis.setIdkey(pidkey);
		siparis.setEtiket(pEtiket);
		siparis.setSiparisTarihi(pSiparisTarihi);
		siparis.setSiparisDurumu(pSiparisDurumu);
		
		MarkaItem mrk=DbObjects.getMarkaItemList().findMarkaByIdkey(pMarkaId);
		if (mrk!=null)
			siparis.setMarkaItem(mrk);
		else
			siparis.setMarkaItem(MarkaItem.MARKA_SEC);

		set(indexOf(siparis), siparis);
	}
}
