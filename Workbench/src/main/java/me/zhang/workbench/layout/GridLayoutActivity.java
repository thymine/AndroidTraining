package me.zhang.workbench.layout;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.TextView;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.widget.GridLayout.ALIGN_BOUNDS;
import static android.widget.GridLayout.BASELINE;
import static android.widget.GridLayout.CENTER;
import static android.widget.GridLayout.FILL;
import static android.widget.GridLayout.LEFT;
import static android.widget.GridLayout.LayoutParams;
import static android.widget.GridLayout.RIGHT;
import static android.widget.GridLayout.spec;

public class GridLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(create(this));
    }

    public static View create(Context context) {
        GridLayout p = new GridLayout(context);
        p.setUseDefaultMargins(true);
        p.setAlignmentMode(ALIGN_BOUNDS);
        Configuration configuration = context.getResources().getConfiguration();
        if ((configuration.orientation == Configuration.ORIENTATION_PORTRAIT)) {
            p.setColumnOrderPreserved(false);
        } else {
            p.setRowOrderPreserved(false);
        }

        Spec titleRow = spec(0);
        Spec introRow = spec(1);
        Spec emailRow = spec(2, BASELINE);
        Spec passwordRow = spec(3, BASELINE);
        Spec button1Row = spec(5);
        Spec button2Row = spec(6);

        Spec centerInAllColumns = spec(0, 4, CENTER);
        Spec leftAlignInAllColumns = spec(0, 4, LEFT);
        Spec labelColumn = spec(0, RIGHT);
        Spec fieldColumn = spec(1, LEFT);
        Spec defineLastColumn = spec(3);
        Spec fillLastColumn = spec(3, FILL);

        {
            TextView c = new TextView(context);
            c.setTextSize(32);
            c.setText("Email setup");
            p.addView(c, new LayoutParams(titleRow, centerInAllColumns));
        }
        {
            TextView c = new TextView(context);
            c.setTextSize(16);
            c.setText("You can configure email in a few simple steps:");
            p.addView(c, new LayoutParams(introRow, leftAlignInAllColumns));
        }
        {
            TextView c = new TextView(context);
            c.setText("Email address:");
            p.addView(c, new LayoutParams(emailRow, labelColumn));
        }
        {
            EditText c = new EditText(context);
            c.setEms(10);
            c.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            p.addView(c, new LayoutParams(emailRow, fieldColumn));
        }
        {
            TextView c = new TextView(context);
            c.setText("Password:");
            p.addView(c, new LayoutParams(passwordRow, labelColumn));
        }
        {
            TextView c = new EditText(context);
            c.setEms(8);
            c.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
            p.addView(c, new LayoutParams(passwordRow, fieldColumn));
        }
        {
            Button c = new Button(context);
            c.setText("Manual setup");
            p.addView(c, new LayoutParams(button1Row, defineLastColumn));
        }
        {
            Button c = new Button(context);
            c.setText("Next");
            p.addView(c, new LayoutParams(button2Row, fillLastColumn));
        }

        return p;
    }

}
