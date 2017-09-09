package com.mcu32.firstq.wechat.bean;

import java.util.Date;

public class ACLine extends BaseDevice{
    private String aclineId;

    private String lineMaterial;

    private String lineModel;

    private Short overheadDistance;

    private Short wallDistance;

    private Short groundDistance;

    private Short lineLength;

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

	public String getAclineId() {
        return aclineId;
    }

    public void setAclineId(String aclineId) {
        this.aclineId = aclineId == null ? null : aclineId.trim();
    }

    public String getLineMaterial() {
        return lineMaterial;
    }

    public void setLineMaterial(String lineMaterial) {
        this.lineMaterial = lineMaterial == null ? null : lineMaterial.trim();
    }

    public String getLineModel() {
        return lineModel;
    }

    public void setLineModel(String lineModel) {
        this.lineModel = lineModel == null ? null : lineModel.trim();
    }

    public Short getOverheadDistance() {
        return overheadDistance;
    }

    public void setOverheadDistance(Short overheadDistance) {
        this.overheadDistance = overheadDistance;
    }

    public Short getWallDistance() {
        return wallDistance;
    }

    public void setWallDistance(Short wallDistance) {
        this.wallDistance = wallDistance;
    }

    public Short getGroundDistance() {
        return groundDistance;
    }

    public void setGroundDistance(Short groundDistance) {
        this.groundDistance = groundDistance;
    }

    public Short getLineLength() {
        return lineLength;
    }

    public void setLineLength(Short lineLength) {
        this.lineLength = lineLength;
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
        ACLine other = (ACLine) that;
        return (this.getAclineId() == null ? other.getAclineId() == null : this.getAclineId().equals(other.getAclineId()))
            && (this.getLineMaterial() == null ? other.getLineMaterial() == null : this.getLineMaterial().equals(other.getLineMaterial()))
            && (this.getLineModel() == null ? other.getLineModel() == null : this.getLineModel().equals(other.getLineModel()))
            && (this.getOverheadDistance() == null ? other.getOverheadDistance() == null : this.getOverheadDistance().equals(other.getOverheadDistance()))
            && (this.getWallDistance() == null ? other.getWallDistance() == null : this.getWallDistance().equals(other.getWallDistance()))
            && (this.getGroundDistance() == null ? other.getGroundDistance() == null : this.getGroundDistance().equals(other.getGroundDistance()))
            && (this.getLineLength() == null ? other.getLineLength() == null : this.getLineLength().equals(other.getLineLength()))
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
        result = prime * result + ((getAclineId() == null) ? 0 : getAclineId().hashCode());
        result = prime * result + ((getLineMaterial() == null) ? 0 : getLineMaterial().hashCode());
        result = prime * result + ((getLineModel() == null) ? 0 : getLineModel().hashCode());
        result = prime * result + ((getOverheadDistance() == null) ? 0 : getOverheadDistance().hashCode());
        result = prime * result + ((getWallDistance() == null) ? 0 : getWallDistance().hashCode());
        result = prime * result + ((getGroundDistance() == null) ? 0 : getGroundDistance().hashCode());
        result = prime * result + ((getLineLength() == null) ? 0 : getLineLength().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
        result = prime * result + ((getLastUser() == null) ? 0 : getLastUser().hashCode());
        result = prime * result + ((getLastUserId() == null) ? 0 : getLastUserId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}