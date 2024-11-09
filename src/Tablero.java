import java.util.ArrayList;
public class Tablero
{
    public Tablero(){
    }

    public void limpiarPantalla(){
        Canvas.getCanvas().clear();
    }
    public void mostrarPila(ArrayList<Carta> cartas){
        if(!cartas.isEmpty()){
            cartas.get(cartas.size()-1).move(500,100);
            cartas.get(cartas.size()-1).draw();
        }
    }

    public void mostrarMano(ArrayList<Carta> cartas){
        int x=50;
        int y=350;
        for (Carta carta: cartas){
            carta.move(x,y);
            carta.draw();
            x+=155;
        }
    }

    public void actualizarTablero(ArrayList<Carta> pila, ArrayList<Carta> mano){
        limpiarPantalla();
        mostrarPila(pila);
        mostrarMano(mano);
    }
}