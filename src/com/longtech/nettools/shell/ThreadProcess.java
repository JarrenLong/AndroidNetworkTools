package com.longtech.nettools.shell;

import android.os.Handler;

import com.longtech.nettools.shell.ShellProcess.OnComplete;

// TODO: Auto-generated Javadoc
/**
 * The Class ConsoleRunnable.
 */
public abstract class ThreadProcess implements Runnable {

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
	public ThreadProcess() {
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
	 * Sets the results.
	 * 
	 * @param results
	 *            the new results
	 */
	protected void setResults(String results) {
		mResults = results;
	}

	/**
	 * Check args.
	 * 
	 * @param args
	 *            the args
	 * @return true, if successful
	 */
	public abstract boolean checkArgs(String[] args);

	/**
	 * Run thread.
	 */
	protected abstract void runThread();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			runThread();
		} catch (Exception e) {
			mResults = e.toString();
		}

		mHandler.post(mUpdateResults);
	}
}
