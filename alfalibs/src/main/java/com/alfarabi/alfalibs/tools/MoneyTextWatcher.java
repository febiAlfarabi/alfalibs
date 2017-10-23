package com.alfarabi.alfalibs.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.Locale;

public class MoneyTextWatcher implements TextWatcher {
    public static final String T = "MoneyTextWatcher";
	
	private final EditText editText;
	private String formatType;
	private String current = "";
	private boolean isDeleting;
	
	protected int max_length = Integer.MAX_VALUE;


	/**
	 * @param editText
	 * @param formatType String formatting style like "%.2f $"
	 */
	public MoneyTextWatcher(EditText editText, String formatType) {
		this.editText = editText;
		this.formatType = formatType;
//		Log.e(T, "::MoneyTextWatcher:" + "formatType "+formatType);
	}
	

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		WLog.i(T, "::beforeTextChanged:" + "CharSequence " + s +" start=" + start +" count=" + count +" after=" + after);
		if (after <= 0 && count > 0) {
			isDeleting = true;
		} else {
			isDeleting = false;
		}
		if (!s.toString().equals(current)) {
			editText.removeTextChangedListener(this);
			String clean_text = s.toString().replaceAll("[^\\d]", "");
			editText.setText(clean_text);
			editText.addTextChangedListener(this);
		}
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		WLog.i(T, "::onTextChanged:" + "CharSequence " + s +" start=" + start +" count=" + count +" before=" + before);
	}
	
	
	@Override
	public void afterTextChanged(Editable s) {
		WLog.i(T, "::afterTextChanged:" + "Editable "+s + "; Current "+current);
		if (!s.toString().equals(current)) {
			editText.removeTextChangedListener(this);
			String clean_text = s.toString().replaceAll("[^\\d]", "");
			if (isDeleting && s.length() > 0 && !Character.isDigit(s.charAt(s.length() - 1))) {
				clean_text = deleteLastChar(clean_text);				
			}
			
//			Log.e(T, "::afterTextChanged:" + "clean_text "+clean_text);
			double v_value = 0;
			if (clean_text != null && clean_text.length() > 0) {
				v_value = Double.parseDouble(clean_text);
				
				String s_value = Double.toString(Math.abs(v_value / 100));
				int integerPlaces = s_value.indexOf('.');
				if (integerPlaces > max_length) {
					v_value = Double.parseDouble(deleteLastChar(clean_text));
				}
			}
			
			String formatted_text = String.format(Locale.getDefault(), formatType, v_value / 100);
			WLog.i(T, "::afterTextChanged:" + "formatted_text "+formatted_text);
			current = formatted_text;
			editText.setText(formatted_text);
			editText.setSelection(formatted_text.length());			
			editText.addTextChangedListener(this);
		}
		
	}


	private String deleteLastChar(String clean_text) {
		if (clean_text.length() > 0) {
			clean_text = clean_text.substring(0, clean_text.length()-1);
		} else {
			clean_text = "0";
		}
		return clean_text;
	}


	public int getMax_length() {
		return max_length;
	}


	public void setMax_length(int max_length) {
		this.max_length = max_length;
	}

	//Trims all the comma of the string and returns
	public static String trimCommaOfString(String string) {
//        String returnString;
		if(string.contains(",")){
			return string.replace(",","");}
		else {
			return string;
		}

	}

	//Trims all the comma of the string and returns
	public static String moneyNormalize(float in) {
		BigDecimal roundfinalPrice = new BigDecimal(in).setScale(2,BigDecimal.ROUND_HALF_UP);
//		NumberFormat decimalFormat = new DecimalFormat("#,###.##");
//		String res = decimalFormat.format(in);

		return  roundfinalPrice.toString() ;

	}



	public static float floatOf(String in){
		in = in.replaceAll("\\.", "");
		if(in.contains(",")){
			return Float.valueOf(in.replace(",","."));
		} else {
			return Float.valueOf(in);
		}

	}

}