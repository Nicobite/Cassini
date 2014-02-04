package main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Sylvain
 */

// something to display several things about an exception or error

public class CustomLogFormatter extends SimpleFormatter {
	public String format (LogRecord record) {
		Date date = new Date(record.getMillis());
		Thread thread = null;
		
		ThreadGroup rootThreadGroup = Thread.currentThread().getThreadGroup();
		while (rootThreadGroup.getParent() != null)
			rootThreadGroup = rootThreadGroup.getParent();
		Thread[] threads = new Thread[rootThreadGroup.activeCount()];
		rootThreadGroup.enumerate(threads);
		for (Thread th : threads) {
			if (th.getId() == record.getThreadID()) {
				thread = th;
				break;
			}
		}
		
		
		StringBuilder s = new StringBuilder ();

		s.append(record.getLevel().toString());
		s.append(" : ");
		s.append(record.getMessage());
		s.append(" (thread ");
		if (thread != null) s.append(thread.getName());
		else s.append("null");
		s.append(", class ");
		s.append(record.getSourceClassName());
		s.append(", method ");
		s.append(record.getSourceMethodName());
		s.append(", date : ");
		s.append(date.toString());
		s.append(")\n");
		
		if (record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			record.getThrown().printStackTrace(new PrintWriter(sw));
			
			s.append("\tTHROW : ");
			s.append(sw.toString());
		}
		
		s.append("\n");
		
		return s.toString();
	}
}
