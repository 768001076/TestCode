package nxw.diy.ormframe.enumuse;

public enum ExecuteTypeEnum {
    SELECT(1,"SELECT","FROM"),INSERT(2,"INSERT","INTO"),UPDATE(3,"UPDATE","SET");

    private int no;

    private String exec;

    private String execSuffix;

    ExecuteTypeEnum(int no, String exec, String execSuffix) {
        this.no = no;
        this.exec = exec;
        this.execSuffix = execSuffix;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getExec(String tableName) {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }
}
