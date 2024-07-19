package net.torosamy.safetradeywy.pojo;

public class MainConfig {
    private static Boolean startValue;
    private static Integer cancelSecond;
    private static Boolean sneakMode;
    private static Integer successSecond;
    private static Boolean startLogs;



    public static Integer getSuccessSecond() {
        return successSecond;
    }

    public static void setSuccessSecond(Integer successSecond) {
        MainConfig.successSecond = successSecond;
    }

    //    private static Boolean rightClick;
//    public static Boolean getRightClick() {
//        return rightClick;
//    }
//
//    public static void setRightClick(Boolean rightClick) {
//        MainConfig.rightClick = rightClick;
//    }
    public static Boolean getSneakMode() {
        return sneakMode;
    }

    public static void setSneakMode(Boolean sneakMode) {
        MainConfig.sneakMode = sneakMode;
    }

    public static Integer getCancelSecond() {
        return cancelSecond;
    }

    public static void setCancelSecond(Integer cancelSecond) {
        MainConfig.cancelSecond = cancelSecond;
    }

    public static Boolean getStartValue() {
        return startValue;
    }

    public static void setStartValue(Boolean startValue) {
        MainConfig.startValue = startValue;
    }
    public static Boolean getStartLogs() {
        return startLogs;
    }

    public static void setStartLogs(Boolean startLogs) {
        MainConfig.startLogs = startLogs;
    }
}
