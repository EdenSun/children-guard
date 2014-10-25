package eden.sun.childrenguard.server.model.generated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChildExtraInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChildExtraInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("LONGITUDE is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("LONGITUDE is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(Double value) {
            addCriterion("LONGITUDE =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(Double value) {
            addCriterion("LONGITUDE <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(Double value) {
            addCriterion("LONGITUDE >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("LONGITUDE >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(Double value) {
            addCriterion("LONGITUDE <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("LONGITUDE <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<Double> values) {
            addCriterion("LONGITUDE in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<Double> values) {
            addCriterion("LONGITUDE not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(Double value1, Double value2) {
            addCriterion("LONGITUDE between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("LONGITUDE not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("LATITUDE is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("LATITUDE is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(Double value) {
            addCriterion("LATITUDE =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(Double value) {
            addCriterion("LATITUDE <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(Double value) {
            addCriterion("LATITUDE >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("LATITUDE >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(Double value) {
            addCriterion("LATITUDE <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("LATITUDE <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<Double> values) {
            addCriterion("LATITUDE in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<Double> values) {
            addCriterion("LATITUDE not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(Double value1, Double value2) {
            addCriterion("LATITUDE between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("LATITUDE not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeIsNull() {
            addCriterion("LOCATION_UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeIsNotNull() {
            addCriterion("LOCATION_UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeEqualTo(Date value) {
            addCriterion("LOCATION_UPDATE_TIME =", value, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeNotEqualTo(Date value) {
            addCriterion("LOCATION_UPDATE_TIME <>", value, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeGreaterThan(Date value) {
            addCriterion("LOCATION_UPDATE_TIME >", value, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LOCATION_UPDATE_TIME >=", value, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeLessThan(Date value) {
            addCriterion("LOCATION_UPDATE_TIME <", value, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("LOCATION_UPDATE_TIME <=", value, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeIn(List<Date> values) {
            addCriterion("LOCATION_UPDATE_TIME in", values, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeNotIn(List<Date> values) {
            addCriterion("LOCATION_UPDATE_TIME not in", values, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("LOCATION_UPDATE_TIME between", value1, value2, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLocationUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("LOCATION_UPDATE_TIME not between", value1, value2, "locationUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedIsNull() {
            addCriterion("SPEED is null");
            return (Criteria) this;
        }

        public Criteria andSpeedIsNotNull() {
            addCriterion("SPEED is not null");
            return (Criteria) this;
        }

        public Criteria andSpeedEqualTo(Double value) {
            addCriterion("SPEED =", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedNotEqualTo(Double value) {
            addCriterion("SPEED <>", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedGreaterThan(Double value) {
            addCriterion("SPEED >", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedGreaterThanOrEqualTo(Double value) {
            addCriterion("SPEED >=", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedLessThan(Double value) {
            addCriterion("SPEED <", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedLessThanOrEqualTo(Double value) {
            addCriterion("SPEED <=", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedIn(List<Double> values) {
            addCriterion("SPEED in", values, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedNotIn(List<Double> values) {
            addCriterion("SPEED not in", values, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedBetween(Double value1, Double value2) {
            addCriterion("SPEED between", value1, value2, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedNotBetween(Double value1, Double value2) {
            addCriterion("SPEED not between", value1, value2, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeIsNull() {
            addCriterion("SPEED_UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeIsNotNull() {
            addCriterion("SPEED_UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeEqualTo(Date value) {
            addCriterion("SPEED_UPDATE_TIME =", value, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeNotEqualTo(Date value) {
            addCriterion("SPEED_UPDATE_TIME <>", value, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeGreaterThan(Date value) {
            addCriterion("SPEED_UPDATE_TIME >", value, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("SPEED_UPDATE_TIME >=", value, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeLessThan(Date value) {
            addCriterion("SPEED_UPDATE_TIME <", value, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("SPEED_UPDATE_TIME <=", value, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeIn(List<Date> values) {
            addCriterion("SPEED_UPDATE_TIME in", values, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeNotIn(List<Date> values) {
            addCriterion("SPEED_UPDATE_TIME not in", values, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("SPEED_UPDATE_TIME between", value1, value2, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSpeedUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("SPEED_UPDATE_TIME not between", value1, value2, "speedUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedIsNull() {
            addCriterion("NETWORK_TRAFFIC_USED is null");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedIsNotNull() {
            addCriterion("NETWORK_TRAFFIC_USED is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedEqualTo(Double value) {
            addCriterion("NETWORK_TRAFFIC_USED =", value, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedNotEqualTo(Double value) {
            addCriterion("NETWORK_TRAFFIC_USED <>", value, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedGreaterThan(Double value) {
            addCriterion("NETWORK_TRAFFIC_USED >", value, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedGreaterThanOrEqualTo(Double value) {
            addCriterion("NETWORK_TRAFFIC_USED >=", value, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedLessThan(Double value) {
            addCriterion("NETWORK_TRAFFIC_USED <", value, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedLessThanOrEqualTo(Double value) {
            addCriterion("NETWORK_TRAFFIC_USED <=", value, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedIn(List<Double> values) {
            addCriterion("NETWORK_TRAFFIC_USED in", values, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedNotIn(List<Double> values) {
            addCriterion("NETWORK_TRAFFIC_USED not in", values, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedBetween(Double value1, Double value2) {
            addCriterion("NETWORK_TRAFFIC_USED between", value1, value2, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUsedNotBetween(Double value1, Double value2) {
            addCriterion("NETWORK_TRAFFIC_USED not between", value1, value2, "networkTrafficUsed");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeIsNull() {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeIsNotNull() {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeEqualTo(Date value) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME =", value, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeNotEqualTo(Date value) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME <>", value, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeGreaterThan(Date value) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME >", value, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME >=", value, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeLessThan(Date value) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME <", value, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME <=", value, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeIn(List<Date> values) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME in", values, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeNotIn(List<Date> values) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME not in", values, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME between", value1, value2, "networkTrafficUpdateTime");
            return (Criteria) this;
        }

        public Criteria andNetworkTrafficUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("NETWORK_TRAFFIC_UPDATE_TIME not between", value1, value2, "networkTrafficUpdateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}