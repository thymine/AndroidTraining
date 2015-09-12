package me.zhang.lab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;

/**
 * Created by Zhang on 2015/7/27 下午 4:34 .
 */
public class NonScrollableListViewActivity extends Activity
        implements ExpandableListView.OnChildClickListener {

    private ArrayList<String> parents = new ArrayList<>();
    private ArrayList<ArrayList<String>> children = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(me.zhang.lab.R.layout.activity_non_scrollable);

        FixedExpandableListView expandableList = (FixedExpandableListView) findViewById(me.zhang.lab.R.id.list_expandable);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        MyExpandableAdapter adapter = new MyExpandableAdapter(parents, children);
        adapter.setInflater(
                (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this
        );
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }

    private void setGroupParents() {
        parents.add("Android");
        parents.add("Core Java");
        parents.add("Desktop Java");
        parents.add("Enterprise Java");
    }

    private void setChildData() {
        // Android
        ArrayList<String> child = new ArrayList<>();
        child.add("Core");
        child.add("Games");
        children.add(child);

        // Core Java
        child = new ArrayList<>();
        child.add("Apache");
        child.add("Applet");
        child.add("AspectJ");
        child.add("Beans");
        child.add("Crypto");
        children.add(child);

        // Desktop Java
        child = new ArrayList<>();
        child.add("Accessibility");
        child.add("AWT");
        child.add("ImageIO");
        child.add("Print");
        children.add(child);

        // Enterprise Java
        child = new ArrayList<>();
        child.add("EJB3");
        child.add("GWT");
        child.add("Hibernate");
        child.add("JSP");
        children.add(child);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Log.i("Zhang", "Child Clicked!");
        return false;
    }
}
