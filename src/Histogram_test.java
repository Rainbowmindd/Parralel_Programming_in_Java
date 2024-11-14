import java.util.Scanner;


class Histogram_test {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Set image size: n (#rows), m(#kolumns)");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int size=94;

        System.out.println("Set number of threads");
        int num_threads = scanner.nextInt();
        size=num_threads;


        Thread[] NewThr = new Thread[num_threads];

        Watek[] watek_wariant1=new Watek[num_threads];

        Obraz obraz_1 = new Obraz(n, m,size);


        //obliczenie sekwencyjnie
        obraz_1.calculate_histogram();
        obraz_1.print_histogram();

        //obliczenia rownolegle

         for (int i = 0; i < num_threads; i++) {
             (watek_wariant1[i] = new Watek(obraz_1,obraz_1.getSymbol(i),i)).start();

             try {
                 Thread.sleep(10);
             }catch (InterruptedException e) {
                 System.out.println(e);
             }
         }

         for (int i = 0; i <num_threads; i++) {
             try {
         	watek_wariant1[i].join();
             } catch (InterruptedException e) {
                 System.out.println(e);
             }
         }

        // Porównanie wyników
        System.out.println("Porownywanie sekwencyjnego i rownoleglego histogramu: ");
        System.out.print("\n");
        obraz_1.compare();
        obraz_1.clear_histogramParallel();

         //Zad5 -----------------------------
        //Runnable
        System.out.println("Wariant 2");
        int dx = 10 / num_threads;

        for (int i = 0; i < num_threads; i++)
            (NewThr[i] = new Thread(new WatekRunnable(obraz_1, i*dx+33, (i+1) * dx+33,i))).start();

        for (int i = 0; i < num_threads; i++) {
            try {
                NewThr[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        obraz_1.compare();
        obraz_1.clear_histogramParallel();
    }

}
