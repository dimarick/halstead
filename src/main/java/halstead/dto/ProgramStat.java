package halstead.dto;

public class ProgramStat {
    final private int size;
    final private int bugCount;

    public ProgramStat(int size, int bugCount) {
        this.size = size;
        this.bugCount = bugCount;
    }

    public int getSize() {
        return size;
    }

    public int getBugCount() {
        return bugCount;
    }
}
