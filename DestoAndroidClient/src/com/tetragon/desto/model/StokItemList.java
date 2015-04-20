package com.tetragon.desto.model;

import java.util.ArrayList;

public class StokItemList extends ArrayList<StokItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 309034410956296630L;

	public StokItemList getStokItemList() {
		return this;
	}
	
	public StokItem findStokByIdkey(String idkey) {
		StokItem found = null;
		for (int i = 0; i < size(); i++) {
			StokItem stok = (StokItem) get(i);
			if (stok.getIdkey().equals(idkey)) {
				found = stok;
				break;
			}

		}
		return found;
	}

	public StokItem findStokByModelId(String idkey) {
		StokItem found = null;
		for (int i = 0; i < size(); i++) {
			StokItem stok = (StokItem) get(i);
			if (stok.getModelItem().getIdkey().equals(idkey)) {
				found = stok;
				break;
			}

		}
		return found;
	}

	public StokItem findStokByStokYeriAndModelId(String stok_yeri, String idkey) {
		StokItem found = null;
		for (int i = 0; i < size(); i++) {
			StokItem stok = (StokItem) get(i);
			if (stok.getModelItem().getIdkey().equals(idkey)) {
				if (stok.getStok_yeri().getIdkey().equals(stok_yeri)) {
					found = stok;
					break;
				}
			}

		}
		return found;
	}

	public void setSelectedAll(boolean selected){
		for (int i = 0; i < size(); i++) {
			get(i).setSelected(selected);
		}
		
	}
	
	public void addOrUpdate(StokItem stokItem){
		if (indexOf(stokItem)<0)
			add (stokItem);
		else 
			set(indexOf(stokItem),stokItem);
	}
}
