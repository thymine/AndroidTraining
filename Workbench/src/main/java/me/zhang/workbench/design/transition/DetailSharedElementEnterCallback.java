package me.zhang.workbench.design.transition;

import android.app.SharedElementCallback;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DetailSharedElementEnterCallback extends SharedElementCallback {

    private View colorView;

    @Override
    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        removeObsoleteElements(names, sharedElements, mapObsoleteElements(names));
        mapSharedElement(names, sharedElements, getColorView());
    }

    public void setColorView(@NonNull View colorView) {
        this.colorView = colorView;
    }

    private View getColorView() {
        if (colorView != null) {
            return colorView;
        } else {
            throw new NullPointerException("Must set a view before transitioning.");
        }
    }

    /**
     * Maps all views that don't start with "android" namespace.
     *
     * @param names All shared element names.
     * @return The obsolete shared element names.
     */
    @NonNull
    private List<String> mapObsoleteElements(List<String> names) {
        List<String> elementsToRemove = new ArrayList<>(names.size());
        for (String name : names) {
            if (name.startsWith("android")) continue;
            elementsToRemove.add(name);
        }
        return elementsToRemove;
    }

    /**
     * Removes obsolete elements from names and shared elements.
     *
     * @param names            Shared element names.
     * @param sharedElements   Shared elements.
     * @param elementsToRemove The elements that should be removed.
     */
    private void removeObsoleteElements(List<String> names,
                                        Map<String, View> sharedElements,
                                        List<String> elementsToRemove) {
        if (elementsToRemove.size() > 0) {
            names.removeAll(elementsToRemove);
            for (String elementToRemove : elementsToRemove) {
                sharedElements.remove(elementToRemove);
            }
        }
    }

    /**
     * Puts a shared element to transitions and names.
     *
     * @param names          The names for this transition.
     * @param sharedElements The elements for this transition.
     * @param view           The view to add.
     */
    private void mapSharedElement(List<String> names, Map<String, View> sharedElements, View view) {
        String transitionName = view.getTransitionName();
        names.add(transitionName);
        sharedElements.put(transitionName, view);
    }

}