package edu.cooper.ece366.week1;

import java.util.ArrayList;

public class HelloWorld {
  private static int MY_FAVORITE_NUMBER = 5;

  public static void main(String[] args) {
    int my_favorite_number = HelloWorld.MY_FAVORITE_NUMBER;
    HelloWorld helloWorld = new HelloWorld();
    MyString myString = new MyString("hey there");
    System.out.println(myString);

    int[] myFavIntegers = new int[] {1,2,3,4,5};
    ArrayList<Integer> myOtherFavIntegers = new ArrayList<>();
    myOtherFavIntegers.add(1);
    myOtherFavIntegers.add(1);
    myOtherFavIntegers.add(2);
    myOtherFavIntegers.add(3);
    myOtherFavIntegers.add(4);
  }

  public void modifyFavoriteNumber(int num) {
    MY_FAVORITE_NUMBER = num;
  }

  public int getMyFavoriteNumber() {
    return MY_FAVORITE_NUMBER;
  }

}
