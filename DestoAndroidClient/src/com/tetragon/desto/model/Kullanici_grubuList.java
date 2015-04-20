package com.tetragon.desto.model;

import java.util.LinkedList;

public class Kullanici_grubuList extends LinkedList<Kullanici_grubu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7818753765652612361L;

	public Kullanici_grubuList getKullanici_grubuList() {
		return this;
	}

	public Kullanici_grubu findKullanici_grubuByIdkey(String idkey) {
		Kullanici_grubu found = null;
		for (int i = 0; i < size(); i++) {
			Kullanici_grubu kullanici_grubu = (Kullanici_grubu) get(i);
			if (kullanici_grubu.getIdkey().equals(idkey)) {
				found = kullanici_grubu;
				break;
			}

		}
		return found;
	}

	public void setKullanici_grubu(Kullanici_grubu result) {
		
		Kullanici_grubu kullanici_grubu = findKullanici_grubuByIdkey(result.getIdkey());
		
		String pidkey="";
		Object object=result.get(Kullanici_grubu.PROP_IDKEY);
		if (object!=null){
			pidkey=object.toString();
		}
		String pGrup="";
		object=result.get(Kullanici_grubu.PROP_GRUP);
		if (object!=null){
			pGrup=object.toString();
		}
		
		kullanici_grubu.setIdkey(pidkey);
		kullanici_grubu.setGrup(pGrup);
		
		set(indexOf(kullanici_grubu), kullanici_grubu);
	}
}
