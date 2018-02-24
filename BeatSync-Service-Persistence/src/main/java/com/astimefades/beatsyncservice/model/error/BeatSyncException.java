package com.astimefades.beatsyncservice.model.error;

public class BeatSyncException extends Exception {

    private int errorNumber;
    private String errorDescription;

    public BeatSyncException(int errorNumber, String errorDescription) {
        super(errorDescription);

        this.errorNumber = errorNumber;
        this.errorDescription = errorDescription;
    }

    public BeatSyncException(Throwable t) {
        super(t.getMessage());

        this.errorNumber = BeatSyncError.UNHANDLED.errorNumber;
        this.errorDescription = t.getMessage();
    }

    public BeatSyncException(BeatSyncError beatSyncError) {
        super(beatSyncError.errorDescription);

        this.errorNumber = beatSyncError.errorNumber;
        this.errorDescription = beatSyncError.errorDescription;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
