package org.emailresume.htmlparser;


public class ParserFactory {

    public static Parser getParser(int channel){
        switch (channel){
            case 1:
                return new ZhiLianParser();
            case 2:
                return new QianChengParser();
            case 3:
                return new LiePinParser();
            default:
                return null;
        }
    }
}
