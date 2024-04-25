/**
 * Match class to allow encapsulation of customer variables
 */
public class Match {
    private String matchID;
    private String matchName;
    private String venue;
    private int numOfTickets;
    private double ticketPrice;
    private String filePath;

    /**
     * No-arg constructor
     */
    public Match() {

    }

    /**
     * Construct a new Match object with specified match ID, name, venue, number of
     * tickets, price of tickets, and the file path where its details are saved.
     * 
     * @param matchID
     *                     Assigned ID for the match
     * 
     * @param matchName
     *                     Name of the match
     * 
     * @param venue
     *                     Location where the match will take place
     * 
     * @param numOfTickets
     *                     Number of seatings available to be sold
     * 
     * @param ticketPrice
     *                     Price for each ticket to the match
     * 
     * @param filePath
     *                     Name of the file for saving the match details
     */
    public Match(String matchID, String matchName, String venue, int numOfTickets, double ticketPrice,
            String filePath) {
        this.matchID = matchID;
        this.matchName = matchName;
        this.venue = venue;
        this.numOfTickets = numOfTickets;
        this.ticketPrice = ticketPrice;
        this.filePath = filePath;
    }

    /**
     * Returns the ID of the match
     * 
     * @return
     *         String of the ID of the match
     */
    public String getMatchID() {
        return matchID;
    }

    /**
     * Returns the name of the match
     * 
     * @return
     *         Name of the match
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * Set the name of the match to the input name
     * 
     * @param matchName
     *                  The name to overwrite the previous match name
     */
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    /**
     * Return the venue of the match
     * 
     * @return
     *         Laocation of the match
     */
    public String getVenue() {
        return venue;
    }

    /**
     * Change the venue of the match to a new venue
     * 
     * @param venue
     *              The venue to overwrite the previous venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * Return the number of the tickets
     * 
     * @return
     *         The number of tickets (seating) available for the match
     */
    public int getNumOfTickets() {
        return numOfTickets;
    }

    /**
     * Set new capacity (number of tickets) available for the match
     * 
     * @param numOfTickets
     *                     New capacity to overwrite the previous one
     */
    public void setNumOfTickets(int numOfTickets) {
        this.numOfTickets = numOfTickets;
    }

    /**
     * Return the price of ticket
     * 
     * @return
     *         Price for each ticket
     */
    public double getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Set new price for the ticket of the match
     * 
     * @param ticketPrice
     *                    New ticket price to overwrite the old price
     */
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Return the file path of the match
     * 
     * @return
     *         File path of a given match
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Set new file path for the match
     * 
     * @param filePath
     *                 New file path to overwrite the previous one
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Print out the match ID, name, venue, capacity and price of the ticket of a
     * given match
     */
    public void viewAllDetails() {
        System.out.println("Match ID    : " + matchID);
        System.out.println("Match       : " + matchName);
        System.out.println("Venue       : " + venue);
        System.out.println("Capacity    : " + numOfTickets);
        System.out.println("Ticket Price: " + ticketPrice);

    }

    /**
     * Generate csv string of the match to be written into csv file
     * 
     * @return
     *         Details of the match in csv format - single line with each element
     *         separated with comma
     */
    public String toCSVString() {
        return matchID + "," + matchName + "," + venue + "," + numOfTickets + "," + ticketPrice + "," + filePath;
    }

}
