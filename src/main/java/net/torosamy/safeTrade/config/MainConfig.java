package net.torosamy.safeTrade.config;

import net.torosamy.torosamyCore.config.IConfigManage;

public class MainConfig implements IConfigManage {
    public Integer cancelSecond;

    public Boolean sneakMode;

    public Integer continueSecond;

    public StartLogs startLogs = new StartLogs();
    public class StartLogs implements IConfigManage {
        public Boolean enabled;
        public Boolean consoleEnabled;
//        public Boolean autoSave;
    }
}
