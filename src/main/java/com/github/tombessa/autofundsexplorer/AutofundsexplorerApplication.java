package com.github.tombessa.autofundsexplorer;

import com.github.tombessa.autofundsexplorer.util.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author antonyonne.bessa
 */

@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class AutofundsexplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutofundsexplorerApplication.class, args);
	}

	@PostConstruct
	public void init() {
		Locale.setDefault(new Locale(Constants.PT, Constants.BR));
		ZoneId fusoHorarioDeSaoPaulo = ZoneId.of(Constants.SP_TIMEZONE);
		TimeZone.setDefault(TimeZone.getTimeZone(fusoHorarioDeSaoPaulo));
	}
}
