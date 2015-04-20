package com.tetragon.desto.model;

import java.util.ArrayList;

public class UrungrubuItemList extends ArrayList<UrungrubuItem>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4466286993552449804L;
	
	public UrungrubuItemList getUrungrubuList() {
		return this;
	}
	
	
	public UrungrubuItem findUrungrubuItemByIdkey(String idkey) {
		UrungrubuItem found= null;
		for (int i = 0; i < size(); i++) {
			UrungrubuItem item= (UrungrubuItem) get(i);
			if (item.getIdkey().equals(idkey)){
				found=item;
				break;
			}

		} 
		return found;
	}
	
	public void addOrUpdate(UrungrubuItem item){
		if (indexOf(item)<0)
			add (item);
		else 
			set(indexOf(item),item);
	}
}
