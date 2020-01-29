package edu.cooper.ece366.week1;

class MyString {

  private String s;

  MyString(String s) {
    this.s = s;
  }

  public String toUpperCase() {
    return toUpperCase(s.toUpperCase());
  }

  public String toUpperCase(String other) {
    return other.toUpperCase();
  }

  @Override
  public String toString() {
    return "MyString: " + s;
  }
}
