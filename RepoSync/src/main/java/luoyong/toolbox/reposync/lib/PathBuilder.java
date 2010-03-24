package luoyong.toolbox.reposync.lib;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class PathBuilder extends PathHandler {

   public void build(String srcBaseDir, String destBaseDir) {
      LocalDir srcDir = new LocalDir(srcBaseDir, null);
      LocalDir destDir = new LocalDir(destBaseDir, null);

      this.buildSubDir(srcDir, destDir);
   }

   private void buildSubDir(LocalDir srcDir, LocalDir destDir) {
      List<LocalFsItem> items = RepoHandler.getSrcLocalFsItems(srcDir);
      for (LocalFsItem item : items) {
         if (item instanceof LocalDir) {
            LocalDir nextSrcDir = (LocalDir)item;
            LocalDir nextDestDir = RepoHandler.buildDir(destDir, item.getName());
            if (nextDestDir != null) {
               buildSubDir(nextSrcDir, nextDestDir);
            }else {
//               // Report error.
//               this.triggerSevereEvent(
//                       ((nextSrcDir==null)?null:nextSrcDir.getAbsolutePath()),
//                       ((nextDestDir==null)?null:nextDestDir.getAbsolutePath()),
//                       "buildDir",
//                       "Cannot build sub directory");
            }
         }else if (item instanceof LocalFile) {
            LocalFile srcFile = (LocalFile)item;
            try {
               RepoHandler.copyFileToDest(srcFile, destDir, srcFile.getName());
            }catch(IOException e) {
               // Report error.
               this.triggerSevereEvent(srcDir.getAbsolutePath(),
                       destDir.getAbsolutePath(),
                       "copyFileToDest",
                       ExceptionUtils.getFullStackTrace(e));
            }
         }else {
            // Report error.
            this.triggerSevereEvent(srcDir.getAbsolutePath(),
                    destDir.getAbsolutePath(),
                    "buildSubDir",
                    "Unknown file type");
         }
      }
   }
}
