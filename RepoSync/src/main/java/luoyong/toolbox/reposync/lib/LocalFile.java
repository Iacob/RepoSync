package luoyong.toolbox.reposync.lib;

import java.io.File;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public final class LocalFile extends LocalFsItem {

   public LocalFile(String baseDir, String path) {
      super(baseDir, path);
   }

   public LocalFile(File baseDir, File file) {
      super(baseDir, file);
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
