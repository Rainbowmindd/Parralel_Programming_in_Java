public class WatekRunnable implements Runnable {
    private static final Object STATIC_MONITOR = new Object();
    private Obraz obraz;
//       private int end;
//        private int start;
    private  int index;
    private int count;

//    private int count;

    public WatekRunnable(Obraz obraz, int index, int count ) {
        this.obraz = obraz;
//        this.start = start;
//        this.end = end;
        this.index=index;
        this.count=count;
    }


    @Override
    public void run() {

//        for (int i = start; i < end; i++) {
//            obraz.calc_watek1(obraz.getSymbol(i), index);
//        }
        try {
            Thread.sleep(30);
            obraz.calc_watek_runnable(index,count);
//            obraz.print_histogram();
//synchronized wywolujemy po calculate, wypisywanie do drugiej funkcji
//
//            }
        } catch (InterruptedException e) {
            System.out.println("WatekRunnable " + index + " został przerwany.");
        }
    }
}

