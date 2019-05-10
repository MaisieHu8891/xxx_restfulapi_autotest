package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecCmd{
    private static final Logger logger = LoggerFactory.getLogger(ExecCmd.class);

    /**
     * 执行命令，结果以String返回
     */
    public String execCMDget(String cmmand) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmmand);
            InputStream is = p.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "gbk"));
            String line = null;
            StringBuffer sbBuffer = new StringBuffer();
            while ((line = bReader.readLine()) != null)
                sbBuffer.append(line+ "\n");
            is.close();
            return sbBuffer.toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 只执行命令，结果不做处理
     */
    public void execCmd(String cmmand) {
        ProcessHandle processHandle;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmmand).inheritIO();
            processHandle= processBuilder.start().toHandle();
            logger.debug("exeCmd启动的进程pid是："+processHandle.pid());
        } catch (IOException e) {
            logger.error("exeCmd失败: "+e.getMessage());
        }
    }

}
