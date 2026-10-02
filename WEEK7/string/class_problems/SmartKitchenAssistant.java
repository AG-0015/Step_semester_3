abstract class KitchenTool {

    private int speedLevel = 1;

    public KitchenTool() {
    }

    public abstract String prepare();

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        if (speedLevel < 1 || speedLevel > 5) {
            throw new IllegalArgumentException(
                    "Speed level must be between 1 and 5"
            );
        }

        this.speedLevel = speedLevel;
    }
}


interface Washable {
    String clean();
}


class Blender extends KitchenTool implements Washable {

    public Blender() {
        super();
    }

    @Override
    public String prepare() {
        return "Blending at speed " + getSpeedLevel();
    }

    @Override
    public String clean() {
        return "Blender rinsed and dried";
    }
}


public class SmartKitchenAssistant {

    public static void main(String[] args) {

        Blender b = new Blender();

        b.setSpeedLevel(3);

        System.out.println(b.getSpeedLevel());
        System.out.println(b.prepare());
        System.out.println(b.clean());

        try {
            b.setSpeedLevel(9);
        } catch (IllegalArgumentException e) {
            System.out.println("Speed level rejected");
        }

        System.out.println(b.getSpeedLevel());
    }
}