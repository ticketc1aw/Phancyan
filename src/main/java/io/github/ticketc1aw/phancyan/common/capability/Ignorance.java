package io.github.ticketc1aw.phancyan.common.capability;

public class Ignorance implements IIgnorance{
    private int value = 0;

    @Override
    public int getIgnorance() {
        return value;
    }

    @Override
    public void setIgnorance(int value) {
        this.value = value;
    }
}
