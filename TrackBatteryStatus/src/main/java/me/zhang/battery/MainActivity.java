package me.zhang.battery;

public class MainActivity extends MenuActivity {

    @Override
    public void prepareMenu() {
        addMenuItem("Charge Status", ChargeStatusActivity.class);
        addMenuItem("Job Service", WakelockActivity.class);
    }
}
