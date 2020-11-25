package org.cloudkitchens.ordermgmt.injestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputJArrayObject implements InputDataObject {

    private final Logger logger = LoggerFactory.getLogger(InputJArrayObject.class);


    JSONArray jsonArray;


    public InputJArrayObject(String data) {

        try {

            jsonArray = new JSONArray(data);

        } catch (JSONException jsonException) {

            jsonArray = new JSONArray();
        }
    }

    public JSONArray getJsonArrayObject() {

        return jsonArray;
    }
}
