package com.tetragon.desto.model;

import java.util.ArrayList;

public class MusteriList extends ArrayList<Musteri>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4466286993552449804L;
	
	public MusteriList getMusteriList() {
		return this;
	}
	
	
	public Musteri findMusteriByIdkey(String idkey) {
		Musteri found= null;
		for (int i = 0; i < size(); i++) {
			Musteri m= (Musteri) get(i);
			if (m.getIdkey().equals(idkey)){
				found=m;
				break;
			}

		} 
		return found;
	}
	
	public void setMusteri(Musteri result) {
		Musteri musteri= findMusteriByIdkey(result.getIdkey());

		String pidkey="";
		Object object=result.get(Musteri.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		
		String pmusteriAdi="";
		object=result.get(Musteri.PROP_ADI);
		if (object!=null){
			pmusteriAdi=object.toString();
		}
		
		String pmusteriSoyadi="";
		object=result.get(Musteri.PROP_SOYADI);
		if (object!=null){
			pmusteriSoyadi=object.toString();
		}

		String padres="";
		object=result.get(Musteri.PROP_ADRES);
		if (object!=null){
			padres=object.toString();
		}
		
		musteri.setIdkey(pidkey);
		musteri.setAdi(pmusteriAdi);
		musteri.setSoyadi(pmusteriSoyadi);
		musteri.setAdres(padres);
		
		set(indexOf(musteri),musteri);
	}
}
