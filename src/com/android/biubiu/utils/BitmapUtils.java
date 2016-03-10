package com.android.biubiu.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class BitmapUtils {
	public static BitmapUtils bitUtils ;
	public static BitmapUtils getInstance(){
		if(bitUtils == null){
			bitUtils = new BitmapUtils();
		}
		return bitUtils;
	}
	
}
