package com.tetragon.desto.model;

import java.util.ArrayList;

public class SatisItemList extends ArrayList<SatisItem> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7623524416652380155L;

	
	public SatisItemList getSatisList() {
		return this;
	}

	public SatisItem findSatisItemByIdkey(String idkey) {
		SatisItem found = null;
		for (int i = 0; i < size(); i++) {
			SatisItem satis = (SatisItem) get(i);
			if (satis.getIdkey().equals(idkey)) {
				found = satis;
				break;
			}

		}
		return found;
	}

	
}
