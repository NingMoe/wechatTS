package com.mcu32.firstq.wechat.bean;

import java.util.Date;

public class ACDistributionBox extends BaseDevice{
    private String acDistributionId;

    private Short acCapacity;

    private String acManufacturer;

    private String haveGeneratorInterface;

    private String stationId;

    private Date lastTime;

    private String lastUser;

    private String lastUserId;

    private String remark;

    private String oldDeviceId;
    
    private String inStatus;
    
	private String outStatus;
	
	private String barCode;;
	
	private String outLose;
	
    public String getOutLose() {
		return outLose;
	}

	public void setOutLose(String outLose) {
		this.outLose = outLose;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getAcDistributionId() {
        return acDistributionId;
    }

    public void setAcDistributionId(String acDistributionId) {
        this.acDistributionId = acDistributionId == null ? null : acDistributionId.trim();
    }

    public Short getAcCapacity() {
        return acCapacity;
    }

    public void setAcCapacity(Short acCapacity) {
        this.acCapacity = acCapacity;
    }


    public String getAcManufacturer() {
		return acManufacturer;
	}

	public void setAcManufacturer(String acManufacturer) {
		this.acManufacturer = acManufacturer;
	}

	public String getHaveGeneratorInterface() {
        return haveGeneratorInterface;
    }

    public void setHaveGeneratorInterface(String haveGeneratorInterface) {
        this.haveGeneratorInterface = haveGeneratorInterface == null ? null : haveGeneratorInterface.trim();
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
        ACDistributionBox other = (ACDistributionBox) that;
        return (this.getAcDistributionId() == null ? other.getAcDistributionId() == null : this.getAcDistributionId().equals(other.getAcDistributionId()))
            && (this.getAcCapacity() == null ? other.getAcCapacity() == null : this.getAcCapacity().equals(other.getAcCapacity()))
            && (this.getAcManufacturer() == null ? other.getAcManufacturer() == null : this.getAcManufacturer().equals(other.getAcManufacturer()))
            && (this.getHaveGeneratorInterface() == null ? other.getHaveGeneratorInterface() == null : this.getHaveGeneratorInterface().equals(other.getHaveGeneratorInterface()))
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
        result = prime * result + ((getAcDistributionId() == null) ? 0 : getAcDistributionId().hashCode());
        result = prime * result + ((getAcCapacity() == null) ? 0 : getAcCapacity().hashCode());
        result = prime * result + ((getAcManufacturer() == null) ? 0 : getAcManufacturer().hashCode());
        result = prime * result + ((getHaveGeneratorInterface() == null) ? 0 : getHaveGeneratorInterface().hashCode());
        result = prime * result + ((getStationId() == null) ? 0 : getStationId().hashCode());
        result = prime * result + ((getLastTime() == null) ? 0 : getLastTime().hashCode());
        result = prime * result + ((getLastUser() == null) ? 0 : getLastUser().hashCode());
        result = prime * result + ((getLastUserId() == null) ? 0 : getLastUserId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}