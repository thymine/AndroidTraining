package me.zhang.workbench.design;

import android.animation.Animator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import me.zhang.workbench.R;

import static me.zhang.workbench.design.SimplePaperTransformations.ViewHolder.green;
import static me.zhang.workbench.design.SimplePaperTransformations.ViewHolder.red;

public class SimplePaperTransformations extends AppCompatActivity {

    static String baconTitle = "Bacon";
    static String baconText = "Bacon ipsum dolor amet pork belly meatball kevin spare ribs. Frankfurter swine corned beef meatloaf, strip steak.";
    static String veggieTitle = "Veggie";
    static String veggieText = "Veggies es bonus vobis, proinde vos postulo essum magis kohlrabi welsh onion daikon amaranth tatsoi tomatillo melon azuki bean garlic.";
    static SparseBooleanArray mCurrentState = new SparseBooleanArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_paper_transformations);

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle(getString(R.string.app_name));
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.list_item, parent, false));
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                boolean isVeggie = mCurrentState.get(viewHolder.getAdapterPosition());
                viewHolder.text1.setText(isVeggie ? veggieTitle : baconTitle);
                viewHolder.text2.setText(isVeggie ? veggieText : baconText);
                viewHolder.mItemView.setBackgroundColor(isVeggie ? green : red);
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mItemView;
        TextView text1;
        TextView text2;
        static int green;
        static int red;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
            text2 = (TextView) itemView.findViewById(android.R.id.text2);
            itemView.setOnClickListener(this);

            if (green == 0)
                green = itemView.getContext().getResources().getColor(R.color.green);
            if (red == 0)
                red = itemView.getContext().getResources().getColor(R.color.red);
        }

        @Override
        public void onClick(View view) {
            boolean isVeggie = view.getBackground() != null && ((ColorDrawable) view.getBackground()).getColor() == green;
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).setBackgroundColor(isVeggie ? green : red);
            }

            int finalRadius = (int) Math.hypot(view.getWidth() / 2, view.getHeight() / 2);

            Animator anim = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2, view.getHeight() / 2, 0, finalRadius);
            if (isVeggie) {
                text1.setText(baconTitle);
                text2.setText(baconText);
                view.setBackgroundColor(red);
            } else {
                text1.setText(veggieTitle);
                text2.setText(veggieText);
                view.setBackgroundColor(green);
            }
            anim.start();

            mCurrentState.put(getAdapterPosition(), !isVeggie);
        }

    }
}
