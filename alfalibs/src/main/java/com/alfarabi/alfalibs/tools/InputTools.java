package com.alfarabi.alfalibs.tools;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.alfarabi.alfalibs.R;

/**
 * Created by Alfarabi on 7/27/17.
 */

public class InputTools {

    public static boolean notEmpty(@NonNull String... texts){
        boolean ret = false ;
        for (String text : texts) {
            if(text!=null && !text.isEmpty()){
                ret = true ;
                break;
            }
        }
        return ret;
    }

    public static boolean isComplete(@NonNull EditText... editTexts){
        boolean ret = true ;
        for (EditText editText : editTexts) {
            if(editText.getText().toString().isEmpty()){
                if(ret){
                    ret = false ;
                }
                editText.setError(editText.getContext().getResources().getString(R.string.required_field));
            }
        }
        return ret;
    }

    public static boolean isComplete(@NonNull TextView... textViews){
        boolean ret = true ;
        for (TextView textView : textViews) {
            if(textView.getText().toString().isEmpty()){
                if(ret){
                    ret = false ;
                }
                textView.setError(textView.getContext().getResources().getString(R.string.required_field));
            }
        }
        return ret;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public final static boolean isValidEmail(EditText editText) {
        if (editText == null || editText.getText().toString().equals("")) {
            editText.setError(editText.getContext().getResources().getString(R.string.required_field));
            return false;
        } else {
            boolean ret = android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches() ;
            if(!ret){
                editText.setError(editText.getContext().getResources().getString(R.string.error_invalid_email));
            }
            return ret ;
        }
    }

    public static boolean equals(EditText... editTexts) {
        boolean result = true ;
        if (editTexts == null) {
            result = false ;
        } else if (editTexts.length == 0) {
            result = false ;
        } else{
            String firstText = editTexts[0].getText().toString();
            loop:
            for (EditText editText : editTexts) {
                if(!editText.getText().toString().equals(firstText)){
                    result = false ;
                    break loop;
                }
                
            }
        }

        if(!result){
            for (EditText editText : editTexts) {
                editText.setError(editText.getContext().getResources().getString(R.string.words_arent_match));
            }
        }
        return result ;
    }

}
