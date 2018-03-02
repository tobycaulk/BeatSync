package com.astimefades.beatsyncservice.model.error;

public class BeatSyncError {

    public static final BeatSyncError UNHANDLED = new BeatSyncError(1, "An unexpected error occurred");

    public static final BeatSyncError EMAIL_TAKEN = new BeatSyncError(2, "Email is taken");
    public static final BeatSyncError EMAIL_NOT_FOUND = new BeatSyncError(3, "Email not found");
    public static final BeatSyncError INVALID_PASSWORD_FOR_ACCOUNT = new BeatSyncError(4, "Invalid password");

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