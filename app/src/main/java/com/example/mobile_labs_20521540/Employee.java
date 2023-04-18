package com.example.mobile_labs_20521540;

public class Employee {
    public String name = "";
    public double gross;
    public double net;
    Employee (String name , double gross ) {
        this.name = name;
        this.gross = gross;
    }
    void setName (String Name) {
        this.name = Name;
    }
    void setGross (double Gross) {
        this.gross = gross;
    }
    void setNet (double net) {
        this.net = net;
    }
    double TinhNet () {
        double net;
        double temp = this.gross - this.gross*0.105;
        if (temp <= 11000000) net = this.gross;
        else {
            double tax = (temp - 11000000) * 0.05;
            net = temp - tax;
        }
        return net;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.name+" - Net salary: "+Math.round(this.net);
    }
}
