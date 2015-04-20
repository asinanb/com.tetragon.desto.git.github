package com.tetragon.desto.model;

import java.util.ArrayList;

import com.google.cloud.backend.core.DbObjects;

public class KullaniciList extends ArrayList<Kullanici> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2272208980634672750L;

	public KullaniciList getKullanici_grubuList() {
		return this;
	}

	public Kullanici findKullaniciByIdkey(String idkey) {
		Kullanici found = null;
		for (int i = 0; i < size(); i++) {
			Kullanici kullanici = (Kullanici) get(i);
			if (kullanici.getIdkey().equals(idkey)) {
				found = kullanici;
				break;
			}

		}
		return found;
	}

	public void setKullanici(Kullanici result) {
		
		Kullanici kullanici = findKullaniciByIdkey(result.getIdkey());
		
		String pidkey="";
		Object object=result.get(Kullanici.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		String pKullanici_adi="";
		object=result.get(Kullanici.PROP_KULLANICIADI);
		if (object!=null){
			pKullanici_adi=object.toString();
		}
		
		String pAd="";
		object=result.get(Kullanici.PROP_AD);
		if (object!=null){
			pAd=object.toString();
		}

		String pSoyad="";
		object=result.get(Kullanici.PROP_SOYAD);
		if (object!=null){
			pSoyad=object.toString();
		}

		String pGrup="";
		object=result.get(Kullanici.PROP_GRUP_ID);
		if (object!=null){
			pGrup=object.toString();
		}
		
		String pKonum="";
		object=result.get(Kullanici.PROP_KONUM_ID);
		if (object!=null){
			pKonum=object.toString();
		}

		
		Kullanici_grubu kgr =DbObjects.getKullanici_grubuList().findKullanici_grubuByIdkey(pGrup);
		
		if (kgr!=null)
			kullanici.setGrup(kgr);
		else
			kullanici.setGrup(Kullanici_grubu.GRUP_SEC);
		
		Konum knm =DbObjects.getKonumList().findKonumByIdkey(pKonum);
		
		if (knm!=null)
			kullanici.setKonum(knm);
		else
			kullanici.setKonum(Konum.KONUM_SEC);
		
		kullanici.setIdkey(pidkey);
		kullanici.setAd(pAd);
		kullanici.setSoyad(pSoyad);
		kullanici.setKullanici_adi(pKullanici_adi);
		
		set(indexOf(kullanici), kullanici);
	}
}
