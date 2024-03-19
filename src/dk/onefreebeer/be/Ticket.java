package dk.onefreebeer.be;

import java.util.UUID;

public class Ticket {
    int id;
    Event event;
    String type;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    UUID uuid;


    public Ticket(){

    }
    public Ticket(int id, Event event, String type, UUID uuid) {
        this.id = id;
        this.event = event;
        this.type = type;
        this.uuid = uuid;
    }

    public Ticket(Event event, String type, UUID uuid) {
        this.event = event;
        this.type = type;
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event=" + event +
                ", type='" + type + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
