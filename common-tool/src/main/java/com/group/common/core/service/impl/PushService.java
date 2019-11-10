package com.group.common.core.service.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 消息推送
 * @author Administrator
 *
 */

@Service
public class PushService {
	
	protected Logger logger = LoggerFactory.getLogger(PushService.class);
	
	@Value("${masterSecret}")
	private String masterSecret;
	
	@Value("${appKey}")
	private String appKey;
	
	private JPushClient jpushClient = null;

	/**
	 * 推送消息
	 * @param userId 用户id
	 * @param alert 推送消息
	 * @param title 推送标题
	 * @param extras 推送参数
	 */
	public void push(String userId,String alert,String title,Map<String,String> extras){
		if(jpushClient==null){
			jpushClient = new JPushClient(masterSecret, appKey);
		}
		
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(alert)) {
            return;
        }
		
		try {
			List<String> alias = new ArrayList<String>();
			alias.add(userId);

			PushPayload pushPayload = buildPushObject_all_alias_alertWithTitle(alias, alert, title, extras);
			PushResult pushResult = jpushClient.sendPush(pushPayload);
			System.out.println(pushResult);
		}  catch (Exception e){
			logger.error("推送失败:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 推送消息
	 * @param userIds 用户id 
	 * @param alert 推送消息
	 * @param title 推送标题
	 * @param extras 推送参数
	 */
	public void push(List<String> userIds,String alert,String title,Map<String,String> extras){
		if(jpushClient==null){
			jpushClient = new JPushClient(masterSecret, appKey);
		}
		
		if(userIds==null || userIds.size()==0 || StringUtils.isEmpty(alert)) {
            return;
        }
		
		try {
			List<String> alias = new ArrayList<String>();
			for(int i=0;i<userIds.size();i++){
				alias.add(userIds.get(i));
			}

			PushPayload pushPayload = buildPushObject_all_alias_alertWithTitle(alias, alert, title, extras);
			PushResult result = jpushClient.sendPush(pushPayload);
			System.out.println(result);
		}  catch(Exception e){
			logger.error("推送失败:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 构建推送平台
	 * @param alias
	 * @param alert
	 * @param title
	 * @param extras
	 * @return
	 */
	public PushPayload buildPushObject_all_alias_alertWithTitle(List<String> alias,String alert,String title,Map<String, String> extras) {
		AndroidNotification androidNotification = AndroidNotification.newBuilder().setAlert(alert).setTitle(title).addExtras(extras).build();
		IosNotification iosNotification = IosNotification.newBuilder().setAlert(alert).setContentAvailable(true).addExtras(extras).build();
		Options options = Options.newBuilder().setApnsProduction(false).build();
		
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setOptions(options)
                .setNotification(Notification.newBuilder().addPlatformNotification(androidNotification).addPlatformNotification(iosNotification).build())
                .build();
    }
	
	/**
	 * 构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
	 * @param alert
	 * @return
	 */
	public static PushPayload buildPushObject_all_all_alert(String alert,String title,Map<String, String> extras) {
		AndroidNotification androidNotification = AndroidNotification.newBuilder().setAlert(alert).setTitle(title).addExtras(extras).build();
		IosNotification iosNotification = IosNotification.newBuilder().setAlert(alert).setContentAvailable(true).addExtras(extras).build();
		Options options = Options.newBuilder().setApnsProduction(false).build();
		
		return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setOptions(options)
                .setNotification(Notification.newBuilder().addPlatformNotification(androidNotification).addPlatformNotification(iosNotification).build())
                .build();
    }
	
}
