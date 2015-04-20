package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;

public class Musteri extends CloudEntity {

	/**
	 * Musteri Tablosu
	 * Kolon adlarý
	 */
	public static final String KIND_MUSTERI = "Musteri";
	
	public static final String LABEL_MUSTERI = "Müþteri";
	
    public static final String PROP_IDKEY = "idkey";

    public static final String PROP_ADI = "adi";

    public static final String PROP_SOYADI = "soyadi";
    
    public static final String PROP_ADRES = "adres";

	public static Musteri MUSTERI_SEC= new Musteri ("Musteri yok");

    private String idkey;

    private String adi;
    
    private String soyadi;

    private String adres;

	public Musteri(String pMusteriId) {
		super(KIND_MUSTERI);
		this.idkey = pMusteriId;
	}

	public Musteri(String kindName, String pidkey, String padi, String psoyadi,String padres) {
		super(kindName);
		this.idkey = pidkey;
		this.adi = padi;
		this.adi = psoyadi;
		this.adres = padres;
	}

	public Musteri(String kindName,CloudEntity result) {
		super(kindName);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
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
		
		String pmusteriAdresi="";
		object=result.get(Musteri.PROP_ADRES);
		if (object!=null){
			pmusteriAdresi=object.toString();
		}
		
		put(Musteri.PROP_IDKEY, pidkey);
		put(Musteri.PROP_ADI, pmusteriAdi);
		put(Musteri.PROP_SOYADI, pmusteriSoyadi);
		put(Musteri.PROP_ADRES, pmusteriAdresi);
		
		setIdkey(pidkey);
		setAdi(pmusteriAdi);
		setSoyadi(pmusteriSoyadi);
		setAdres(pmusteriAdresi);
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getSoyadi() {
		return soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public CloudEntity getMusteriCloudEntity() {
		return (CloudEntity) super.get(this.getId());
	}

	@Override
	public String toString() {
		return adi + " " + soyadi;
	}

}