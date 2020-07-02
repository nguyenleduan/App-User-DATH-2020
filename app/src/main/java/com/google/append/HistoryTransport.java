package com.google.append;

public class HistoryTransport {
    public String BusHistory;
    public String CostHistory;
    public String DateHistory;
    public String IDBusHistory;
    public String IDUserHistory;
    public String LocationBeginHistory;
    public String LocationEndHistory;
    public String SpeciesBusHistory;
    public String StatusHistory;
    public String TonnageHistory;

    public HistoryTransport() {
    }

    public HistoryTransport(String busHistory, String costHistory, String dateHistory, String IDBusHistory, String IDUserHistory, String locationBeginHistory, String locationEndHistory, String speciesBusHistory, String statusHistory, String tonnageHistory) {
        this.BusHistory = busHistory;
        this.CostHistory = costHistory;
        this.DateHistory = dateHistory;
        this.IDBusHistory = IDBusHistory;
        this.IDUserHistory = IDUserHistory;
        this.LocationBeginHistory = locationBeginHistory;
        this.LocationEndHistory = locationEndHistory;
        this.SpeciesBusHistory = speciesBusHistory;
        this.StatusHistory = statusHistory;
        this.TonnageHistory = tonnageHistory;
    }
}
