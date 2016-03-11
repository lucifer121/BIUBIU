package com.android.biubiu.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/** 
 * @author  lucifer 
 * @date 2015-12-19
 * @return  
 */
public class CloseJianpan {
	 public static void closeKeyboard(Context context, View view) {  
	        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);  
	        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);  
	    }  

}
