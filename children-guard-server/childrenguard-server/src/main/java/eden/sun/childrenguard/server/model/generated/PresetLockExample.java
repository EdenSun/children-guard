package eden.sun.childrenguard.server.model.generated;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PresetLockExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PresetLockExample() {
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

        protected void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
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

        public Criteria andPresetOnOffIsNull() {
            addCriterion("PRESET_ON_OFF is null");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffIsNotNull() {
            addCriterion("PRESET_ON_OFF is not null");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffEqualTo(Boolean value) {
            addCriterion("PRESET_ON_OFF =", value, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffNotEqualTo(Boolean value) {
            addCriterion("PRESET_ON_OFF <>", value, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffGreaterThan(Boolean value) {
            addCriterion("PRESET_ON_OFF >", value, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffGreaterThanOrEqualTo(Boolean value) {
            addCriterion("PRESET_ON_OFF >=", value, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffLessThan(Boolean value) {
            addCriterion("PRESET_ON_OFF <", value, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffLessThanOrEqualTo(Boolean value) {
            addCriterion("PRESET_ON_OFF <=", value, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffIn(List<Boolean> values) {
            addCriterion("PRESET_ON_OFF in", values, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffNotIn(List<Boolean> values) {
            addCriterion("PRESET_ON_OFF not in", values, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffBetween(Boolean value1, Boolean value2) {
            addCriterion("PRESET_ON_OFF between", value1, value2, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andPresetOnOffNotBetween(Boolean value1, Boolean value2) {
            addCriterion("PRESET_ON_OFF not between", value1, value2, "presetOnOff");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterionForJDBCTime("START_TIME =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("START_TIME <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterionForJDBCTime("START_TIME >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("START_TIME >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterionForJDBCTime("START_TIME <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("START_TIME <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterionForJDBCTime("START_TIME in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("START_TIME not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("START_TIME between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("START_TIME not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterionForJDBCTime("END_TIME =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("END_TIME <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterionForJDBCTime("END_TIME >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("END_TIME >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterionForJDBCTime("END_TIME <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("END_TIME <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterionForJDBCTime("END_TIME in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("END_TIME not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("END_TIME between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("END_TIME not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayIsNull() {
            addCriterion("REPEAT_MONDAY is null");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayIsNotNull() {
            addCriterion("REPEAT_MONDAY is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayEqualTo(Boolean value) {
            addCriterion("REPEAT_MONDAY =", value, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayNotEqualTo(Boolean value) {
            addCriterion("REPEAT_MONDAY <>", value, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayGreaterThan(Boolean value) {
            addCriterion("REPEAT_MONDAY >", value, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_MONDAY >=", value, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayLessThan(Boolean value) {
            addCriterion("REPEAT_MONDAY <", value, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayLessThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_MONDAY <=", value, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayIn(List<Boolean> values) {
            addCriterion("REPEAT_MONDAY in", values, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayNotIn(List<Boolean> values) {
            addCriterion("REPEAT_MONDAY not in", values, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_MONDAY between", value1, value2, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatMondayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_MONDAY not between", value1, value2, "repeatMonday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayIsNull() {
            addCriterion("REPEAT_TUESDAY is null");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayIsNotNull() {
            addCriterion("REPEAT_TUESDAY is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayEqualTo(Boolean value) {
            addCriterion("REPEAT_TUESDAY =", value, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayNotEqualTo(Boolean value) {
            addCriterion("REPEAT_TUESDAY <>", value, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayGreaterThan(Boolean value) {
            addCriterion("REPEAT_TUESDAY >", value, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_TUESDAY >=", value, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayLessThan(Boolean value) {
            addCriterion("REPEAT_TUESDAY <", value, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayLessThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_TUESDAY <=", value, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayIn(List<Boolean> values) {
            addCriterion("REPEAT_TUESDAY in", values, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayNotIn(List<Boolean> values) {
            addCriterion("REPEAT_TUESDAY not in", values, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_TUESDAY between", value1, value2, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatTuesdayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_TUESDAY not between", value1, value2, "repeatTuesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayIsNull() {
            addCriterion("REPEAT_WEDNESDAY is null");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayIsNotNull() {
            addCriterion("REPEAT_WEDNESDAY is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayEqualTo(Boolean value) {
            addCriterion("REPEAT_WEDNESDAY =", value, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayNotEqualTo(Boolean value) {
            addCriterion("REPEAT_WEDNESDAY <>", value, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayGreaterThan(Boolean value) {
            addCriterion("REPEAT_WEDNESDAY >", value, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_WEDNESDAY >=", value, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayLessThan(Boolean value) {
            addCriterion("REPEAT_WEDNESDAY <", value, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayLessThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_WEDNESDAY <=", value, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayIn(List<Boolean> values) {
            addCriterion("REPEAT_WEDNESDAY in", values, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayNotIn(List<Boolean> values) {
            addCriterion("REPEAT_WEDNESDAY not in", values, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_WEDNESDAY between", value1, value2, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatWednesdayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_WEDNESDAY not between", value1, value2, "repeatWednesday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayIsNull() {
            addCriterion("REPEAT_THURDAY is null");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayIsNotNull() {
            addCriterion("REPEAT_THURDAY is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayEqualTo(Boolean value) {
            addCriterion("REPEAT_THURDAY =", value, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayNotEqualTo(Boolean value) {
            addCriterion("REPEAT_THURDAY <>", value, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayGreaterThan(Boolean value) {
            addCriterion("REPEAT_THURDAY >", value, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_THURDAY >=", value, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayLessThan(Boolean value) {
            addCriterion("REPEAT_THURDAY <", value, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayLessThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_THURDAY <=", value, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayIn(List<Boolean> values) {
            addCriterion("REPEAT_THURDAY in", values, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayNotIn(List<Boolean> values) {
            addCriterion("REPEAT_THURDAY not in", values, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_THURDAY between", value1, value2, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatThurdayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_THURDAY not between", value1, value2, "repeatThurday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayIsNull() {
            addCriterion("REPEAT_FRIDAY is null");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayIsNotNull() {
            addCriterion("REPEAT_FRIDAY is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayEqualTo(Boolean value) {
            addCriterion("REPEAT_FRIDAY =", value, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayNotEqualTo(Boolean value) {
            addCriterion("REPEAT_FRIDAY <>", value, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayGreaterThan(Boolean value) {
            addCriterion("REPEAT_FRIDAY >", value, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_FRIDAY >=", value, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayLessThan(Boolean value) {
            addCriterion("REPEAT_FRIDAY <", value, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayLessThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_FRIDAY <=", value, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayIn(List<Boolean> values) {
            addCriterion("REPEAT_FRIDAY in", values, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayNotIn(List<Boolean> values) {
            addCriterion("REPEAT_FRIDAY not in", values, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_FRIDAY between", value1, value2, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatFridayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_FRIDAY not between", value1, value2, "repeatFriday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayIsNull() {
            addCriterion("REPEAT_SATURDAY is null");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayIsNotNull() {
            addCriterion("REPEAT_SATURDAY is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayEqualTo(Boolean value) {
            addCriterion("REPEAT_SATURDAY =", value, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayNotEqualTo(Boolean value) {
            addCriterion("REPEAT_SATURDAY <>", value, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayGreaterThan(Boolean value) {
            addCriterion("REPEAT_SATURDAY >", value, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_SATURDAY >=", value, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayLessThan(Boolean value) {
            addCriterion("REPEAT_SATURDAY <", value, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayLessThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_SATURDAY <=", value, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayIn(List<Boolean> values) {
            addCriterion("REPEAT_SATURDAY in", values, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayNotIn(List<Boolean> values) {
            addCriterion("REPEAT_SATURDAY not in", values, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_SATURDAY between", value1, value2, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSaturdayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_SATURDAY not between", value1, value2, "repeatSaturday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayIsNull() {
            addCriterion("REPEAT_SUNDAY is null");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayIsNotNull() {
            addCriterion("REPEAT_SUNDAY is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayEqualTo(Boolean value) {
            addCriterion("REPEAT_SUNDAY =", value, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayNotEqualTo(Boolean value) {
            addCriterion("REPEAT_SUNDAY <>", value, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayGreaterThan(Boolean value) {
            addCriterion("REPEAT_SUNDAY >", value, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_SUNDAY >=", value, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayLessThan(Boolean value) {
            addCriterion("REPEAT_SUNDAY <", value, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayLessThanOrEqualTo(Boolean value) {
            addCriterion("REPEAT_SUNDAY <=", value, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayIn(List<Boolean> values) {
            addCriterion("REPEAT_SUNDAY in", values, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayNotIn(List<Boolean> values) {
            addCriterion("REPEAT_SUNDAY not in", values, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_SUNDAY between", value1, value2, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andRepeatSundayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("REPEAT_SUNDAY not between", value1, value2, "repeatSunday");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusIsNull() {
            addCriterion("LOCK_CALL_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusIsNotNull() {
            addCriterion("LOCK_CALL_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusEqualTo(Boolean value) {
            addCriterion("LOCK_CALL_STATUS =", value, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusNotEqualTo(Boolean value) {
            addCriterion("LOCK_CALL_STATUS <>", value, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusGreaterThan(Boolean value) {
            addCriterion("LOCK_CALL_STATUS >", value, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("LOCK_CALL_STATUS >=", value, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusLessThan(Boolean value) {
            addCriterion("LOCK_CALL_STATUS <", value, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("LOCK_CALL_STATUS <=", value, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusIn(List<Boolean> values) {
            addCriterion("LOCK_CALL_STATUS in", values, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusNotIn(List<Boolean> values) {
            addCriterion("LOCK_CALL_STATUS not in", values, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCK_CALL_STATUS between", value1, value2, "lockCallStatus");
            return (Criteria) this;
        }

        public Criteria andLockCallStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCK_CALL_STATUS not between", value1, value2, "lockCallStatus");
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