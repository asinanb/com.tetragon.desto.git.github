package com.tetragon.desto;
import com.tetragon.desto.model.StokItemList;

import android.app.Application;
 
public class DestoApplication extends Application{
     
    private boolean satisTamamlandi;
//    private StokItemList stokItemList = new StokItemList();
//    private Kullanici_grubuList kullanici_grubuList = new Kullanici_grubuList();
    
	public boolean isSatisTamamlandi() {
		return satisTamamlandi;
	}

	public void setSatisTamamlandi(boolean satisTamamlandi) {
		this.satisTamamlandi = satisTamamlandi;
	}

//	public StokItemList getStokItemList() {
//		return stokItemList;
//	}
//
//	public void setStokItemList(StokItemList stokItemList) {
//		this.stokItemList = stokItemList;
//	}

//	public Kullanici_grubuList getKullanici_grubuList() {
//		return kullanici_grubuList;
//	}
//
//	public void setKullanici_grubuList(Kullanici_grubuList kullanici_grubuList) {
//		this.kullanici_grubuList = kullanici_grubuList;
//	}
 
}