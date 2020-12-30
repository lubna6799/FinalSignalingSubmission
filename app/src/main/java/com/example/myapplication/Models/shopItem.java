package com.example.myapplication.Models;

public class shopItem implements Comparable<shopItem> {
  private String uid;
  private String name ;
  private  double price;
  private  double distance ;

  public static boolean sortDistance = false;

  public double getDistance() {
    return distance;
  }

  public double getPrice() {
    return price;
  }

  public boolean getSort(){
    return sortDistance;
  }

  public void setSort(boolean bool){
    sortDistance = bool;
  }



  public String getName() {
    return name;
  }

  public String getUid() {
    return uid;
  }

  public shopItem(String uid, String name , double price, double distance) {
    this.uid= uid ;
    this.name= name ;
    this.price =price;
    this.distance = distance ;
  }

  @Override
  public int compareTo(shopItem o) {
    return sortDistance? (int)(this.distance - o.distance) : (int)(this.price - o.price);
  }
}
