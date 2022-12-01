package model;

import model.exceptions.CurrentFolderException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a folder of reference images and sub-reference folders
public class ReferenceFolder implements Writable {
    private String folderName;
    private List<ReferenceImage> refImages;
    private List<ReferenceFolder> subRefFolders;
    private EventLog eventLog = EventLog.getInstance();

    // Creates a reference folder with given name and no images added yet
    public ReferenceFolder(String name) {
        this.folderName = name;
        this.refImages = new ArrayList<>();
        this.subRefFolders = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add given reference to collection of reference images if it does not already
    //          exist and return true
    //          else, do nothing and return false
    public boolean addRef(ReferenceImage ref) {
        if (!ifRefExistsAlready(ref)) {
            this.refImages.add(ref);
            eventLog.logEvent(new Event("Added image " + ref.getName() + " to " + getFolderName()));
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes images given reference from collection of reference images
    public boolean deleteRef(ReferenceImage ref) {
        if (refImages.isEmpty()) {
            return false;
        }

        if (ifRefExistsAlready(ref)) {
            this.refImages.remove(ref);
            eventLog.logEvent(new Event("Removed image " + ref.getName() + " from " + getFolderName()));

            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: if collection of subRefFolders does not have a reference folder
    //             with same name as given reference folder, nor
    //             is the given reference folder a parent reference folder,
    //          adds given reference folder to collection of subRefFolders, return true
    //          else, do nothing and return false
    public boolean addSubRefFolder(ReferenceFolder referenceFolder) throws CurrentFolderException {
        if (folderName.equals(referenceFolder.getFolderName())) {
            throw new CurrentFolderException();
        }
        if (ifSubRefFolderAlreadyExists(referenceFolder)) {
            return false;
        }
        eventLog.logEvent(new Event("Added sub folder " + referenceFolder.getFolderName()
                + " to " + getFolderName()));
        this.subRefFolders.add(referenceFolder);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: if collection of subRefFolders has a sub folder
    //                 with same name as given sub folder,
    //                 remove given sub folder from collection of subRefFolders, return true
    //              else, do nothing and return false
    public boolean deleteSubRefFolder(ReferenceFolder referenceFolder) throws CurrentFolderException {
        if (this.folderName.equals(referenceFolder.getFolderName())) {
            throw new CurrentFolderException();
        }
        if (ifSubRefFolderAlreadyExists(referenceFolder)) {
            this.subRefFolders.remove(referenceFolder);
            eventLog.logEvent(new Event("Deleted sub folder " + referenceFolder.getFolderName()
                    + " from " + getFolderName()));
            return true;
        }

        return false;
    }

    // EFFECTS: returns true if current folder already has given sub folder as sub folder,
    //          else returns false
    public boolean ifSubRefFolderAlreadyExists(ReferenceFolder referenceFolder) {
        for (ReferenceFolder rf: subRefFolders) {
            if (rf.getFolderName().equals(referenceFolder.getFolderName())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: checks if given reference image is already in the collection of reference images and returns
    public boolean ifRefExistsAlready(ReferenceImage ref) {
        for (ReferenceImage r: this.refImages) {
            if (r.getName().equals(ref.getName()) || r.getImageURL().equals(ref.getImageURL())) {
                return true;
            }
        }
        return false;
    }

    // Getters and setters
    // EFFECTS: returns the name of the reference folder
    public String getFolderName() {
        return this.folderName;
    }

    // EFFECTS: returns the list of reference images
    public List<ReferenceImage> getReferenceImages() {
        return this.refImages;
    }

    // EFFECTS: returns the list of sub-reference folders
    public List<ReferenceFolder> getSubRefFolders() {
        return this.subRefFolders;
    }

    // MODIFIES: this
    // EFFECTS: sets the current folder name to given name
    public void setFolderName(String newName) {
        this.folderName = newName;
    }

    // **based on JsonSerializationDemo project**
    // EFFECTS: turns this ReferenceFolder object into a JSON object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray arrayOfRefImages = new JSONArray();
        JSONArray arrayOfSubRefFolders = new JSONArray();

        for (ReferenceImage r: this.refImages) {
            JSONObject refImageToJson = r.toJson();
            arrayOfRefImages.put(refImageToJson);
        }

        for (ReferenceFolder rf: this.subRefFolders) {
            JSONObject subFolderToJson = rf.toJson();
            arrayOfSubRefFolders.put(subFolderToJson);

        }
        json.put("folderName", this.folderName);
        json.put("listOfImages", arrayOfRefImages);
        json.put("listOfSubFolders", arrayOfSubRefFolders);

        return json;
    }


}


