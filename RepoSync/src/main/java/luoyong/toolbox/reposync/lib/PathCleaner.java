package luoyong.toolbox.reposync.lib;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class PathCleaner extends PathHandler {

   public void clean(String srcBaseDir, String destBaseDir) {
      LocalDir srcDir = new LocalDir(srcBaseDir, null);
      LocalDir destDir = new LocalDir(destBaseDir, null);

      this.cleanSubDir(srcDir, destDir);
   }

   private void cleanSubDir(LocalDir srcDir, LocalDir destDir) {

      // Clean files.
      List<LocalFsItem> items = RepoHandler.getDestLocalFsItems(destDir);
      for (LocalFsItem item : items) {
         if (item instanceof LocalDir) {
            LocalFsItem srcItem = RepoHandler.getSrcLocalFsItemByName(
                    srcDir, item.getName());
            if ((srcItem == null) || (!(srcItem instanceof LocalDir))) {
               try {
                  RepoHandler.deleteDestDir((LocalDir)item);
               }catch(IOException e) {
                  // Report error.
                  this.triggerSevereEvent(srcDir.getAbsolutePath(),
                       destDir.getAbsolutePath(),
                       "removeDir",
                       ExceptionUtils.getFullStackTrace(e));
                  e.printStackTrace(System.err);
               }
            }
         }else if (item instanceof LocalFile) {
            LocalFsItem srcItem = RepoHandler.getSrcLocalFsItemByName(
                    srcDir, item.getName());
            if ((srcItem == null) || (!(srcItem instanceof LocalFile))) {
               if (!RepoHandler.deleteDestFile((LocalFile)item)) {
                  // Report error.
                  this.triggerSevereEvent(srcDir.getAbsolutePath(),
                       destDir.getAbsolutePath(),
                       "removeFile",
                       "Cannot remove file.");
               }
            }
         }else {
            // Report error.
            this.triggerSevereEvent(srcDir.getAbsolutePath(),
                    destDir.getAbsolutePath(),
                    "buildSubDir",
                    "Unknown file type");
         }
      }

      // Process next directory.
      items = RepoHandler.getDestLocalFsItems(destDir);
      for (LocalFsItem item : items) {
         if (item instanceof LocalDir) {
            LocalFsItem foundSrcItem =
                    RepoHandler.getSrcLocalFsItemByName(srcDir, item.getName());
            if ((foundSrcItem != null) && (foundSrcItem instanceof LocalDir)) {
               LocalDir nextSrcDir = (LocalDir)foundSrcItem;
               LocalDir nextDestDir = (LocalDir)item;
               this.cleanSubDir(nextSrcDir, nextDestDir);
            }
         }
      }
   }
}
