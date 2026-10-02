@'
abstract class GardenTool {

    public GardenTool() {
    }

    public abstract String use();

    protected String baseUseMessage() {
        return "Using the tool in the garden";
    }
}

class CuttingTool extends GardenTool {

    public CuttingTool() {
        super();
    }

    @Override
    public String use() {
        return baseUseMessage() + ", blade sharpened first";
    }
}

class Pruner extends CuttingTool {

    public Pruner() {
        super();
    }

    @Override
    public String use() {
        return super.use() + ", then trimming branches precisely";
    }
}

public class BackyardToolshedRoutine {

    public static void main(String[] args) {
        CuttingTool c = new CuttingTool();
        Pruner p = new Pruner();

        System.out.println(c.use());
        System.out.println(p.use());
    }
}
'@ | Set-Content "WEEK7\string\assigment_problems\BackyardToolshedRoutine.java"