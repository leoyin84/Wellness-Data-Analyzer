public class Staff extends Person {
    public Staff(String name, int steps, double sleep, double water) {
        super(name, steps, sleep, water);
    }

    @Override
    public double calculateWellnessScore() {
        return 0.5 * (getSteps() / 1000.0)
                + 0.3 * getSleep()
                + 2 * getWater();
    }

    @Override
    public String getRole() {
        return "Staff";
    }
}