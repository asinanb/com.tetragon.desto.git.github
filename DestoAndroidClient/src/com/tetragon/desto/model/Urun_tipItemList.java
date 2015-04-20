package com.tetragon.desto.model;

import java.util.ArrayList;

public class Urun_tipItemList extends ArrayList<Urun_tipItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3205895309168902625L;

	public Urun_tipItemList getUrun_tipList() {
		return this;
	}

	public Urun_tipItem findUrun_tipByIdkey(String idkey) {
		Urun_tipItem found = null;
		for (int i = 0; i < size(); i++) {
			Urun_tipItem item = (Urun_tipItem) get(i);
			if (item.getIdkey().equals(idkey)) {
				found = item;
				break;
			}

		}
		return found;

	}
	
	public void addOrUpdate(Urun_tipItem item){
		if (indexOf(item)<0)
			add (item);
		else 
			set(indexOf(item),item);
	}
}
