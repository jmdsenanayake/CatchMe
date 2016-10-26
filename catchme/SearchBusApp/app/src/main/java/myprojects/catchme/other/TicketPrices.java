package myprojects.catchme.other;

/**
 * Created by admin on 10/8/2016.
 */
public class TicketPrices
{
    public String origin;
    public String destination;
    public Double nTicket;
    public Double sTicket;
    public Double lTicket;

    public TicketPrices(String origin, String destination, Double nTicket, Double sTicket, Double lTicket)
    {
        this.origin = origin;
        this.destination = destination;
        this.nTicket = nTicket;
        this.sTicket = sTicket;
        this.lTicket = lTicket;
    }
}
