import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Juego {
    private ArrayList<Carta> mazo;
    private ArrayList<Carta> cartas;
    private Jugador player1;
    private Jugador player2;
    private boolean turno;

    public Juego() {
        mazo = new ArrayList<>();
        cartas = new ArrayList<>();
        player1 = new Jugador();
        player2 = new Jugador();
        turno = true;
        inicializarMazo();
    }

    public Jugador getPlayer1() {
        return player1;
    }
    public Jugador getPlayer2() {
        return player2;
    }
    public Jugador getJugadorActual(){
        if(turno){return player1;}
        else{return player2;}
    }
    public boolean getTurno(){
        return turno;
    }
    public void cambiarTurno(){
        turno = !turno;
    }
    private void inicializarMazo() {
        String[] colors = {"blue", "yellow", "red", "green"};
        Arrays.stream(colors).forEach(color -> {
            mazo.add(new Carta(0, color, 0, 0));
            for (int num = 1; num <= 12; num++) {
                mazo.add(new Carta(num, color, 0, 0));
                mazo.add(new Carta(num, color, 0, 0));
            }
        });
        // Añadir 4 cartas +4 y 4 de cambio de color (sin color específico)
        for (int i = 0; i < 4; i++) {
            mazo.add(new Carta(13, "blank",0,0)); // +4
            mazo.add(new Carta(14, "blank",0,0)); // Cambio de color
        }
        Collections.shuffle(mazo);
    }
    public ArrayList<Carta> getMazo(){
        return mazo;
    }
    public ArrayList<Carta> getCartas(){
        return cartas;
    }
    public void entregarCartas(){
        for(int i = 1; i<=7; i++){
            player1.getMano().add(mazo.remove(0));
        }
        for(int i = 1; i<=7; i++){
            player2.getMano().add(mazo.remove(0));
        }
    }
    public void iniciarPila(){
        cartas.add(mazo.remove(0));
    }
    //--------------------------------------------------------------------------
    public boolean puedeJugar(){
            Carta cartaComparar = cartas.get(cartas.size() - 1);
            Jugador jugador = turno ? player1 : player2;

            return jugador.getMano().stream().anyMatch(cartaJugador ->
                    cartaJugador.getNumero() == cartaComparar.getNumero() ||
                            cartaJugador.getColor().equals(cartaComparar.getColor()) ||
                            cartaJugador.getNumero() == 13 || cartaJugador.getNumero() == 14
            );
    }

    public boolean validarCarta(Carta cartaJugador){
        Carta cartaComparar = cartas.getLast();

        if(cartaJugador.getNumero() == cartaComparar.getNumero()||cartaJugador.getColor().equals(cartaComparar.getColor())){return true;}
        if(cartaJugador.getNumero() == 13||cartaJugador.getNumero() == 14){return true;}
        return false;
    }

    public void tomarCarta(int cantidad){
        Jugador jugador;
        if(turno){jugador = player1;}
        else{jugador = player2;}

        for(int i = 0; i < cantidad;i++){jugador.agregarCarta(mazo.removeFirst());}
    }

    public void colocarCarta(Carta cartaJugador){
        Jugador jugador;
        cartas.add(cartaJugador);
        if(turno){jugador = player1;}
        else{jugador = player2;}
        jugador.removerCarta(cartaJugador);
    }

    public Carta pedirCarta(){
        Jugador jugador;
        Scanner teclado = new Scanner(System.in);
        Carta cartaPedida;
        int numeroCarta;

        System.out.println("-----------------------------------------");
        if(turno){
            System.out.println("Turno jugador 1");
            jugador = player1;}
        else{
            System.out.println("Turno jugador 2");
            jugador = player2;}

        // Repetir hasta que introduzca numero valido de carta y que sea jugable
        do {
            System.out.println("Ingrese la carta a colocar: ");
            numeroCarta = teclado.nextInt();
            cartaPedida = jugador.getMano().get(numeroCarta-1);
            if(!validarCarta(cartaPedida)){System.out.println("Numero de carta no valido...\n");}
        }while(!validarCarta(cartaPedida));
        return cartaPedida;
    }
    public void cartasEspeciales(){
        int numero = cartas.getLast().getNumero();

        if(numero >= 10 && numero <= 13){
            if(numero == 10 ){
                tomarCarta(2);
            } else if (numero == 13) {
                tomarCarta(4);
            }
            cambiarTurno();
        }
    }
    public void cambiarColor(){
        Scanner teclado = new Scanner(System.in);
        Carta carta = cartas.getLast();
        int opcion;
        if(carta.getNumero()==13 || carta.getNumero()==14){
            // Repetir hasta que la opcion sea valida
            do {
                System.out.println("Ingrese el color a cambiar... \n1- Rojo \n2- Azul \n3- Amarillo \n4- Verde");
                opcion = teclado.nextInt();

                switch (opcion) {
                    case 1:
                        carta.setColor("red");
                        break;
                    case 2:
                        carta.setColor("blue");
                        break;
                    case 3:
                        carta.setColor("yellow");
                        break;
                    case 4:
                        carta.setColor("green");
                        break;
                    default:
                        System.out.println("Opcion no valida...\n");
                }
            }while(opcion < 1 || opcion > 4);
        }

    }
}