package luoyong.toolbox.reposync.lib;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public final class LocalDir extends LocalFsItem {

   public LocalDir(String baseDir, String path) {
      super(baseDir, path);
   }

   protected LocalDir(File baseDir, File file) {
      super(baseDir, file);
   }

   /**
    *
    * @deprecated
    * @return
    */
   public List<LocalFsItem> getLocalFsItems() {
      List<LocalFsItem> itemList = new LinkedList<LocalFsItem>();
      File[] fileList = this.getFile().listFiles();
      for (File fileItem : fileList) {
         if (fileItem.isFile()) {
            itemList.add(new LocalFile(this.getBaseDir(), fileItem));
         }else if (fileItem.isDirectory()) {
            itemList.add(new LocalDir(this.getBaseDir(), fileItem));
         }
      }
      return itemList;
   }

   /**
    *
    * @deprecated
    * @param name
    * @return
    */
   public LocalFsItem getLocalFsItemByName(String name) {
      File[] fileList = this.getFile().listFiles();
      for (File fileItem : fileList) {

         if (fileItem.getName().equals(name)) {

            if (fileItem.isFile()) {
               return (new LocalFile(this.getBaseDir(), fileItem));
            }else if (fileItem.isDirectory()) {
               return (new LocalDir(this.getBaseDir(), fileItem));
            }
         }
      }
      return null;
   }

   /**
    *
    * @deprecated
    * @param name
    * @return
    * @throws IOException
    */
   public LocalFile buildLocalFile(String name) throws IOException {
      File newFile = new File(this.getFile().getAbsolutePath() + File.separator
              + name);

      if (newFile.createNewFile()) {
         return new LocalFile(this.getBaseDir(), newFile);
      }else {
         return null;
      }
   }

   /**
    * 
    * @deprecated
    * @param name
    * @return
    */
   public LocalDir buildLocalDir(String name) {
      File newDir = new File(this.getFile().getAbsolutePath() + File.separator
              + name);
      if (newDir.mkdir()) {
         return new LocalDir(this.getBaseDir(), newDir);
      }else {
         return null;
      }
   }

   /**
    * 
    * @deprecated
    * @param sourceFile
    * @param name
    * @return
    * @throws IOException
    */
   public LocalFile copyFileFrom(LocalFile sourceFile, String name)
           throws IOException{

      File srcFile = sourceFile.getFile();
      File destFile = new File(this.getFile().getAbsolutePath()
              + File.separator + name);
      
      FileUtils.copyFile(srcFile, destFile);

      return new LocalFile(this.getBaseDir(), destFile);
   }

   /**
    *
    * @deprecated
    */
   @Override
   public void delete() {
      this.getFile().delete();
   }

}
