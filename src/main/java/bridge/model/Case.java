package bridge.model;

public enum Case {
    SUCCESS("O", "성공"),
    FAIL("X", "실패");

    private final String pictogram;
    private final String name;

    Case(String pictogram, String name) {
        this.pictogram = pictogram;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getPictogram(boolean isPass) {
        if (isPass) {
            return SUCCESS.pictogram;
        }
        return FAIL.pictogram;
    }
}
