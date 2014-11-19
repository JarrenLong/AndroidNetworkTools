package com.longtech.nettools.shell;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// TODO: Auto-generated Javadoc
/**
 * The Class Ping.
 */
public class PortScan extends ThreadProcess {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.longtech.nettools.shell.ThreadProcess#checkArgs(java.lang.String[])
	 */
	public boolean checkArgs(String[] args) {
		// Make sure the first argument is a valid host name or IPv4 address
		return NetworkTools.isHostName(args[0])
				|| NetworkTools.isIPv4Adress(args[0]);
	}

	/**
	 * Port is open.
	 * 
	 * @param es
	 *            the es
	 * @param ip
	 *            the ip
	 * @param port
	 *            the port
	 * @param timeout
	 *            the timeout
	 * @return the future
	 */
	public static Future<PortScanResult> portIsOpen(final ExecutorService es,
			final String ip, final int port, final int timeout) {
		return es.submit(new Callable<PortScanResult>() {
			@Override
			public PortScanResult call() {
				boolean ret = true;
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(ip, port), timeout);
					socket.close();
				} catch (Exception ex) {
					ret = false;
				}

				return new PortScanResult(port, ret);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.longtech.nettools.shell.ThreadProcess#runThread()
	 */
	@Override
	protected void runThread() {
		final ExecutorService es = Executors.newFixedThreadPool(10);
		final String ip = getArgs()[0];
		final int timeout = 200;
		final List<Future<PortScanResult>> futures = new ArrayList<Future<PortScanResult>>();

		for (int port = 1; port <= 443; port++) {
			futures.add(portIsOpen(es, ip, port, timeout));
		}
		es.shutdown();

		String buf = "";
		int openPorts = 0;
		for (final Future<PortScanResult> f : futures) {
			try {
				buf += "Port " + f.get().port + ": "
						+ (f.get().isOpen ? " Open" : "Closed") + "\n";
				if (f.get().isOpen) {
					openPorts++;
				}
			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
			}
		}

		setResults(openPorts + "/1024 open ports on host " + ip
				+ " (probed with a timeout of " + timeout + "ms)\n" + buf);
	}
}
