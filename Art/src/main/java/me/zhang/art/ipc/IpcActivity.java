package me.zhang.art.ipc;

import me.zhang.art.base.MenuActivity;
import me.zhang.art.ipc.provider.ProviderInternalUserActivity;

/**
 * Created by Li on 6/9/2016 12:22 PM.
 */
public class IpcActivity extends MenuActivity {

    @Override
    protected void prepareMenu() {
        addMenuItem("MultiProcess", DefaultProcessActivity.class);
        addMenuItem("Provider", ProviderInternalUserActivity.class);
    }

}
