package halstead.dto;

public class EstimationProgramModel {
    private final double programVolume;
    private final double bugCount;

    public EstimationProgramModel(double programVolume, double bugCount) {
        this.programVolume = programVolume;
        this.bugCount = bugCount;
    }

    public double getProgramVolume() {
        return programVolume;
    }

    public double getBugCount() {
        return bugCount;
    }
}
