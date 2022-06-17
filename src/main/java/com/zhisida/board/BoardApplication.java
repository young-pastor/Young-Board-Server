package com.zhisida.board;

import cn.hutool.log.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot方式启动类
 *
 * @author young-pastor
 */
@SpringBootApplication
public class BoardApplication {

    private static final Log log = Log.get();

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
        log.info(">>> " + BoardApplication.class.getSimpleName() + " is success!");
    }

}
