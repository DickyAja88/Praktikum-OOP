import java.util.Scanner;
import java.lang.Math;

public class Lingkaran {
  protected double radius;
  private final double PI = 3.141550;

  public double get_pi() {
    return PI;
  }

  public double getRadius() {
    Scanner input = new Scanner(System.in);
    System.out.print("Masukkan jari-jari (r): ");
    double r = input.nextDouble();
    return r;
  }

  public double hasil() {
    return PI * Math.pow( getRadius(), 2);
  }

  public static void main(String[] args) {
    Lingkaran lingkaran = new Lingkaran();
    System.out.println("Rumus : PI * r * r");
    System.out.println("Luas lingkaran adalah "+ lingkaran.hasil());
  }
}
