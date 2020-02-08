package comemployee.cleanchain_sa.cleanchaindriver;

/**
 * Created by moh on 24 أكت، 2017 م.
 */

public class AdapterItems {

    public   String OrderID;
    public  String Price;
    public  String status;
    public   String created_at;
    public   String Notes;


    //for news details
    AdapterItems(String orderID, String notes, String price, String status, String created_at)
    {
        this. OrderID=orderID;
        this. Notes=notes;
        this. Price=price;
        this. status=status;
        this. created_at=created_at;

    }



}
