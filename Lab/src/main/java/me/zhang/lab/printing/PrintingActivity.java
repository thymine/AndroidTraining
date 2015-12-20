package me.zhang.lab.printing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

import me.zhang.lab.R;

import static me.zhang.lab.utils.StringUtils.LOCAL_HTML_CONTENT;

public class PrintingActivity extends AppCompatActivity {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<PrintJob> printJobs = new ArrayList<>();

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_printing);

        final WebView webView = (WebView) findViewById(R.id.web_view);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                createWebPrintJob(webView);
                return true;
            }
        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });


        webView.loadUrl(LOCAL_HTML_CONTENT);
    }

    /**
     * When using WebView for creating print documents, you should be aware of the following limitations:
     * <p/>
     * You cannot add headers or footers, including page numbers, to the document.
     * <p/>
     * The printing options for the HTML document do not include the ability to print page ranges, for example: Printing page 2 to 4 of a 10 page HTML document is not supported.
     * <p/>
     * An instance of WebView can only process one print job at a time.
     * <p/>
     * An HTML document containing CSS print attributes, such as landscape properties, is not supported.
     * <p/>
     * You cannot use JavaScript in a HTML document to trigger printing.
     * <p/>
     * Note: The content of a WebView object that is included in a layout can also be printed once it has loaded a document.
     */
    private void createWebPrintJob(WebView view) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Get a PrintManager instance
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

            // Get a print adapter instance
            //noinspection deprecation
            PrintDocumentAdapter printDocumentAdapter = view.createPrintDocumentAdapter();

            // Create a print job with name and adapter instance
            String jobName = getString(R.string.app_name) + " Document";
            PrintJob printJob = printManager.print(jobName, printDocumentAdapter, new PrintAttributes.Builder().build());

            // Save the job object for later status checking
            printJobs.add(printJob);
        }
    }

    public void printMe(View view) {
        doPhotoPrint();
    }

    /**
     * Printing API: Android 4.4 (API Level 19) or higher
     */
    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.cloud);
        photoPrinter.printBitmap("cloud.jpg - test print", bitmap);
    }

}
