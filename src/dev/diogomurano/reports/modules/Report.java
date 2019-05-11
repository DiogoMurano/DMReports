package dev.diogomurano.reports.modules;

public class Report {

    private String sender;
    private String target;
    private String description;
    private long time;

    public Report(String sender, String target, String description, long time) {
        this.sender = sender;
        this.target = target;
        this.description = description;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getTarget() {
        return target;
    }

    public String getDescription() {
        return description;
    }

    public long getTime() {
        return time;
    }
}
