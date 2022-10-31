package model;

import java.util.List;

// Represents a folder of reference images and sub-reference folders
public class ReferenceFolder {
    private String folderName;
    private List<ReferenceImage> refImages;
    private List<ReferenceFolder> referenceFolders;

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
            if (r == ref) {
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
    public List<ReferenceFolder> getReferenceFolders() {
        return this.referenceFolders;
    }

    // MODIFIES: this
    // EFFECTS: sets the current folder name to given name
    public void setFolderName(String newName) {
        this.folderName = newName;
    }


}


