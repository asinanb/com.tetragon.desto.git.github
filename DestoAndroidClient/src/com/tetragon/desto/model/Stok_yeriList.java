package com.tetragon.desto.model;

import java.util.ArrayList;

public class Stok_yeriList extends ArrayList<Stok_yeri> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4928860944799979973L;

	public Stok_yeriList getStok_yeriList() {
		return this;
	}

	public Stok_yeri findStok_yeriByIdkey(String idkey) {
		Stok_yeri found = null;
		for (int i = 0; i < size(); i++) {
			Stok_yeri stok_yeri = (Stok_yeri) get(i);
			if (stok_yeri.getIdkey().equals(idkey)) {
				found = stok_yeri;
				break;
			}

		}
		return found;
	}

	public void setStok_yeri(Stok_yeri result) {

		Stok_yeri stok_yeri = findStok_yeriByIdkey(result.getIdkey());

		String pidkey = "";
		Object object = result.get(Siparis.PROP_IDKEY);
		if (object != null) {
			pidkey = object.toString();
		}

		String pStokYeri = "";
		object = result.get(Stok_yeri.PROP_STOKYERI);
		if (object != null) {
			pStokYeri = object.toString();
		}
		
		stok_yeri.setIdkey(pidkey);
		stok_yeri.setStok_yeri(pStokYeri);

		set(indexOf(stok_yeri), stok_yeri);
	}
}
