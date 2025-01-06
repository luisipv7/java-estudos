public class Usuario {
    public static void main(String[] args) throws Exception {
        SmartTv smartTv = new SmartTv();

        smartTv.diminuirVolume();
        smartTv.diminuirVolume();
        smartTv.diminuirVolume();
        smartTv.diminuirVolume();
        smartTv.aumentarVolume();
        System.out.println("Volume " +  smartTv.volume);

        System.out.println("Tv ligada? " + smartTv.ligada);
        System.out.println("Qual canal? " + smartTv.canal);
        System.out.println("Volume " +  smartTv.volume);

        smartTv.ligar();
        System.out.println("Tv ligada? " + smartTv.ligada);

        smartTv.desligar();
        System.out.println("Tv ligada? " + smartTv.ligada);

        smartTv.aumentaCanal();
        System.out.println("Qual canal? " + smartTv.canal);

        smartTv.diminuirCanal();
        System.out.println("Qual canal? " + smartTv.canal);

        smartTv.mudarCanal(50);
        System.out.println("Qual canal? " + smartTv.canal);
    }
}
