public class Main {
    public static void main(String[] args) {
        Juego juego = new Juego();
        Tablero tablero = new Tablero();
        juego.entregarCartas();
        int cont=0;
        juego.iniciarPila();
        while(true){
            if(!(cont==0)){
                juego.cartasEspeciales();
            }
            if(juego.puedeJugar()){
                tablero.actualizarTablero(juego.getCartas(),juego.getJugadorActual().getMano());
                juego.colocarCarta(juego.pedirCarta());
                juego.cambiarColor();
                juego.cambiarTurno();
            }else{
                System.out.println("Comes una carta");
                juego.tomarCarta(1);
            }

            if(juego.getPlayer1().getMano().isEmpty() || juego.getPlayer2().getMano().isEmpty()){
                System.out.println("El juego ha terminado\n");
                if(juego.getPlayer1().getMano().isEmpty()){
                    System.out.println("Ganador: Jugador 1!");
                }else if(juego.getPlayer2().getMano().isEmpty()){
                    System.out.println("Ganador: Jugador 2!");
                }
                break;
            }
            cont++;
        }
    }

}