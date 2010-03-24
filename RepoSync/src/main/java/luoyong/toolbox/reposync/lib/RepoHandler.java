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
public class RepoHandler extends CommonHandler {

   public static LocalDir buildDir(LocalDir destDir, String name) {

      File newDir = new File(destDir.getFile().getAbsolutePath() + File.separator
              + name);
      if (newDir.mkdir()) {
         return new LocalDir(destDir.getBaseDir(), newDir);
      }else {
         return null;
      }
   }

   public static LocalFile copyFileToDest(LocalFile sourceFile,
           LocalDir destDir, String name) throws IOException {

      if (isInSubversionDir(destDir.getFile().getAbsolutePath())) {
         return null;
      }
      if (isInSourceSafeDir(sourceFile.getFile().getAbsolutePath())) {
         return null;
      }
      if (isInSubversionDir(name)) {
         return null;
      }

      File srcFile = sourceFile.getFile();
      File destFile = new File(destDir.getFile().getAbsolutePath()
              + File.separator + name);

      FileUtils.copyFile(srcFile, destFile);

      return new LocalFile(destDir.getBaseDir(), destFile);
   }

   public static List<LocalFsItem> getSrcLocalFsItems(LocalDir srcDir) {

      List<LocalFsItem> itemList = getLocalFsItems(srcDir);
      List<LocalFsItem> srcItemList = new LinkedList();
      for (LocalFsItem item : itemList) {
         if (!isInSourceSafeDir(item.getFile().getAbsolutePath())) {
            srcItemList.add(item);
         }
      }
      return srcItemList;
   }

   public static List<LocalFsItem> getDestLocalFsItems(LocalDir destDir) {

      List<LocalFsItem> itemList = getLocalFsItems(destDir);
      List<LocalFsItem> destItemList = new LinkedList();
      for (LocalFsItem item : itemList) {
         if (!isInSubversionDir(item.getFile().getAbsolutePath())) {
            destItemList.add(item);
         }
      }
      return destItemList;
   }

   private static List<LocalFsItem> getLocalFsItems(LocalDir dir) {
      List<LocalFsItem> itemList = new LinkedList<LocalFsItem>();
      File[] fileList = dir.getFile().listFiles();
      for (File fileItem : fileList) {
         if (fileItem.isFile()) {
            itemList.add(new LocalFile(dir.getBaseDir(), fileItem));
         }else if (fileItem.isDirectory()) {
            itemList.add(new LocalDir(dir.getBaseDir(), fileItem));
         }
      }
      return itemList;
   }

   public static boolean deleteDestFile(LocalFile destFile) {
      if (isInSubversionDir(destFile.getFile().getAbsolutePath())) {
         return false;
      }
      return destFile.getFile().delete();
   }

   public static void deleteDestDir(LocalDir destDir) throws IOException {
      if (isInSubversionDir(destDir.getFile().getAbsolutePath())) {
         return;
      }
      FileUtils.deleteDirectory(destDir.getFile());
   }

   public static LocalFsItem getSrcLocalFsItemByName(
           LocalDir srcDir, String name) {
      
      File[] fileList = srcDir.getFile().listFiles();
      for (File fileItem : fileList) {

         if (fileItem.getName().equals(name)) {

            if (!isInSourceSafeDir(fileItem.getAbsolutePath())) {

               if (fileItem.isFile()) {
                  return (new LocalFile(srcDir.getBaseDir(), fileItem));
               }else if (fileItem.isDirectory()) {
                  return (new LocalDir(srcDir.getBaseDir(), fileItem));
               }
            }
         }
      }
      return null;
   }

   private static boolean isInSubversionDir(String path) {
      if (path.matches("^.*?" + escapeBackSlash(File.separator) + "\\.svn"
               + escapeBackSlash(File.separator) + ".?$")
              || path.matches("^.*?" + escapeBackSlash(File.separator) + "\\.svn$")
              || path.matches("^\\.svn$")) {
         
         return true;
      }else {
         return false;
      }
   }

   private static String escapeBackSlash(String inputString) {
      return inputString.replace("\\", "\\\\");
   }

   private static boolean isInSourceSafeDir(String path) {
      return false;
   }
}
