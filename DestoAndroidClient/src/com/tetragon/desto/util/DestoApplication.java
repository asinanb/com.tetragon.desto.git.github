package com.tetragon.desto.util;
import com.tetragon.desto.model.StokItemList;

import android.app.Application;
 
public class DestoApplication extends Application{
     
    private boolean satisTamamlandi;
    private StokItemList stokItemList = new StokItemList();
    
	public boolean isSatisTamamlandi() {
		return satisTamamlandi;
	}

	public void setSatisTamamlandi(boolean satisTamamlandi) {
		this.satisTamamlandi = satisTamamlandi;
	}

	public StokItemList getStokItemList() {
		return stokItemList;
	}

	public void setStokItemList(StokItemList stokItemList) {
		this.stokItemList = stokItemList;
	}
 
}