package Schedule;

import java.io.Serializable;
import java.util.ArrayList;

public class Schedule implements Serializable {

    private ArrayList<Contact> schedule = new ArrayList<Contact>();

    public Schedule(ArrayList<Contact> schedule) {
        this.schedule = schedule;
    }

    public void addContact(Contact contacto) {
        this.schedule.add(contacto);
    }

    public ArrayList<Contact> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Contact> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Schedule{" + "schedule=" + schedule + '}';
    }
}
