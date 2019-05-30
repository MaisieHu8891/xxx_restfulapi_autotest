package ut;

import org.testng.annotations.Test;
import utils.ExecCmd;

public class ExecCmdTest {
    ExecCmd execCmd = new ExecCmd();



    @Test
    public void testExecShell() {
        execCmd.execShell("src/shell/pullimage.sh","jenkins");
    }
}