package dev.diogomurano.reports.player.impl;

import dev.diogomurano.reports.modules.Report;
import dev.diogomurano.reports.player.ReportPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportPlayerImpl implements ReportPlayer {

    private String name;
    private Set<Report> reports;

    public ReportPlayerImpl(String name) {
        this.name = name;
        this.reports = new HashSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<Report> getReports() {
        return reports;
    }

    @Override
    public Set<Report> searchReport(Predicate<Report> predicate) {
        return reports.stream().filter(predicate).collect(Collectors.toSet());
    }

    @Override
    public Report getReport(Predicate<Report> predicate) {
        return reports.stream().filter(predicate).findFirst().orElse(null);
    }

    @Override
    public void addReport(Report report) {
        this.reports.add(report);
    }
}
