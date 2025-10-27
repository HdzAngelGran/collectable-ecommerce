package org.arkn37.model;

import java.util.UUID;

public class BaseEntity {
    private UUID uuid;
    private boolean isEnabled;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
