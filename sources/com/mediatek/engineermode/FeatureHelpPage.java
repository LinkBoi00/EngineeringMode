package com.mediatek.engineermode;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class FeatureHelpPage extends Activity {
    public static final String HELP_MESSAGE_KEY = "helpMessage";
    public static final String HELP_TITLE_KEY = "helpTitle";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);
        Intent intent = getIntent();
        Resources resources = getResources();
        String helpTitle = "";
        String helpMsg = "";
        if (intent != null) {
            helpTitle = resources.getString(intent.getIntExtra(HELP_TITLE_KEY, R.string.help));
            helpMsg = resources.getString(intent.getIntExtra(HELP_MESSAGE_KEY, R.string.help));
        }
        ((TextView) findViewById(R.id.textview_title)).setText(helpTitle);
        TextView mMessageView = (TextView) findViewById(R.id.textview_help);
        mMessageView.setText(helpMsg);
        mMessageView.setHorizontallyScrolling(false);
        mMessageView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
