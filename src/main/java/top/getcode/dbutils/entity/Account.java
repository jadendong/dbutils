package top.getcode.dbutils.entity;

import java.sql.Date;

public class Account {
    private int id;
    private String name;
    private double balance;
    private Date lastDate;


    public Account() {
        super();
    }

    public Account(int id, String name, double balance, Date lastDate) {
        super();
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.lastDate = lastDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ",name=" + name + ", balance=" + balance + ", lastDate="
                + lastDate + " ]";
    }
}
