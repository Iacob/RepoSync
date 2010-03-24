package luoyong.toolbox.reposync.lib;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class RepoSyncEvent {

   public static String EVENT_INFO = "event_normal";
   public static String EVENT_WARNING = "event_warning";
   public static String EVENT_SEVERE = "event_severe";

   private String eventType = null;
   private String sourceFile = null;
   private String destFile = null;
   private String action = null;
   private String message = null;

   public RepoSyncEvent(String eventType, String sourceFile, String destFile,
           String action, String message) {

      this.eventType = eventType;
      this.sourceFile = sourceFile;
      this.destFile = destFile;
      this.action = action;
      this.message = message;
   }

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   public String getDestFile() {
      return destFile;
   }

   public void setDestFile(String destFile) {
      this.destFile = destFile;
   }

   public String getEventType() {
      return eventType;
   }

   public void setEventType(String eventType) {
      this.eventType = eventType;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getSourceFile() {
      return sourceFile;
   }

   public void setSourceFile(String sourceFile) {
      this.sourceFile = sourceFile;
   }
}
