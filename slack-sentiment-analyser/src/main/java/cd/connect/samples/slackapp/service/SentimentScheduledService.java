package cd.connect.samples.slackapp.service;


import cd.connect.samples.slackapp.dao.SentimentDao;
import net.stickycode.stereotype.configured.PostConfigured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SentimentScheduledService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static ScheduledExecutorService service;

	@Inject
	SentimentDao sentimentDao;

	@PostConfigured
	public void init() {

		service = Executors.newSingleThreadScheduledExecutor();

		service.scheduleAtFixedRate(
				() -> sentimentDao.clearCurrentHourMessages()
		, getInitialDelay(), 60 * 60 * 1000, TimeUnit.MILLISECONDS);

	}

	@PreDestroy
	public void preDestroy() {
		if (service != null) {
			service.shutdown();
		}
	}

	private long getInitialDelay() {
		long delay = getNextRunTime() - Calendar.getInstance().getTimeInMillis();
		log.info("Initial delay == {}", delay);
		return delay > 0 ? delay : 0;
	}

	private long getNextRunTime() {

		Calendar cal = Calendar.getInstance();

		if (cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0) {
			return cal.getTimeInMillis();
		}

		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();

	}

}
