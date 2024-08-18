package net.torosamy.safeTrade.config;

import net.torosamy.torosamyCore.config.TorosamyConfig;

public class MainConfig extends TorosamyConfig {
    public Integer cancelSecond;

    public Boolean sneakMode;

    public Integer continueSecond;

    public StartLogs startLogs = new StartLogs();
    public class StartLogs extends TorosamyConfig {
        public Boolean enabled;
        public Boolean consoleEnabled;
//        public Boolean autoSave;
    }
}
