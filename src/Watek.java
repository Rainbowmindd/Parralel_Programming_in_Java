
public class Watek extends Thread {
   // private static final Object STATIC_MONITOR = new Object();
    private Obraz obraz;
    private  char character;
    private  int index;

    public Watek(Obraz obraz, char character, int index) {
        this.obraz = obraz;
        this.character = character;
        this.index = index;
    }


    public void run() {
            obraz.calc_watek1(character,index);
    }
}
