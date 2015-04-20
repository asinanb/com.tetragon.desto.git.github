package com.tetragon.desto.model;

import com.google.cloud.backend.core.CloudEntity;

public class Satis extends CloudEntity {

	/**
	 * Satis Tablosu Kolon adlarý
	 */
	public static final String KIND_SATIS = "Satis";
	public static final String LABEL_SATIS = "Satýþ";
	
	public static final String PROP_IDKEY = "idkey";
	public static final String PROP_MUSTERI_IDKEY = "musteri";
	public static final String PROP_SATISTARIHI = "satis_tarihi";
	public static final String PROP_TOPLAMFIYAT = "toplam_fiyat";
	public static final String PROP_NAKIT = "nakit";
	public static final String PROP_KKARTI = "kkarti";
	public static final String PROP_TAKSITSAYISI = "taksit_sayisi";
	public static final String PROP_TAKSITTL = "taksittl";
	public static final String PROP_SONTAKSIT = "son_taksit";
	public static final String PROP_TAKSITBASTAR = "taksit_bastar";
	public static final String PROP_TAKSITBITTAR = "taksit_bittar";
	public static final String PROP_SATISAYRINTI = "satis_ayrinti";
	

	public Satis(SatisItem satisItem) {
		super(KIND_SATIS);
		CloudEntity result= new CloudEntity(KIND_SATIS);
		setCreatedAt(result.getCreatedAt());
		setCreatedBy(result.getCreatedBy());
		setId(result.getId());
		setOwner(result.getOwner());
		setUpdatedAt(result.getUpdatedAt());
		setUpdatedBy(result.getUpdatedBy());
		
		
		put(PROP_IDKEY,satisItem.getIdkey());
		put(PROP_MUSTERI_IDKEY,satisItem.getMusteri().getIdkey());
		put(PROP_SATISTARIHI,satisItem.getSatis_tarihi());
		put(PROP_TOPLAMFIYAT,satisItem.getToplamFiyat());
		put(PROP_NAKIT,satisItem.getNakit());
		put(PROP_KKARTI,satisItem.getKkarti());
		put(PROP_TAKSITSAYISI,satisItem.getTaksitSayisi());
		put(PROP_TAKSITTL,satisItem.getTaksitTl());
		put(PROP_TAKSITBASTAR,satisItem.getTaksitBasTar());
		put(PROP_TAKSITBITTAR,satisItem.getTaksitBitTar());
		put(PROP_SONTAKSIT,satisItem.getSonTaksit());
		put(PROP_SATISAYRINTI,satisItem.getSatis_ayrinti());
		
	}

}