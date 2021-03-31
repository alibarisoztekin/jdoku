package persistance;

import org.json.JSONObject;

// interface for Object to json serialization
public interface Jsonable {
    JSONObject jsoned();
}
