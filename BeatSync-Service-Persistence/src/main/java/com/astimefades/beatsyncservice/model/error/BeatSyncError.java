package com.astimefades.beatsyncservice.model.error;

public class BeatSyncError {

    public static final BeatSyncError UNHANDLED = new BeatSyncError(1, "An unexpected error occurred");

    public static final BeatSyncError EMAIL_TAKEN = new BeatSyncError(2, "Email is taken");

    public final int errorNumber;
    public final String errorDescription;

    private BeatSyncError(int errorNumber, String errorDescription) {
        this.errorNumber = errorNumber;
        this.errorDescription = errorDescription;
    }

    public static BeatSyncException getException(BeatSyncError error) {
        return new BeatSyncException(error);
    }
}