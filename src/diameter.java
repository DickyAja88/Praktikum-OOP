class diameter extends Lingkaran {
    public double getDiameter(){
        Lingkaran lingkaran = new Lingkaran();
        double d = lingkaran.getRadius() * 2;
        System.out.println("Determinan : "+d);
        return  d;
    }
    public  double hasil(){
        return 0.25 * Math.PI * Math.pow(getDiameter(), 2);
    }
    public static void main(String[] args) {
        diameter hitung = new diameter();
        System.out.println("Rumus : 1/4 * PI * d * d");
        System.out.println("Luas lingkaran adalah " + hitung.hasil());
    }
}


