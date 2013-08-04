package it.amattioli.encapsulate.dates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class CompositeDuration implements Duration {
	private Collection<Duration> durations = new ArrayList<Duration>();

	public CompositeDuration() {
		
	}
	
	public CompositeDuration(Duration... newDurations) {
		durations.addAll(Arrays.asList(newDurations));
	}
	
	public void add(Duration newDuration) {
		durations.add(newDuration);
	}
	
	public Duration plus(Duration d) {
		return new CompositeDuration(this, d);
	}
	
	@Override
	public Date after(Date begin) {
		Date result = begin;
		for (Duration curr: durations) {
			result = curr.after(result);
		}
		return result;
	}

	@Override
	public Date before(Date begin) {
		Date result = begin;
		for (Duration curr: durations) {
			result = curr.before(result);
		}
		return result;
	}
	
}
