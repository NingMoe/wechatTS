package com.mcu32.firstq.wechat.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mcu32.firstq.common.bean.bo.AcceptCategoryBO;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.RoomBO;
import com.mcu32.firstq.common.bean.bo.StationBO;
import com.mcu32.firstq.common.bean.bo.TowerBO;
import com.mcu32.firstq.wechat.bean.AcceptCategoryInfo;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.RoomInfo;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.TowerInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;

public class FirstQBeanUtils {
	
	public static List<StationInfo> getStationInfoList(List<StationBO> sourceList,UserInfo user){
		List<StationInfo> targetList = new ArrayList<StationInfo>();
		for(StationBO source : sourceList){
			targetList.add(getStationInfo(source,user));
		}
		return targetList;
	}
	
	public static StationInfo getStationInfo(StationBO source,UserInfo user){
		List<PhotoBO> photoBOList = source.getPhotoList();
		List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
		StationInfo target = new StationInfo();
		BeanCopier.create(StationBO.class, StationInfo.class, false).copy(source, target, null);
		if (photoBOList != null) {
			
			PhotoInfo photoInfoRoom = null;
			boolean towerPhotoIsNotFind = true;
			for (PhotoBO photoBO : photoBOList) {
				PhotoInfo photoInfo = new PhotoInfo();
				BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(photoBO, photoInfo, null);
				if(photoInfo.getStatus().equals(WebContentConstants.STATUS_WAIT_UPLOAD) && user != null){
					if(photoInfo.getCreateUserId() != null && !"".equals(photoInfo.getCreateUserId()) && photoInfo.getCreateUserId().equals(user.getUserId())){
						photoInfo.setThumbLocation(photoInfo.getLocalId());
						photoInfo.setFileLocation(photoInfo.getLocalId());
					}else{
						String callBackDomain = ToolUtil.getAppConfig("CallBackDomain");
						String waitUploadImg = "http://"+callBackDomain +"/wechat/assets/i/waitUpload.png";
						photoInfo.setThumbLocation(waitUploadImg);
						photoInfo.setFileLocation(waitUploadImg);
					}
				}else{
					String thumbLocation =  FirstqTool.getPhotoLocation(photoInfo.getThumbLocation());
					String realLocation =  FirstqTool.getPhotoLocation(photoInfo.getFileLocation());
					photoInfo.setThumbLocation(thumbLocation);
					photoInfo.setFileLocation(realLocation);
				}
				
				if(WebContentConstants.STATION_TOWER.equals(photoInfo.getPhotoType()) && towerPhotoIsNotFind){
					target.setThumbLocation(photoInfo.getThumbLocation());
					target.setPhotoStatus(photoInfo.getStatus());
					target.setPhotoLocalId(photoInfo.getLocalId());
					towerPhotoIsNotFind=false;
				}else if(WebContentConstants.STATION_ROOM.equals(photoInfo.getPhotoType()) && null==photoInfoRoom){
					photoInfoRoom=photoInfo;
				}
				photoInfoList.add(photoInfo);
			}
			if(null != photoInfoRoom && towerPhotoIsNotFind ){
				target.setThumbLocation(photoInfoRoom.getThumbLocation());
				target.setPhotoStatus(photoInfoRoom.getStatus());
				target.setPhotoLocalId(photoInfoRoom.getLocalId());
			}
		}
		TowerInfo towerInfo = new TowerInfo();
		TowerBO tower = source.getTower();
		if(tower != null) {
			getTarget(tower,towerInfo);
			target.setTowerInfo(towerInfo);
			target.setTowerId(towerInfo.getTowerId());
		}
		
		
		RoomInfo roomInfo = new RoomInfo();
		RoomBO room = source.getRoom();
		if(room != null) {
			getTarget(room, roomInfo);
			target.setRoomInfo(roomInfo);
			target.setRoomId(roomInfo.getRoomId());
		}
		FirstqTool.convertPhotoPath(photoInfoList,user);
		target.setPhotoList(photoInfoList);
		return target;
	}
	
	public static Object getTarget(Object source, Object target){
		BeanCopier.create(source.getClass(), target.getClass(), false).copy(source, target, null);
		return target;
	}
	
	public Object deepCopy(Object source){
		try {
			// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(source);
			// 将流序列化成对象
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			LogUtil.error(e);
		} catch (IOException e) {
			LogUtil.error(e);
		}
		return null;
	} 

	public static void getAcceptCategoryInfoList(List<AcceptCategoryBO> sourceList, List<AcceptCategoryInfo> targetList){
		for(int i=0;i<sourceList.size();i++){
			targetList.add((AcceptCategoryInfo)getTarget(sourceList.get(i),new AcceptCategoryInfo()));
		}
	}
	
	public static void getPhotoInfoList(List<PhotoBO> sourceList, List<PhotoInfo> targetList){
		for(int i=0;i<sourceList.size();i++){
			targetList.add((PhotoInfo)getTarget(sourceList.get(i),new PhotoInfo()));
		}
	}
}
