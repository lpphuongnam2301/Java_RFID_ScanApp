/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeminarDemo;

/**
 *
 * @author lpphu
 */
public class DetailObject {
    private String book_id, borrow_id, book_title, book_author, tag_rfid;
    private int borrowDetail_status, book_status;
    private String scanStatus = "";
    
    public DetailObject() {}

    public DetailObject(String book_id, String borrow_id, String book_title, String book_author, int borrowDetail_status, int book_status, String tag_rfid) {
        this.book_id = book_id;
        this.borrow_id = borrow_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.borrowDetail_status = borrowDetail_status;
        this.book_status = book_status;
        this.tag_rfid = tag_rfid;
    }
    public DetailObject(String book_id, String borrow_id, String book_title, String book_author, int borrowDetail_status) {
        this.book_id = book_id;
        this.borrow_id = borrow_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.borrowDetail_status = borrowDetail_status;
    }
    public DetailObject(String book_id, String book_title, String book_author) {
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
    }
    
    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    public String getTag_rfid() {
        return tag_rfid;
    }

    public void setTag_rfid(String tag_rfid) {
        this.tag_rfid = tag_rfid;
    }
    
    public String getBook_id() {
        return book_id;
    }

    public String getBorrow_id() {
        return borrow_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public int getBorrowDetail_status() {
        return borrowDetail_status;
    }

    public int getBook_status() {
        return book_status;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setBorrow_id(String borrow_id) {
        this.borrow_id = borrow_id;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public void setBorrowDetail_status(int borrowDetail_status) {
        this.borrowDetail_status = borrowDetail_status;
    }

    public void setBook_status(int book_status) {
        this.book_status = book_status;
    }
    
    
}
