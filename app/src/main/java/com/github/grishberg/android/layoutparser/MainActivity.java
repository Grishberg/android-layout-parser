package com.github.grishberg.android.layoutparser;

import android.app.*;
import android.os.*;
import java.io.*;
import com.github.grishberg.android.tools.layoutparser.*;
import android.content.res.*;
import com.github.grishberg.consoleview.*;
import com.github.grishberg.android.tools.layoutparser.common.*;

public class MainActivity extends Activity {
	private LayoutParser parser;
	private Logger log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		log = new LoggerImpl();
		parser = new LayoutParser(new L());
		
		try
		{
			readCapture();
		} catch (IOException e)
		{
			log.d("MA","capture error: "+ e.getMessage());
		}
    }

	private ParsedLayoutHierarchy readCapture() throws IOException {
        Resources res = getResources();
        InputStream stream = res.openRawResource(R.raw.layoutcapture);
        return parser.parse(stream);
    }

	private class L implements ParserLogger {
		@Override
		public void d(String t, String m) {
			log.d(t, m);
		}
	}
}
