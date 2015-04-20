package com.tetragon.desto.util;
import android.app.Application;
 
public class DestoApplication extends Application{
     
    private boolean satisTamamlandi;

	public boolean isSatisTamamlandi() {
		return satisTamamlandi;
	}

	public void setSatisTamamlandi(boolean satisTamamlandi) {
		this.satisTamamlandi = satisTamamlandi;
	}
 
}