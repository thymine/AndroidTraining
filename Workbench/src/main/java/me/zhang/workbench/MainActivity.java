package me.zhang.workbench;

import me.zhang.art.ipc.messenger.MessengerClientActivity;
import me.zhang.art.ipc.parcel.ClientActivity;
import me.zhang.art.ipc.provider.ProviderExternalUserActivity;
import me.zhang.art.ipc.socket.TcpClientActivity;
import me.zhang.workbench.adapter.AdapterActivity;
import me.zhang.workbench.animation.AnimateLayoutChangesActivity;
import me.zhang.workbench.animation.AnimationsActivity;
import me.zhang.workbench.animation.CardFlipActivity;
import me.zhang.workbench.animation.LogoWhiteAnimActivity;
import me.zhang.workbench.animation.RecyclerViewAnimationActivity;
import me.zhang.workbench.animation.TranslateActivity;
import me.zhang.workbench.base.MenuActivity;
import me.zhang.workbench.design.BottomSheetActivity;
import me.zhang.workbench.design.CustomFabActivity;
import me.zhang.workbench.design.DynamicSurfacesActivity;
import me.zhang.workbench.design.FabActivity;
import me.zhang.workbench.design.SimplePaperTransformations;
import me.zhang.workbench.drawable.ClipDrawableActivity;
import me.zhang.workbench.drawable.CustomDrawableActivity;
import me.zhang.workbench.drawable.LevelListActivity;
import me.zhang.workbench.drawable.ScaleDrawableActivity;
import me.zhang.workbench.drawable.TransitionActivity;
import me.zhang.workbench.gestures.GesturesActivity;
import me.zhang.workbench.handler.HandlerActivity;
import me.zhang.workbench.layout.ConstraintActivity;
import me.zhang.workbench.layout.CustomLayoutActivity;
import me.zhang.workbench.layout.LayoutActivity;
import me.zhang.workbench.layout.SimpleLayoutActivity;
import me.zhang.workbench.leaks.LeakyActivity;
import me.zhang.workbench.remoteViews.NotificationActivity;
import me.zhang.workbench.retrofit.TestActivity;
import me.zhang.workbench.rx.RxActivity;
import me.zhang.workbench.touchEvent.TouchEventActivity;
import me.zhang.workbench.touchEvent.conflict.HVConflictExternalActivity;
import me.zhang.workbench.touchEvent.conflict.HVConflictInternalActivity;
import me.zhang.workbench.ui.CircleViewActivity;
import me.zhang.workbench.view.CustomViewActivity;
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
        addMenuItem("Custom View", CustomViewActivity.class);
        addMenuItem("Custom Layout", CustomLayoutActivity.class);
        addMenuItem("Circle View", CircleViewActivity.class);
        addMenuItem("Notification", NotificationActivity.class);
        addMenuItem("Handler", HandlerActivity.class);
        addMenuItem("Bottom Sheet", BottomSheetActivity.class);
        addMenuItem("Level List Drawable", LevelListActivity.class);
        addMenuItem("Transition Drawable", TransitionActivity.class);
        addMenuItem("Retrofit Test", TestActivity.class);
        addMenuItem("Rx", RxActivity.class);
        addMenuItem("Contraint Layout", ConstraintActivity.class);
        addMenuItem("Scale Drawable", ScaleDrawableActivity.class);
        addMenuItem("Clip Drawable", ClipDrawableActivity.class);
        addMenuItem("Custom Drawable", CustomDrawableActivity.class);
        addMenuItem("Animations", AnimationsActivity.class);
        addMenuItem("Adapter", AdapterActivity.class);
        addMenuItem("Floating Action Button", FabActivity.class);
        addMenuItem("Custom Floating Action Button", CustomFabActivity.class);
        addMenuItem("Simple Paper Transformations", SimplePaperTransformations.class);
        addMenuItem("RecyclerView Animation", RecyclerViewAnimationActivity.class);
        addMenuItem("Dynamic Surfaces", DynamicSurfacesActivity.class);
    }

}
