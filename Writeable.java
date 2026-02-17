package persistence;

import org.json.JSONObject;

// CREDIT: Based entirely on JsonSerializationDemo;
// toJson method in classes implementing writeable 
// are also based on JsonSerializationDemo

public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
