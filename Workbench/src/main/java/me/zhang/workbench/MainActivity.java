package me.zhang.workbench;

import me.zhang.art.ipc.messenger.MessengerClientActivity;
import me.zhang.art.ipc.parcel.ClientActivity;
import me.zhang.art.ipc.provider.ProviderExternalUserActivity;
import me.zhang.art.ipc.socket.TcpClientActivity;
import me.zhang.workbench.animation.AnimateLayoutChangesActivity;
import me.zhang.workbench.animation.CardFlipActivity;
import me.zhang.workbench.animation.LogoWhiteAnimActivity;
import me.zhang.workbench.animation.TranslateActivity;
import me.zhang.workbench.base.MenuActivity;
import me.zhang.workbench.gestures.GesturesActivity;
import me.zhang.workbench.layout.LayoutActivity;
import me.zhang.workbench.layout.SimpleLayoutActivity;
import me.zhang.workbench.leaks.LeakyActivity;
import me.zhang.workbench.touchEvent.TouchEventActivity;
import me.zhang.workbench.touchEvent.conflict.HVConflictExternalActivity;
import me.zhang.workbench.touchEvent.conflict.HVConflictInternalActivity;
import me.zhang.workbench.view.GetViewDimensActivity;
import me.zhang.workbench.view.ScrollViewActivity;
import me.zhang.workbench.view.SmoothScrollActivity;
import me.zhang.workbench.view.ViewScrollActivity;
import me.zhang.workbench.view.VisibilityActivity;
import me.zhang.workbench.webview.WebViewActivity;

public class MainActivity extends MenuActivity {

    @Override
    protected void prepareMenu() {
        addMenuItem("Layout", LayoutActivity.class);
        addMenuItem("Leaks", LeakyActivity.class);
        addMenuItem("WebView", WebViewActivity.class);
        addMenuItem("GesturesActivity", GesturesActivity.class);
        addMenuItem("Visibility", VisibilityActivity.class);
        addMenuItem("Card Flip", CardFlipActivity.class);
        addMenuItem("Animate Layout Changes", AnimateLayoutChangesActivity.class);
        addMenuItem("I/O Logo White Animation", LogoWhiteAnimActivity.class);
        addMenuItem("Client", ClientActivity.class);
        addMenuItem("Messenger Client", MessengerClientActivity.class);
        addMenuItem("Use Provider", ProviderExternalUserActivity.class);
        addMenuItem("Tcp client", TcpClientActivity.class);
        addMenuItem("Simple Layout", SimpleLayoutActivity.class);
        addMenuItem("View Scroll", ViewScrollActivity.class);
        addMenuItem("Translate Animation", TranslateActivity.class);
        addMenuItem("Smooth Scroll", SmoothScrollActivity.class);
        addMenuItem("Touch Event", TouchEventActivity.class);
        addMenuItem("HV Conflict External", HVConflictExternalActivity.class);
        addMenuItem("HV Conflict Internal", HVConflictInternalActivity.class);
        addMenuItem("Get View Dimens", GetViewDimensActivity.class);
        addMenuItem("ScrollView Dimens", ScrollViewActivity.class);
    }

}
