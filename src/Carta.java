import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

public class Carta {
    private int numero;
    private String color;
    private JLabel label;
    private int x;
    private int y;
    private int width;
    private int height;

    public Carta(int numero, String color,int x,int y) {
        this.numero = numero;
        //10 = +2, 11 = voltear, 12 = skip, 13 = +4, 14 = color
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = 150;
        this.height = 200;
        this.label = new JLabel();
        setIcon("Images/UNO"+numero+"-"+color+".png");
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    // Método para cambiar el tamaño de la imagen de la carta
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        Canvas canvas = Canvas.getCanvas();
        setIcon("Images/UNO"+numero+"-"+color+".png");  // Actualiza el ícono con el nuevo tamaño
        canvas.repaintCanvas(); // Vuelve a pintar el canvas para reflejar el cambio
    }

    // Método para asignar el ícono escalado al tamaño de la carta
    private void setIcon(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        // Escalar la imagen al tamaño deseado
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(image));
        label.setBounds(0, 0, width, height);  // Ajustar el tamaño del JLabel
    }

    public JLabel getLabel() {
        return label;
    }

    // Método para dibujar la carta en el Canvas
    public void draw() {
        Canvas canvas = Canvas.getCanvas();
        label.setBounds(x, y, width, height);
        canvas.addCarta(this, x, y);
    }

    // Método para mover la carta a nuevas coordenadas
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        Canvas canvas = Canvas.getCanvas();
        label.setBounds(x, y, width, height); // Actualiza la posición en el Canvas
        canvas.repaintCanvas(); // Vuelve a pintar el canvas para reflejar el cambio
    }
    public int getNumero(){return numero;}

    public String getColor(){return color;}
    public void setColor(String color){this.color = color;}

}
