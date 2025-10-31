package org.arkn37.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Offer extends BaseEntity {
    private String name;
    private BigDecimal amount;
    private UUID itemUuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getItemUuid() {
        return itemUuid;
    }

    public void setItemUuid(UUID itemUuid) {
        this.itemUuid = itemUuid;
    }
}
