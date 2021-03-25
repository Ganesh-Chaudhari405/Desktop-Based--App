package DB;

import java.time.LocalDate;

public class Feedback {

    private long id, d;
    private String feedmsg;
    private String rating, name;
    private LocalDate date;
    private Customer customer;


    public String getFeedmsg()
    {
        return feedmsg;
    }

    public void setFeedmsg(String feedmsg)
    {
        this.feedmsg = feedmsg;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getD()
    {
        return d;
    }

    public void setD(long d)
    {
        this.d = d;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }
}
