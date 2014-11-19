package com.longtech.nettools.shell;

import java.io.IOException;

import android.os.Handler;

import com.longtech.nettools.shell.ProcessStream.ProcessStreamReader;

// TODO: Auto-generated Javadoc
/**
 * The Class ConsoleRunnable.
 */
public abstract class ShellProcess implements Runnable {

	/**
	 * The Interface OnComplete.
	 */
	public interface OnComplete {

		/**
		 * On complete.
		 * 
		 * @param results
		 *            the results
		 */
		public void onComplete(String results);
	}

	/** The reader. */
	private ProcessStreamReader reader = null;

	/** The complete. */
	private OnComplete complete = null;

	/** The m args. */
	private String[] mArgs = null;

	/** The m results. */
	private String mResults = "";

	/** The m handler. */
	private final Handler mHandler = new Handler();

	/** The m update results. */
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			complete.onComplete(mResults);
		}
	};

	/**
	 * Instantiates a new console runnable.
	 */
	public ShellProcess() {
	}

	/**
	 * Sets the reader.
	 * 
	 * @param reader
	 *            the new reader
	 */
	public void setReader(ProcessStreamReader reader) {
		this.reader = reader;
	}

	/**
	 * Sets the complete.
	 * 
	 * @param cb
	 *            the new complete
	 */
	public void setComplete(OnComplete cb) {
		this.complete = cb;
	}

	/**
	 * Gets the args.
	 * 
	 * @return the args
	 */
	public String[] getArgs() {
		return mArgs;
	}

	/**
	 * Sets the args.
	 * 
	 * @param args
	 *            the new args
	 */
	public void setArgs(String[] args) {
		mArgs = args;
	}

	/**
	 * Gets the system command.
	 * 
	 * @return the system command
	 */
	protected abstract String getSystemCommand();

	/**
	 * Check args.
	 * 
	 * @param args
	 *            the args
	 * @return true, if successful
	 */
	public abstract boolean checkArgs(String[] args);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		Process proc = null;
		ProcessStream out = null;// , err = null;
		String cmd = getSystemCommand();

		try {
			proc = Runtime.getRuntime().exec(cmd);

			// Process stdout stream
			out = new ProcessStream(proc.getInputStream(), reader);
			out.start();
			out.join();
		} catch (IOException ie) {
			mResults = "Sorry, " + cmd.split(" ")[0]
					+ " is not available on your device!";
		} catch (Exception e) {
			mResults = e.toString();
		}

		mHandler.post(mUpdateResults);
	}
}
