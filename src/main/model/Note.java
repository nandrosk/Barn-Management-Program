package model;

import org.json.JSONObject;

import persistence.Writeable;

// Represents a note
public class Note implements Writeable {

    private String body;

    //EFFECTS: constructs a note with the given body
    public Note(String body) {
        this.body = body;
    }

    //Getters

    public String getNote() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }

    public String toString() {
        return "-------" + "\n" + body;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("body", body);
        return json;
    }

}
