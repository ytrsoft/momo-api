package com.ytrsoft.config;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class MomoBanner implements Banner {

    private static final String[] BANNER = {
            "____                      ____             ",
            ",'  , `.                  ,'  , `.           ",
            ",-+-,.' _ |   ,---.       ,-+-,.' _ |   ,---.   ",
            ",-+-. ;   , ||  '   ,'\\   ,-+-. ;   , ||  '   ,'\\  ",
            ",--.'|'   |  || /  M O M O ,--.'|'   |  || /  M O M O ",
            "|   |  ,', |  |,.   ; ,. :|   |  ,', |  |,.   ; ,. : ",
            "|   | /  | |--' '   | |: :|   | /  | |--' '   | |: : ",
            "|   : |  | ,    '   | .; :|   : |  | ,    '   | .; : ",
            "|   : |  |/     |   :    ||   : |  |/     |   :    | ",
            "|   | |`-'       \\   \\  / |   | |`-'       \\   \\  /  ",
            "|   ;/            `----'  |   ;/            `----'   ",
            "'---'          ||         '---'          ||           ",
            "               ||                        ||           "
    };

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        for (String line : BANNER) {
            out.println(line);
        }
        out.println();
    }
}
