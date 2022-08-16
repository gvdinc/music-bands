package serverUDP;

public enum ServerState {
    RECEIVING,
    SENDING,
    OPERATING,
    UNCONNECTED,
    OFFLINE;

    @Override
    public String toString() {
        return super.toString();
    }
}

