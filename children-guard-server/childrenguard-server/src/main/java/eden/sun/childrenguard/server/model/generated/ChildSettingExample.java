package eden.sun.childrenguard.server.model.generated;

import java.util.ArrayList;
import java.util.List;

public class ChildSettingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChildSettingExample() {
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

        public Criteria andLockCallsSwitchIsNull() {
            addCriterion("LOCK_CALLS_SWITCH is null");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchIsNotNull() {
            addCriterion("LOCK_CALLS_SWITCH is not null");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchEqualTo(Boolean value) {
            addCriterion("LOCK_CALLS_SWITCH =", value, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchNotEqualTo(Boolean value) {
            addCriterion("LOCK_CALLS_SWITCH <>", value, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchGreaterThan(Boolean value) {
            addCriterion("LOCK_CALLS_SWITCH >", value, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("LOCK_CALLS_SWITCH >=", value, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchLessThan(Boolean value) {
            addCriterion("LOCK_CALLS_SWITCH <", value, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchLessThanOrEqualTo(Boolean value) {
            addCriterion("LOCK_CALLS_SWITCH <=", value, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchIn(List<Boolean> values) {
            addCriterion("LOCK_CALLS_SWITCH in", values, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchNotIn(List<Boolean> values) {
            addCriterion("LOCK_CALLS_SWITCH not in", values, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCK_CALLS_SWITCH between", value1, value2, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockCallsSwitchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCK_CALLS_SWITCH not between", value1, value2, "lockCallsSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchIsNull() {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH is null");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchIsNotNull() {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH is not null");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchEqualTo(Boolean value) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH =", value, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchNotEqualTo(Boolean value) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH <>", value, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchGreaterThan(Boolean value) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH >", value, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH >=", value, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchLessThan(Boolean value) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH <", value, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchLessThanOrEqualTo(Boolean value) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH <=", value, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchIn(List<Boolean> values) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH in", values, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchNotIn(List<Boolean> values) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH not in", values, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH between", value1, value2, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andLockTextMessageSwitchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("LOCK_TEXT_MESSAGE_SWITCH not between", value1, value2, "lockTextMessageSwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchIsNull() {
            addCriterion("WIFI_ONLY_SWITCH is null");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchIsNotNull() {
            addCriterion("WIFI_ONLY_SWITCH is not null");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchEqualTo(Boolean value) {
            addCriterion("WIFI_ONLY_SWITCH =", value, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchNotEqualTo(Boolean value) {
            addCriterion("WIFI_ONLY_SWITCH <>", value, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchGreaterThan(Boolean value) {
            addCriterion("WIFI_ONLY_SWITCH >", value, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("WIFI_ONLY_SWITCH >=", value, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchLessThan(Boolean value) {
            addCriterion("WIFI_ONLY_SWITCH <", value, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchLessThanOrEqualTo(Boolean value) {
            addCriterion("WIFI_ONLY_SWITCH <=", value, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchIn(List<Boolean> values) {
            addCriterion("WIFI_ONLY_SWITCH in", values, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchNotIn(List<Boolean> values) {
            addCriterion("WIFI_ONLY_SWITCH not in", values, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchBetween(Boolean value1, Boolean value2) {
            addCriterion("WIFI_ONLY_SWITCH between", value1, value2, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andWifiOnlySwitchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("WIFI_ONLY_SWITCH not between", value1, value2, "wifiOnlySwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchIsNull() {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH is null");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchIsNotNull() {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH is not null");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchEqualTo(Boolean value) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH =", value, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchNotEqualTo(Boolean value) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH <>", value, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchGreaterThan(Boolean value) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH >", value, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH >=", value, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchLessThan(Boolean value) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH <", value, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchLessThanOrEqualTo(Boolean value) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH <=", value, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchIn(List<Boolean> values) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH in", values, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchNotIn(List<Boolean> values) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH not in", values, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchBetween(Boolean value1, Boolean value2) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH between", value1, value2, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andNewAppNotificationSwitchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("NEW_APP_NOTIFICATION_SWITCH not between", value1, value2, "newAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchIsNull() {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH is null");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchIsNotNull() {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH is not null");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchEqualTo(Boolean value) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH =", value, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchNotEqualTo(Boolean value) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH <>", value, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchGreaterThan(Boolean value) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH >", value, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH >=", value, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchLessThan(Boolean value) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH <", value, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchLessThanOrEqualTo(Boolean value) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH <=", value, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchIn(List<Boolean> values) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH in", values, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchNotIn(List<Boolean> values) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH not in", values, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchBetween(Boolean value1, Boolean value2) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH between", value1, value2, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andUninstallAppNotificationSwitchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("UNINSTALL_APP_NOTIFICATION_SWITCH not between", value1, value2, "uninstallAppNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchIsNull() {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH is null");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchIsNotNull() {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH is not null");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchEqualTo(Boolean value) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH =", value, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchNotEqualTo(Boolean value) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH <>", value, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchGreaterThan(Boolean value) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH >", value, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH >=", value, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchLessThan(Boolean value) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH <", value, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchLessThanOrEqualTo(Boolean value) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH <=", value, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchIn(List<Boolean> values) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH in", values, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchNotIn(List<Boolean> values) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH not in", values, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchBetween(Boolean value1, Boolean value2) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH between", value1, value2, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingNotificationSwitchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("SPEEDING_NOTIFICATION_SWITCH not between", value1, value2, "speedingNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitIsNull() {
            addCriterion("SPEEDING_LIMIT is null");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitIsNotNull() {
            addCriterion("SPEEDING_LIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitEqualTo(Integer value) {
            addCriterion("SPEEDING_LIMIT =", value, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitNotEqualTo(Integer value) {
            addCriterion("SPEEDING_LIMIT <>", value, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitGreaterThan(Integer value) {
            addCriterion("SPEEDING_LIMIT >", value, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("SPEEDING_LIMIT >=", value, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitLessThan(Integer value) {
            addCriterion("SPEEDING_LIMIT <", value, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitLessThanOrEqualTo(Integer value) {
            addCriterion("SPEEDING_LIMIT <=", value, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitIn(List<Integer> values) {
            addCriterion("SPEEDING_LIMIT in", values, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitNotIn(List<Integer> values) {
            addCriterion("SPEEDING_LIMIT not in", values, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitBetween(Integer value1, Integer value2) {
            addCriterion("SPEEDING_LIMIT between", value1, value2, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andSpeedingLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("SPEEDING_LIMIT not between", value1, value2, "speedingLimit");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchIsNull() {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH is null");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchIsNotNull() {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH is not null");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchEqualTo(Boolean value) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH =", value, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchNotEqualTo(Boolean value) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH <>", value, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchGreaterThan(Boolean value) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH >", value, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchGreaterThanOrEqualTo(Boolean value) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH >=", value, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchLessThan(Boolean value) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH <", value, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchLessThanOrEqualTo(Boolean value) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH <=", value, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchIn(List<Boolean> values) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH in", values, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchNotIn(List<Boolean> values) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH not in", values, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchBetween(Boolean value1, Boolean value2) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH between", value1, value2, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockUnlockNotificationSwitchNotBetween(Boolean value1, Boolean value2) {
            addCriterion("APP_LOCK_UNLOCK_NOTIFICATION_SWITCH not between", value1, value2, "appLockUnlockNotificationSwitch");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordIsNull() {
            addCriterion("APP_LOCK_PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordIsNotNull() {
            addCriterion("APP_LOCK_PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordEqualTo(String value) {
            addCriterion("APP_LOCK_PASSWORD =", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordNotEqualTo(String value) {
            addCriterion("APP_LOCK_PASSWORD <>", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordGreaterThan(String value) {
            addCriterion("APP_LOCK_PASSWORD >", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("APP_LOCK_PASSWORD >=", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordLessThan(String value) {
            addCriterion("APP_LOCK_PASSWORD <", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordLessThanOrEqualTo(String value) {
            addCriterion("APP_LOCK_PASSWORD <=", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordLike(String value) {
            addCriterion("APP_LOCK_PASSWORD like", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordNotLike(String value) {
            addCriterion("APP_LOCK_PASSWORD not like", value, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordIn(List<String> values) {
            addCriterion("APP_LOCK_PASSWORD in", values, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordNotIn(List<String> values) {
            addCriterion("APP_LOCK_PASSWORD not in", values, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordBetween(String value1, String value2) {
            addCriterion("APP_LOCK_PASSWORD between", value1, value2, "appLockPassword");
            return (Criteria) this;
        }

        public Criteria andAppLockPasswordNotBetween(String value1, String value2) {
            addCriterion("APP_LOCK_PASSWORD not between", value1, value2, "appLockPassword");
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