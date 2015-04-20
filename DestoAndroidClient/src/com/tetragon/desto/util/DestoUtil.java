package com.tetragon.desto.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.widget.ImageView;

import com.google.cloud.backend.core.CloudEntity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Marka;
import com.tetragon.desto.model.Urun_tip;
import com.tetragon.desto.model.Urungrubu;

public class DestoUtil {
	public static boolean isNumeric(String str)
	{
		if (!str.isEmpty()){
			 for (char c : str.toCharArray())
			    {
			        if (!Character.isDigit(c)) 
			        	return false;
			    }
			    return true;
		}else 
			return false;
	   
	}
	
	public static float stringToFloat(String str){
		float retval=0f;
		try {
			retval = Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			retval = 0f;
		}
		return retval;
	}
	
	public static int stringToInt(String str){
		str=(str==null)?str="0":str;
		int retval=0;
		if (DestoUtil.isNumeric(str)){
			if (Integer.valueOf(str)>0){
				retval=Integer.valueOf(str);
			}
		}
		return retval;
	}
	
	/*
	 * public static boolean isNumeric(String str)
{
  NumberFormat formatter = NumberFormat.getInstance();
  ParsePosition pos = new ParsePosition(0);
  formatter.parse(str, pos);
  return str.length() == pos.getIndex();
}

public static boolean isNumeric(String str)
{
  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
}

public static boolean isStringNumeric( String str )
{
    DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
    char localeMinusSign = currentLocaleSymbols.getMinusSign();

    if ( !Character.isDigit( str.charAt( 0 ) ) && str.charAt( 0 ) != localeMinusSign ) return false;

    boolean isDecimalSeparatorFound = false;
    char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

    for ( char c : str.substring( 1 ).toCharArray() )
    {
        if ( !Character.isDigit( c ) )
        {
            if ( c == localeDecimalSeparator && !isDecimalSeparatorFound )
            {
                isDecimalSeparatorFound = true;
                continue;
            }
            return false;
        }
    }
    return true;
}

android.text.TextUtils.isDigitsOnly(CharSequence str)
	 */
	public static String generateId() {
			Calendar cal = Calendar.getInstance(); 
			  int millisecond = cal.get(Calendar.MILLISECOND);
			  int second = cal.get(Calendar.SECOND);
			  int minute = cal.get(Calendar.MINUTE);
			  int hourofday = cal.get(Calendar.HOUR_OF_DAY);
			  int month = cal.get(Calendar.MONTH);
			  int year = cal.get(Calendar.YEAR);
			  String idkey=year+""+month+""+hourofday+""+minute+""+second+""+millisecond;
			return idkey;
		}
	public static List<String> createList(List<CloudEntity> list, String prop) {
		List<String> newlist =  new LinkedList<String>(); 
		for (CloudEntity cloudEntity : list) {
			newlist.add(cloudEntity.get(prop).toString());
		}
		return newlist;
	}
	public static Locale getLocale() {
		Locale trlocale= new Locale("tr-TR");
		return trlocale;
	}
	
