package me.zhang.lab.editor;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2016/2/18 上午 11:26 .
 */
public class EditorActivity extends Activity {

    private static final String TAG = EditorActivity.class.getSimpleName();

    private static final HashMap<String, Integer> emoticons = new HashMap<>();

    static {
        emoticons.put(":*", R.drawable.emo_im_kiss);
        emoticons.put(":-D", R.drawable.emo_im_glad);
        emoticons.put(":)", R.drawable.emo_im_happy);
        emoticons.put(":-(", R.drawable.emo_im_sad);
    }

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editor);

        editText = (EditText) findViewById(R.id.et_content);
    }


    Random random = new Random(System.currentTimeMillis());
    Object[] emojis = emoticons.keySet().toArray();

    public void insertImage(View view) {
        editText.getText().insert(editText.getSelectionStart(), getSmiledText((String) emojis[random.nextInt(emoticons.size())]));

        Log.i(TAG, editText.getText().toString());
    }

    private Spannable getSmiledText(String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        int index;
        for (index = 0; index < builder.length(); index++) {
            for (Map.Entry<String, Integer> entry : emoticons.entrySet()) {
                int length = entry.getKey().length();
                if (index + length > builder.length())
                    continue;
                if (builder.subSequence(index, index + length).toString().equals(entry.getKey())) {
                    @SuppressWarnings("deprecation") Drawable drawable = getResources().getDrawable(entry.getValue());
                    if (drawable != null) {
                        /* 获取文本高度 */
//                        Rect bounds = new Rect();
//                        editText.getPaint().getTextBounds("高", 0, 1, bounds); // 使用中文的高度
//                        int size = Math.abs(bounds.height());
                        drawable.setBounds(0, 0, editText.getLineHeight() * 2, editText.getLineHeight() * 2);
                        builder.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM) {
//                                            public void draw(Canvas canvas, CharSequence text, int start,
//                                                             int end, float x, int top, int y, int bottom,
//                                                             Paint paint) {
//                                                Drawable b = getDrawable();
//                                                canvas.save();
//
//                                                int transY = bottom - b.getBounds().bottom;
//                                                // this is the key
//                                                transY -= paint.getFontMetricsInt().descent / 2;
//
//                                                canvas.translate(x, transY);
//                                                b.draw(canvas);
//                                                canvas.restore();
//                                            }
                                        }, index, index + length,
                                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        index += length - 1;
                    }
                    break;
                }
            }
        }
        return builder;
    }

    /****************************** speed improvement ********************************/

    /*private static final Spannable.Factory spannableFactory = Spannable.Factory
            .getInstance();

    private static final Map<Pattern, Integer> emojiicons = new HashMap<>();

    static {
        addPattern(emojiicons, ":)", R.drawable.emo_im_happy);
        addPattern(emojiicons, ":-)", R.drawable.emo_im_happy);
        // ...
    }

    private static void addPattern(Map<Pattern, Integer> map, String smile,
                                   int resource) {
        map.put(Pattern.compile(Pattern.quote(smile)), resource);
    }

    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emojiicons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    spannable.setSpan(new ImageSpan(context, entry.getValue()),
                            matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }*/
}
