package com.mcu32.firstq.wechat.service;

import java.util.List;

import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.SoundInfo;
import com.mcu32.firstq.wechat.exception.FirstQException;

public interface IResourceService {

	public List<PhotoInfo> getWaitUploadPhotos(String token) throws FirstQException;
	
	public List<PhotoInfo> getPhotosByRelateId(String relateId) throws FirstQException;
	
	public boolean saveWaitUploadPhoto(PhotoInfo photoInfo) throws FirstQException;
	
	public boolean deletePhoto(String photoId) throws FirstQException;
	
	public boolean deletePhotoByRelateId(String relateId) throws FirstQException;
	
	public boolean uploadPhoto(String photoId,String access_token,String mediaId) throws FirstQException;
	
	public int getPhotoCount() throws FirstQException;
	
	public List<SoundInfo> getWaitUploadSounds(String token) throws FirstQException;
	
	public boolean saveWaitUploadSound(SoundInfo soundInfo) throws FirstQException;
	
	public boolean deleteSound(String soundId) throws FirstQException;
	
	public boolean uploadSound(String soundId,String access_token,String mediaId) throws FirstQException;

	public int getSoundCount() throws FirstQException;
	
}