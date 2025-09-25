package halstead.dto;

public class EstimationModuleProgramModel {
    private final double moduleCount;
    private final double programSize;
    private final double programVolume;
    private final double assemblerInstructions;
    private final double projectTime;
    private final double bugCount;
    private final double meanTimeToFailure;

    public EstimationModuleProgramModel(double moduleCount, double programSize, double programVolume, double assemblerInstructions, double projectTime, double bugCount, double meanTimeToFailure) {
        this.moduleCount = moduleCount;
        this.programSize = programSize;
        this.programVolume = programVolume;
        this.assemblerInstructions = assemblerInstructions;
        this.projectTime = projectTime;
        this.bugCount = bugCount;
        this.meanTimeToFailure = meanTimeToFailure;
    }

    public double getModuleCount() {
        return moduleCount;
    }

    public double getProgramSize() {
        return programSize;
    }

    public double getProgramVolume() {
        return programVolume;
    }

    public double getAssemblerInstructions() {
        return assemblerInstructions;
    }

    public double getProjectTime() {
        return projectTime;
    }

    public double getBugCount() {
        return bugCount;
    }

    public double getMeanTimeToFailure() {
        return meanTimeToFailure;
    }
}
