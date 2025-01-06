public class SmartTv {
    boolean ligada = false;
    int canal = 1;
    int volume = 10;

    public  void ligar() {
        ligada = true;
    }
    public void desligar() {
        ligada = false;
    }
    public void aumentarVolume() {
        volume++;
        System.out.println("Aumentando Volume " +  volume);
    }
    public void diminuirVolume() {
        volume--;
        System.out.println("Diminuindo Volume " +  volume);
    }

    public void aumentaCanal() {
        canal++;
        System.out.println("Aumentando Canal " +  canal);
    }
    public void diminuirCanal() {
        canal--;
        System.out.println("Diminuindo Canal " +  canal);
    }
    public void mudarCanal(int novoCanal) {
        canal = novoCanal;
        System.out.println("Mudando Canal " +  canal);
    }
}
