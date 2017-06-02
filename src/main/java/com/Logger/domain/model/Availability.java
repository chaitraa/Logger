package com.Logger.domain.model;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class Availability {
    public int free;
    public String bookDate;
    public LocalDate bookingDate;
    public void setBookDate(String bookDate)
    {
        this.bookingDate = LocalDate.parse(bookDate, DateTimeFormatter.ISO_DATE);
        this.bookDate=bookDate;
    }
    public String getBookDate() {
        return bookDate;
    }
}

