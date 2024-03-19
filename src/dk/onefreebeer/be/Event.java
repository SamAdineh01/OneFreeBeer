package dk.onefreebeer.be;

public class Event {
    int id;
    String title;
    String note;
    String location;
    String startDate;
    String endDate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public Event(int id, String title, String note, String location, String startDate, String endDate, String date) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.date = date;
    }

    public Event(String title, String note, String location, String startDate, String endDate, String date) {
        this.title = title;
        this.note = note;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.date = date;
    }

    public Event(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", location='" + location + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
