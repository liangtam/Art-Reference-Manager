package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

// Represents a folder of reference images and sub-reference folders
public class ReferenceFolder implements Writable {
    private String folderName;
    private List<ReferenceImage> refImages;
    private List<ReferenceFolder> subRefFolders;

    // Creates a reference folder with given name and no images added yet
    public ReferenceFolder(String name) {
        this.folderName = name;
    }

    // MODIFIES: this
    // EFFECTS: add given reference to collection of reference images if it does not already
    //          exist and return true
    //          else, do nothing and return false
    public boolean addRef(ReferenceImage ref) {
        if (!ifRefExistsAlready(ref)) {
            this.refImages.add(ref);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes images given reference from collection of reference images
    public boolean deleteRef(ReferenceImage ref) {
        if (!ifRefExistsAlready(ref)) {
            this.refImages.remove(ref);
            return true;
        }
        return false;
    }

    // EFFECTS: checks if given reference image is already in the collection of reference images and returns
    public boolean ifRefExistsAlready(ReferenceImage ref) {
        for (ReferenceImage r: this.refImages) {
            if (r == ref || r.getName().equals(ref.getName()) || r.getImage().equals(ref.getImage())) {
                return true;
            }
        }
        return false;
    }

    public void printAllReferenceImageNames() {
        for (int i = 0; i < refImages.size(); i++) {
            System.out.println("[ " + i + " ]" + refImages.get(i).getName());
        }
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


