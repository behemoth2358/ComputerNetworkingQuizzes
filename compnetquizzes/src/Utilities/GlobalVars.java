package Utilities;

public class GlobalVars {
    private static GlobalVars ourInstance = new GlobalVars();

    public static GlobalVars getInstance() {
        return ourInstance;
    }

    public static final String PATHTOPROJ = "/home/bogdanboboc97/Projects/aspecte_pragmatice/ComputerNetworkingQuizzes/compnetquizzes/src";

    private GlobalVars() {
    }
}
