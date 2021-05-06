import java.io.File;

public class FileNode {

  private String fileName;
  private File file;

  public FileNode(File f, String absolutePath) {
    this.file = f;
    this.fileName = absolutePath;
  }

  public String getFileName() {
    return this.fileName;
  }

  public File getFile() {
    return this.file;
  }

  public boolean isFolder() {
    return this.file.isDirectory();
  }

  public String toString() {
    if (this.file.getName().equals("")) {
      return file.getPath();
    }
    return file.getName();
  }
}
