package dk.onefreebeer.be;

public class Event {
    int id;
    String title;
    String note;
    String location;
    String date;
    String time;


    public Event(int id, String title, String note, String location, String date, String endDate, String time) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public Event(String title, String note, String location, String startDate, String endDate, String date) {
        this.title = title;
        this.note = note;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public Event() {

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


    public String getDate() {
        return date;
    }

    public void setDate(String endDate) {
        this.date = endDate;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;

    }
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", location='" + location + '\'' +
                ", endDate='" + date + '\'' +
                ", date='" + time + '\'' +
                '}';
    }


}
