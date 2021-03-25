package DB;

import java.time.LocalDate;

public class Customer {

    private int d;
    private long id;
    private LocalDate ddate;
    private Double pending,deposited;
    private String name,mob,email,addr,gstno;
    private String state;
    private String custtype;
    private String custfile;
    private String panno;
    private String msg;



    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDdate() {
        return ddate;
    }

    public void setDdate(LocalDate ddate) {
        this.ddate = ddate;
    }

    public Double getPending() {
        return pending;
    }

    public void setPending(Double pending) {
        this.pending = pending;
    }

    public Double getDeposited() {
        return deposited;
    }

    public void setDeposited(Double deposited) {
        this.deposited = deposited;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return this.mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getGstno() {
        return this.gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }

    public String getCustfile() {
        return custfile;
    }

    public void setCustfile(String custfile) {
        this.custfile = custfile;
    }


}
