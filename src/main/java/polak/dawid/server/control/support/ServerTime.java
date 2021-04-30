package polak.dawid.server.control.support;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ServerTime {

    private final Instant start;
    private final LocalDate startDateOfServer;

    public ServerTime() {
        this.start = Instant.now();
        startDateOfServer = LocalDate.now();
    }

    private long getDurationInSeconds() {
        return Duration.between(start, Instant.now()).getSeconds();
    }

    public Uptime getUptime() {
        long duration = getDurationInSeconds();
        int days = (int) SECONDS.toDays(duration);
        int hours = (int) (SECONDS.toHours(duration) - (days * 24));
        int minutes = (int) (SECONDS.toMinutes(duration) - (SECONDS.toHours(duration) * 60));
        int seconds = (int) (SECONDS.toSeconds(duration) - (SECONDS.toMinutes(duration) * 60));

        return new Uptime(days, hours, minutes, seconds);
    }

    public LocalDate getStartDateOfServer() {
        return startDateOfServer;
    }
}
