package utils;
/**
 * Created by hujunxiang on 2019-05-05
 */

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class ExecCmd{
    private static final Logger logger = LoggerFactory.getLogger(ExecCmd.class);



    /**
     * 执行命令，结果以String返回
     */
    public String getExecRes(String cmmand) {
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

    /**
     * 执行.sh脚本文件
     */
    public void execShell(String shellfile,String... param){
        String[] cmdlist= new String[]{shellfile};
        cmdlist = ArrayUtils.addAll(cmdlist,param);
        ProcessBuilder processChmod = new ProcessBuilder("chmod","777",shellfile);
        ProcessBuilder processExecShell = new ProcessBuilder(cmdlist);
        int runningStatus = 0;
        String s = null;
        try {
            processChmod.start();
            Process pexecShell = processExecShell.start();
            try {
                runningStatus = pexecShell.waitFor();
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(pexecShell.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(pexecShell.getErrorStream()));
                while ((s = stdInput.readLine()) != null) {
                    logger.debug(s);
                }
                while ((s = stdError.readLine()) != null) {
                    logger.debug(s);
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        if (runningStatus != 0) {
        }
    }
}
