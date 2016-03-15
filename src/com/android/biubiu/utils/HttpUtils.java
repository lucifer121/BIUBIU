package com.android.biubiu.utils;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.util.Log;

import com.android.biubiu.bean.InterestTagBean;
import com.android.biubiu.bean.PersonalTagBean;
import com.android.biubiu.bean.UserInfoBean;

public class HttpUtils {
	
	public static RequestParams getUpdateInfoParams(Context context,UserInfoBean infoBean,String updateTags){
		String token = SharePreferanceUtils.getInstance().getToken(context, SharePreferanceUtils.TOKEN, "");
		String deviceId = SharePreferanceUtils.getInstance().getDeviceId(context, SharePreferanceUtils.DEVICE_ID, "");
		RequestParams params = new RequestParams(HttpContants.HTTP_ADDRESS+HttpContants.UPDATE_USETINFO);
		JSONObject requestObject = new JSONObject();
		try {
			requestObject.put("token",token);
			requestObject.put("device_code", deviceId);
			requestObject.put("nickname",infoBean.getNickname());
			requestObject.put("sex", infoBean.getSex());
			requestObject.put("birth_date",infoBean.getBirthday());
			requestObject.put("city", infoBean.getCity());
			requestObject.put("hometown",infoBean.getHomeTown());
			requestObject.put("height", infoBean.getHeight());
			requestObject.put("weight",infoBean.getWeight());
			requestObject.put("isgraduated", infoBean.getIsStudent());
			requestObject.put("career",infoBean.getCareer());
			requestObject.put("school", infoBean.getSchool());
			requestObject.put("company",token);
			StringBuffer personalTags = new StringBuffer();
			if(infoBean.getPersonalTags().size()>0){
				ArrayList<PersonalTagBean> beans = infoBean.getPersonalTags();
				for(int i=0;i<beans.size();i++){
					PersonalTagBean bean = beans.get(i);
					if(i == beans.size()-1){
						personalTags.append(bean.getCode());
						break;
					}
					personalTags.append(bean.getCode()+",");
				}
			}
			requestObject.put("personality_tags", personalTags.toString());
			StringBuffer interTags = new StringBuffer();
			if(infoBean.getInterestTags().size()>0){
				ArrayList<InterestTagBean> beans = infoBean.getInterestTags();
				for(int i=0;i<beans.size();i++){
					InterestTagBean bean = beans.get(i);
					if(i == beans.size()-1){
						interTags.append(bean.getCode());
						break;
					}
					interTags.append(bean.getCode()+",");
				}
			}
			requestObject.put("interested_tags",interTags.toString());
			requestObject.put("parameters", updateTags);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addBodyParameter("data",requestObject.toString());
		return params;
	}

}
