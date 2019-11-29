package net.wangds.zxingcomp;

import net.wangds.log.helper.LogHelper;

import java.nio.charset.Charset;

public class Test {

    @org.junit.Test
    public void test(){
        LogHelper.info(Charset.forName("UTF8").name());
    }

}
