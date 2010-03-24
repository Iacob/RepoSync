package luoyong.toolbox.reposync.lib;

import java.io.File;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public abstract class LocalFsItem {
   
   private File file = null;
   private File baseDir = null;

   public LocalFsItem(String baseDir, String path) {
      this.baseDir = new File(baseDir);
      this.file = new File(baseDir + ((path==null)?"":(File.separator + path)));
   }

   protected LocalFsItem(File baseDir, File file) {
      this.baseDir = baseDir;
      this.file = file;
   }

   public String getName() {
      return file.getName();
   }

   public LocalDir getParent() {
      if (this.file.equals(this.baseDir)) {
         return null;
      }else {
         return new LocalDir(this.baseDir, file.getParentFile());
      }
   }

   public File getFile() {
      return this.file;
   }

   public File getBaseDir() {
      return this.baseDir;
   }

   public String getAbsolutePath() {
      if (this.getFile() != null) {
         return this.getFile().getAbsolutePath();
      }else {
         return null;
      }
   }

   /**
    *
    * @deprecated
    */
   public abstract void delete();
}