	public static void changeMarkaImage(ImageView image, String marka) {
		if (marka.equalsIgnoreCase(Marka.VESTEL))
			image.setImageResource(R.drawable.ic_vestel);
		else if (marka.equalsIgnoreCase(Marka.BOSCH))
			image.setImageResource(R.drawable.ic_bosch);
		else if (marka.equalsIgnoreCase(Marka.PHILIPS))
			image.setImageResource(R.drawable.ic_philips);
		else if (marka.equalsIgnoreCase(Marka.BRAUN))
			image.setImageResource(R.drawable.ic_braun);
		else if (marka.equalsIgnoreCase(Marka.SHARP))
			image.setImageResource(R.drawable.ic_sharp);
		else if (marka.equalsIgnoreCase(Marka.ROWENTA))
			image.setImageResource(R.drawable.ic_rowenta);
		else if (marka.equalsIgnoreCase(Marka.TEFAL))
			image.setImageResource(R.drawable.ic_tefal);
		else if (marka.equalsIgnoreCase(Marka.FAKIR))
			image.setImageResource(R.drawable.ic_fakir);
		else if (marka.equalsIgnoreCase(Marka.SAMSUNG))
			image.setImageResource(R.drawable.ic_samsung);
		else if (marka.equalsIgnoreCase(Marka.ARZUM))
			image.setImageResource(R.drawable.ic_arzum);
		else if (marka.equalsIgnoreCase(Marka.WHIRLPOOL))
			image.setImageResource(R.drawable.ic_whirlpool);
		else if (marka.equalsIgnoreCase(Marka.HP))
			image.setImageResource(R.drawable.ic_hp);
		else if (marka.equalsIgnoreCase(Marka.AEG))
			image.setImageResource(R.drawable.ic_aeg);
		else if (marka.equalsIgnoreCase(Marka.PROFILO))
			image.setImageResource(R.drawable.ic_profilo);
		else if (marka.equalsIgnoreCase(Marka.LG))
			image.setImageResource(R.drawable.ic_lg);
		else 
			image.setImageResource(R.drawable.ic_marka);
	}
	public static void changeUGImage(ImageView image, String urungrubu) {
		if (urungrubu.contains(Urungrubu.BEYAZ))
			image.setImageResource(R.drawable.ic_beyazesya);
		else if (urungrubu.contains(Urungrubu.EVALETLERI))
			image.setImageResource(R.drawable.ic_evaletleri);
		else if (urungrubu.contains(Urungrubu.ANKASTRE))
			image.setImageResource(R.drawable.ic_ankastre);
		else if (urungrubu.contains(Urungrubu.BILISIM))
			image.setImageResource(R.drawable.ic_bilisim);
		else if (urungrubu.contains(Urungrubu.MOBIL))
			image.setImageResource(R.drawable.ic_mobil);
		else if (urungrubu.contains(Urungrubu.ELEKTRO))
			image.setImageResource(R.drawable.ic_elektro);
		else if (urungrubu.contains(Urungrubu.KISISELBAKIM))
			image.setImageResource(R.drawable.ic_kisiselbakim);
		else if (urungrubu.contains(Urungrubu.MUTFAK))
			image.setImageResource(R.drawable.ic_mutfak);
		else 
			image.setImageResource(R.drawable.ic_urungrubu);
	}
	public static void changeUrunImage(ImageView image, String urun) {
			
		if (urun.contains(Urun_tip.BUZDOLABI))
			image.setImageResource(R.drawable.ic_buzdolabi);
		else if (urun.contains(Urun_tip.DERIN))
			image.setImageResource(R.drawable.ic_derin);
		else if (urun.contains(Urun_tip.TELEVIZYON))
			image.setImageResource(R.drawable.ic_tv);
		else if (urun.contains(Urun_tip.BULA))
			image.setImageResource(R.drawable.ic_bula);
		else if (urun.contains(Urun_tip.CAMA))
			image.setImageResource(R.drawable.ic_cama);
		else if (urun.contains(Urun_tip.KURUTMA))
			image.setImageResource(R.drawable.ic_kurut);
		else if (urun.contains(Urun_tip.BILGISAYAR))
			image.setImageResource(R.drawable.ic_bilgisayar);
		else if (urun.contains(Urun_tip.NOTEBOOK))
			image.setImageResource(R.drawable.ic_notebook);
		else if (urun.contains(Urun_tip.PISIRICI))
			image.setImageResource(R.drawable.ic_firin);
		else if (urun.contains(Urun_tip.UYDU))
			image.setImageResource(R.drawable.ic_uydu);
		else if (urun.contains(Urun_tip.OCAK))
			image.setImageResource(R.drawable.ic_ocak);
		else if (urun.contains(Urun_tip.DAVLUMBAZ))
			image.setImageResource(R.drawable.ic_davlumbaz);
		else if (urun.contains(Urun_tip.ASPIRATOR))
			image.setImageResource(R.drawable.ic_davlumbaz);
		else if (urun.contains(Urun_tip.SU_SEBILI))
			image.setImageResource(R.drawable.ic_susebili);
		else if (urun.contains(Urun_tip.UTU))
			image.setImageResource(R.drawable.ic_utu);
		else if (urun.contains(Urun_tip.MIKSER))
			image.setImageResource(R.drawable.ic_mikser);
		else if (urun.contains(Urun_tip.BLENDER))
			image.setImageResource(R.drawable.ic_blender);
		else if (urun.contains(Urun_tip.TARTI))
			image.setImageResource(R.drawable.ic_tarti);
		else if (urun.contains(Urun_tip.SU_ISITICI))
			image.setImageResource(R.drawable.ic_suisitici);
		else if (urun.contains(Urun_tip.EKMEK))
			image.setImageResource(R.drawable.ic_ekmek);
		else if (urun.contains(Urun_tip.KAHVE))
			image.setImageResource(R.drawable.ic_kahve);
		else if (urun.contains(Urun_tip.TOST))
			image.setImageResource(R.drawable.ic_tost);
		else if (urun.contains(Urun_tip.CAY))
			image.setImageResource(R.drawable.ic_cay);
		else if (urun.contains(Urun_tip.SAC_KURUTMA))
			image.setImageResource(R.drawable.ic_sackurutma);
		else if (urun.contains(Urun_tip.SAC_DUZLE))
			image.setImageResource(R.drawable.ic_sacduzle);
		else if (urun.contains(Urun_tip.SUPURGE))
			image.setImageResource(R.drawable.ic_supurge);
		else if (urun.contains(Urun_tip.ANKASTRE_FIRIN))
			image.setImageResource(R.drawable.ic_ankfirin);
		else if (urun.contains(Urun_tip.ANKASTRE_CAMA))
			image.setImageResource(R.drawable.ic_cama);
		else if (urun.contains(Urun_tip.ANKASTRE_BULA))
			image.setImageResource(R.drawable.ic_ankbula);
		else if (urun.contains(Urun_tip.ANKASTRE_BUZ))
			image.setImageResource(R.drawable.ic_buzdolabi);
		else if (urun.contains(Urun_tip.TABLET ))
			image.setImageResource(R.drawable.ic_tablet);
		else if (urun.contains(Urun_tip.AKILLI))
			image.setImageResource(R.drawable.ic_akilli);
		else if (urun.contains(Urun_tip.EPILASYON))
			image.setImageResource(R.drawable.ic_kisiselbakim);
		else if (urun.contains(Urun_tip.MUTFAKROBOTU))
			image.setImageResource(R.drawable.ic_mutfak);
		else 
			image.setImageResource(R.drawable.ic_uruntip); 

		
	}
	public static String getToday() {
		
		Calendar calendar = Calendar.getInstance();
//		int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
//		int monthOfYear=calendar.get(Calendar.MONTH+1);
//		int year=calendar.get(Calendar.YEAR);
//		String today=String.format("%s/%s/%s", dayOfMonth, monthOfYear , year);
//		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String today=dateFormat.format(calendar.getTime());
		
		return today;
		
	}
	
}
