package com.longtech.nettools;

import android.os.Bundle;
import android.os.Handler;
//import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.longtech.nettools.shell.NetworkTools;
import com.longtech.nettools.shell.ProcessStream.ProcessStreamReader;
import com.longtech.nettools.shell.ShellProcess.OnComplete;

// TODO: Auto-generated Javadoc
/**
 * A fragment representing a single Tool detail screen. This fragment is either
 * contained in a {@link ToolListActivity} in two-pane mode (on tablets) or a
 * {@link ToolDetailActivity} on handsets.
 */
public class ToolDetailFragment extends Fragment implements OnClickListener,
		ProcessStreamReader, OnComplete {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The content this fragment is presenting.
	 */
	private NetworkTools.NetworkTool mItem;

	// Handle callbacks to UI thread
	/** The m handler. */
	private final Handler mHandler = new Handler();

	/** The sline. */
	private static String sline = "";

	/** The root view. */
	private View rootView = null;

	/** The console. */
	private EditText console = null;

	/** The b. */
	private Button b = null;

	/** The m update results. */
	final Runnable mUpdateResults = new Runnable() {
		public void run() { // Write results to "console" EditText
			console.append("\n" + sline);
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ToolDetailFragment() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			mItem = NetworkTools.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_tool_detail, container,
				false);
		console = (EditText) rootView.findViewById(R.id.tool_console);

		b = (Button) rootView.findViewById(R.id.tool_start);
		b.setOnClickListener(this);
		if (mItem != null) {
			b.setText(mItem.content);
		} else {
			b.setText("Start");
		}

		return rootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		String[] args = new String[1];
		args[0] = ((EditText) rootView.findViewById(R.id.tool_input)).getText()
				.toString();

		if ((mItem.worker != null && !mItem.worker.checkArgs(args))
				|| (mItem.tworker != null && !mItem.tworker.checkArgs(args))) {
			console.setText("Please enter a hostname (something.com) or IPv4 address (123.45.67.89)");
		} else {
			console.setText("");
			b.setEnabled(false);

			mItem.start(args, this, this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.longtech.nettools.shell.ProcessStream.ProcessStreamReader#onLineRead
	 * (java.lang.String)
	 */
	@Override
	public void onLineRead(String line) {
		sline = line;
		mHandler.post(mUpdateResults);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.longtech.nettools.shell.ShellProcess.OnComplete#onComplete(java.lang
	 * .String)
	 */
	@Override
	public void onComplete(String results) {
		console.append("\n" + results);
		b.setEnabled(true);
	}

}
