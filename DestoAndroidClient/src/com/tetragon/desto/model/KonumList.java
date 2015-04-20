package com.tetragon.desto.model;

import java.util.ArrayList;

public class KonumList extends ArrayList<Konum> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8481052531563458205L;

	public KonumList getKullanici_grubuList() {
		return this;
	}

	public Konum findKonumByIdkey(String idkey) {
		Konum found = null;
		for (int i = 0; i < size(); i++) {
			Konum konum = (Konum) get(i);
			if (konum.getIdkey().equals(idkey)) {
				found = konum;
				break;
			}

		}
		return found;
	}

	public void setKonum(Konum result) {
		
		Konum konum = findKonumByIdkey(result.getIdkey());
		
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
		
		konum.setIdkey(pidkey);
		konum.setKonum(pKonum);
		
		set(indexOf(konum), konum);
	}
}
