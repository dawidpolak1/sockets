package polak.dawid.server.control.support;

public class Uptime {

    private final int days;
    private final int hours;
    private final int minutes;
    private final int seconds;

    public Uptime(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
}
