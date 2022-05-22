/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SeminarDemo;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author lpphu
 */
public class DatabaseQuery {
    MyDBConnection conn = new MyDBConnection();
    BorrowObject borrow;
    DetailObject detail;
    
    public DatabaseQuery(){}
    
    public ArrayList<BorrowObject> readBorrow()
    {
        ArrayList list = new ArrayList<BorrowObject>();
        try
        {
            String query = "SELECT * FROM borrow, users where borrow.user_id = users.user_id"
                    + " and (borrow_status = 3 or borrow_status = 4)";
            ResultSet rs = conn.executeQuery(query);
            while (rs.next())
            {
                borrow = new BorrowObject(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                list.add(borrow);
            }
        } catch(Exception e)
        {
            System.out.println("Lỗi đọc da");
        }
        return list;
    }
    
    public ArrayList<DetailObject> readDetail(String id)
    {
        ArrayList list = new ArrayList<DetailObject>();
        try
        {
            String query = "SELECT * FROM borrow_detail, books, tag_read where books.book_id = borrow_detail.book_id and tag_read.book_id = books.book_id"
                    + " and borrowdetail_status = 0 and borrow_detail.borrow_id = '" + id + "'";
            ResultSet rs = conn.executeQuery(query);
            while (rs.next())
            {
                detail = new DetailObject(rs.getString("book_id"), rs.getString("borrow_id"), rs.getString("book_title"),
                        rs.getString("book_author"), rs.getInt("borrowdetail_status"), rs.getInt("book_status"), rs.getString("tag_rfid"));
                list.add(detail);
            }
        } catch(Exception e)
        {
            System.out.println("Lỗi đọc da");
        }
        return list;
    }
    
    public void save(ArrayList<DetailObject> arr, String returnDate, String borrowID, int borrowStatus)
    {
        try
        {
            for (DetailObject object : arr) 
            {
                if(object.getScanStatus().equals("Scanned"))
                {
                    String query = "UPDATE borrow_detail SET "
                            + "`borrowdetail_status`= '1'"
                            + " WHERE book_id = '" + object.getBook_id()
                            + "' AND borrow_id = '" + borrowID + "'";
                    conn.executeUpdate(query);
                    
                    String query2 = "UPDATE books SET "
                            + "`book_status`= '0'"
                            + " WHERE book_id = '" + object.getBook_id() + "'"
                            ;
                    conn.executeUpdate(query2);
                }
            }
            
            String query = "UPDATE borrow SET "
                            + "`borrow_returndate`= '" + returnDate +"',"
                            + "`borrow_status`= '" + borrowStatus +"'"
                            + " WHERE borrow_id = '" + borrowID + "'";
            conn.executeUpdate(query);
            
        } catch(Exception e)
        {
            System.out.println("Lỗi save DAL");
        }
    }
    
    public ArrayList<DetailObject> readBook()
    {
        ArrayList list = new ArrayList<DetailObject>();
        DetailObject obj;
        try
        {
            String query = "select * from books WHERE book_id not in (SELECT book_id FROM tag_read)";
            ResultSet rs = conn.executeQuery(query);
            while (rs.next())
            {
                obj = new DetailObject(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(obj);
            }
        } catch(Exception e)
        {
            System.out.println("Lỗi đọc da");
        }
        return list;
    }
    public ArrayList<String> readTagRFID()
    {
        ArrayList list = new ArrayList<String>();
        try
        {
            String query = "select * from tag_read";
            ResultSet rs = conn.executeQuery(query);
            while (rs.next())
            {
                list.add(rs.getString(1));
            }
        } catch(Exception e)
        {
            System.out.println("Lỗi đọc da");
        }
        return list;
    }
    
    public void mapping(String rfid, String bookId)
    {
        try
        {
            String query = "INSERT INTO `tag_read` VALUES ('"
                    + rfid + "','" + bookId +"')";
            conn.executeUpdate(query);
            
        } catch(Exception e)
        {
            System.out.println("Lỗi them DAL");
        }
    }
    public ArrayList<DetailObject> readDetailPDF(String id)
    {
        ArrayList list = new ArrayList<DetailObject>();
        try
        {
            String query = "SELECT * FROM borrow_detail, books where books.book_id = borrow_detail.book_id"
                    + " and borrow_detail.borrow_id = '" + id + "'";
            ResultSet rs = conn.executeQuery(query);
            while (rs.next())
            {
                detail = new DetailObject(rs.getString("book_id"), rs.getString("borrow_id"), rs.getString("book_title"),
                        rs.getString("book_author"), rs.getInt("borrowdetail_status"));
                list.add(detail);
            }
        } catch(Exception e)
        {
            System.out.println("Lỗi đọc da");
        }
        return list;
    }
    public BorrowObject readBorrowPDF(String id)
    {
        try
        {
            String query = "SELECT * FROM borrow, users where borrow.user_id = users.user_id and "
                    + "borrow.borrow_id = '" + id + "'";
            ResultSet rs = conn.executeQuery(query);
            while (rs.next())
            {
                borrow = new BorrowObject(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
                
            }
        } catch(Exception e)
        {
            System.out.println("Lỗi đọc da");
        }
        return borrow;
    }
}
