package seedu.planpal.utility.filemanager;

public interface Storeable {
    public void setCommandDescription(String description);
    public String getCommandDescription();
    public String getStoragePath();
}
