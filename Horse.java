package model;

import org.json.JSONObject;

import persistence.Writeable;

// Represents a horse
public class Horse implements Writeable {

    private String horseName;
    private String ownerName;
    private String ownerPhone;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String farrier;
    private String vet;

    // EFFECTS: constructs a horse with the given name, owner, and owner phone
    // all other fields are empty unless set
    public Horse(String horseName, String ownerName, String ownerPhone) {
        this.horseName = horseName;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        emergencyContactName = "null";
        emergencyContactPhone = "null";
        farrier = "null";
        vet = "null";
    }

    // EFFECTS: constructs a horse with the given fields
    public Horse(String horseName, String ownerName, String ownerPhone,
            String emergencyContactName, String emergencyContactPhone,
            String farrier, String vet) {
        this.horseName = horseName;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.farrier = farrier;
        this.vet = vet;
    }

    // Getters

    public String getHorseName() {
        return horseName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public String getEmergencyConactPhone() {
        return emergencyContactPhone;
    }

    public String getFarrier() {
        return farrier;
    }

    public String getVet() {
        return vet;
    }

    public String toString() {
        String info = "\nName: " + horseName + "\nOwner: " + ownerName
                + "\nOwner phone: " + ownerPhone + "\nEmergency contact: " + emergencyContactName
                + "\nEmergency phone: " + emergencyContactPhone + "\nFarrer: " + farrier
                + "\nVet: " + vet;
        return info;
    }

    // SETTERS

    // EFFECTS: sets the horse's name to the given value 
    // and logs the event
    public void setHorseName(String horseName) {
        EventLog.getInstance().logEvent(new Event("Horse name changed from " + this.horseName + " to " + horseName));
        this.horseName = horseName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public void setEmergencyContactName(String emerContactName) {
        this.emergencyContactName = emerContactName;
    }

    public void setEmergencyContactPhone(String emerContactPhone) {
        this.emergencyContactPhone = emerContactPhone;
    }

    public void setFarrier(String farrier) {
        this.farrier = farrier;
    }

    public void setVet(String vet) {
        this.vet = vet;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", horseName);
        json.put("owner", ownerName);
        json.put("ownerPhone", ownerPhone);
        json.put("emergencyContactName", emergencyContactName);
        json.put("emergencyContactPhone", emergencyContactPhone);
        json.put("farrier", farrier);
        json.put("vet", vet);
        return json;
    }

}
