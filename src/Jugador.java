import java.util.ArrayList;
public class Jugador {
    private ArrayList<Carta> mano;
    public Jugador() {
        mano = new ArrayList<>();
    }
    public void agregarCarta(Carta c){
        mano.add(c);
    }

    public ArrayList<Carta> getMano(){
        return mano;
    }

    public void removerCarta(Carta carta){
        mano.remove(carta);
    }

    public int getCantidadCartas() {
        return mano.size();
    }
}
