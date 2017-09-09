package com.mcu32.firstq.wechat.bean;

import java.util.Date;

public class PressureRegulator extends BaseDevice{
    private String prId;

    private String prRatedPower;

    private String prManufacturer;

    private String stationId;

    private Date lastTime;

    private String lastUser;

    private String lastUserId;

    private String remark;
    
    private String oldDeviceId;
    
    private String barCode;
    

    public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId == null ? null : prId.trim();
    }

    public String getPrRatedPower() {
        return prRatedPower;
    }

    public void setPrRatedPower(String prRatedPower) {
        this.prRatedPower = prRatedPower == null ? null : prRatedPower.trim();
    }

    public String getPrManufacturer() {
        return prManufacturer;
    }

    public void setPrManufacturer(String prManufacturer) {
        this.prManufacturer = prManufacturer == null ? null : prManufacturer.trim();
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
        PressureRegulator other = (PressureRegulator) that;
        return (this.getPrId() == null ? other.getPrId() == null : this.getPrId().equals(other.getPrId()))
            && (this.getPrRatedPower() == null ? other.getPrRatedPower() == null : this.getPrRatedPower().equals(other.getPrRatedPower()))
            && (this.getPrManufacturer() == null ? other.getPrManufacturer() == null : this.getPrManufacturer().equals(other.getPrManufacturer()))
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
        result = prime * result + ((getPrId() == null) ? 0 : getPrId().hashCode());
        result = prime * result + ((getPrRatedPower() == null) ? 0 : getPrRatedPower().hashCode());
        result = prime * result + ((getPrManufacturer() == null) ? 0 : getPrManufacturer().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
        result = prime * result + ((getLastUser() == null) ? 0 : getLastUser().hashCode());
        result = prime * result + ((getLastUserId() == null) ? 0 : getLastUserId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}