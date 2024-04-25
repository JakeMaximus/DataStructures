/**
* Customer class to allow encapsulation of customer variables
*/
public class Customer {
    private int id;
    private String name;
    /**
    * Empty no argument constructor to avoid complilation errors
    */
    public Customer () {}
    /**
    * Customer constructor with id and name parameters
    * @param id 
    *           id number of the customer
    * @param name
    *           name of the customer 
    */
    public Customer (int id, String name) {
        this.id = id;
        this.name = name;
    }
    /**
    * getter to return id
    * @return
    *        returns id
    */
    public int getId() {
        return id;
    }
    /**
    * getter to return name
    * @return 
    *       returns name
    */
    public String getName(){
        return name;
    }
    /**
    * getter to return toString method (to make the elements into String data field)
    * @return 
    *       returns id and name with tab 
    */
    public String toString() {
        return id + " \t " + name;
    }
    /**
    * getter to return toCVCString method 
    * @return
    *       returns id and name with ","
    */
    public String toCSVString() {
        return id + "," + name;
    }
}