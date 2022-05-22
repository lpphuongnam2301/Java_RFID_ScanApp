package SeminarDemo;


public class BorrowObject {
    private String borrow_id, borrow_beginDate, borrow_endDate, borrow_returnDate;
    private String user_id, user_name, user_phone;
    private int borrow_status;
    
    public BorrowObject()
    {}

    public BorrowObject(String borrow_id, String borrow_beginDate, String borrow_endDate, int borrow_status, String borrow_returnDate, String user_id, String user_name, String user_phone) {
        this.borrow_id = borrow_id;
        this.borrow_beginDate = borrow_beginDate;
        this.borrow_endDate = borrow_endDate;
        this.borrow_returnDate = borrow_returnDate;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.borrow_status = borrow_status;
    }

    public String getBorrow_id() {
        return borrow_id;
    }

    public String getBorrow_beginDate() {
        return borrow_beginDate;
    }

    public String getBorrow_endDate() {
        return borrow_endDate;
    }

    public int getBorrow_status() {
        return borrow_status;
    }

    public String getBorrow_returnDate() {
        return borrow_returnDate;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setBorrow_id(String borrow_id) {
        this.borrow_id = borrow_id;
    }

    public void setBorrow_beginDate(String borrow_beginDate) {
        this.borrow_beginDate = borrow_beginDate;
    }

    public void setBorrow_endDate(String borrow_endDate) {
        this.borrow_endDate = borrow_endDate;
    }

    public void setBorrow_status(int borrow_status) {
        this.borrow_status = borrow_status;
    }

    public void setBorrow_returnDate(String borrow_returnDate) {
        this.borrow_returnDate = borrow_returnDate;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
    
    
    

}
