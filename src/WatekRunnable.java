public class WatekRunnable implements Runnable {
    private static final Object STATIC_MONITOR = new Object();
    private Obraz obraz;
    private int end;
    private int start;
    private  int index;

    public WatekRunnable(Obraz obraz, int start, int end, int index) {
        this.obraz = obraz;
        this.start = start;
        this.end = end;
        this.index=index;
    }


    @Override
    public void run() {
        try {

            Thread.sleep(30);

            synchronized (STATIC_MONITOR) {
                for (int charCode = start; charCode <= end; charCode++) {
                    char character = (char) charCode;
                    int res = obraz.calculate(character);  //zliczanie ile razy pojawil sie znak

                    if (res != 0) {
                        System.out.print("WatekRunnable: " + index + ": " + character + " [" + res + "] ");
                        for (int i = 0; i < res; i++)
                            System.out.print("=");

                        System.out.print("\n");

                        // update histogramu rownoleglebgo
                        int cal = character - 33;
                        obraz.getHistogramParallel()[cal] = res;
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WatekRunnable " + index + " został przerwany.");
        }
    }
}