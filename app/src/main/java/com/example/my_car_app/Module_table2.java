package com.example.my_car_app;

public class Module_table2 {

    private int id;
    private String nick_name,number,next_event,dates_of_next;

    public Module_table2() {
    }

    public Module_table2(String nick_name, String number, String next_event, String dates_of_next) {
        this.nick_name = nick_name;
        this.number = number;
        this.next_event = next_event;
        this.dates_of_next = dates_of_next;
    }

    public Module_table2(int id, String nick_name, String number, String next_event, String dates_of_next) {
        this.id = id;
        this.nick_name = nick_name;
        this.number = number;
        this.next_event = next_event;
        this.dates_of_next = dates_of_next;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNext_event() {
        return next_event;
    }

    public void setNext_event(String next_event) {
        this.next_event = next_event;
    }

    public String getDates_of_next() {
        return dates_of_next;
    }

    public void setDates_of_next(String dates_of_next) {
        this.dates_of_next = dates_of_next;
    }
}
