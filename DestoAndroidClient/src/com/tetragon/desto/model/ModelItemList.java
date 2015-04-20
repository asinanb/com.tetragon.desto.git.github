package com.tetragon.desto.model;

import java.util.ArrayList;

public class ModelItemList extends ArrayList<ModelItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3696405603489889039L;

	public ModelItemList getModelList() {
		return this;
	}

	public ModelItem findModelItemByIdkey(String idkey) {
		ModelItem found = null;
		for (int i = 0; i < size(); i++) {
			ModelItem item = (ModelItem) get(i);
			if (item.getIdkey().equals(idkey)) {
				found = item;
				break;
			}

		}
		return found;
	}

	public void addOrUpdate(ModelItem item){
		if (indexOf(item)<0)
			add (item);
		else 
			set(indexOf(item),item);
	}
	
}