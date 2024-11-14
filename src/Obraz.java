import  java.util.Random;


class Obraz {

    private int size_n;
    private int size_m;
    private char[][] tab; //tablica znakow
    private char[] tab_symb;
    private int histSize;
    private int[] histogram; //histogram sekwencyjny

    private int[] hist_parallel; //histogram rownolegly

    public Obraz(int n, int m,int histSize) {
        this.histSize=histSize;
        this.size_n = n;
        this.size_m = m;
        tab = new char[n][m];
        tab_symb = new char[94];

        final Random random = new Random();

        // for general case where symbols could be not just integers
        for(int k=0;k<94;k++) {
            tab_symb[k] = (char)(k+33); // substitute symbols
        }

        //generowanie tablicy znakow
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                tab[i][j] = (char)(random.nextInt(histSize)+33);  // ascii 33-127
                //tab[i][j] = (char)(random.nextInt(94)+33);  // ascii 33-127
                System.out.print(tab[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");

        histogram = new int[94];
        //3a
        hist_parallel=new int[94];
        clear_histogram();
    }

    public void clear_histogram(){

        for(int i=0;i<histSize;i++){
            histogram[i]=0;
        }
    }
    public void clear_histogramParallel(){

        for(int i=0;i<94;i++) hist_parallel[i]=0;

    }


    // obliczanie histogramu w sposob sekwencyjny
    public void calculate_histogram(){

        for(int i=0;i<size_n;i++) {
            for(int j=0;j<size_m;j++) {

                // optymalna wersja obliczania histogramu, wykorzystujaca fakt, ze symbole w tablicy
                // mozna przeksztacac w indeksy tablicy histogramu
                // histogram[(int)tab[i][j]-33]++;

                // wersja bardziej ogolna dla tablicy symboli nie utozsamianych z indeksami
                // tylko dla tej wersji sensowne jest zrownoleglenie w dziedzinie zbioru znakow ASCII
//                for(int k=0;k<10;k++) {
//                    if(tab[i][j] == tab_symb[k]) histogram[k]++;
                histogram[(int) tab[i][j] - 33]++;
                    //if(tab[i][j] == (char)(k+33)) histogram[k]++;
                }

            }
        }


    //synchronized-> tylko jeden watek moze uzyskac dostep do tej metody w danym momencie
    public void calculateHistogramPart(int start, int end) {
        char[] charArray = new char[end - start + 1];

        for (int i = start; i <= end; i++) {
            charArray[i - start] = (char)i;
        }

        // Print the array of characters
        System.out.println(charArray);
        for(int i=0;i<size_n;i++) {
            for(int j=0;j<size_m;j++) {
                if(isCharInArray(tab[i][j], charArray)){
                    for(int k=0;k<94;k++) {
                        if(tab[i][j] == tab_symb[k]) hist_parallel[k]++;
                    }
                }
            }
        }

        int len = end-start;
        for(int i=0;i<len;i++) {
            int res = histogram[start + i + 33];
            System.out.print(charArray[i] + " ");
            for (int j = 0; j < res; j++)
                System.out.print("=");
            System.out.print("\n");

        }
    }


// uniwersalny wzorzec dla roznych wariantow zrownoleglenia - mozna go modyfikowac dla
// roznych wersji dekompozycji albo stosowac tak jak jest zapisane ponizej zmieniajac tylko
// parametry wywolania w watkach
//
//calculate_histogram_wzorzec(start_wiersz, end_wiersz, skok_wiersz,
//                            start_kol, end_kol, skok_kol,
//                            start_znak, end_znak, skok_znak){
//
//  for(int i=start_wiersz;i<end_wiersz;i+=skok_wiersz) {
//     for(int j=start_kol;j<end_kol;j+=skok_kol) {
//        for(int k=start_znak;k<end_znak;k+=skok_znak) {
//           if(tab[i][j] == tab_symb[k]) histogram[k]++;
//
    public void print_histogram(){

    for(int i=0;i<94;i++) {
        System.out.print(tab_symb[i]+" "+histogram[i]+"\n");
        //System.out.print((char)(i+33)+" "+histogram[i]+"\n");
     }

    }

    // Znajdowanie indexu elementu
    private int getIndexOfElement(char sign) {
        for (int i = 0; i < histSize; i++)
            if (tab_symb[i] == sign)
                return i;

        return -1;
    }
    public int calculate(char symbol) {
        int counter = 0;

        for (int i = 0; i < size_n; i++)
            for (int j = 0; j < size_m; j++)
                if (tab[i][j] == symbol)
                    counter++;

        return counter;
    }

    public int[] getHistogramParallel() {
        return hist_parallel;
    }
    public char getSymbol(int index) {
        return tab_symb[index];
    }


    public void compare() {
        System.out.println("Znak  Sekwencyjnie  Równolegle");
        for (int i = 0; i < 94; i++) {
            System.out.print(tab_symb[i] + " " + histogram[i] + "  "  + hist_parallel[i] + "\n");
        }
    }
    private boolean isCharInArray(char targetChar, char[] charArray){
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == targetChar) {
                return true;
            }
        }
        return false;
    }
}

