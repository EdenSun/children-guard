package eden.sun.childrenguard.server.model.generated;

import java.util.Date;

public class ChildExtraInfo {
    private Integer id;

    private Double longitude;

    private Double latitude;

    private Date locationUpdateTime;

    private Double speed;

    private Date speedUpdateTime;

    private Double networkTrafficUsed;

    private Date networkTrafficUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getLocationUpdateTime() {
        return locationUpdateTime;
    }

    public void setLocationUpdateTime(Date locationUpdateTime) {
        this.locationUpdateTime = locationUpdateTime;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Date getSpeedUpdateTime() {
        return speedUpdateTime;
    }

    public void setSpeedUpdateTime(Date speedUpdateTime) {
        this.speedUpdateTime = speedUpdateTime;
    }

    public Double getNetworkTrafficUsed() {
        return networkTrafficUsed;
    }

    public void setNetworkTrafficUsed(Double networkTrafficUsed) {
        this.networkTrafficUsed = networkTrafficUsed;
    }

    public Date getNetworkTrafficUpdateTime() {
        return networkTrafficUpdateTime;
    }

    public void setNetworkTrafficUpdateTime(Date networkTrafficUpdateTime) {
        this.networkTrafficUpdateTime = networkTrafficUpdateTime;
    }
}