import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private static Canvas canvasSingleton;

    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Picture Demo", 1200, 700, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private JScrollPane scrollPane;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;
    private int canvasWidth;
    private int canvasHeight;

    private Canvas(String title, int width, int height, Color bgColor) {
        frame = new JFrame();
        canvas = new CanvasPane();
        canvas.setLayout(null);
        scrollPane = new JScrollPane(canvas);
        frame.setContentPane(scrollPane);
        frame.setTitle(title);
        frame.setLocation(30, 30);
        canvasWidth = width;
        canvasHeight = height;
        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        backgroundColor = bgColor;
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana
        objects = new ArrayList<>();
        shapes = new HashMap<>();
        createCanvasImage();
    }

    private void createCanvasImage() {
        canvasImage = canvas.createImage(canvasWidth, canvasHeight);
        graphic = (Graphics2D) canvasImage.getGraphics();
        graphic.setColor(backgroundColor);
        graphic.fillRect(0, 0, canvasWidth, canvasHeight);
        graphic.setColor(Color.black);
    }

    public void setVisible(boolean visible) {
        if (graphic == null) {
            createCanvasImage();
        }
        frame.setVisible(visible);
    }

    public void draw(Object referenceObject, String color, Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        updateCanvasSizeIfNeeded(shape.getBounds());
        redraw();
    }

    private void updateCanvasSizeIfNeeded(Rectangle shapeBounds) {
        int newWidth = Math.max(canvasWidth, shapeBounds.x + shapeBounds.width + 50);
        int newHeight = Math.max(canvasHeight, shapeBounds.y + shapeBounds.height + 50);

        if (newWidth != canvasWidth || newHeight != canvasHeight) {
            canvasWidth = newWidth;
            canvasHeight = newHeight;
            canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
            resizeCanvasImage(); // Llama a este método para actualizar la imagen del canvas
            frame.pack(); // Reajustar el tamaño del frame
            scrollPane.revalidate(); // Revalidar el JScrollPane
            scrollPane.getViewport().scrollRectToVisible(shapeBounds); // Asegurar que el área de la carta sea visible
        }
        canvas.repaint(); // Repintar el canvas
    }

    private void resizeCanvasImage() {
        canvasImage = canvas.createImage(canvasWidth, canvasHeight); // Crea una nueva imagen
        graphic = (Graphics2D) canvasImage.getGraphics();
        graphic.setColor(backgroundColor);
        graphic.fillRect(0, 0, canvasWidth, canvasHeight); // Rellena el nuevo canvas con el color de fondo
    }

    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    public void setForegroundColor(String colorString) {
        switch (colorString) {
            case "red":
                graphic.setColor(new Color(235, 25, 25));
                break;
            case "black":
                graphic.setColor(Color.black);
                break;
            case "blue":
                graphic.setColor(new Color(30, 75, 220));
                break;
            case "yellow":
                graphic.setColor(new Color(255, 230, 0));
                break;
            case "green":
                graphic.setColor(new Color(80, 160, 60));
                break;
            case "magenta":
                graphic.setColor(Color.magenta);
                break;
            case "white":
                graphic.setColor(Color.white);
                break;
            case "gray":
                graphic.setColor(Color.gray);
                break;
            case "brown":
                graphic.setColor(new Color(150, 75, 0));
                break;
            case "orange":
                graphic.setColor(Color.orange);
                break;
            case "pink":
                graphic.setColor(Color.pink);
                break;
            default:
                graphic.setColor(Color.black);
                break;
        }
    }

    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // Ignorando excepción
        }
    }

    private void redraw() {
        erase();
        for (Object shape : objects) {
            shapes.get(shape).draw(graphic);
        }
        canvas.repaint();
    }

    private void erase() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        graphic.fill(new Rectangle(0, 0, canvasWidth, canvasHeight));
        graphic.setColor(original);
    }

    public void addCarta(Carta carta, int x, int y) {
        JLabel label = carta.getLabel();
        label.setBounds(x, y, carta.getWidth(), carta.getHeight()); // Usa los valores de la carta
        canvas.add(label); // Agrega la carta al CanvasPane
        canvas.repaint();

        // Ajustar el tamaño del canvas si es necesario
        updateCanvasSizeIfNeeded(new Rectangle(x, y, carta.getWidth(), carta.getHeight()));

        // Actualiza el tamaño preferido del canvas
        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        scrollPane.revalidate(); // Revalidar el JScrollPane
    }

    private class CanvasPane extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(canvasWidth, canvasHeight); // Retornar el tamaño preferido
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    private class ShapeDescription {
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color) {
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.fill(shape);
        }
    }

    public void repaintCanvas() {
        canvas.repaint(); // Repintar el CanvasPane
    }

    public void clear() {
        // Eliminar todos los objetos y formas
        canvas.removeAll();

        // Volver a crear la imagen del canvas con el fondo
        createCanvasImage();

        // Repintar el canvas
        canvas.repaint();
    }
}