package com.longtech.nettools.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessStream.
 */
public class ProcessStream extends Thread {

	/** The is. */
	private InputStream is;

	/** The reader. */
	private ProcessStreamReader reader = dummy;

	/** The contents. */
	private String contents = "";

	/**
	 * The Interface ProcessStreamReader.
	 */
	public interface ProcessStreamReader {

		/**
		 * On line read.
		 * 
		 * @param line
		 *            the line
		 */
		public void onLineRead(String line);
	}

	/** The dummy. */
	private static ProcessStreamReader dummy = new ProcessStreamReader() {
		@Override
		public void onLineRead(String line) {
			// Dummy callback, do nothing
		}
	};

	/**
	 * Instantiates a new process stream.
	 * 
	 * @param is
	 *            the is
	 * @param reader
	 *            the reader
	 */
	ProcessStream(InputStream is, ProcessStreamReader reader) {
		this.is = is;
		this.reader = reader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				contents += line + "\n";
				reader.onLineRead(line);
			}
		} catch (IOException ioe) {
		}
	}

	/**
	 * Gets the stream contents.
	 * 
	 * @return the stream contents
	 */
	public String getStreamContents() {
		return contents;
	}
}