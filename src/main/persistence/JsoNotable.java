package persistence;

import org.json.JSONObject;

// interface for Object to JSON serialization
public interface JsoNotable {
    JSONObject json();
}
