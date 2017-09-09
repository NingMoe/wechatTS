package com.mcu32.firstq.wechat.bean;

import java.util.Date;

public class MonitoringInfo extends BaseDevice{
    private String monitoringId;

    private String mModel;

    private String mSupplier;

    private String stationId;

    private Date lastTime;

    private String lastUser;

    private String lastUserId;

    private String remark;
    
    private String oldDeviceId;
	
	private String barCode;

    public String getMonitoringId() {
        return monitoringId;
    }

    public void setMonitoringId(String monitoringId) {
        this.monitoringId = monitoringId == null ? null : monitoringId.trim();
    }

    public String getmModel() {
        return mModel;
    }

    public void setmModel(String mModel) {
        this.mModel = mModel == null ? null : mModel.trim();
    }

    public String getmSupplier() {
        return mSupplier;
    }

    public void setmSupplier(String mSupplier) {
        this.mSupplier = mSupplier == null ? null : mSupplier.trim();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId == null ? null : stationId.trim();
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser == null ? null : lastUser.trim();
    }

    public String getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(String lastUserId) {
        this.lastUserId = lastUserId == null ? null : lastUserId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOldDeviceId() {
		return oldDeviceId;
	}

	public void setOldDeviceId(String oldDeviceId) {
		this.oldDeviceId = oldDeviceId;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MonitoringInfo other = (MonitoringInfo) that;
        return (this.getMonitoringId() == null ? other.getMonitoringId() == null : this.getMonitoringId().equals(other.getMonitoringId()))
            && (this.getmModel() == null ? other.getmModel() == null : this.getmModel().equals(other.getmModel()))
            && (this.getmSupplier() == null ? other.getmSupplier() == null : this.getmSupplier().equals(other.getmSupplier()))
            && (this.getStationId() == null ? other.getStationId() == null : this.getStationId().equals(other.getStationId()))
            && (this.getLastTime() == null ? other.getLastTime() == null : this.getLastTime().equals(other.getLastTime()))
            && (this.getLastUser() == null ? other.getLastUser() == null : this.getLastUser().equals(other.getLastUser()))
            && (this.getLastUserId() == null ? other.getLastUserId() == null : this.getLastUserId().equals(other.getLastUserId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMonitoringId() == null) ? 0 : getMonitoringId().hashCode());
        result = prime * result + ((getmModel() == null) ? 0 : getmModel().hashCode());
        result = prime * result + ((getmSupplier() == null) ? 0 : getmSupplier().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
        result = prime * result + ((getLastUser() == null) ? 0 : getLastUser().hashCode());
        result = prime * result + ((getLastUserId() == null) ? 0 : getLastUserId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}