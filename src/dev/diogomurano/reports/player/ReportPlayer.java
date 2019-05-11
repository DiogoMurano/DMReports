package dev.diogomurano.reports.player;

import dev.diogomurano.reports.modules.Report;

import java.util.Set;
import java.util.function.Predicate;

public interface ReportPlayer {

    String getName();

    Set<Report> getReports();

    Set<Report> searchReport(Predicate<Report> predicate);

    Report getReport(Predicate<Report> predicate);

    void addReport(Report report);
}
