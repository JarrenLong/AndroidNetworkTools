package com.longtech.nettools.shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.longtech.nettools.shell.ProcessStream.ProcessStreamReader;
import com.longtech.nettools.shell.ShellProcess.OnComplete;

// TODO: Auto-generated Javadoc
/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class NetworkTools {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<NetworkTool> ITEMS = new ArrayList<NetworkTool>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, NetworkTool> ITEM_MAP = new HashMap<String, NetworkTool>();

	static {
		// Local device status utilities
		addItem(new NetworkTool("1", "Netstat", new NetStat()));
		addItem(new NetworkTool("2", "Netcfg", new NetCfg()));
		addItem(new NetworkTool("3", "IP Tools", new IPTools()));
		addItem(new NetworkTool("4", "Ping", new Ping()));
		// addItem(new NetworkTool("8", "Trace Route", new IPTools()));
		// Remote service test utilities
		addItem(new NetworkTool("5", "Port Scan", new PortScan()));
		// addItem(new NetworkTool("5", "Whois", new IPTools()));
		// addItem(new NetworkTool("7", "DNS Lookup", new IPTools()));
		// addItem(new NetworkTool("6", "Location Lookup", new IPTools()));
		// addItem(new NetworkTool("10", "Get HTTP Headers", new IPTools()));
		// addItem(new NetworkTool("11", "Email Tests", new IPTools()));
	}

	/**
	 * Checks if is host name.
	 * 
	 * @param arg
	 *            the arg
	 * @return true, if is host name
	 */
	public static boolean isHostName(String arg) {
		// Make sure the first argument is a valid host name or IPv4 address
		return arg.length() > 0 && arg.matches("\\w{1,}\\.\\w{2,4}");
	}

	/**
	 * Checks if is i pv4 adress.
	 * 
	 * @param arg
	 *            the arg
	 * @return true, if is i pv4 adress
	 */
	public static boolean isIPv4Adress(String arg) {
		// Make sure the first argument is a valid host name or IPv4 address
		return arg.length() > 0
				&& arg.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
	}

	/**
	 * Adds the item.
	 * 
	 * @param item
	 *            the item
	 */
	private static void addItem(NetworkTool item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class NetworkTool {

		/** The id. */
		public String id;

		/** The content. */
		public String content;

		/** The worker. */
		public ShellProcess worker = null;

		/** The tworker. */
		public ThreadProcess tworker = null;

		/** The reader. */
		public ProcessStreamReader reader;

		/**
		 * Instantiates a new network tool.
		 * 
		 * @param id
		 *            the id
		 * @param content
		 *            the content
		 * @param worker
		 *            the worker
		 */
		public NetworkTool(String id, String content, ShellProcess worker) {
			this.id = id;
			this.content = content;
			this.worker = worker;
		}

		/**
		 * Instantiates a new network tool.
		 * 
		 * @param id
		 *            the id
		 * @param content
		 *            the content
		 * @param worker
		 *            the worker
		 */
		public NetworkTool(String id, String content, ThreadProcess worker) {
			this.id = id;
			this.content = content;
			this.tworker = worker;
		}

		/**
		 * Start.
		 * 
		 * @param args
		 *            the args
		 * @param reader
		 *            the reader
		 * @param complete
		 *            the complete
		 */
		public void start(String[] args, ProcessStreamReader reader,
				OnComplete complete) {
			if (this.worker != null) {
				this.worker.setArgs(args);
				this.worker.setReader(reader);
				this.worker.setComplete(complete);
				new Thread(this.worker).start();
			} else if (this.tworker != null) {
				this.tworker.setArgs(args);
				this.tworker.setComplete(complete);
				new Thread(this.tworker).start();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return content;
		}
	}

}
