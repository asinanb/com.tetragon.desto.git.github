package com.tetragon.desto.model;

import java.util.ArrayList;

public class MarkaItemList extends ArrayList<MarkaItem>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4466286993552449804L;
	
	public MarkaItemList getMarkaList() {
		return this;
	}
	
	
	public MarkaItem findMarkaByIdkey(String idkey) {
		MarkaItem found= null;
		for (int i = 0; i < size(); i++) {
			MarkaItem markaItem= (MarkaItem) get(i);
			if (markaItem.getIdkey().equals(idkey)){
				found=markaItem;
				break;
			}

		} 
		return found;
	}
	
	public void addOrUpdate(MarkaItem item){
		if (indexOf(item)<0)
			add (item);
		else 
			set(indexOf(item),item);
	}
}
