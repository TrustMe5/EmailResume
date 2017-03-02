package org.emailresume.htmlparser;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class LongValueProcessor implements JsonValueProcessor {
    public Object processObjectValue(String paramString,
                                     Object paramObject, JsonConfig paramJsonConfig) {
        if (paramObject == null) {
            return null;
        }
        String ret = String.format("{\"$numberLong\": \"%s\"}" ,(paramObject).toString());
        return JSONObject.fromObject(ret);
    }

    public Object processArrayValue(Object paramObject,
                                    JsonConfig paramJsonConfig) {
        return null;
    }
}

