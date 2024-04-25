/**
 * TicketSold representing the ticket sold where numOfTickets is integers and ticketPrice are double
 */
public class TicketSold {
    private int numOfTickets;
    private double ticketPrice;

    /** 
     * Construct a TicketSold where numOfTicket and ticketPrice are set to zero(0)
    */
    public TicketSold() {
    }

    /** 
     * Construct TicketSold with specified numOfTickets and ticketPrice
     * 
     * @param numOfTickets the number of ticket buy by customer
     * @param ticketPrice the price of the ticket buy by customer
    */
    public TicketSold(int numOfTickets, double ticketPrice) {
        this.numOfTickets = numOfTickets;
        this.ticketPrice = ticketPrice;
    }

    /** 
     * Return the numOfTickets 
     *
     * @return numOfTickets 
    */
    public int getNumOfTickets() {
        return numOfTickets;
    }

    /** 
     * Return the ticketPrice 
     * 
     * @return ticketPrice
    */
    public double getTicketPrice() {
        return ticketPrice;
    }

    /** 
     * Returns a string representation of the TicketSold 
     * 
     * @return numOfTickets ticketPrice
    */
    public String toString() {
        return numOfTickets + " \t " + ticketPrice;
    }

    /** 
     * Returns a CSV representation of the TicketSold 
     *
     * @return numOfTickets,ticketPrice 
    */
    public String toCSVString() {
        return numOfTickets + "," + ticketPrice;
    }
}
