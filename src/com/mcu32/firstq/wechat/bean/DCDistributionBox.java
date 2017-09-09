package com.mcu32.firstq.wechat.bean;

import java.util.Date;

public class DCDistributionBox extends BaseDevice{
    private String dcDistributionId;

    private String boxBrand;

    private Short dcCapacity;

    private Short fuseUsedNum;

    private Short fuseNotUsedNum;

    private String stationId;

    private Date lastTime;

    private String lastUser;

    private String lastUserId;

    private String remark;
    
    private String oldDeviceId;

    private String inStatus;
    
	private String outStatus;
	
	private String barCode;
	
	
    public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDcDistributionId() {
        return dcDistributionId;
    }

    public void setDcDistributionId(String dcDistributionId) {
        this.dcDistributionId = dcDistributionId == null ? null : dcDistributionId.trim();
    }

    public String getBoxBrand() {
        return boxBrand;
    }

    public void setBoxBrand(String boxBrand) {
        this.boxBrand = boxBrand == null ? null : boxBrand.trim();
    }

    public Short getDcCapacity() {
        return dcCapacity;
    }

    public void setDcCapacity(Short dcCapacity) {
        this.dcCapacity = dcCapacity;
    }

    public Short getFuseUsedNum() {
        return fuseUsedNum;
    }

    public void setFuseUsedNum(Short fuseUsedNum) {
        this.fuseUsedNum = fuseUsedNum;
    }

    public Short getFuseNotUsedNum() {
        return fuseNotUsedNum;
    }

    public void setFuseNotUsedNum(Short fuseNotUsedNum) {
        this.fuseNotUsedNum = fuseNotUsedNum;
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

	public String getInStatus() {
		return inStatus;
	}

	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}

	public String getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(String outStatus) {
		this.outStatus = outStatus;
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
        DCDistributionBox other = (DCDistributionBox) that;
        return (this.getDcDistributionId() == null ? other.getDcDistributionId() == null : this.getDcDistributionId().equals(other.getDcDistributionId()))
            && (this.getBoxBrand() == null ? other.getBoxBrand() == null : this.getBoxBrand().equals(other.getBoxBrand()))
            && (this.getDcCapacity() == null ? other.getDcCapacity() == null : this.getDcCapacity().equals(other.getDcCapacity()))
            && (this.getFuseUsedNum() == null ? other.getFuseUsedNum() == null : this.getFuseUsedNum().equals(other.getFuseUsedNum()))
            && (this.getFuseNotUsedNum() == null ? other.getFuseNotUsedNum() == null : this.getFuseNotUsedNum().equals(other.getFuseNotUsedNum()))
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
        result = prime * result + ((getDcDistributionId() == null) ? 0 : getDcDistributionId().hashCode());
        result = prime * result + ((getBoxBrand() == null) ? 0 : getBoxBrand().hashCode());
        result = prime * result + ((getDcCapacity() == null) ? 0 : getDcCapacity().hashCode());
        result = prime * result + ((getFuseUsedNum() == null) ? 0 : getFuseUsedNum().hashCode());
        result = prime * result + ((getFuseNotUsedNum() == null) ? 0 : getFuseNotUsedNum().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
        result = prime * result + ((getLastUser() == null) ? 0 : getLastUser().hashCode());
        result = prime * result + ((getLastUserId() == null) ? 0 : getLastUserId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}